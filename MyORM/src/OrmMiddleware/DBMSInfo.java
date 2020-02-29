package OrmMiddleware;

public class DBMSInfo {
    private String dbms_name;
    private Float version;
    private String jdbc_driver ;
    private String database_url;
    private String user;
    private String pwd;

    public String getDbms_name() {
        return dbms_name;
    }

    public void setDbms_name(String dbms_name) {
        this.dbms_name = dbms_name;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public String getJdbc_driver() {
        return jdbc_driver;
    }

    public void setJdbc_driver(String jdbc_driver) {
        this.jdbc_driver = jdbc_driver;
    }

    public String getDatabase_url() {
        return database_url;
    }

    public void setDatabase_url(String database_url) {
        this.database_url = database_url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
