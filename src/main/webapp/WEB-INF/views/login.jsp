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
	
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="people5">로그인</div>
		</div>
		<table>
			<tr>
				<td style="text-align: center; padding-left: 150px;">
					<a href="/login/seeker.do" style="text-decoration: none;">
						<img class="login" src="/img/seeker.png"><br>
						<span style="font: bold 2.5em sans-serif;">일반회원</span>
					</a>
				</td>
				<td></td>
				<td style="text-align: center; padding-right: 150px;">
					<a href="/login/offerer.do" style="text-decoration: none;">
						<img  class="login" src="/img/offerer.png"><br>
						<span style="font: bold 2.5em sans-serif;">고용주</span>
					</a>
				</td>
				<td></td>
				<td style="text-align: center; padding-right: 150px;">
					<a href="/login/admin.do" style="text-decoration: none;">
						<img  class="login" src="/img/offerer.png"><br>
						<span style="font: bold 2.5em sans-serif;">관리자</span>
					</a>
				</td>
			</tr>
		</table>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>
