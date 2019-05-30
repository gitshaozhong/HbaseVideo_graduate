package com.util.commonUtil;

import java.io.IOException;
import java.io.InputStream;
//import java.net.URI;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class CommonUtil {
	
	private InputStream inputStream ; 
	private Properties property;

	public CommonUtil(){
		inputStream = this.getClass().getClassLoader().getResourceAsStream("ipConfig.properties");
		property = new Properties();
		try {
			property.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getIp(){
		return property.getProperty("ip");
	}
	
	public String getPort(){
		return property.getProperty("port");
	}
	
	public String getUri(String type,String filename){
		if(type.equals("image")){
			return getImageUri(filename);
		}else if(type.equals("video")){
			return getVideoUri(filename);
		}else if(type.equals("paper")){
			return getPaperUri(filename);
		}
		return "";
	}
	
	public String getImageUri(String filename){
		return property.getProperty("imagePath")+"/"+filename;
	}
	
	public String getPaperUri(String filename){
		return property.getProperty("paperPath")+"/"+filename;
	}
	
	public String getVideoUri(String filename){
		return property.getProperty("videoPath")+"/"+filename;
	}
	
	public String getWebapp_Path(){
		return property.getProperty("webapp_path");
	}

	public String getTmp_image_dir(){
		return property.getProperty("tmp_image_dir");
	}
	
	public String getTmp_paper_dir(){
		return property.getProperty("tmp_paper_dir");
	}
	
	public String getTmp_video_dir(){
		return property.getProperty("tmp_video_dir");
	}
	
	public String getTmp_dir(String type) {
		if(type.equals("image")){
			return getTmp_image_dir();
		}else if(type.equals("video")){
			return getTmp_video_dir();
		}else if(type.equals("paper")){
			return getTmp_video_dir();
		}
		return "";
	}
	
}
