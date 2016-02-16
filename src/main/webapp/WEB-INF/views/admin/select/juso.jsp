<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
	
	var myUrl = "/admin/select/juso.do";
	
	setValue($("#sidoName"), '${sidoName}');
	setValue($("#sigunguName"), '${sigunguName}');
	
	$("#search").click(function() {
		var form = $("#form");
		form.action = myUrl;
		form.submit();
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash">
		<h3>주소 조회</h3>
		<form method="post" action="#" id="form">
			<table class="data-table">
				<tbody>
					<tr>
						<th>시/도</th>
						<td><input type="text" id="sidoName" name="sidoName" class="text" ></td>
						<th>시/구/군</th>
						<td><input type="text" id="sigunguName" name="sigunguName" class="text" ></td>
						<td><input type="button" id="search" value="검색" class="button"></td>
					</tr>
				</tbody>
			</table>
			<table class="data-table">
				<tbody>
					<th>id</th>
					<th colspan="2">시.도</th>
					<th colspan="2">시/구/군</th>
				</tbody>
				<c:forEach items="${jusoSeoulList}" var="juso">
					<tr>
						<td><c:out value="${juso.id}" /></td>
						<td><c:out value="${juso.sidoId}" /></td>
						<td><c:out value="${juso.sidoName}" /></td>
						<td><c:out value="${juso.sigunguId}" /></td>
						<td><c:out value="${juso.sigunguName}" /></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>