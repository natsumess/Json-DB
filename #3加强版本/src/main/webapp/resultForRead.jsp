<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		boolean result = (boolean) session.getAttribute("result");
		String resultMsg = (String) session.getAttribute("resultMsg");
		String readResult = (String) session.getAttribute("readResult");
	%>
	<% 
		if(result == true) {
			out.println(resultMsg);
			out.println(readResult);
		} else {
			out.println(resultMsg);
		}
	%>
</body>
</html>