/**定义insertdata和geturl*/

package com.util.HbaseUtil;

import java.io.IOException;
import java.util.ArrayList;
//import java.util.Hashtable;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

public class HbaseUtil {

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
	
	public void insertData(String tableName,List<Put> put){
		System.out.println("插入数据到:"+tableName);
		try {
			HTable table = new HTable(configuration,tableName);
			table.put(put);
			table.flushCommits();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getUrl(String tableName,String rowKey){
		List<String> list = new ArrayList<String>();
		try {
			HTable table = new HTable(configuration,tableName);
			Get get = new Get(rowKey.getBytes());
			Result rs = table.get(get);
			String row = "";
			String family="";
			String qualifier="";
			String timeStamp="";
			String value="";
			for(KeyValue kv:rs.raw()){
				row = new String(kv.getRow());
				family = new String(kv.getFamily());
				qualifier = new String(kv.getQualifier());
				timeStamp =new String(kv.getTimestamp()+"");
				value = new String(kv.getValue());
				System.out.println(row+" "+family+" "+qualifier+" "+timeStamp+" "+value);
				//System.out.println("getUrl: "+qualifier);
				list.add(qualifier+"-----"+value);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		HbaseUtil hbaseUtil = new HbaseUtil();
		List<String> list = hbaseUtil.getUrl("image", "url");
		for(String name:list){
			System.out.println(name);
		}
	}
	
}
