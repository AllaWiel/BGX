package net.bgx.bgxnetwork.dao;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * ������ DAO � ����������� �� ������������ ���� ������, ������� ������� ��������� ���������
 * <b>d3.database.vendor</b>.
 * <p/>
 * ��� ������������� ��������� �������� �� ����� � ��������� ������.
 * ��� ������� ����������� � ������������ �� ��������� ������:
 * <pre><code><b>aPackageName</b>/dao_<b>d3.database.vendor</b>.properties</code></pre>
 * <p/>
 * ���� ������� �������� ���� <code>����=��������</code>, ��� ������ �������� ������ ���
 * ���������� DAO, � ��������� - ������ ��� ������, ������������ ��������� ���������.
 */
public class DAOFactory {
    private static final Map<String, DAOFactory> _factories = new HashMap<String, DAOFactory>();
    private Properties _map = new Properties();
    private final Map<String, AbstractDAO> _templates = new HashMap<String, AbstractDAO>();

    private DAOFactory(String aPackageName, ClassLoader aClassLoader) {
        String vendor = System.getProperty("net.bgx.bgxnetwork.toolkit.db.DAOFactory.VENDOR");
        if (vendor == null) throw new Error("database vendor is not specified");
        String resource = aPackageName.replace('.', '/') + "/dao_" + vendor + ".properties";
        InputStream stream = null;
        try {
            stream = aClassLoader.getResourceAsStream(resource);
            _map.load(stream);
        } catch (Exception e) {
            throw new Error("can't load DAOFactory configuration [" + resource + "]");
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException ignore) {
                }
        }
    }

    public static DAOFactory getInstance(String aPackageName, ClassLoader aClassLoader) {
        DAOFactory factory = _factories.get(aPackageName);
        if (factory == null) {
            synchronized (_factories) {
                factory = new DAOFactory(aPackageName, aClassLoader);
                _factories.put(aPackageName, factory);
            }
        }
        return factory;

    }

    /**
     * ������ ��������� ������, ������������ ��������� ���������.
     * � ��������� ��������� ������ ����
     * net.bgx.bgxnetwork.dao.TEMPLATE_CACHE_DISABLED= true
     * �.�. ��� �������� ����� ������ ��� ����� ����������
     * @param aClass ��������� DAO
     * @return ��������� ������, ������������ ��������� ���������.
     */
    public Object getDAO(Class aClass) {
        return createTemplate(aClass);
    }

    private AbstractDAO createTemplate(Class aClass) {
        String className = _map.getProperty(aClass.getName());
        if (className == null)
            throw new Error("mapping for DAO [" + className + "] is not defined");
        try {
            Class<?> daoClass = aClass.getClassLoader().loadClass(className);
            return (AbstractDAO)daoClass.newInstance();
        } catch (Exception e) {
            throw new Error("can't create DAO [" + className + "]", e);
        }
    }
}
