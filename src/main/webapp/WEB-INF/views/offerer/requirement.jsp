<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">

$(document).ready(function() {
	
	$("#join").click(function() {
		if (!checkvalue($("#workDate1").val())) {
			alert("근무일자를 꼭 입력하세요.");
			$("#workDate1").focus();
			return;
		} else
		if (!checkvalue($("#workDate2").val())) {
			alert("출근시간을 꼭 입력하세요.");
			$("#workDate2").focus();
			return;
		} else
		if (!checkvalue($("#workTime").val())) {
			alert("근무시간을 꼭 입력하세요.");
			$("#workTime").focus();
			return;
		} else
		if (!checkvalue($('input:radio[name="workAbility"]:checked').val())) {
			alert("업무를 꼭 입력하세요.");
			$("#workAbility").focus();
			return;
		} else
		if (!checkvalue($('input:radio[name="gender"]:checked').val())) {
			alert("성별을 꼭 입력하세요.");
			$("#gender").focus();
			return;
		} else
		if (!checkvalue($("#ageRange").val())) {
			alert("연령을 꼭 입력하세요.");
			$("#ageRange").focus();
			return;
		} else
		if (!checkvalue($('input:radio[name="nation"]:checked').val())) {
			alert("국적을 꼭 입력하세요.");
			$("#nation").focus();
			return;
		}
		
		var rsa = new RSAKey();
		rsa.setPublic($("#publicKeyModulus").val(), $("#publicKeyExponent").val());
		$("#offererId").val(rsa.encrypt($("#myoffererId").val()));
		
		var formData = $("#form").serialize();
		$.ajax({
			type : "POST",
			url : '/offerer/requirement_r.do',
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
				alert("xhr="+xhr+"\nstatus="+status+"\nerror="+error);
			}
		});
	});
	
	$("#workDate1").datepicker({
		dateFormat: 'yy-mm-dd',
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		minDate: "+1",
		maxDate: "+100",
	});
});	

</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../menu.jspf" %>
	
	<div id="splash">
		<h3>배정요청</h3>
		
		<form method="post" action="#" id="form">
			<input type="hidden" id="offererId" name="offererId" />
			<input type="hidden" id="myoffererId" value='<c:out value="${offerer.id}" />' />
			<input type="hidden" id="publicKeyModulus"  value='<c:out value="${publicKeyModulus}" />' />
			<input type="hidden" id="publicKeyExponent" value='<c:out value="${publicKeyExponent}" />' />
			<table class="data-table">
				<tbody>
					<tr>
						<th>업체명</th>
						<td><c:out value="${offerer.id}"/> (<c:out value="${offerer.offererName}"/>)</td>
					</tr>
					<tr>
						<th>근무일자</th>
						<td>
							<input type="text" id="workDate1" name="workDate1" class="text">(익일부터 신청가능)
						</td>
					</tr>
					<tr>
						<th>출근시간</th>
						<td>
							<select id="workDate2" name="workDate2">
								<c:forEach var="i" begin="1" end="24" step="1">
								<c:choose>
									<c:when test="${i ==8}">
										<option value="${i}" selected="selected">
									</c:when>
									<c:otherwise>
										<option value="${i}">
									</c:otherwise>
								</c:choose>
									<fmt:formatNumber pattern="00" value="${i}"/>:00
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>근무시간</th>
						<td>
							<select id="workTime" name="workTime">
								<c:forEach var="i" begin="5" end="8" step="1">
									<option value="${i}"><c:out value="${i}"/> 시간</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>업무</th>
						<td>
							<c:forEach items="${context.WorkAbility}" var="row">
								<input type="radio" name="workAbility" id='<c:out value="${row.workAbility}"/>' value='<c:out value="${row}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>인원</th>
						<td>
							<select id="person" name="person">
								<c:forEach var="i" begin="1" end="3" step="1">
									<option value="${i}"><c:out value="${i}"/> 명</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>성별</th>
						<td>
							<c:forEach items="${context.Gender}" var="row">
								<input type="radio" name="gender" id='<c:out value="${row.gender}"/>' value='<c:out value="${row}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>연령</th>
						<td>
							<select id="ageRange" name="ageRange">
								<c:forEach var="i" begin="20" end="60" step="10">
									<option value="${i}"><c:out value="${i}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>국적</th>
						<td>
							<c:forEach items="${context.Nation}" var="row">
								<input type="radio" name="nation" id='<c:out value="${row.nation}"/>' value='<c:out value="${row}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input type="button" id="join" class="smallbutton" value="배정요청">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>