<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
	
	var myUrl = "/admin/match/matchresult2.do";
	
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
	
	$('#line').change(function(){
		$("#page").val(1);
		//$('#line option:selected').val()
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
	
	$(".data-table a").on("click", function() {
		var target = $(this).text();
		$('#target').val(target);
		
		var form = $("#form");
		form.action = "/admin/match/matchresult2.do";
		form.submit();
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash">
		<h3>매칭결과 조회</h3>
		<form method="post" action="#" id="form">
			<input type="hidden" name="target">
			<table class="data-table">
				<tbody>
					<tr>
						<th>업체명</th>
						<td><input type="text" id="offererName" name="offererName" class="text" ></td>
						<td><input type="button" id="search" value="검색" class="button"></td>
					</tr>
				</tbody>
			</table>
			<table class="data-table">
				<tbody>
					<th>업체명</th>
					<c:forEach items="${list}" var="f">
					<tr>
						<td>
							<a href="#"><c:out value="${f.name}" /></a>
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