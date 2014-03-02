package com.eraclouds.Mysql2SqliteCovertor;

import java.util.HashMap;

public class DataTypeConvertorMysql2Sqlite implements DataTypeConvertor{
	static HashMap<String,String> mysqlTypeMap=new HashMap<String,String>(){{
		this.put("int", "Integer");
		this.put("tinyint", "Integer");
		this.put("smallint", "Integer");
		this.put("bigint", "Integer");
		this.put("decimal", "Integer");
		this.put("char", "TEXT");
		this.put("varchar", "TEXT");
		this.put("text", "TEXT");
		this.put("blob", "TEXT");
		this.put("float", "REAL");
		this.put("double", "REAL");
		this.put("date", "TEXT");
		this.put("datetime", "TEXT");
		this.put("timestamp", "TEXT");
	}};
	/**
	 * convert data type between databases
	 * 
	 * @author yifangyou
	 * @version xsb 2014-03-02 13:16:00
	 */
	public String getType(String aType){
		return mysqlTypeMap.get(aType);
	}
}
