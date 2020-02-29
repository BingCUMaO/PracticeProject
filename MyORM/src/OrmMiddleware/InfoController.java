package OrmMiddleware;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoController {
    /**
     * Map class name and table name.
     *
     * @param <K> class name
     * @param <V> table name
     */
    private HashMap<String,ClassInfo>CTMap;

    /**
     * Map class name and information of database management system.
     *
     * @param <K> class name
     * @param <V> information of database management system
     */
    private HashMap<String,DBMSInfo>CDMap;

    private ArrayList<ClassInfo>ClassInfoList;
    private ClassInfo _class;

    /**
     * Map lists for controling all information.
     * @param ClassInfoList
     * @param DBMSInfoList
     */
    public InfoController(ArrayList<ClassInfo>ClassInfoList,ArrayList<DBMSInfo>DBMSInfoList){
        CTMap = new HashMap<>();
        CDMap = new HashMap<>();

        this.ClassInfoList = ClassInfoList;

        this.initializeMaps(ClassInfoList, DBMSInfoList);
    }

    private void initializeMaps(ArrayList<ClassInfo>ClassInfoList,ArrayList<DBMSInfo>DBMSInfoList){
        int num = ClassInfoList.size();
        for(int i = 0;i<num;i++){
            ClassInfo ci = ClassInfoList.get(i);
            DBMSInfo di = DBMSInfoList.get(i);

            CTMap.put(ci.getClassName(), ci);
            CDMap.put(ci.getClassName(), di);
        }

        resetClassInfoNonius();
    }

    public String getTableName(String className){
        return CTMap.get(className).getTableName();
    }

    public DBMSInfo getDBMSInfo(String className){
        return CDMap.get(className);
    }

    public int getClassInfoListLength(){
        return ClassInfoList.size();
    }

    public void setClassInfo(String index,String value){
        if(CTMap.containsKey(index)){
            CTMap.get(index).setTableName(value);
        }
    }

    public ClassInfo getClassInfo(String className){
        return CTMap.get(className);
    }


    public void nextClassInfo(){
        if(areNextClassInfo()){
            _class = ClassInfoList.get(ClassInfoList.indexOf(_class) + 1);
        }
    }


    public boolean areNextClassInfo(){
        return ClassInfoList.indexOf(_class)<ClassInfoList.size()-1;
    }


    public void resetClassInfoNonius(){
        _class = ClassInfoList.get(0);
    }

    public String getNonius(){
        return _class.getClassName();
    }


}
