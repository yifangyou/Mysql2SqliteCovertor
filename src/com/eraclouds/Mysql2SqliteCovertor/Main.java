package com.eraclouds.Mysql2SqliteCovertor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args){
		String DBName="xsb";
		String url="jdbc:mysql://192.168.60.5:3306/xsb?user="+DBName+"&password=text";
		try {
			Connection conn = DriverManager.getConnection(url);
			Mysql2SqliteCovertor mysql2SqliteCovertor=new Mysql2SqliteCovertor(conn,DBName);
			ArrayList<String>mysqlTableNames=new ArrayList<String>(){{
				this.add("business_insurance");
				this.add("user");
				this.add("car_type");
			}};
			
			mysql2SqliteCovertor.covertMysql2Sqlite(mysqlTableNames, "D:/test.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
