<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
function login(target) {
	if (!checkvalue($("#my"+target+"Id").val())) {
		alert("아이디를 꼭 입력하세요.");
		$("#my"+target+"Id").focus();
		return;
	} else
	if (!checkvalue($("#my"+target+"Password").val())) {
		alert("비밀번호를 꼭 입력하세요.");
		$("#my"+target+"Password").focus();
		return;
	}
	
	var rsa = new RSAKey();
	rsa.setPublic($("#publicKeyModulus").val(), $("#publicKeyExponent").val());
	$("#"+target+"Id").val(rsa.encrypt($("#my"+target+"Id").val()));
	$("#"+target+"Password").val(rsa.encrypt($("#my"+target+"Password").val()));	

	var formData = $("#"+target+"Form").serialize();
	$.ajax({
		type : "POST",
		url : "/login/"+target+"_r.do",
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
}
$(document).ready(function() {	
	$("#seekerLogin").click(function() {
		login("seeker");
	});
	$("#offererLogin").click(function() {
		login("offerer");
	});
	$("#adminLogin").click(function() {
		login("admin");
	});
		

	$("#myseekerId, #myseekerPassword").keydown(function(key) {
		if (key.keyCode == 13) {
			login("seeker");
		}
	});
	$("#myoffererId, #myoffererPassword").keydown(function(key) {
		if (key.keyCode == 13) {
			login("offerer");
		}
	});
	$("#myadminId, #myadminPassword").keydown(function(key) {
		if (key.keyCode == 13) {
			login("admin");
		}
	});
});
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="menu.jspf" %>
	
	<div class="main">
		<div class="left" id="main-content">
			<div class="loing_box">
				<h2 class="label label-green">구직자 로그인</h2>
				<form method="post" id="seekerForm">
					<input type="hidden" id="publicKeyModulus"  value='<c:out value="${publicKeyModulus}" />' />
					<input type="hidden" id="publicKeyExponent" value='<c:out value="${publicKeyExponent}" />' />
					<input type="hidden" name="seekerId" id="seekerId">
					<input type="hidden" name="seekerPassword" id="seekerPassword">
					<table>
						<tr>
							<th>아이디</th>
							<td><input type="text" id="myseekerId" class="text w150"></td>
							<td rowspan="2">
								<input type="button" id="seekerLogin" value="로그인" class="bigbutton">
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input type="password" id="myseekerPassword" class="text w150"></td>
						</tr>
					</table>
				</form>
			</div>
			
			<div class="loing_box">
				<h2 class="label label-orange">업체 로그인</h2>
				<form method="post" id="offererForm">
					<input type="hidden" id="publicKeyModulus"  value='<c:out value="${publicKeyModulus}" />' />
					<input type="hidden" id="publicKeyExponent" value='<c:out value="${publicKeyExponent}" />' />
					<input type="hidden" name="offererId" id="offererId">
					<input type="hidden" name="offererPassword" id="offererPassword">
					<table>
						<tr>
							<th>아이디</th>
							<td><input type="text" id="myoffererId" class="text w150"></td>
							<td rowspan="2">
								<input type="button" id="offererLogin" value="로그인" class="bigbutton">
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input type="password" id="myoffererPassword" class="text w150"></td>
						</tr>
					</table>
				</form>
			</div>
			
			<div class="loing_box">			
				<h2 class="label label-blue">관리자 로그인</h2>
				<form method="post" id="adminForm">
					<input type="hidden" id="publicKeyModulus"  value='<c:out value="${publicKeyModulus}" />' />
					<input type="hidden" id="publicKeyExponent" value='<c:out value="${publicKeyExponent}" />' />
					<input type="hidden" name="adminId" id="adminId">
					<input type="hidden" name="adminPassword" id="adminPassword">
					<table>
						<tr>
							<th>아이디</th>
							<td><input type="text" id="myadminId" class="text w150" value="projecta"></td>
							<td rowspan="2">
								<input type="button" id="adminLogin" value="로그인" class="bigbutton">
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input type="password" id="myadminPassword" class="text w150" value="qwe123"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- 
		<div class="right sidebar" id="sidebar"></div>		
		 -->
		 <div class="clearer">&nbsp;</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>
