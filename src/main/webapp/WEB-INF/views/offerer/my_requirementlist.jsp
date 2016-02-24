<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
	
	$(".offerer a").on("click", function() {
/*
		var popupUrl = "/offerer/detail.do";
		var width = 700;
		var height = 300;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var offererId = $(this).children(':input').val();
		window.open(popupUrl+"?id="+offererId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
*/
	});
	
	$(".person .smallbutton").on("click", function() {
		var popupUrl = "/offerer/match/seekerlist.do";
		var width = 850;
		var height = 300;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var requirementId = $(this).attr("id");
		window.open(popupUrl+"?requirementId="+requirementId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../menu.jspf" %>
	
	<div id="splash">
		<h3>나의 배정내역</h3>
		<form method="post" action="#" id="form">
			<table class="data-table">
				<tbody>
					<th>배정번호</th>
					<th>업체명</th>
					<th>배정상태</th>
					<th>업무</th>
					<th>출근일자</th>
					<th>근무시간</th>
					<th>근무지역</th>
					<th>연령</th>
					<th>성별</th>
					<th>국적</th>
					<th>인원</th>
					<th>배정내역</th>
					<c:forEach items="${list}" var="r">
					<tr>
						<td><c:out value="${r.id}"/></td>						
						<td class="offerer">
							<a href="#">								
								<c:out value="${r.offererName}" />
								<input type="hidden" value="${r.offererId}" />
							</a>
						</td>
						<td>${r.matchStatus}</td>
						<td>${r.workAbility}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${r.workDate}" /></td>
						<td><c:out value="${r.workTime}" /></td>
						<td><c:out value="${r.location.sigunguName}" /></td>
						<td><c:out value="${r.ageRange}"></c:out></td>
						<td>${r.gender}</td>
						<td>${r.nation}</td>
						<td><c:out value="${assignedMap[r.id]}/${r.person}" /></td>
						<td class="person">
							<input type="button" class="smallbutton" value="보기" id="${r.id}">
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>