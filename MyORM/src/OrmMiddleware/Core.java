package OrmMiddleware;

import DataAnnotion.PersistentTable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * 通过XML配置Class文件名
 * 再用Reflect获取不同类的数据，已方便对应建表
 */
public class Core {
    private static ClassHandler handler;

    static {

        try {
            //1、获取SAX解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2、获取解析器parser
            SAXParser parser = factory.newSAXParser();
            //3、编写处理器handler

            //4、使用编写好的handler
            handler = new ClassHandler();
            //5、解析(丢入xml文件位置和handler)
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("Config/configClass.xml"), handler);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 持久化
     * 根据所保存的信息，来灵活的选择JDBC来写入数据库
     */
    public boolean persist() throws ClassNotFoundException {
        InfoController controller = new InfoController(handler.getClassInfoList(), handler.getDBMSInfoList());
        boolean areRunning = true;

        if ( !(controller.getClassInfoListLength() > 0) ){
            areRunning = false;
            return false;
        }

        while (areRunning) {
            /**
             * controller 创建后，还缺少table name信息
             * 需使用反射读取对应的类名中的Annotation信息
             * 然后将该Annotation添加到controller中对应ClassInfo
             */
            Class<?> _class = Class.forName(controller.getNonius());
            if(_class.isAnnotationPresent(PersistentTable.class)){
                PersistentTable table = _class.getAnnotation(PersistentTable.class);
                controller.setClassInfo(controller.getNonius(),table.value());
            }




            String dbms = controller.getDBMSInfo(controller.getNonius()).getDbms_name();

            if (dbms.equals("mysql")) {

                JdbcDriver_mysql j =
                        new JdbcDriver_mysql(
                                controller.getClassInfo(controller.getNonius()),
                                controller.getDBMSInfo(controller.getNonius())
                        );
                try {
                    j.putToDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //结束持久化
            if(!controller.areNextClassInfo()){
                areRunning = false;
                controller.resetClassInfoNonius();
                break;
            }

            controller.nextClassInfo();
        }

        return true;
    }


}
