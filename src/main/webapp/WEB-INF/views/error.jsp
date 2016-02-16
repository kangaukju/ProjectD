<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>error page</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
</head>
<body>
Error<br>
code: <c:out value="${param.code}" /><br>
failure: <c:out value="${param.failure}" /><br>
error: <c:out value="${param.error}" /><br>
data: <c:out value="${param.data}" /><br>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>