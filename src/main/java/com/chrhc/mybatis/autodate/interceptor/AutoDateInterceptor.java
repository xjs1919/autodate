package com.chrhc.mybatis.autodate.interceptor;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.chrhc.mybatis.autodate.util.PluginUtil;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @author 605162215@qq.com
 * @date 2016-06-23
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class AutoDateInterceptor implements Interceptor {
	
	private static final Log logger = LogFactory.getLog(AutoDateInterceptor.class);  
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("-yyyy-MM-dd HH:mm:ss.SSS-");
    
    private Properties props = null;
    
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		String interceptMethod = invocation.getMethod().getName();
		if(!"prepare".equals(interceptMethod)) {
			return invocation.proceed();
		}
		
		StatementHandler handler = (StatementHandler) PluginUtil.processTarget(invocation.getTarget());
		MetaObject metaObject = SystemMetaObject.forObject(handler);
		MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		SqlCommandType sqlCmdType = ms.getSqlCommandType();
		if(sqlCmdType != SqlCommandType.UPDATE && sqlCmdType != SqlCommandType.INSERT) {
			return invocation.proceed();
		}
		//获取配置参数
		String createDateColumn, updateDateColumn;
		if(null == props || props.isEmpty()) {
			return invocation.proceed();
		} 
		createDateColumn = props.getProperty("createDateColumn", "");
		updateDateColumn = props.getProperty("updateDateColumn", "");
		if(createDateColumn.length() <= 0 && updateDateColumn.length() <= 0){
			return invocation.proceed();
		}
		//获取原始sql
		String originalSql = (String) metaObject.getValue("delegate.boundSql.sql"); 
		logger.debug("==> originalSql: " + originalSql);
		//追加上日期
		String newSql = "";
		if(sqlCmdType == SqlCommandType.UPDATE && updateDateColumn.length() > 0){
			newSql = addUpdateDateToSql(originalSql, updateDateColumn);
		}else if(sqlCmdType == SqlCommandType.INSERT && createDateColumn.length() > 0){
			newSql = addCreateDateToSql(originalSql, createDateColumn);
		}
		//修改原始sql
		if(newSql.length() > 0){
			logger.debug("==> newSql: " + newSql);
			metaObject.setValue("delegate.boundSql.sql", newSql);
		}
	    return invocation.proceed();
	}
	
	private String addCreateDateToSql(String originalSql, String columnName){
		try{
			Statement stmt = CCJSqlParserUtil.parse(originalSql);
			if(!(stmt instanceof Insert)){
				return originalSql;
			}
			Insert update = (Insert)stmt;
			List<Column> columns = update.getColumns();
			if(!contains(columns, columnName)){
				Column versionColumn = new Column();
				versionColumn.setColumnName(columnName);
				columns.add(versionColumn);
				ItemsList itemList = update.getItemsList();
				if(itemList instanceof ExpressionList){//单个
					ExpressionList expressionList = (ExpressionList)itemList;
					List<Expression> expressions = expressionList.getExpressions();
					TimestampValue val = new TimestampValue(TIMESTAMP_FORMAT.format(new Date()));
					expressions.add(val);
				}else if(itemList instanceof MultiExpressionList){//批量
					MultiExpressionList multiExpressionList = (MultiExpressionList)itemList;
					List<ExpressionList> expressionLists = multiExpressionList.getExprList();
					for(ExpressionList expressionList : expressionLists){
						List<Expression> expressions = expressionList.getExpressions();
						TimestampValue val = new TimestampValue(TIMESTAMP_FORMAT.format(new Date()));
						expressions.add(val);
					}
				}else{//insert select
					columns.remove(columns.size() - 1);
				}
			}
			return stmt.toString();
		}catch(Exception e){
			e.printStackTrace();
			return originalSql;
		}
	}
	
	private String addUpdateDateToSql(String originalSql, String columnName){
		StringBuilder sb = new StringBuilder();
		String sqlList[] = originalSql.split(";");
		for(int i=0; i<sqlList.length; i++){
			if(i > 0 ){
				sb.append(";");
			}
			String sql = _addUpdateDateToSql(sqlList[i], columnName);
			sb.append(sql);
		}
		return sb.toString();
	}
	
	private String _addUpdateDateToSql(String originalSql, String columnName){
		try{
			Statement stmt = CCJSqlParserUtil.parse(originalSql);
			if(!(stmt instanceof Update)){
				return originalSql;
			}
			Update update = (Update)stmt;
			List<Column> columns = update.getColumns();
			if(!contains(columns, columnName)){
				Column versionColumn = new Column();
				versionColumn.setColumnName(columnName);
				columns.add(versionColumn);
				List<Expression> expressions = update.getExpressions();
				TimestampValue val = new TimestampValue(TIMESTAMP_FORMAT.format(new Date()));
				expressions.add(val);
			}
			return stmt.toString();
		}catch(Exception e){
			e.printStackTrace();
			return originalSql;
		}
	}
	
	private boolean contains(List<Column> columns, String columnName){
		if(columns == null || columns.size() <= 0){
			return false;
		}
		if(columnName == null || columnName.length() <= 0){
			return false;
		}
		for(Column column : columns){
			if(column.getColumnName().equalsIgnoreCase(columnName)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }  
	}

	@Override
	public void setProperties(Properties properties) {
		if(null != properties && !properties.isEmpty()) props = properties;
	}
	
}
