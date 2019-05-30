# HbaseVideo_graduate
毕业论文中的hbase存储视频信息，参考工程


环境：hadoop-0.20.2、hbase-0.90.5、fuse、ffmpeg、eclipse、tomcat6.0、jdk6


启动Hadoop:
{$HADOOP_HOME}/bin/start-all.sh


离开安全模式:
/home/wangchen/HadoopInstall/Hadoo{HADOOP_HOME}/bin/hadoop dfsadmin -safemode leave


启动Hbase:
{$HBASE_HOME}/bin/start-hbase.sh


hdfs挂载:  {$HADOOP_HOME}/build/contrib/fuse-dfs/fuse_dfs_wrapper.sh dfs://master:9000 {$TOMCAT_HOME}/webapps/HbaseVideo/hdfs


关闭Hbase:
{$HBASE_HOME}/bin/stop-hbase.sh



关闭Hadoop:
{$HADOOP_HOME}/bin/stop-all.sh