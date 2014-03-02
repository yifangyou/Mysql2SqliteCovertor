package com.eraclouds.Mysql2SqliteCovertor;


import java.text.SimpleDateFormat;
import java.util.Date;



public class GlobalUtil {
    /**
     * 时间格式转换
     */
    public static String getFormatTime(Date logtime) {
        if (null == logtime) {
            logtime = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
            time = sdf.format(logtime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return time;
    }
    /**
	 * 格式化输出,多个字段时以\t分隔
	 * 
	 * @param msgs 一个或者多个字段
	 * @return void
	 * @author yifangyou
	 * @since 2011-08-09 09:35:00
	 */
    public static void log(Object ...msgs){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	StringBuffer logmsg=new StringBuffer(sdf.format(new Date()));
    	for(int i=0,maxlen=msgs.length;i<maxlen;i++){
    		logmsg.append("\t").append(msgs[i].toString());
    	}
    	System.out.println(logmsg);
    }
   
    /**
	 * 把 user_role 转化为UserRole
	 * 
	 * @param name user_role之类的表名或者列名
	 * @return UserRole java标准命名
	 * @author yifangyou
	 * @since 2011-08-09 09:35:00
	 */
//    public static String covertTableName(String name){
//    	StringBuffer cname=new StringBuffer();
//    	String[] namebreaks=name.split("[_|-]");
//    	for(String namebreak:namebreaks){
//    		cname.append(toFirstUpperCase(new StringBuffer(namebreak)));
//    	}
//    	return cname.toString();
//    }
//    
    /* 把 user_role 转化为userRole
	 * 
	 * @param name user_role之类的表名或者列名
	 * @return userRole java标准命名
	 * @author yifangyou
	 * @since 2011-08-09 09:35:00
	 */
//   public static String covertColumnName(String name){
//   	StringBuffer cname=new StringBuffer();
//   	String[] namebreaks=name.split("[_|-]");
//   	for(String namebreak:namebreaks){
//   		cname.append(toPascalName(new StringBuffer(namebreak)));
//   	}
//   	return toPascalName(cname).toString();
//   }
   
   /**
	* 把c语言变量命名法转为骆驼法则 （camel）命名法
	* search_site_log -> searchSiteLog
	* SearchSiteLog -> searchSiteLog
	* 骆驼法则 （camel）命名法
	* 第一个单字以小写字母开始；第二个单字的首字母大写或每一个单字的首字母都采用大写字母
	* 例如 pascalName
	* */
   public static StringBuffer toCamelName(StringBuffer name){
	   StringBuffer res=new StringBuffer();
	   for(int i=0;i<name.length();i++){
		   char c=name.charAt(i);
		   if(Character.isDigit(c)){
			   res.append(c);
		   }else if(Character.isLetter(c)){
			   //若是前一个字符不是字母数字则，转为大写
			   if(i>0 &&  !Character.isLetterOrDigit(name.charAt(i-1))){ 
				   res.append(Character.toUpperCase(c));
			   }else{
				   res.append(c);
			   }
		   }else if(Character.isLetter(c)){
			   res.append(c);
		   }
	   }
	   if(res.length()>0){
		   char c=res.charAt(0);
		   if(Character.isUpperCase(c)){
			   res.setCharAt(0,Character.toLowerCase(c)); 
		   }   
	   }
	   return res;
   }
   /**
	* 把c语言变量命名法转为骆驼法则 （camel）命名法
	* search_site_log -> searchSiteLog
	* SearchSiteLog -> searchSiteLog
	* 骆驼法则 （camel）命名法
	* 第一个单字以小写字母开始；第二个单字的首字母大写或每一个单字的首字母都采用大写字母
	* 例如 pascalName
	* */
   public static String toCamelName(String name){
	   return toCamelName(new StringBuffer(name)).toString();
   }
   /**
	* 把c语言变量命名法转为帕斯卡（pascal）命名法
	* search_site_log -> SearchSiteLog
	* searchSiteLog -> SearchSiteLog 
	* 帕斯卡（pascal）命名法
	* 骆驼命名法是首字母小写，而帕斯卡命名法是首字母大写
	* 例如 PascalName
	* */
   public static StringBuffer toPascalName(StringBuffer name){
	   StringBuffer res=new StringBuffer();
	   for(int i=0;i<name.length();i++){
		   char c=name.charAt(i);
		   if(Character.isDigit(c)){
			   res.append(c);
		   }else if(Character.isLetter(c)){
			   //若是前一个字符不是字母数字则，转为大写
			   if(i>0 &&  !Character.isLetterOrDigit(name.charAt(i-1))){ 
				   res.append(Character.toUpperCase(c));
			   }else{
				   res.append(c);
			   }
		   }else if(Character.isLetter(c)){
			   res.append(c);
		   }
	   }
	   
	   if(res.length()>0){
		   char c=res.charAt(0);
		   if(Character.isLowerCase(c)){
			   res.setCharAt(0,Character.toUpperCase(c)); 
		   }   
	   }
	   return res;
   }
   /**
	* 把c语言变量命名法转为帕斯卡（pascal）命名法
	* search_site_log -> SearchSiteLog
	* searchSiteLog -> SearchSiteLog
	* 帕斯卡（pascal）命名法
	* 骆驼命名法是首字母小写，而帕斯卡命名法是首字母大写
	* 例如 PascalName
	* */
   public static String toPascalName(String str){
	   return toPascalName(new StringBuffer(str)).toString();
   }
   
   /**
	* 把帕斯卡（pascal）命名法或者骆驼法则 （camel）命名法 转为c语言变量命名法
	* SearchSiteLog->search_site_log
	* searchSiteLog->search_site_log 
	* 下划线 命名法 ,c语言变量命名
	* 单词之间用"_"分隔,全小写
	* 例如 search_site_log
	* */
   public static StringBuffer toCVarName(StringBuffer name){
	   StringBuffer res=new StringBuffer();
	   for(int i=0;i<name.length();i++){
		   char c=name.charAt(i);
		   if(Character.isUpperCase(c)){
			   //若是前一个字符不是字母数字则，转为大写
			   res.append("_");
			   res.append(Character.toLowerCase(c));
		   }else{
			   res.append(c);
		   }
	   }
	   return res;
   }
   /**
	* 把帕斯卡（pascal）命名法或者骆驼法则 （camel）命名法 转为c语言变量命名法
	* 下划线 命名法 ,c语言变量命名
	* 单词之间用"_"分隔,全小写
	* 例如 search_site_log
	* */
   public static String toCVarName(String name){
	   return toCVarName(new StringBuffer(name)).toString();
   }
   
   
	public static void main(String[]argv){
		String name="search_site_log";
		System.out.println(toPascalName(name));
		System.out.println(toCamelName(name));
	}
}
