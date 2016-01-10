package com.max.net;

import java.lang.reflect.Type;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.star.cms.common.net.DateTypeAdapter;
/**
 * 
 * @author yaohw
 *
 */
public class JSONUtil {
	private static Log log = LogFactory.getLog(JSONUtil.class);
	private static Gson gson;
	
	private static Gson getGSON() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
			gson = gsonBuilder.create();
		}
		return gson;
	}
	
	public static <T> T getFromJSON(String json, Class<T> clazz){
		try{
			return getGSON().fromJson(json,clazz);
		}catch(RuntimeException e){
			log.error("JSON to Object["+clazz+"] error. json:"+json, e);
			throw e;
		}
	}
	
	public static <T> T getFromJSON(String json, Type typeOfT){
		try{
			return getGSON().fromJson(json,typeOfT);
		}catch(RuntimeException e){
			log.error("JSON to Object["+typeOfT+"] error. json:"+json, e);
			throw e;
		}
	}
	
	public static String getJSON(Object object){
		return getGSON().toJson(object);
	}
}
