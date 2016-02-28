<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/popup_header.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
});
</script>
</head>
<body>
<div id="splash" class="subline" style="padding-top: 0px; margin-top: 0px;">
	<div class="sub-nav-img">
		<div class="people5">배정정보</div>
	</div>
	<form method="post" id="form">
		<input type="hidden" id="requirementId" name="requirementId" value="${param.id}">
		<input type="hidden" id="seekerId" name="seekerId">
		<table class="data-table">
		<c:if test="${fn:length(candidateSeekerList) == 0 && fn:length(confirmSeekerList) == 0}">
			<tr>
				<th>배정된 인원이 없습니다. 배정인원이 지정되면 SMS문자로 통보됩니다.</th>
			</tr>
		</c:if>
		<c:if test="${fn:length(candidateSeekerList) > 0 || fn:length(confirmSeekerList) > 0}">
			<tr>
				<th>요청인원</th>
				<td colspan="12"><c:out value="${requirement.person}"/></td>
			</tr>
			<tr style="height: 10px;"></tr>
		</c:if>
			<tr>
				<th colspan="13">
					<div class="sub-nav-img">
						<div class="people5">임시배정 구직자 목록</div>
					</div>
				</th>
			</tr>
			<th>전화번호</th>
			<th>이름</th>
			<th>생년월일</th>
			<th>성별</th>
			<th>국가</th>
			<th>파트</th>
			<th>근무지역1</th>
			<th>근무지역2</th>
			<th>근무지역3</th>
			<th>근무요일</th>
			<th>근무시간</th>
		<c:if test="${fn:length(candidateSeekerList) == 0}">
			<tr>
				<td colspan="13" style="text-align: center;">임시배정된 구직자가 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach items="${candidateSeekerList}" var="s">
			<tr class="candidate">
				<td>
					<a href="#"><c:out value="${s.id}"/></a>
				</td>
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
			</tr>
		</c:forEach>
			
			<tr style="height: 10px;"></tr>
			
			<tr>
				<th colspan="13">
					<div class="sub-nav-img">
						<div class="people5">배정확정 구직자 목록</div>
					</div>
				</th>
			</tr>
			<th>전화번호</th>
			<th>이름</th>
			<th>생년월일</th>
			<th>성별</th>
			<th>국가</th>
			<th>파트</th>
			<th>근무지역1</th>
			<th>근무지역2</th>
			<th>근무지역3</th>
			<th>근무요일</th>
			<th>근무시간</th>
		<c:if test="${fn:length(confirmSeekerList) == 0}">
			<tr>
				<td colspan="13" style="text-align: center;">배정확정된 구직자가 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach items="${confirmSeekerList}" var="s">
			<tr class="confirm">
				<td>
					<a href="#"><c:out value="${s.id}"/></a>
				</td>
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
			</tr>
		</c:forEach>
		</table>
	</form>
</div>
</body>
</html>