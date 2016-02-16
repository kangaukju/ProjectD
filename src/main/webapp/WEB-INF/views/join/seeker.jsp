<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">

$(document).ready(function() {	
	
	$("#allowAll").click(function(){
		//alert($(this).is(":checked"));
		$("input:checkbox[name=allow]").prop("checked", $(this).is(":checked"));
	});
	
	$("#mdayAllow").click(function(){
		$("input:checkbox[name=mday]").prop("checked", $(this).is(":checked"));
	});
	$("#qtimeAllow").click(function(){
		$("input:checkbox[name=qtime]").prop("checked", $(this).is(":checked"));
	});
	$("#regionAllow").click(function(){
		if ($(this).is(":checked")) {
			$("#region1").val("all").attr("selected", "selected");
			$("#region2").val("all").attr("selected", "selected");
			$("#region3").val("all").attr("selected", "selected");
		}
	});
	
	$("#join").click(function() {
		if (!checkvalue($("#phone").val())) {
			alert("전화번호를 꼭 입력하세요.");
			$("#phone").focus();
			return;
		} 
		if (!checkvalue($("#name").val())) {
			alert("이름을 꼭 입력하세요.");
			$("#name").focus();
			return;
		}
		if (!checkvalue($('input:radio[name="gender"]:checked').val())) {
			$('input:radio[name="gender"]').focus();
			alert("성별을 꼭 입력하세요.");
			return;
		}
		if (!checkvalue($("#years").val())) {
			alert("태어난 해를 꼭 입력하세요.");
			$("#years").focus();
			return;
		}
		if (!checkvalue($('input:radio[name="nation"]:checked').val())) {
			alert("국적을 꼭 입력하세요.");
			$("#nation").focus();
			return;
		}
		if (	!checkvalue($("#region1").val()) || 
				!checkvalue($("#region2").val()) ||
				!checkvalue($("#region3").val()))
		{
			if (!checkvalue($("#region1").val())) {
				alert("업무지역을 꼭 입력하세요.");
				$("#region1").focus();
				return;
			} 
			if (!checkvalue($("#region2").val())) {
				alert("업무지역을 꼭 입력하세요.");
				$("#region2").focus();
				return;
			} 
			if (!checkvalue($("#region3").val())) {
				alert("업무지역을 꼭 입력하세요.");
				$("#region3").focus();
				return;
			}
		}
		if (!checkvalue($('input:radio[name="workAbility"]:checked').val())) {
			alert("가능한 업무를 꼭 입력하세요.");
			$("#workAbility").focus();
			return;
		}
		if ($('input:checkbox[name="mday"]:checked').length == 0) {
			alert("업무요일을 꼭 입력하세요.");
			$("#mday").focus();
			return;
		}
		if ($('input:checkbox[name="qtime"]:checked').length == 0) {
			alert("업무시간을 꼭 입력하세요.");
			$("#qtime").focus();
			return;
		}
		
		var rsa = new RSAKey();
		rsa.setPublic($("#publicKeyModulus").val(), $("#publicKeyExponent").val());
		$("#id").val(rsa.encrypt($("#phone").val()));	
		
		var formData = $("#form").serialize();
		$.ajax({
			type : "POST",
			url : '/join/seeker_r.do',
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
});	

</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="../menu.jspf" %>
	
	<div id="splash">
		<h3>구직자 회원가입</h3>
		
		<form method="post" action="#" id="form">
			<input type="hidden" id="publicKeyModulus"  value='<c:out value="${publicKeyModulus}" />' />
			<input type="hidden" id="publicKeyExponent" value='<c:out value="${publicKeyExponent}" />' />
			<table class="data-table">
				<tbody>
					<tr>
						<th>전화번호</th>
						<td>
							<input type="hidden" id="id" name="id" /> 
							<input type="text" id="phone" class="text" value="010-7239-0421" placeholder="010-0000-0000" />
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input type="text" name="name" id="name" class="text" value="강석주" placeholder="Name" />
						</td>
					</tr>
					<tr>
						<th>성별</th>
						<td>						
							<c:forEach items="${context.Gender}" var="row">
								<input type="radio" name="gender" id='<c:out value="${row}"/>' value='<c:out value="${row.originalName}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row.name}"/></label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>출생년도</th>
						<td>
							<select name="years" id="years">
								<c:forEach items="${context.Common[0].years}" var="row">
									<option value='<c:out value="${row}"/>'><c:out value="${row}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>국적</th>
						<td>
							<c:forEach items="${context.Nation}" var="row">
								<input type="radio" name="nation" id='<c:out value="${row}"/>' value='<c:out value="${row.originalName}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row.name}"/></label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>업무지역</th>
						<td>
							<select name="region1" id="region1">
								<option value=''>선택안함</option>								
								<c:forEach items="${jusoSeoulList}" var="row">
									<option value='<c:out value="${row.id}"/>'><c:out value="${row.sigunguName}"/></option>
								</c:forEach>
							</select>
							<select name="region2" id="region2">
								<option value=''>선택안함</option>								
								<c:forEach items="${jusoSeoulList}" var="row">
									<option value='<c:out value="${row.id}"/>'><c:out value="${row.sigunguName}"/></option>
								</c:forEach>
							</select>
							<select name="region3" id="region3">
								<option value=''>선택안함</option>																		
								<c:forEach items="${jusoSeoulList}" var="row">
									<option value='<c:out value="${row.id}"/>'><c:out value="${row.sigunguName}"/></option>
								</c:forEach>
							</select>
							<input type="checkbox" name="regionAllow" id="regionAllow">
							<label for="regionAllow">전체선택</label>
						</td>
					</tr>
					<tr>
						<th>가능업무</th>
						<td>
							<c:forEach items="${context.WorkAbility}" var="row">
								<input type="radio" name="workAbility" id='<c:out value="${row}"/>' value='<c:out value="${row.originalName}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row.name}"/></label>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>업무요일</th>
						<td>
							<c:forEach items="${context.MdayBit}" var="row">
								<input type="checkbox" name="mday" id='<c:out value="${row}"/>' value='<c:out value="${row.originalName}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row.name}"/></label>
							</c:forEach>
							<input type="checkbox" name="mdayAllow" id="mdayAllow">
							<label for="mdayAllow">전체선택</label>
						</td>
					</tr>
					<tr>
						<th>업무시간</th>
						<td>
							<c:forEach items="${context.QtimeBit}" var="row">
								<input type="checkbox" id='<c:out value="${row}"/>' name="qtime" value='<c:out value="${row.originalName}"/>'>
								<label for='<c:out value="${row}"/>'><c:out value="${row.name}"/></label>
							</c:forEach>
							<input type="checkbox" name="qtimeAllow" id="qtimeAllow">
							<label for="qtimeAllow">전체선택</label>
						</td>
					</tr>
					<tr>
						<th>본인인증</th>
						<td></td>
					</tr>
					<tr>
						<th>결제</th>
						<td></td>
					</tr>
					<tr>
						<th>이용약관 동의</th>
						<td>
							<a href="#">자세히</a>
							<input type="checkbox" name="allow" id="allow1" value="allow1">
							<label for="allow1">동의</label>
						</td>
					</tr>
					<tr>
						<th>개인정보 취급약관 동의</th>
						<td>
							<a href="#">자세히</a>
							<input type="checkbox" name="allow" id="allow2" value="allow2">
							<label for="allow2">동의</label>
						</td>
					</tr>
					<tr>
						<th>상품이용 약관 동의</th>
						<td>
							<a href="#">자세히</a>
							<input type="checkbox" name="allow" id="allow3" value="allow3">
							<label for="allow3">동의</label>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input type="checkbox" id="allowAll" name="allowAll">
							<label for="allowAll">전체동의</label>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>								
							<a href="#" id="join">가입하기</a>
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