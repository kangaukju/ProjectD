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
	
	var popupUrl = "/offerer/detail.do";
	$(".data-table a").on("click", function() {
		var width = 600;
		var height = 300;		
		var x = (screen.availWidth - width) / 2;
		var y = (screen.availHeight - height) / 2;
		var id = $(this).text();
		window.open(popupUrl+"?id="+id, "", "width="+width+", height="+height+", toolbar=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left="+x+", top="+y);
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
								<option value="${row.originalName}"><c:out value="${row.name}"/></option>
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
					<th>업무</th>
					<th>출근일자</th>
					<th>근무시간</th>
					<th>근무지역</th>
					<th>연령</th>
					<th>성별</th>
					<th>국적</th>
					<c:forEach items="${list}" var="s">
					<tr>
						<td><c:out value="${s.id}"></c:out></td>						
						<td>
							<a href="#">								
								<c:out value="${s.offererName}" />
								<input type="hidden" value="${s.offererId}" />
							</a>
						</td>
						<td>${naming:workAbility(s.workAbility).name}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${s.workDate}" /></td>
						<td><c:out value="${s.workTime}" /></td>
						<td><c:out value="${s.location.sigunguName}" /></td>
						<td><c:out value="${s.ageRange}"></c:out></td>
						<td>${naming:gender(s.gender).name}</td>
						<td>${naming:nation(s.nation).name}</td>
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