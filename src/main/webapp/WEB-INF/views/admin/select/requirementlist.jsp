<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
	
	var myUrl = "/admin/select/requirement.do"
	
	setValue($("#line"), '${line}');
	setValue($("#workAbility"), '${context.WorkAbility}');
	setValue($("#id"), '${id}');
	setValue($("#name"), '${name}');
	
	$("#search").click(function() {
		var form = $("#form");
		$("#page").val(1);
		form.action = myUrl;
		form.submit();
	});
	
	$('#paging').paging({
		current : '${page}',
		max : '${navCount}',
		onclick : function(e, page) {
			var form = $("#form");
			$("#page").val(page);
			form.action = myUrl;
			form.submit();
		}
	});
	
	$(".data-table .offerer a").on("click", function() {
		var popupUrl = "/offerer/detail.do";
		var width = 700;
		var height = 300;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var offererId = $(this).children(':input').val();
		window.open(popupUrl+"?id="+offererId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
	});
	
	$(".data-table .person a").on("click", function() {
		var popupUrl = "/admin/match/seekerlist.do";
		var width = 850;
		var height = 300;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var requirementId = $(this).children(':input').val();
		window.open(popupUrl+"?requirementId="+requirementId, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash">
		<h3>업체 조회</h3>
		<form method="post" action="#" id="form">
			<table class="data-table">
				<tbody>
					<tr>
						<th>전화번호</th>
						<td><input type="text" id="id" name="id" class="text" ></td>
						<th>이름</th>
						<td><input type="text" id="name" name="name" class="text" ></td>
						<th>가능업무</th>
						<td>
							<select name="workAbility">
								<option value=''>선택안함 </option>
							<c:forEach items="${context.WorkAbility}" var="row">
								<option value="${row.workAbility}"><c:out value="${row}"/></option>
							</c:forEach>
							</select>
						<td>
						<td><input type="button" id="search" value="검색" class="button"></td>
					</tr>
				</tbody>
			</table>
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
					<th>근무인원</th>
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
						<td><c:out value="${r.person}" /></td>
						<td class="person">
							<a href="#">
								자세히<input type="hidden" value="${r.id}" />
							</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type="hidden" name="page" id="page">
			<div id="paging"></div>
			<select id="line" name="line">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="5">5</option>
				<option value="10">10</option>
			</select>
		</form>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>