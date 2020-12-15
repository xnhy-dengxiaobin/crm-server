package io.renren.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

	public static <T> T readJsonObject(String json, Class<T> cls)
			throws Exception {
		return JSON.parseObject(json, cls);
	}

	public static String objectToJson(Object o) throws Exception {
		return JSON.toJSONString(o);
	}

	public static <T> T readJsonObject(String json, String key, Class<T> cls)
			throws Exception {
		JSONObject jsonObj = JSON.parseObject(json);
		if (!jsonObj.containsKey(key))
			return null;
		JSONObject jsonObj2 = jsonObj.getJSONObject(key);
		String json2 = jsonObj2.toJSONString();
		return readJsonObject(json2, cls);
	}

	public static void main(String[] args) {
		class A {
			public String name;
			public int age;
		}
		A a = new A();
		a.name = "小明";
		a.age = 20;
		A b = new A();
		b.name = "小明";
		b.age = 20;
		List<A> list = new ArrayList<A>();
		list.add(b);
		list.add(a);
		try {
			System.out.println(objectToJson(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
