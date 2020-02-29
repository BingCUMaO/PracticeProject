package ClassList;

import DataAnnotion.PersistentField;
import DataAnnotion.PersistentMethod;
import DataAnnotion.PersistentTable;

@PersistentTable("employees_table")
public class Employee {
    @PersistentField(fieldName = "id", type = "varchar", size = 10)
    private String id;
    @PersistentField(fieldName = "department", type = "varchar", size = 20)
    private String department;
    @PersistentField(fieldName = "jobTitle", type = "varchar", size = 20)
    private String jobTitble;

    @PersistentField(fieldName = "salary", type = "int", size = 10)
    private int salary;

    @PersistentMethod(methodName = "getId",type = "varchar",size = 10)
    public String getId() {
        return id;
    }

    @PersistentMethod(methodName = "setId",type = "varchar",size = 10)
    public void setId(String id) {
        this.id = id;
    }

    @PersistentMethod(methodName = "getDepartment",type = "varchar",size = 10)
    public String getDepartment() {
        return department;
    }

    @PersistentMethod(methodName = "setDepartment",type = "varchar",size = 10)
    public void setDepartment(String department) {
        this.department = department;
    }

    @PersistentMethod(methodName = "getJobTitble",type = "varchar",size = 10)
    public String getJobTitble() {
        return jobTitble;
    }

    @PersistentMethod(methodName = "methodName",type = "varchar",size = 10)
    public void setJobTitble(String jobTitble) {
        this.jobTitble = jobTitble;
    }

    @PersistentMethod(methodName = "getSalary",type = "varchar",size = 10)
    public int getSalary() {
        return salary;
    }

    @PersistentMethod(methodName = "setSalary",type = "varchar",size = 10)
    public void setSalary(int salary) {
        this.salary = salary;
    }
}
