<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="com.util.videoUtil.VideoUtil"%>
<%@page import="java.net.URLEncoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
		//request.setCharacterEncoding("utf-8");
		
		//如果查询为空,返回主界面,其实可以js处理
		String query = request.getParameter("queryString");
		if(query==null||query.equals("")){
			response.sendRedirect("index.html");
			return;
		}
		query = new String(request.getParameter("queryString").getBytes("ISO-8859-1"),"UTF-8");
		
System.out.println(query);
		String currentpages = request.getParameter("currentpages"); 
		//取得访问的页面
		int cp=0;
		if(currentpages!=null){
			cp = Integer.parseInt(currentpages);
		}
		
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>视频列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/mm_entertainment.css" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {font-size: large}
.STYLE2 {font-size: medium}
.STYLE3 {font-size: x-large}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: underline;
}
a:active {
	text-decoration: none;
}
-->
</style>

  </head>
  
  <body bgcolor="#14285f">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="02021e">
    <td colspan="2" rowspan="2" valign="bottom" nowrap="nowrap"><div class="STYLE2"> <a href="index.html">主页</a>&nbsp; <a href="#">上传视频</a></div></td>
    <td width="792" height="58" nowrap="nowrap" id="logo" valign="bottom">PKUSS CLOUD SEARCH </td>
    <td width="143">&nbsp;</td>
  </tr>
  <tr bgcolor="02021E">
    <td height="57" valign="top" nowrap="nowrap" id="tagline"><p>software developer in 2012</p>
    </td>
	<td width="143">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" bgcolor="#cc3300"><img src="mm_spacer.gif" alt="" width="1" height="2" border="0" /></td>
  </tr>

   <tr>
    <td colspan="4"><img src="mm_spacer.gif" alt="" width="1" height="2" border="0" /></td>
  </tr>

   <tr>
    <td colspan="4" bgcolor="#cc3300"><img src="mm_spacer.gif" alt="" width="1" height="1" border="0" /></td>
  </tr>

   <tr>
    <td colspan="5" id="dateformat"><form id="form1" name="form1" method="post" action="video_list.jsp">
      <a class="pageName">&nbsp; PKUSS CLOUD VIDEO SEARCH: </a>
      <label>
      <input name="queryString" type="text" size="30" maxlength="30" />
      </label>
      <label>
      <input type="submit" name="Submit" value="搜一下" />
      </label>
    </form>
	</td>
  </tr>

  <tr>
   <td width="174" valign="top">&nbsp;</td>
   <td colspan="2" valign="top"><br />
	<table border="0" cellspacing="0" cellpadding="2" width="100%">
        <tr>
          <td height="35" colspan="9" class="subHeader"><p>视频列表</p>
          </td>
        </tr>
<%
		//System.out.println("query:"+query);
  		VideoUtil videoUtil = new VideoUtil();
  		List<String> videoList =videoUtil.getVideoNamesList(query);
  		int count = videoList.size()-cp*10;
  		int pages;
  		int rows;
  		if(videoList.size()%10==0){
  			pages = videoList.size()/10;
  		}else{
  			pages = videoList.size()/10 +1;
  		}
  		//现在每页显示最多10个视频
  		//最多2行,每行5个
  		if(count<5){
  			rows = 1;
  		}else{
  			rows = 2;
  		}
  		int i=0;
  		boolean tag = true;
 System.out.println("videoTable size:"+videoList.size());
  		for(i=0;i<count;i++){
  			if(i>=10){
  				tag = false;
  			}


  		//out.print(videoTable.get(name));
  		//System.out.println(name);
  		//System.out.println(videoTable.get(i) );
  %>
  
  <%
  //特别处理只有不到10个视频
  for(int j=0;j<rows&&tag;j++){
  	int tail = 5;
  	if (j==rows-1 && count <10){
  		tail = count%5;
  	}
   %>
		<tr>
		<%
  		for(int k=0;k<tail;k++){
  		String info = videoList.get(i+k+cp*10);
  		String url = info.split("-----")[0];
  		String des = info.split("-----")[1];
  %>
          <td width="157" height="110">
		 	<a href="video_display.jsp?videoPath=<%=url %>&des=<%=des %>" target="_blank">
		 	<% 
		 		String imageName = url.split("\\.")[0]+".jpg";
		 		//System.out.println(imageName);
		 	%>
		  <img src="<%=imageName %>" alt="small product photo" width="157" height="116" border="0" />
		  </a>
		  </td>
<%
		}
		 %>
        </tr>
        
		<tr>
		<%
  		for(int k=0;k<tail;k++){
  		String info = videoList.get(i+k+cp*10);
  		String url = info.split("-----")[0];
  		String des = info.split("-----")[1];
  %>
          <td class="detailText" valign="top" nowrap="nowrap"><a href="javascript:;">视频信息:</a>
          <%
          	if(des.length()>12){
          		des = des.substring(0,12);
          		des +="...";
          	}
           %>
          <br /><%=des %></td>
          <%
		}
		 %>
        </tr>
		<tr>
			<td colspan="10">&nbsp;</td>
		</tr>
		<%
		 i = i+tail;
		}
		 %>
<%
}
%>
		<tr>
			<td colspan="10" background="video_list.html"><p>&nbsp;</p>
		  <div align="center" class="STYLE1">
		  <%
		  	for(int m=1; m<=pages&&m<=9;m++){
		   %>
		  <a href="video_list.jsp?currentpages=<%=m-1%>&queryString=<%=query  %>" ><%=m %></a> 
		  <%
		  }
		   %>
		 </div></td>
		</tr>
      </table>	  </td>
	  <td width="143">&nbsp;</td>
  </tr>
  <tr>
    <td width="174">&nbsp;</td>
    <td width="199">&nbsp;</td>
    <td width="792">&nbsp;</td>
	<td width="143">&nbsp;</td>
  </tr>
</table>
<br />
</body>
</html>
