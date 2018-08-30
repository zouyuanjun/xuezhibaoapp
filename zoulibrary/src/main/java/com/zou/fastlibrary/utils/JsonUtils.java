package com.zou.fastlibrary.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	/**
	 * 对象转字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String objectToString(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 字符串转对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object stringToObject(String jsonString, Class cla) {
		JSONObject jsonObject = JSON.parseObject(jsonString);
		Object obj = JSON.toJavaObject(jsonObject, cla);
		return obj;
	}

	/**
	 * 从标准Json字符串中获取String值
	 * 
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static String getStringValue(String jsonString, String key) {
		JSONObject jsonObject = JSON.parseObject(jsonString);
		boolean b = jsonObject.containsKey(key);
		if (b) {
			return jsonObject.getString(key);
		} else {
			return "没有找到这个键";
		}
	}

	/**
	 * biao'zhunlistjson转成List<T>对象
	 * 
	 * @param jsonString
	 * @param cla
	 * @return
	 */
	public static <T> List<T> stringToList(String jsonString, Class cla) {
		List<T> list = new ArrayList<T>();
		list = JSON.parseArray(jsonString, cla);
		return list;
	}

	/**
	 * 从Json字符串中获取int值
	 * 
	 * @param jsonString
	 * @param key
	 * @return
	 */
	public static int getIntValue(String jsonString, String key) {
		JSONObject jsonObject = JSON.parseObject(jsonString);
		boolean b = jsonObject.containsKey(key);
		if (b) {
			return jsonObject.getIntValue(key);
		} else {
			return 10000;
		}
	}

	/**
	 * 构建单个键值对的Json字符串
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String keyValueToString(String key, Object value) {
		JSONObject json = new JSONObject();
		json.put(key, value);
		return json.toJSONString();
	}

	/**
	 * 构建含有两个键值对的Json字符串
	 * 
	 * @param key1
	 * @param value1
	 * @param key2
	 * @param value2
	 * @return
	 */
	public static String keyValueToString2(String key1, Object value1, String key2, Object value2) {
		JSONObject json = new JSONObject();
		json.put(key1, value1);
		json.put(key2, value2);
		return json.toJSONString();
	}

	/**
	 * 添加字段到json字符串中
	 * @param jsonstring
	 * @param key
	 * @param value
	 * @return
	 */
	public static String addKeyValue(String jsonstring,String key,String value){
        JSONObject jsonObject;
	    if (StringUtil.isEmpty(jsonstring)){
	        jsonObject=new JSONObject();
        }else {
            jsonObject=JSON.parseObject(jsonstring);
        }
		jsonObject.put(key,value);
		return  jsonObject.toJSONString();
	}
}
