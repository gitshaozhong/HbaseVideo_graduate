<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	//request.setCharacterEncoding("utf-8");
	if(request.getParameter("queryString").equals("")){
		response.sendRedirect("index.html");
	}else{
				RequestDispatcher rd = null;
				String select = request.getParameter("change");
				if(select.equals("paper")){
					rd = request.getRequestDispatcher("paper_list.jsp");
					rd.forward(request,response);
				}else if(select.equals("video")){
					rd = request.getRequestDispatcher("video_list.jsp");
					rd.forward(request,response);
				}else if(select.equals("image")){
					rd = request.getRequestDispatcher("image_list.jsp");
					rd.forward(request,response);
				}
	}
 %>
