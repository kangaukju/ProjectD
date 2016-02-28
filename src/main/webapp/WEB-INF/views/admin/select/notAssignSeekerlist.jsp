<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<script language="JavaScript">
var myUrl = "/admin/select/notAssignSeekerlist.do"
$(document).ready(function() {
	setValue($("#workAbility"), '${workAbility}');
	setValue($("#id"), '${id}');
	setValue($("#name"), '${name}');
	
	$("#search").click(function() {
		var form = $("#form");
		$("#page").val(1);
		form.action = myUrl;
		form.submit();
	});
	
	$("#assign").click(function() {
		var length = $('input:checkbox[name="seekers"]:checked').length;
		if (length == 0) {
			return;
		}
		var assignedCount = ${assignedCount};
		if (assignedCount + length > '${requirement.person}') {
			alert("배정인원을 초과하였습니다.\n[${requirement.offererName}]은 최대 배정인원은 ${requirement.person}명입니다.");
			return;
		}
		var msg="";
		$('input:checkbox[name="seekers"]:checked').each(function() {
			msg += $(this).attr("id")+" 회원님\n";
		});
		if (confirm(msg+"정말로 배정하시겠습니까?") == false) {
			return;
		}
		var formData = $("#form").serialize();
		$.ajax({
			type : "POST",
			url : "/admin/match/manualmatch.do",
			dataType : 'JSON',
			cache : false,
			data : formData,
			success : function(data) {
				Responser.action(data);
				opener.location.reload();
				self.close();
			},
			complete : function(data) {
				// 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
			},
			error : function(xhr, status, error) {
				alert("xhr="+xhr.status+"\nstatus="+status+"\nerror="+error);
			}
		});
		
	});
});
</script>
</head>
<body>
<form method="post" id="form">
<div id="splash" class="subline" style="padding-top: 0px; margin-top: 0xp;">
	<div class="sub-nav-img">
		<div class="people5">${requirement.offererName}</div>
	</div>
	<input type="hidden" name="requirementId" value="${param.requirementId}">
	
	<table class="data-table">
		<th>배정번호</th>
		<th>상호</th>
		<th>배정상태</th>
		<th>업무</th>
		<th>출근일자</th>
		<th>근무시간</th>
		<th>근무지역</th>
		<th>연령</th>
		<th>성별</th>
		<th>국적</th>
		<tr>
			<td><c:out value="${requirement.id}"/></td>						
			<td class="offerer">
				<a href="#">
					<c:out value="${requirement.offererName}" />
				</a>
			</td>
			<td>${requirement.matchStatus}</td>
			<td>${requirement.workAbility}</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd" value="${requirement.workDate}" /></td>
			<td><c:out value="${requirement.workTime}" /></td>
			<td><c:out value="${requirement.location.sigunguName}" /></td>
			<td><c:out value="${requirement.ageRange}"></c:out></td>
			<td>${requirement.gender}</td>
			<td>${requirement.nation}</td>
		</tr>
	</table>
	
	<div class="sub-nav-img">
		<div class="people5">미배정 구직자 목록</div>
	</div>
	<table class="data-table">
		<tr>
			<th>전화번호</th>
			<td><input type="text" id="id" name="id" class="text" ></td>
			<th>이름</th>
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
			<th></th>
			<td><input type="button" id="search" value="검색" class="smallbutton"></td>
		</tr>
	</table>
		
	<table class="data-table">
		<th></th>
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
		
		<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="12" style="text-align: center;">미배정된 구직자가 없습니다.</td>
		</tr>
		</c:if>
		<c:forEach items="${list}" var="s">
		<tr>
			<td>
				<input type="checkbox" name="seekers" value="${s.id}" id="${s.name}">
			</td>
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
		</tr>
		</c:forEach>
	</table>
		
	<%@ include file="/WEB-INF/views/include/pagenumber.jspf" %>
	
	<div style="text-align: center; padding: 10px; 10px; 0px; 0px;">
		<input type="button" id="assign" value="배정하기" class="bigbutton" style="width: 30%;">
	</div>
</div>
</form>
</body>
</html>