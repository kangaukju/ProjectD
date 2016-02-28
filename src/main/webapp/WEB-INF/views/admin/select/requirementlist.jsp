<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
var myUrl = "/admin/select/requirementlist.do"
$(document).ready(function() {
	setValue($("#workAbility"), '${workAbility}');
	setValue($("#offererName"), '${offererName}');
	setValue($("#startTime"), '${startTime}');
	setValue($("#endTime"), '${endTime}');
	
	$(".offerer a").on("click", function() {
		var popupUrl = "/offerer/detail.do";
		var width = 650;
		var height = 500;
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var offererId = $(this).children(':input').val();
		window.open(popupUrl+"?id="+offererId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
	});
	
	$(".person .smallbutton").on("click", function() {
		var popupUrl = "/admin/match/seekerlist.do";
		var width = 950;
		var height = 500;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var requirementId = $(this).attr("id")
		window.open(popupUrl+"?requirementId="+requirementId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
	});
	
	$(".assign .smallbutton").on("click", function() {
		var popupUrl = "/admin/select/notAssignSeekerlist.do";
		var width = 950;
		var height = 500;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var requirementId = $(this).attr("id")
		window.open(popupUrl+"?requirementId="+requirementId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
	});
	// 출근날짜 검색
	$("#startTime").datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		showButtonPanel: true, 
		currentText: '오늘',
		closeText: '닫기', 
	});
	// 출근날짜 검색
	$("#endTime").datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		showButtonPanel: true, 
		currentText: '오늘',
		closeText: '닫기', 
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="chart3">배정현황</div>
		</div>
		<form method="post" id="form">
			<table class="data-table">
				<tr>
					<th>상호</th>
					<td><input type="text" id="offererName" name="offererName" class="text" ></td>						
					<th>업무</th>
					<td>
						<select name="workAbility" id="workAbility">
							<option value=''>선택안함 </option>
						<c:forEach items="${context.WorkAbility}" var="row">
							<option value="${row.workAbility}"><c:out value="${row}"/></option>
						</c:forEach>
						</select>
					<td>
					<th>출근날짜</th>
					<td>
						<input type="text" id="startTime" name="startTime" class="text w70"> ~ 
						<input type="text" id="endTime" name="endTime" class="text w70">
					</td>
					<th>지역</th>
					<td>
						<select name="location" id="location">
							<option value=''>선택안함</option>
							<c:forEach items="${jusoSeoulList}" var="row">
								<c:if test="${row.id != 0}">
								<option value='<c:out value="${row.sigunguId}"/>'><c:out value="${row.sigunguName}"/></option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<th></th>
					<td><input type="button" id="search" value="검색" class="smallbutton"></td>
				</tr>
			</table>
			<table class="data-table">
				<th>배정번호</th>
				<th>상호</th>
				<th>배정상태</th>
				<th>업무</th>
				<th>출근날짜</th>
				<th>출근시간</th>
				<th>근무시간</th>
				<th>근무지역</th>
				<th>연령</th>
				<th>성별</th>
				<th>국적</th>
				<th>근무인원</th>
				<th>배정내역</th>
				<th>수동배정</th>
				
				<c:if test="${fn:length(list) == 0 }">
				<tr>
					<td colspan="14" style="text-align: center;">배정된 결과가 없습니다.</td>
				</tr>
				</c:if>
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
					<td><fmt:formatDate pattern="HH:00" value="${r.workDate}" /></td>
					<td><c:out value="${r.workTime}"/>시간</td>
					<td><c:out value="${r.location.sigunguName}" /></td>
					<td><c:out value="${r.ageRange}"></c:out></td>
					<td>${r.gender}</td>
					<td>${r.nation}</td>
					<td><c:out value="${fn:length(assignMap[r.id])}/${r.person}" /></td>
					<td class="person">
						<input type="button" class="smallbutton" value="배정" id="${r.id}">
					</td>
					<td class="assign">
						<c:if test="${r.matchStatus.matchStatus == 0}">
							<input type="button" class="smallbutton" value="배정" id="${r.id}">
						</c:if>
					<td>
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