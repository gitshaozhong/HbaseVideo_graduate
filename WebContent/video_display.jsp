<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>视频播放</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- A minimal Flowplayer setup to get you started -->
	<!-- 
		include flowplayer JavaScript file that does  
		Flash embedding and provides the Flowplayer API.
	-->
	<script type="text/javascript" src="flowplayer/flowplayer-3.2.11.min.js"></script>
	<!-- some minimal styling, can be removed -->
	<link rel="stylesheet" type="text/css" href="flowplayer/style.css">
	<!-- page title -->
  </head>
  <%
  		String videoPath = request.getParameter("videoPath");
  		String viodeoDes = new String(request.getParameter("des").getBytes("iso-8859-1"),"utf-8");
 System.out.println(viodeoDes);

   %>
  <body>
    <div id="page">
		<h1><%=viodeoDes %></h1>
		<p>View commented source code to get familiar with Flowplayer installation.</p>
		<!-- this A tag is where your Flowplayer will be placed. it can be anywhere -->
		<a  
			 href="<%=videoPath %>"
			 style="display:block;width:520px;height:330px"  
			 id="player"> 
		</a> 
		<!-- this will install flowplayer inside previous A- tag. -->
		<script>
			flowplayer("player", "flowplayer/flowplayer-3.2.12.swf",{
				clip:{
					autoPlay: false
				}
			});
		</script>
		<!-- 
			after this line is purely informational stuff. 
			does not affect on Flowplayer functionality 
		-->
		<p>		
			If you are running these examples <strong>locally</strong> and not on some webserver you must edit your 
			<a href="http://www.macromedia.com/support/documentation/en/flashplayer/help/settings_manager04.html">
				Flash security settings</a>. 
		</p>
		<p class="less">
			Select "Edit locations" &gt; "Add location" &gt; "Browse for files" and select
			flowplayer-x.x.x.swf you just downloaded.
		</p>
		<h2>Documentation</h2>
		<p>
			<a href="http://flowplayer.org/documentation/installation/index.html">Flowplayer installation</a>
		</p>
		<p>
			<a href="http://flowplayer.org/documentation/configuration/index.html">Flowplayer configuration</a>
		</p>
		<p>
			See this identical page on <a href="http://flowplayer.org/demos/example/index.htm">Flowplayer website</a> 
		</p>
	</div>
  </body>
</html>
