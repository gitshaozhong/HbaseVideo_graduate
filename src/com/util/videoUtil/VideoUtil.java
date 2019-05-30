package com.util.videoUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.apache.hadoop.hbase.client.Put;

import com.util.HbaseUtil.HbaseUtil;
import com.util.analyseWords.AnalyseWords;
import com.util.commonUtil.CommonUtil;
import com.util.ffmpegUtil.FfmpegUtil;

public class VideoUtil {

	private Hashtable<String,String> videoNames = null;
	private List<String> videoNamesList = null;
	private HbaseUtil hbaseUtil = null;
	private CommonUtil commonUtil = null;
	private FfmpegUtil ffmpegUtil = null;
	
	public VideoUtil(){
		videoNames = new Hashtable<String,String>();
		videoNamesList = new ArrayList<String>();
		hbaseUtil = new HbaseUtil();
		commonUtil = new CommonUtil();
		ffmpegUtil = new FfmpegUtil();
	}

	/**
	 * 
	 * path 视频路径包括名称
	 * name 视频名字
	 * des 视频的描述
	 */
	synchronized public void loadVideo(String path,String name,String des) {
		String postfix = name.split("\\.")[1];//用 " . " 分割开
		//存入hdfs的uuid的名称
		//1234
		String uuid = UUID.randomUUID()+"";//由java自动生成uuid
System.out.println(uuid);
		//1234.flv
		String filename = uuid+"."+postfix;
		String desImagePath = commonUtil.getWebapp_Path()+commonUtil.getUri("video", uuid+".jpg");
		AnalyseWords analyseWords = new AnalyseWords(des);
		List<String> list = analyseWords.getWords();
		//存入Hhase信息,视频存入
		for(String var : list){
			List<Put> put = new ArrayList<Put>();
			//rowkey
			Put video = new Put(new String(var).getBytes());
			//cf:url value
			video.add(new String("url").getBytes(), new String(filename).getBytes(), new String(des).getBytes());
			put.add(video);
			hbaseUtil.insertData("video", put);
		}
		ffmpegUtil.screenshot(path, desImagePath);
		ffmpegUtil.mvResToHdfs(path, "video", filename);
	}
	
	public void setVideoNames(String query) {
		//对query分词....得到分词的结果 比如 hadoop book action 分词做好后循环,现在就先指定hadoop
		//对查询的词分词,为多个关键词
		AnalyseWords analyseWords = new AnalyseWords(query);
		List<String> words = analyseWords.getWords();
		for(String word : words){
			if(hbaseUtil.getUrl("video", word)!=null){
				for(String keyValue:hbaseUtil.getUrl("video", word)){
					String[] values = keyValue.split("-----",2);
					String url = values[0];
					String des = values[1];
					//使用hashtable去重url,使得页面得到的每唯一个视频唯一
					this.videoNames.put(commonUtil.getVideoUri(url), des);
				}
			}
		}
	}
	
	public List<String> getVideoNamesList(String query){
		setVideoNames(query);
		for(String url : videoNames.keySet()){
			String info = url+"-----"+videoNames.get(url);
			videoNamesList.add(info);
		}
		return videoNamesList ;
	}
	
	public static void main(String[] args) {
		VideoUtil vu = new VideoUtil();
		//使用的是命令来执行文件的操作,则路径中不能有空格
		//vu.loadVideo("/usr/test/video/第八季03.mp4", "第八季03.mp4","the big bang theory");
		vu.loadVideo("/usr/test/video/050模块：模块就是程序.mp4", "模块就是程序.mp4","python module");
		vu.loadVideo("/usr/test/video/84散列函数的构造方法.mp4", "散列函数的构造方法.mp4","data struct ");
     // vu.getVideoNamesList("python");
		
	}
}
