package com.bean;

import com.alibaba.fastjson.JSONObject;

public class ImgBean {
	public String picUrl;
	
	public String picName;
	
	public String desc;
	
	public String hashCode;
	
	public int fileSize;
	
	public JSONObject picJson;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public JSONObject getPicJson() {
		return picJson;
	}

	public void setPicJson(JSONObject picJson) {
		this.picJson = picJson;
	}

	@Override
	public String toString() {
		return "ImgBean [picUrl=" + picUrl + ", picName=" + picName + ", desc=" + desc + ", hashCode=" + hashCode
				+ ", fileSize=" + fileSize + ", picJson=" + picJson + "]";
	}
}
