/**
 * 
 */
package com.vanstone.centralserver.common;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author shipeng
 */
public class JsonUtil {
	
	/**
	 * JSON转Map
	 * @param strJson
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String strJson) {
		if (strJson == null || "".equals(strJson)) {
			return null;
		}
		Gson gson = new Gson();
		return gson.fromJson(strJson, LinkedHashMap.class);
	}
	
	/**
	 * 转换为格式化Json字符串
	 * @param object
	 * @return
	 */
	public static String object2PrettyString(Object object, boolean pretty) {
		Gson gson = null;
		if (pretty) {
			gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		}else{
			gson = new GsonBuilder().disableHtmlEscaping().create();
		}
		return gson.toJson(object);
	}
}
