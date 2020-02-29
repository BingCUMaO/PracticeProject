package OrmMiddleware;

import DataAnnotion.PersistentTable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * ͨ��XML����Class�ļ���
 * ����Reflect��ȡ��ͬ������ݣ��ѷ����Ӧ����
 */
public class Core {
    private static ClassHandler handler;

    static {

        try {
            //1����ȡSAX��������
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2����ȡ������parser
            SAXParser parser = factory.newSAXParser();
            //3����д������handler

            //4��ʹ�ñ�д�õ�handler
            handler = new ClassHandler();
            //5������(����xml�ļ�λ�ú�handler)
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("Config/configClass.xml"), handler);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �־û�
     * �������������Ϣ��������ѡ��JDBC��д�����ݿ�
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
             * controller �����󣬻�ȱ��table name��Ϣ
             * ��ʹ�÷����ȡ��Ӧ�������е�Annotation��Ϣ
             * Ȼ�󽫸�Annotation��ӵ�controller�ж�ӦClassInfo
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

            //�����־û�
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
