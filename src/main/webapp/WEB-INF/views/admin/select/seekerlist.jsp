<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
	
	var myUrl = "/admin/select/seekerlist.do";
	
	setValue($("#line"), '${line}');
	setValue($("#workAbility"), '${workAbility}');
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
	});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash">
		<h3>구직자 조회</h3>
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
				</tbody>
			</table>
			<table class="data-table">
				<tbody>
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
					<th>본인인증</th>
					<!-- 
					<th>결제날짜</th>
					<th>만료날짜</th>
					-->
					<c:forEach items="${list}" var="s">
					<tr>
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
						<td><c:out value="${s.licenseFile}"/></td>
						<!-- 
						<td><c:out value="${s.payDate}"></c:out></td>
						<td><c:out value="${s.eosDate}"></c:out></td>
						-->
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