package com.eraclouds.Mysql2SqliteCovertor;


public class ColumnBean {
	String mysqlName=null;//在表中的名字
	String mysqlType=null;
	String sqliteName=null;//在表中的名字
	String sqliteType=null;
	/**
	 * 骆驼法则 （camel）命名法 
	 * 第一个单字以小写字母�?��；第二个单字的首字母大写或每�?��单字的首字母都采用大写字�?
	 * 例如camelCase
	 * */
	String camelName=null; //例如camelCase 
	
	/**
	 * 帕斯卡（pascal）命名法 
	 * 骆驼命名法是首字母小写，而帕斯卡命名法是首字母大�?
	 * 例如 PascalName
	 * */
	String pascalName=null;
	
	/**
	 * 下划�?命名�?,c语言变量命名
	 * 单词之间�?_"分隔,全小�?
	 * 例如 search_site_log
	 * */
	String cVarName=null;
	
	public String getMysqlName() {
		return mysqlName;
	}
	public void setMysqlName(String name) {
		this.mysqlName=name;
		this.pascalName=GlobalUtil.toPascalName(name);
		this.camelName=GlobalUtil.toCamelName(name);
		this.sqliteName=this.camelName;
	}
	
	public String getCamelName() {
		return camelName;
	}
	public void setCamelName(String camelName) {
		this.camelName = camelName;
	}
	public String getPascalName() {
		return pascalName;
	}
	public void setPascalName(String pascalName) {
		this.pascalName = pascalName;
	}
	public String getCVarName() {
		return cVarName;
	}
	public void setCVarName(String varName) {
		cVarName = varName;
	}
	public String getMysqlType() {
		return mysqlType;
	}
	public void setMysqlType(String mysqlType) {
		this.mysqlType = mysqlType;
	}
	public String getSqliteName() {
		return sqliteName;
	}
	public void setSqliteName(String sqliteName) {
		this.sqliteName = sqliteName;
	}
	public String getSqliteType() {
		return sqliteType;
	}
	public void setSqliteType(String sqliteType) {
		this.sqliteType = sqliteType;
	}
	public String getcVarName() {
		return cVarName;
	}
	public void setcVarName(String cVarName) {
		this.cVarName = cVarName;
	}
}
