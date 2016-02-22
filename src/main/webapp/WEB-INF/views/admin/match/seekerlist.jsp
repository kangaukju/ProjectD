<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
	
	// 임시배정 -> 배정확정하기
	$(".candidate .confirm .button").on("click", function() {
		var seekerId = $(".candidate .confirm .seekerId").val();
		var seekerName = $(".candidate .confirm .seekerName").val();
		$("#seekerId").val(seekerId);
		
		var r = confirm("["+seekerName+"]님의 배정을 확정하시겠습니까?");
		if (!r) {
			return;
		}
				
		var formData = $("#form").serialize();
		$.ajax({
			type : "POST",
			url : "/admin/match/seeker/confirm.do",
			dataType : 'JSON',
			cache : false,
			data : formData,
			success : function(data) {
				Responser.action(data);
			},
			complete : function(data) {
				// 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
			},
			error : function(xhr, status, error) {
				alert("xhr="+xhr.status+"\nstatus="+status+"\nerror="+error);
			}
		});
	});
	
	// 임시배정 -> 배정취소하기
	$(".candidate .cancel .button").on("click", function() {
		var seekerId = $(".candidate .cancel .seekerId").val();
		var seekerName = $(".candidate .cancel .seekerName").val();
		$("#seekerId").val(seekerId);
		
		var r = confirm("["+seekerName+"]님의 배정을 취소하시겠습니까?");
		if (!r) {
			return;
		}
				
		var formData = $("#form").serialize();
		$.ajax({
			type : "POST",
			url : "/admin/match/seeker/cancel.do",
			dataType : 'JSON',
			cache : false,
			data : formData,
			success : function(data) {
				Responser.action(data);
			},
			complete : function(data) {
				// 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
			},
			error : function(xhr, status, error) {
				alert("xhr="+xhr.status+"\nstatus="+status+"\nerror="+error);
			}
		});
	});
	
	// 배정확정 -> 배정취소하기
	$(".confirm .cancel .button").on("click", function() {
		var seekerId = $(".confirm .cancel .seekerId").val();
		var seekerName = $(".confirm .cancel .seekerName").val();
		$("#seekerId").val(seekerId);
		
		var r = confirm("["+seekerName+"]님의 배정을 취소하시겠습니까?");
		if (!r) {
			return;
		}
				
		var formData = $("#form").serialize();
		$.ajax({
			type : "POST",
			url : "/admin/match/seeker/cancel.do",
			dataType : 'JSON',
			cache : false,
			data : formData,
			success : function(data) {
				Responser.action(data);
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
<h3>추천 구직자</h3>
<form method="post" action="#" id="form">
	<input type="hidden" id="requirementId" name="requirementId" value="${param.id}">
	<input type="hidden" id="seekerId" name="seekerId">
	<table class="data-table">
		<tbody>
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
		
		<c:if test="${fn:length(candidateSeekerList) > 0}">
			<tr>
				<th colspan="13">[임시배정 구직자 목록]</th>
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
			<th>배정확정</th>
			<th>배정취소</th>
			<c:forEach items="${candidateSeekerList}" var="s">
			<tr class="candidate">
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
				<td class="confirm">
					<input type="button" class="button" value="확정">
					<input class="seekerId" type="hidden" value="${s.id}">
					<input class="seekerName" type="hidden" value="${s.name}">
				</td>
				<td class="cancel">
					<input type="button" class="button" value="취소">
					<input class="seekerId" type="hidden" value="${s.id}">
					<input class="seekerName" type="hidden" value="${s.name}">
				</td>
			</tr>
			</c:forEach>			
			<tr style="height: 10px;"></tr>
		</c:if>
		
		<c:if test="${fn:length(confirmSeekerList) > 0}">
			<tr>
				<th colspan="13">[배정확정 구직자 목록]</th>
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
			<th>배정취소</th>
			<th></th>
			<!-- 
			<th>결제날짜</th>
			<th>만료날짜</th>
			-->
			<c:forEach items="${confirmSeekerList}" var="s">
			<tr class="confirm">
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
				<td class="cancel">
					<input type="button" class="button" value="취소">
					<input class="seekerId" type="hidden" value="${s.id}">
					<input class="seekerName" type="hidden" value="${s.name}">
				</td>
				<td></td>
			</tr>
			</c:forEach>
		</c:if>
		</tbody>
	</table>
</form>
</body>
</html>