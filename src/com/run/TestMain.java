package com.run;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getBingJson.GetBingJson;

public class TestMain {
	public static void main(String[] args) {
		GetBingJson gbj = new GetBingJson();
		JSONObject obj = gbj.getBingJson(0, 8);
		JSONArray array = gbj.getImgBeanArray(gbj.getImgArray(obj));
		System.out.println(array);
	}
}
