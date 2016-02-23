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
		var v = new Validator();
		
		v.add($("#myid"), "아이디를 꼭 입력하세요.");
		v.add($("#myname"), "이름을 꼭 입력하세요.");
		v.add($("#mypassword"), "비밀번호를 꼭 입력하세요.");
		v.add($("#mypassword1"), "비밀번호를 꼭 입력하세요.");
		
		if (!v.isValid()) return;
		
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
			url : '/join/admin_r.do',
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
		<h3>관리자 회원가입</h3>
		
		<form method="post" action="#" id="form">
			<input type="hidden" id="publicKeyModulus"  value='<c:out value="${publicKeyModulus}" />' />
			<input type="hidden" id="publicKeyExponent" value='<c:out value="${publicKeyExponent}" />' />
			<table class="data-table">
				<tbody>
					<tr>
						<th>아이디</th>
						<td>
							<input type="hidden" id="id" name="id" /> 
							<input type="text" id="myid" class="text" placeholder="아이디" />
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
						<th></th>
						<td>
							<input type="button" id="join" value="가입하기" class="bigbutton">
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