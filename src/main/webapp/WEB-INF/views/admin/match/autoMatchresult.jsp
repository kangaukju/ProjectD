<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
var myUrl = "/admin/match/autoMatchresult.do";
$(document).ready(function() {
	setValue($("#filterDate"), '${filterDate}');
	setValue($("#filterHour"), '${filterHour}');
	
	$("#filterDate").datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	});
	
	$(".result .smallbutton").click(function() {
		var filename = $(this).attr("id");
		var form = $("#form");
		$("#filename").val(filename);
		form.action = myUrl;
		form.submit();
	});
	
	$(".person .smallbutton").on("click", function() {
		var requirementId = $(this).attr("id");
		var url = "/offerer/match/seekerlist.do?requirementId="+requirementId;
		$( "#dialog" ).dialog({
			width: "800px",
			position: top,
			show: {
				effect: "blind",
				duration: 100
			},
			hide: {
				effect: "explode",
				duration: 100
			},
			closeOnEscape: false,
			open: function(event, ui) {
				$(".ui-dialog-titlebar-close", $(this).parent()).hide();
			},
		    dialogClass: "no-close",
		    buttons: [{
		        text: "OK",
		        click: function() {
		            $(this).dialog("close");
		        }
		    }]
		});
		$( "#dialog" ).load(url);
	});
});
</script>
</head>
<body>
<div id="dialog" title="배정 구직자 정보">
</div>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="chart3">자동배정이력</div>
		</div>
		<form method="post" id="form">
			<input type="hidden" name="filename" id="filename">
			<table class="data-table">
				<tr>
					<th class="w60">배정날짜</th>
					<td class="w100"><input type="text" id="filterDate" name="filterDate" class="text"></td>
					<th class="w60">배정시각</th>
					<td class="w100">
						<select id="filterHour" name="filterHour">
							<c:forEach var="i" begin="0" end="23" step="1">
								<option value="${i}" selected="selected">
									<fmt:formatNumber pattern="00" value="${i}"/>:00
								</option>
							</c:forEach>
						</select>
					</td>
					<th class="p50"></th>
					<td><input type="button" id="search" value="검색" class="smallbutton"></td>
				</tr>
			</table>
			<!-- 배정이력 목록 -->
			<table class="data-table">
				<th>번호</th>
				<th>이력정보</th>
				<th>이력보기</th>
			<c:if test="${fn:length(list) == 0 }">
				<tr>
					<td colspan="3" style="text-align: center;">데이터가 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach items="${list}" var="o" varStatus="i">
				<tr>
					<td><c:out value="${i.count}" /></td>
					<td><c:out value="${o.name}" /></td>
					<td class="result">
						<input type="button" id="${o.name}" value="자세히" class="smallbutton">
					</td>
				</tr>
			</c:forEach>
			</table>
			
			<%@ include file="/WEB-INF/views/include/pagenumber.jspf" %>
			
			<!-- 배정정보 목록 -->
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