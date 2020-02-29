package cn.BinGCU;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 	这里使用的mysql驱动包为mysql8.0以上版本
 * 
 * 	驱动包8.0版本以下：
 * 	DB_URL:	<DB的URL>
 * 
 * */


/*
 * 	驱动包8.0版本以上：
 * 	DB_URL:	<DB的URL>?useSSL=false&serverTimezone=<时区>
 * 
 * 	1、com.mysql.jdbc.Driver需要更换为com.mysql.cj.jdbc.Driver。
 * 	2、MySQL 8.0以上版本不需要建立SSL连接，所以需要显示关闭SSL连接
 * 	3、需要设置中央标准时间CST（Central Standard Time）
 * 
 * */

public class practice_JDBC {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";		//mysql驱动包路径
	static final String DB_URL = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";	//DB的URL
	static final String DB_USERNAME = "root";
	static final String DB_PASSWORD = "brilliant";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//注册JDBC驱动(其实就是将Driver类从文件中加载进内存)
		Class.forName(JDBC_DRIVER);
		
		//打开链接
		System.out.println("数据库连接中...");
		Connection conn  = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		
		//执行查询
		System.out.println("实例化Statement对象");
		Statement st = conn.createStatement();
		
		//sql查询
		System.out.println("查询中...");
		String sqlQuery = "SELECT department_id,department_name FROM dept2 WHERE department_id>100";
		ResultSet rs = st.executeQuery(sqlQuery);
		
		//张开结果集数据库
		while(rs.next()) {
			//通过字段检索
			int department_id = rs.getInt("department_id");
			String department_name = rs.getString("department_name");
		
			//打印当前行（记录）的数据
			System.out.println("department_id:\t"+department_id);
			System.out.println("department_name:\t"+department_name);
		}
		
		//关闭ResultSet、Statement、Connection
		rs.close();
		st.close();
		conn.close();
		
		System.out.println("退出");
	}
}
