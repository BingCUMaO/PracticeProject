package OrmMiddleware;

import DataAnnotion.PersistentField;
import DataAnnotion.PersistentMethod;
import DataAnnotion.PersistentTable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

public class JdbcDriver_mysql {
    private String className;
    private String tableName;
    private String jdbc_driver;
    private String database_url;
    private String user;
    private String pwd;

    private ArrayList<Field>fields;
    private ArrayList<Method>methods;

    public JdbcDriver_mysql(ClassInfo classinfos,DBMSInfo dbmsinfos){
        fields = new ArrayList<>();
        methods = new ArrayList<>();

        className = classinfos.getClassName();
        tableName = classinfos.getTableName();

        jdbc_driver = dbmsinfos.getJdbc_driver();
        database_url = dbmsinfos.getDatabase_url();
        user = dbmsinfos.getUser();
        pwd = dbmsinfos.getPwd();
    }

    //这里负责反射类在表中对应的字段，然后一个一个判断
    // 若存在则添加至表，若不存在则根据表名创建表再添加至表
    public boolean putToDatabase() throws Exception {


        //1、获取拥有Persistent标识的Method、Field到列表中
        Class<?> _class = Class.forName(className);
        if(_class.isAnnotationPresent(PersistentTable.class)){
            Field[] fs = _class.getDeclaredFields();
            Method[] ms = _class.getDeclaredMethods();

            for(Field f:fs){
                f.setAccessible(true);
                this.fields.add(f);
            }
            for(Method m:ms){
                m.setAccessible(true);
                this.methods.add(m);
            }
        }

        Connection connection = linkDatabase();
        Statement statement = connection.createStatement();
        //2、若不存在该表，则创建表
        createTable(connection);
        //3、判断是否存在，若否则插入
//        insertFields(statement);
        insertMethods(connection);


        connection.close();
        System.out.println("End");

        return false;
    }

    private Connection linkDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(jdbc_driver);
        System.out.println("Linking database...");
        return DriverManager.getConnection(database_url, user, pwd);
    }

    private void createTable(Connection connection) throws SQLException {
        System.out.println("Querying...");
        System.out.println("tableName="+tableName);
        String sql_createClassTable =
                "CREATE TABLE IF NOT EXISTS "+tableName+"("
                        +"id int(10) NOT NULL AUTO_INCREMENT,"
                        +"type varchar(50) NOT NULL, "
                        +"name varchar(50) NOT NULL,"
                        +" field_or_method varchar(7) NOT NULL, "
                        +"parameter_count int(2), "

                        +"PRIMARY KEY(id),"
                        +"UNIQUE KEY(field_or_method,name)"
                        +")ENGINE=INNODB DEFAULT CHARSET =utf8;";

        String sql_createaArgumentsTable =
                "CREATE TABLE IF NOT EXISTS arguments("
                        +"param_id int(10) NOT NULL AUTO_INCREMENT,"
                        +"param_type varchar(50) NOT NULL,"
                        +"class_table varchar(50) NOT NULL,"
                        +"method_name varchar(20) NOT NULL ,"

                        +"PRIMARY KEY(param_id)"
                        +")ENGINE=INNODB DEFAULT CHARSET = utf8;";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql_createClassTable);
        statement.executeUpdate(sql_createaArgumentsTable);
    }

    private void insertFields(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();

        String type = null;
        String name = null;


        for(Field f:fields){
            type = f.getType().toString();
            name = f.getName();

            String sql_insert =
                    "INSERT IGNORE INTO "+tableName+
                            " SET type=\""+type+"\",name=\""+name+"\","
                            +"field_or_method=\"Field\",parameter_count=NULL;";

            int print = statement.executeUpdate(sql_insert);
            System.out.println("print:-->"+print);
        }

    }

    private void insertMethods(Connection connection) throws SQLException {
        boolean areRepeated = false;
        Statement statement = connection.createStatement();

        String type = null;
        String name = null;
        int parameter_count = 0;

        String param_type = null;
        String class_table = tableName;
        String method_name  =null;




        for(Method m: methods){
            m.setAccessible(true);

            type = m.getReturnType().toString();
            method_name = m.getName();
            parameter_count = m.getParameterCount();

            //插入方法到表中
            String sql_insertToClassTable =
                    "INSERT IGNORE INTO "+tableName+
                            " SET type=\""+type+"\",name=\""+method_name+"\","
                            +"field_or_method=\"Method\",parameter_count="+parameter_count+";";


            //插入参数到第二个表中
            //这里直接通过executeUpdate()的返回结果来判断是否继续第二个表的插入
            if(0!=statement.executeUpdate(sql_insertToClassTable)) {
                Class<?>[] paramTypes = m.getParameterTypes();

                for (Class<?> pt : paramTypes) {
                    param_type = pt.toString();


                    String sql_insertToArgumentsTable =
                            "INSERT IGNORE INTO arguments " +
                                    "SET param_type=\"" + param_type + "\",class_table=\"" + class_table + "\","
                                    + "method_name=\"" + method_name + "\";";

                    statement.executeUpdate(sql_insertToArgumentsTable);
                }

            }

        }





    }

}











