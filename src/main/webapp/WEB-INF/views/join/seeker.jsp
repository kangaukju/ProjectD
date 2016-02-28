<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
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
			// 0은 전지역이다
			$("#region1").val("0").attr("selected", "selected");
			$("#region2").val("0").attr("selected", "selected");
			$("#region3").val("0").attr("selected", "selected");
		}
	});
	
	$("#join").click(function() {
		var v = new Validator();
		v.add($("#myid1"), "전화번호를 꼭 입력하세요.");
		v.add($("#myid2"), "전화번호를 꼭 입력하세요.");
		v.add($("#myid3"), "전화번호를 꼭 입력하세요.");
		v.add($("#myname"), "이름을 꼭 입력하세요.");
		v.add($("#years"), "태어난 해를 꼭 입력하세요.");
		v.add($("#region1"), "업무지역1을 꼭 입력하세요.");
		v.add($("#region2"), "업무지역2을 꼭 입력하세요.");
		v.add($("#region3"), "업무지역3을 꼭 입력하세요.");		
		if (!v.isValid()) return;
		
		$("#myid").val($("#myid1").val()+"-"+$("#myid2").val()+"-"+$("#myid3").val());
		
		if (!isPhoneNumber($("#myid").val())) {
			alert("전화번호 형식이 올바르지 않습니다.");
			$("#myid").focus();
			return;
		}		
		if (!checkvalue($('input:radio[name="gender"]:checked').val())) {
			$('input:radio[name="gender"]').focus();
			alert("성별을 꼭 입력하세요.");
			return;
		}
		if (!checkvalue($('input:radio[name="nation"]:checked').val())) {
			alert("국적을 꼭 입력하세요.");
			$("#nation").focus();
			return;
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
		
		if ($("#mypassword").val() != $("#mypassword1").val()) {
			alert("동일한 비밀번호를 입력하세요.");
			$("#mypassword1").focus();
			return;
		}
		
		var rsa = new RSAKey();
		rsa.setPublic($("#publicKeyModulus").val(), $("#publicKeyExponent").val());
		$("#id").val(rsa.encrypt($("#myid").val()));
		$("#name").val(rsa.encrypt($("#myname").val()));
		$("#password").val(rsa.encrypt($("#mypassword").val()));
		
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
	
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="loginme">일반회원 가입</div>
		</div>
		
		<form method="post" id="form">
			<input type="hidden" id="publicKeyModulus"  value='<c:out value="${publicKeyModulus}" />' />
			<input type="hidden" id="publicKeyExponent" value='<c:out value="${publicKeyExponent}" />' />
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="myid" name="myid" />
			<table class="data-table">
				<tr>
					<th>전화번호</th>
					<td>
						<select id="myid1" class="w60">
							<option value="010">010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="018">018</option>
							<option value="019">019</option>
						</select>
						-
						<input type="text" id="myid2" class="text w40"/>
						-
						<input type="text" id="myid3" class="text w40"/>												
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="hidden" id="name" name="name" /> 
						<input type="text" id="myname" class="text" placeholder="이름" />
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="hidden" id="password" name="password" />
						<input type="password" class="text" id="mypassword" />
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" class="text" id="mypassword1" />
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>						
						<c:forEach items="${context.Gender}" var="row">
							<input type="radio" name="gender" id='<c:out value="${row}"/>' value='<c:out value="${row}"/>'>
							<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>출생년도</th>
					<td>
						<select name="years" id="years">
							<c:forEach items="${context.CommonContext[0].years}" var="row">
								<option value='<c:out value="${row}"/>'><c:out value="${row}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>국적</th>
					<td>
						<c:forEach items="${context.Nation}" var="row">
							<input type="radio" name="nation" id='<c:out value="${row}"/>' value='<c:out value="${row}"/>'>
							<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
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
						<label for="regionAllow">서울 전지역</label>
					</td>
				</tr>
				<tr>
					<th>가능업무</th>
					<td>
						<c:forEach items="${context.WorkAbility}" var="row">
							<input type="radio" name="workAbility" id='<c:out value="${row}"/>' value='<c:out value="${row}"/>'>
							<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>업무요일</th>
					<td>
						<c:forEach items="${context.MdayBit}" var="row">
							<input type="checkbox" name="mday" id='<c:out value="${row}"/>' value='<c:out value="${row}"/>'>
							<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
						</c:forEach>
						<input type="checkbox" name="mdayAllow" id="mdayAllow">
						<label for="mdayAllow">전체선택</label>
					</td>
				</tr>
				<tr>
					<th>업무시간</th>
					<td>
						<c:forEach items="${context.QtimeBit}" var="row">
							<input type="checkbox" id='<c:out value="${row}"/>' name="qtime" value='<c:out value="${row}"/>'>
							<label for='<c:out value="${row}"/>'><c:out value="${row}"/></label>
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
						<input type="button" id="join" value="회원가입" class="bigbutton">
					</td>
				</tr>
			</table>
		</form>		
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>