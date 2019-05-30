package com.util.ffmpegUtil;

import java.io.IOException;

//import org.omg.SendingContext.RunTime;

import com.util.commonUtil.CommonUtil;

public class FfmpegUtil {
	
	private final static int screenshotTime = 5;
	private final static String scrrenshotSize = "350x240";
	private CommonUtil commonUtil = null;
	private String web_path = null;
	
	public FfmpegUtil(){
		commonUtil = new CommonUtil();
		web_path = commonUtil.getWebapp_Path();
	}
	
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	
	public void screenshot(String srcPath,String desPath){
		try {
			String cmd = "ffmpeg -i "+srcPath+" -y -f image2 -ss "+screenshotTime+" -t 0.001 -s "+scrrenshotSize+" "+desPath;
			Runtime.getRuntime().exec(cmd);
System.out.println(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param srcPath 代表要传到hdfs的文件的路径,包括文件名称
	 * @param type 代表要传入文件的类型,这里固定有image,video,paper
	 * @param filename 代表写入hdfs文件的名称,因为要使用uuid
	 */
	public void mvResToHdfs(String srcPath,String type,String filename){
		String dest = commonUtil.getUri(type, filename);
		String cmd = "cp "+srcPath+" "+web_path+dest;
		try {
			
			Runtime.getRuntime().exec(cmd);
System.out.println(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FfmpegUtil ff = new FfmpegUtil();
		String videoName = "zizhidapao.flv";
		String videoUUidName = "341123.flv";
		String imageName = "341123.jpg";
		String srcPath = ff.getCommonUtil().getTmp_dir("video")+"/"+videoName;
		String desPath = ff.getCommonUtil().getWebapp_Path()+ff.getCommonUtil().getUri("video", imageName);
		ff.screenshot(srcPath, desPath);
		ff.mvResToHdfs(srcPath, "video", videoUUidName);
	}
}
