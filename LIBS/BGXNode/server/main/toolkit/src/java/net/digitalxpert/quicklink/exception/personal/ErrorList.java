package net.bgx.bgxnetwork.exception.personal;
public final class ErrorList{
    public static final String MODULE_CODE = "PersonalService";
    // 00100 - EJB
    public static final String EJB_CREATE_EXCEPTION = "00101";
    public static final String EJB_REMOVE_EXCEPTION = "00102";
    public static final String EJB_UPDATE_EXCEPTION = "00103";
    public static final String EJB_ENTITY_IS_EXIST = "00104";
    public static final String EJB_ILLEGAL_ARGUMENT = "00105";
    // 00200 - Data access(SQL)
    public static final String DATA_MANAGER_IS_CLOSED = "00201";
    public static final String DATA_CANNOT_GET_SINGLE_RESULT = "00202";
    public static final String DATA_CANNOT_FIND_DATA = "00203";
    //00300 - Data access(SQL)
    public static final String BUSINES_CANNOT_DELETE_PERSON = "00301";
}
