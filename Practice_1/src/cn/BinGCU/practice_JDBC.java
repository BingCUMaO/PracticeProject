package cn.BinGCU;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 	����ʹ�õ�mysql������Ϊmysql8.0���ϰ汾
 * 
 * 	������8.0�汾���£�
 * 	DB_URL:	<DB��URL>
 * 
 * */


/*
 * 	������8.0�汾���ϣ�
 * 	DB_URL:	<DB��URL>?useSSL=false&serverTimezone=<ʱ��>
 * 
 * 	1��com.mysql.jdbc.Driver��Ҫ����Ϊcom.mysql.cj.jdbc.Driver��
 * 	2��MySQL 8.0���ϰ汾����Ҫ����SSL���ӣ�������Ҫ��ʾ�ر�SSL����
 * 	3����Ҫ���������׼ʱ��CST��Central Standard Time��
 * 
 * */

public class practice_JDBC {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";		//mysql������·��
	static final String DB_URL = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";	//DB��URL
	static final String DB_USERNAME = "root";
	static final String DB_PASSWORD = "brilliant";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//ע��JDBC����(��ʵ���ǽ�Driver����ļ��м��ؽ��ڴ�)
		Class.forName(JDBC_DRIVER);
		
		//������
		System.out.println("���ݿ�������...");
		Connection conn  = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		
		//ִ�в�ѯ
		System.out.println("ʵ����Statement����");
		Statement st = conn.createStatement();
		
		//sql��ѯ
		System.out.println("��ѯ��...");
		String sqlQuery = "SELECT department_id,department_name FROM dept2 WHERE department_id>100";
		ResultSet rs = st.executeQuery(sqlQuery);
		
		//�ſ���������ݿ�
		while(rs.next()) {
			//ͨ���ֶμ���
			int department_id = rs.getInt("department_id");
			String department_name = rs.getString("department_name");
		
			//��ӡ��ǰ�У���¼��������
			System.out.println("department_id:\t"+department_id);
			System.out.println("department_name:\t"+department_name);
		}
		
		//�ر�ResultSet��Statement��Connection
		rs.close();
		st.close();
		conn.close();
		
		System.out.println("�˳�");
	}
}
