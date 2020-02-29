package OrmMiddleware;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * To hanlder the information reading from xml file by SAXParse.
 *
 * @author BinGCU
 */
public class ClassHandler extends DefaultHandler {
    /**
     * ClassInfoList is a docker to manage class names and table names.
     * DBMSInfoList is a docker to manage dbms names and version information.
     */
    private ArrayList<ClassInfo> ClassInfoList;
    private ArrayList<DBMSInfo> DBMSInfoList;

    /**
     * To temporarily save the class information when it started element
     * and ended element.
     */
    private ClassInfo _class;

    /**
     * To temporarily save the current class  where it to be persisted
     * when it started element and ended element.
     */
    private DBMSInfo _dbms;

    /**
     * A temprory field to save current xml label.
     */
    private String tag;

    /**
     * Get list about class information.
     *
     * @return
     */
    public ArrayList<ClassInfo> getClassInfoList() {
        return ClassInfoList;
    }

    /**
     * Get list about DBMS information.
     *
     * @return
     */
    public ArrayList<DBMSInfo> getDBMSInfoList() {
        return DBMSInfoList;
    }

    /**
     * Override the method startDocument() for initialize the controller
     * of class name and table names,which is a private field.
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        ClassInfoList = new ArrayList<>();
        DBMSInfoList = new ArrayList<>();
    }

    /**
     * Override the method for adding ClassInfo and DBMSInfo to lists.
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        tag = qName;
        if (null != tag) {
            if (tag.equals("dbms")) {
                _dbms = new DBMSInfo();
            } else if (tag.equals("class")) {
                _class = new ClassInfo();
            } //else if(tag.equals("orm")){
//
//            }else {
//
//                System.out.println("start--throw:-->tag=="+tag);
//
//                //throw NotFoundXmlLabelException
//                try {
//                    throw new NotFoundXmlLabelException();
//                } catch (NotFoundXmlLabelException e) {
//                    e.printStackTrace();
//                }
//            }
        }


    }


    /**
     * 用SAX事件解析的时候有个坑要注意
     * 就是当XML文件中的内容包含"<></>"和"&(字面值：&amp;)"时，
     * 会调用3次相应的输出函数
     */
    private StringBuilder debris = new StringBuilder();

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String config = new String(ch, start, length);

        if (tag != null) {
            if (config.length() > 0) {
                switch (tag) {
                    case "dbms-name":
                        _dbms.setDbms_name(config);
                        break;
                    case "jdbc-driver":
                        _dbms.setJdbc_driver(config);
                        break;
                    case "database-url":
                        debris.append(config);
                        _dbms.setDatabase_url(debris.toString());
                        break;
                    case "user":
                        _dbms.setUser(config);
                        break;
                    case "password":
                        _dbms.setPwd(config);
                        break;
                    case "version":
                        _dbms.setVersion(Float.valueOf(config));
                        break;
                    case "class-name":
                        _class.setClassName(config);
                        break;
                    default:
                }
            }
        }
    }

    /**
     * Override the method for save the current label information from xml file.
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        tag = qName;

        if (null != tag) {
            if (tag.equals("dbms")) {
                DBMSInfoList.add(_dbms);
            } else if (tag.equals("class")) {
                ClassInfoList.add(_class);
            } //else if(tag.equals("orm")){
//
//            }else {
//                System.out.println("end--throw:-->tag=="+tag);
//                //throw NotFoundXmlLabelException
//                try {
//                    throw new NotFoundXmlLabelException();
//                } catch (NotFoundXmlLabelException e) {
//                    e.printStackTrace();
//                }
//            }
        }

        tag = null;
    }

    public int getClassInfoListLength(){
        return ClassInfoList.size();
    }

    public int getDBMSInfoListLength(){
        return DBMSInfoList.size();
    }

    public void nextClassInfo(){
        if(areNextClassInfo()){
            _class = ClassInfoList.get(ClassInfoList.indexOf(_class) + 1);
        }
    }

    public void nextDBMSInfo(){
        if(areNextDBMSInfo()){
            _dbms = DBMSInfoList.get(DBMSInfoList.indexOf(_dbms));
        }
    }

    public boolean areNextClassInfo(){
        return ClassInfoList.indexOf(_class)<=ClassInfoList.size()-1;
    }

    public boolean areNextDBMSInfo(){
        return DBMSInfoList.indexOf(_dbms)<=DBMSInfoList.size()-1;
    }

    public void resetClassInfoNonius(){
        _class = ClassInfoList.get(0);
    }

    public void resetDBMSInfoNonius(){
        _dbms = DBMSInfoList.get(0);
    }

}
