package com.sc.zhenli.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;


/**
 *
 * @ClassName: MybatisInterceptor
 * @author yq.w
 * @version 1.0
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class MybatisInterceptor implements Interceptor {
	static Logger logger = LoggerFactory.getLogger("sql_logger");
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		String sqlId = mappedStatement.getId();
		JSONObject json = new JSONObject();
		json.put("sqlId",sqlId);
		json.put("command",mappedStatement.getSqlCommandType());

		if(mappedStatement.getResource().lastIndexOf(File.separator)>0){
			String res = mappedStatement.getResource();
			json.put("resource",res.substring(res.lastIndexOf(File.separator)+1,res.length()-1));
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		if(boundSql.getParameterObject()!= null){
			if(boundSql.getParameterObject().getClass().getName().indexOf("com.jyx.crm.common.model")>=0){
				String sql = JSON.toJSONString(boundSql.getParameterObject());
				json.put("param",sql.replaceAll("\\$","****"));
			}else{
				json.put("param",boundSql.getParameterObject());
			}
		}
		//session param会覆盖原来的param对象
		Map<String,Object> sessionParam = ThreadSession.SESSION_PARAM.get();
		if(sessionParam!=null && sessionParam.size()>0){
			JSONObject param = new JSONObject();
			param.putAll(sessionParam);
			json.put("param",param);
		}
		//session peorpery会将属性都添加到根对象
		Map<String,Object> sessionProperty = ThreadSession.SESSION_PROPERTY.get();
		if(sessionProperty!=null && sessionProperty.size()>0){
			json.putAll(sessionProperty);
		}
		Configuration configuration = mappedStatement.getConfiguration();
		Object returnValue = null;
		try {
			json.put("sql",showSql(configuration, boundSql));
			long start = System.currentTimeMillis();
			returnValue = invocation.proceed();
			long end = System.currentTimeMillis();
			long time = (end - start);
			if(mappedStatement.getSqlCommandType()
					!= SqlCommandType.SELECT){
				json.put("result",returnValue);
			}else if(returnValue instanceof List){
				json.put("resultSize",((List)returnValue).size());
			}
			json.put("runTime",time);
		} catch (Exception e) {
			json.put("error",e.getMessage());
			System.out.println(JSON.toJSONString(json,true));
			throw e;
		} finally {
			if(!sqlId.startsWith("com.jyx.crm.service.dao.LogModelDao")){
				if(json.containsKey("error")){
					logger.debug("EXECUTE SQL>"+json.toJSONString());
				}else {
					if(json.getInteger("userId")!=null
							&& json.getInteger("userId")>0){
						logger.debug("EXECUTE SQL>"+json.toJSONString());
					}else{
						logger.debug("EXECUTE SQL>"+json.toJSONString());
					}
				}
			}
		}
		return returnValue;
	}

	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(obj) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}

		}
		return value;
	}

	public static String showSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
					}
				}
			}
		}
		return sql;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties0) {
	}
}