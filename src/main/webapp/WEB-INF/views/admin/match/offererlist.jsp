<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
var myUrl = "/admin/match/offererlist.do";
$(document).ready(function() {
	setValue($("#id"), '${id}');
	setValue($("#name"), '${name}');
	setValue($("#offererName"), '${offererName}');
	
	$(".assign .smallbutton").on("click", function() {
		var popupUrl = "/admin/requirement.do";
		var width = 500;
		var height = 500;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var offererId = $(this).text();
		window.open(popupUrl+"?offererId="+offererId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="assign">수동배정</div>
		</div>
		<form method="post" id="form">
			<table class="data-table">
				<tr>
					<th>고용주 ID</th>
					<td><input type="text" id="id" name="id" class="text" ></td>
					<th>고용주 이름</th>
					<td><input type="text" id="name" name="name" class="text" ></td>
					<th>상호</th>
					<td><input type="text" id="offererName" name="offererName" class="text" ></td>
					<th></th>
					<td><input type="button" id="search" value="검색" class="smallbutton"></td>
				</tr>
			</table>
			<table class="data-table">
				<th>고용주 ID</th>
				<th>고용주 이름</th>
				<th>상호</th>
				<th>가입날짜</th>
				<th>사업자번호</th>
				<th>전화번호</th>
				<th>휴대폰</th>
				<th>수동배정</th>
				<c:forEach items="${list}" var="o">
				<tr>
					<td><c:out value="${o.id}" /></td>
					<td><c:out value="${o.name}" /></td>
					<td><c:out value="${o.offererName}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${o.registerDate}" /></td>
					<td><c:out value="${o.offererNumber}" /></td>
					<td><c:out value="${o.phone}" /></td>
					<td><c:out value="${o.cellPhone}" /></td>
					<td class="assign">
						<input type="button" id="join" class="smallbutton" value="배정">
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<%@ include file="/WEB-INF/views/include/pagenumber.jspf" %>

		</form>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>