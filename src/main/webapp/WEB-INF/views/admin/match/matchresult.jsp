<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
var myUrl = "/admin/match/matchresult.do";
$(document).ready(function() {
	setValue($("#id"), '${id}');
	setValue($("#name"), '${name}');
	setValue($("#offererName"), '${offererName}');
	setValue($("#filterDate1"), '${filterDate1}');
	setValue($("#filterDate2"), '${filterDate2}');
	
	$("#filterDate1").datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	});
	$("#filterDate2").datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	});
	
	$(".offerer").on("click", function() {
		var offererId = $(this).attr("id");
		var form = $("#form");
		form.action = myUrl;
		$("#offerer").val(offererId);
		form.submit();
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="chart3">배정이력</div>
		</div>
		<form method="post" id="form">
			<input type="hidden" name="offerer" id="offerer">
			<table class="data-table">
				<tr>
					<th>고용주 ID</th>
					<td><input type="text" id="id" name="id" class="text w70" ></td>
					<th>고용주 이름</th>
					<td><input type="text" id="name" name="name" class="text w70" ></td>
					<th>상호</th>
					<td><input type="text" id="offererName" name="offererName" class="text w90" ></td>
					<th>배정날짜</th>
					<td>
						<input type="text" id="filterDate1" name="filterDate1" class="text w70">
						~
						<input type="text" id="filterDate2" name="filterDate2" class="text w70">
					</td>
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
				<th>배정내역</th>
				<c:forEach items="${list}" var="o">
				<tr>
					<td><c:out value="${o.id}" /></td>
					<td><c:out value="${o.name}" /></td>
					<td><c:out value="${o.offererName}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${o.registerDate}" /></td>
					<td><c:out value="${o.offererNumber}" /></td>
					<td><c:out value="${o.phone}" /></td>
					<td><c:out value="${o.cellPhone}" /></td>
					<td class="offerer">
						<input type="button" id="${o.id}" value="배정보기" class="smallbutton">
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<%@ include file="/WEB-INF/views/include/pagenumber.jspf" %>
			
			<!-- 배정이력정보를 불러온 구직자 목록 -->
			<c:if test="${resultList != '' && resultList ne null}">
			<div class="sub-nav-img">
				<div class="chart3">자동배정이력</div>
			</div>
			<table>
				<tr>
					<td>
						<table class="data-table">
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
						<c:forEach items="${resultList}" var="a">
							<c:set var="r" value="${a.requirement}"/>
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
								<td><c:out value="${fn:length(a.seekers)}/${r.person}" /></td>
								<td class="person">
									<input type="button" class="smallbutton" value="보기" id="${r.id}">
								</td>
							</tr>
						</c:forEach>
						</table>
					</td>
				</tr>
			</table>
			</c:if>
		</form>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>