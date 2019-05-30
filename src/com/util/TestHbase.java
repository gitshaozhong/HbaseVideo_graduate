package com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

public class TestHbase {
	public static Configuration configuration = null;
	public static HBaseAdmin admin = null;
	static {
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.master", "localhost:60000");
		configuration.set("hbase.zookeeper.quorum", "localhost");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		try {
			admin = new HBaseAdmin(configuration);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteTable(String tableName){
		try {
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable(String tableName){
			try {
				if(admin.tableExists(tableName)){
					System.out.println("表已经存在,删除中...");
					deleteTable(tableName);
				}
				System.out.println("创建表:"+tableName);
				HTableDescriptor tableDescriptor = new HTableDescriptor(tableName.getBytes());
				tableDescriptor.addFamily(new HColumnDescriptor("url"));
				admin.createTable(tableDescriptor);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void insertData(String tableName,List<Put> put){
		System.out.println("插入数据...");
		try {
			HTable table = new HTable(configuration,tableName);
			table.put(put);
			table.flushCommits();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Put> imageUtil(){
		List<Put> put = new ArrayList<Put>();
		Put image1 = new Put(new String("hadoop").getBytes());
		image1.add(new String("url").getBytes(), new String("hadoop.jpg").getBytes(), new String("this is a hadoop pic").getBytes());
		image1.add(new String("url").getBytes(), new String("hadoop_book.jpg").getBytes(), new String("this is a hadoop book").getBytes());
		image1.add(new String("url").getBytes(), new String("hadoop_inaction.jpg").getBytes(), new String("this is a hadoop_inaction book").getBytes());
		Put image2 = new Put(new String("mao").getBytes());
		image2.add(new String("url").getBytes(), new String("mao2.gif").getBytes(), new String("this is a mao pic").getBytes());
		image2.add(new String("url").getBytes(), new String("mao3.gif").getBytes(), new String("this is a mao pic").getBytes());
		put.add(image1);
		//put.add(image2);
		return put;
	}
	
	public List<Put> videoUtil(){
		List<Put> put = new ArrayList<Put>();
		Put video = new Put(new String("dapao").getBytes());
		video.add(new String("url").getBytes(), new String("zizhidapao.flv").getBytes(), new String("this is a dapao flv").getBytes());
		put.add(video);
		return put;
	}
	
	public List<Put> paperUtil(){
		List<Put> put = new ArrayList<Put>();
		Put paper1 = new Put(new String("hadoop").getBytes());
		paper1.add(new String("url").getBytes(), new String("hadoop.txt").getBytes(), new String("this is a hadoop book ").getBytes());
		Put paper2 = new Put(new String("mapreduce").getBytes());
		paper2.add(new String("url").getBytes(), new String("mapreduce.txt").getBytes(), new String("this is a mapreduce book ").getBytes());
		put.add(paper1);
		put.add(paper2);
		return put;
	}
	
	public static void main(String[] args) throws IOException {
		TestHbase testHbase = new TestHbase();
		testHbase.createTable("video");
		//testHbase.createTable("image");
		//testHbase.createTable("paper");
		//testHbase.insertData("paper", testHbase.paperUtil());
		//testHbase.insertData("video", testHbase.videoUtil());
		//testHbase.insertData("paper", testHbase.paperUtil());
	}

}
