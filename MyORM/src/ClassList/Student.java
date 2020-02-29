package ClassList;

import DataAnnotion.PersistentTable;
import DataAnnotion.PersistentField;
import DataAnnotion.PersistentMethod;

@PersistentTable("students_table")
public class Student {
    @PersistentField(fieldName = "id", type = "varchar", size = 10)
    private String id;
    @PersistentField(fieldName = "name", type = "varchar", size = 10)
    private String name;
    @PersistentField(fieldName = "age", type = "int", size = 3)
    private int age;

    @PersistentMethod(methodName = "getId", type = "varchar", size = 12)
    public String getId() {
        return id;
    }

    @PersistentMethod(methodName = "setId", type = "varchar", size = 10)
    public void setId(String id) {
        this.id = id;
    }

    @PersistentMethod(methodName = "getName", type = "varchar", size = 10)
    public String getName() {
        return name;
    }

    @PersistentMethod(methodName = "setName", type = "varchar", size = 10)
    public void setName(String name) {
        this.name = name;
    }

    @PersistentMethod(methodName = "getAge", type = "varchar", size = 10)
    public int getAge() {
        return age;
    }

    @PersistentMethod(methodName = "setAge", type = "varchar", size = 10)
    public void setAge(int age) {
        if (age > 0 && age < 130)
            this.age = age;
    }
}
