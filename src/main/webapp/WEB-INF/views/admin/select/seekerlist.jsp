<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
var myUrl = "/admin/select/seekerlist.do";
$(document).ready(function() {
	setValue($("#workAbility"), '${workAbility}');
	setValue($("#id"), '${id}');
	setValue($("#name"), '${name}');
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="people2">회원조회</div>
		</div>
		<form method="post" id="form">
			<table class="data-table">
				<tr>
					<th>전화번호</th>
					<td><input type="text" id="id" name="id" class="text" ></td>
					<th>회원이름</th>
					<td><input type="text" id="name" name="name" class="text" ></td>
					<th>가능업무</th>
					<td>
						<select name="workAbility" id="workAbility">
							<option value=''>선택안함 </option>
						<c:forEach items="${context.WorkAbility}" var="row">
							<option value="${row.workAbility}"><c:out value="${row}"/></option>
						</c:forEach>
						</select>
					<td>
					<th>지역</th>
					<td></td>
					<th></th>
					<td><input type="button" id="search" value="검색" class="smallbutton"></td>
				</tr>
			</table>
			<table class="data-table">
				<th>전화번호</th>
				<th>회원이름</th>
				<th>생년월일</th>
				<th>성별</th>
				<th>국가</th>
				<th>파트</th>
				<th>근무지역1</th>
				<th>근무지역2</th>
				<th>근무지역3</th>
				<th>근무요일</th>
				<th>근무시간</th>
				<th>본인인증</th>
				<c:forEach items="${list}" var="s">
				<tr>
					<td><c:out value="${s.id}"/></td>
					<td><c:out value="${s.name}"/></td>
					<td><fmt:formatDate pattern="yyyy" value="${s.birth}" /></td>
					<td>${s.gender}</td>
					<td>${s.nation}</td>
					<td>${s.workAbility}</td>
					<td><c:out value="${s.region1.sigunguName}" /></td>
					<td><c:out value="${s.region2.sigunguName}" /></td>
					<td><c:out value="${s.region3.sigunguName}" /></td>						
					<td>${fn:replace(s.workMday, '\"', '')}</td>						
					<td>${fn:replace(s.workQtime, '\"', '')}</td>
					<td><c:out value="${s.licenseFile}"/></td>
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