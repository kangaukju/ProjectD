<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
	
	$("#auto").click(function() {
		if (!confirm("자동배정을 정말 수행하시겠습니까?")) {
			return;
		}
		var form = $("#form");
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
			<div class="people5">자동배정</div>
		</div>
		<form method="post" id="form" action="/admin/match/auto.do?m=m8&s=s4">
		</form>
		<table>
			<tr>
				<td><input type="button" id="auto" class="bigbutton" style="width: 100%" value="자동배정 강제시행"></td>
			</tr>
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
					<c:if test="${fn:length(list) == 0}">
						<tr>
							<td colspan="12" style="text-align: center;">배정된 결과가 없습니다.</td>
						</tr>
					</c:if>
					<c:forEach items="${list}" var="a">
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
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>
