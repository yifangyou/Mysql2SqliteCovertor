package com.eraclouds.Mysql2SqliteCovertor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;





public class Mysql2SqliteCovertor {
	Connection conn;
	String mysqlDBName;
	public Mysql2SqliteCovertor(Connection conn,String mysqlDBName){
		this.conn=conn;
		this.mysqlDBName=mysqlDBName;
	}
	/**
	 * convert mysql table to sqlite table
	 * import mysql data to sqlite table
	 * 
	 * @author yifangyou
	 * @version xsb 2014-03-02 13:16:00
	 */
	public boolean covertMysql2Sqlite(ArrayList<String>mysqlTableNames,String sqliteDbPath){
		Connection sqliteConn =null;
	    try {
	    	 try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			sqliteConn =
			  DriverManager.getConnection("jdbc:sqlite:"+sqliteDbPath);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		for(String mysqlTableName:mysqlTableNames){
			String sqliteTableName=GlobalUtil.toCamelName(mysqlTableName);
			ArrayList<ColumnBean>  columnBeans=getColumnsByTableName(mysqlTableName);
			if(columnBeans.size()==0){
				System.out.println("skip "+mysqlTableName);
			}
			if(createSqliteTable(sqliteConn,sqliteTableName,columnBeans)){
				insertSqliteTable(mysqlTableName,sqliteConn,sqliteTableName,columnBeans);
			}
			
		}
		try {
			if(sqliteConn !=null)
			sqliteConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * drop sqlite table's data and convert mysql table to sqlite table
	 * 
	 * @author yifangyou
	 * @version xsb 2014-03-02 13:16:00
	 */
	public boolean createSqliteTable(Connection sqliteConn,String sqliteTableName,ArrayList<ColumnBean> columnBeans){
		if(columnBeans.size()==0){
			return false;
		}
		 Statement stat =null;
		 try{
			 
			stat=sqliteConn.createStatement();
			stat.executeUpdate("drop table if exists "+sqliteTableName+";");
			   
			StringBuffer sql=new StringBuffer();
			sql.append("CREATE table ").append(sqliteTableName).append(" (\n");
			for(ColumnBean columnBean:columnBeans){
				sql.append(columnBean.getSqliteName()).append(" ").append(columnBean.getSqliteType()).append(",\n");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(");\n");
			System.out.println(sql.toString());
		    stat.executeUpdate(sql.toString());
		    stat.close();
		    return true;
		 }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		 }
	}
	
	/**
	 * import mysql data to sqlite table
	 * 
	 * @author yifangyou
	 * @version xsb 2014-03-02 13:16:00
	 */
	public boolean insertSqliteTable(String mysqlTableName,Connection sqliteConn,String sqliteTableName,ArrayList<ColumnBean> columnBeans) {
		if(columnBeans.size()==0){
			return false;
		}
		StringBuffer sql=new StringBuffer();
		StringBuffer sqlvalues=new StringBuffer();
		sql.append("INSERT INTO ").append(sqliteTableName).append(" (");
		for(ColumnBean columnBean:columnBeans){
			sql.append(columnBean.getSqliteName()).append(",");
			sqlvalues.append("?,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sqlvalues.deleteCharAt(sqlvalues.lastIndexOf(","));
		sql.append(") values(").append(sqlvalues).append(")");
		
		
		 ResultSet rs = null;
		 PreparedStatement mysqlPst  = DatabaseUtil.getPrepStatement(conn,
				"select * from "+mysqlTableName);
		 PreparedStatement sqlitePst  = DatabaseUtil.getPrepStatement(sqliteConn,
				 sql.toString());
			try {
				rs = mysqlPst.executeQuery();
				while (rs.next()) {
					int i=1;
					for(ColumnBean columnBean:columnBeans){
						sqlitePst.setString(i++, rs.getString(columnBean.mysqlName));
					}
					sqlitePst.executeUpdate();
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				 DatabaseUtil.closeResultSet(rs);
				 DatabaseUtil.closePrepStatement(mysqlPst);
				 DatabaseUtil.closePrepStatement(sqlitePst);
			}		
	}
	/**
	 * get a  mysql table's columns
	 * 
	 * @author yifangyou
	 * @version xsb 2014-03-02 13:16:00
	 */
	 public ArrayList<ColumnBean> getColumnsByTableName(String mysqlTableName) {
		 ArrayList<ColumnBean> columnBeans=new ArrayList<ColumnBean>();
		 ResultSet rs = null;
		 PreparedStatement pst  = DatabaseUtil.getPrepStatement(conn,
				"select distinct COLUMN_NAME,DATA_TYPE from information_schema.COLUMNS  where TABLE_SCHEMA=? and TABLE_NAME=?");
			try {
				pst.setString(1, this.mysqlDBName);
				pst.setString(2, mysqlTableName);
				rs = pst.executeQuery();
				DataTypeConvertor dataTypeConvertorMysql=new DataTypeConvertorMysql2Sqlite();
				
				while (rs.next()) {
					String columnName=rs.getString("COLUMN_NAME");
					String columnType=rs.getString("DATA_TYPE");
					ColumnBean columnBean=new ColumnBean();
					columnBean.setMysqlName(columnName);
					columnBean.setMysqlType(columnType);
					columnBean.setSqliteType(dataTypeConvertorMysql.getType(columnType));
					columnBeans.add(columnBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				 DatabaseUtil.closeResultSet(rs);
				 DatabaseUtil.closePrepStatement(pst);
			}
			return columnBeans;
		}
}
