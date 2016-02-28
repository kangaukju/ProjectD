<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>공지사항</title>
<style type='text/css'>
	A:link { text-decoration:none }
	A:visited { text-decoration:none }
	A:active { text-decoration:none }
	A:hover { text-decoration:none }
</style>
<script type="text/javascript">
// 하루동안 열리지 않게하는  스크립트 시작
function getCookie(name) {
	var nameOfCookie = name + "=";
	var x = 0;

	while (x <= document.cookie.length) {
		var y = (x + nameOfCookie.length);
		if (document.cookie.substring(x, y) == nameOfCookie) {
			if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
				endOfCookie = document.cookie.length;
			return unescape(document.cookie.substring(y, endOfCookie));
		}
		x = document.cookie.indexOf(" ", x) + 1;
		if (x == 0)
			break;
	}
	return "";
}

// 쿠키를 만듭니다. 아래 closePopup()함수에서 호출됩니다
function closePopup() {
	if (document.pop_form.pop_input.checked)
		setCookie("popup", "Y", 1); //오른쪽 숫자는 쿠키를 유지할 기간을 설정합니다.0으로하면 매번뜸.
	self.close();
}

// 체크후 닫기버튼을 눌렀을때 쿠키를 만들고 창을 닫습니다
function setCookie(name, value, expiredays) {
	var endDate = new Date();
	endDate.setDate(endDate.getDate() + expiredays);
	endDate.setHours(0, 0, -1);
	document.cookie = name + "=" + escape(value) + "; path=/; expires="+ endDate.toGMTString() + ";"
}
// 하루동안 열리지 않게하는  스크립트 끝
</script>
</head>

<body>
<form name="pop_form">
	<table style="width: 100%; height: 100%;">
		<tr height="420">
			<td>
				<!-- 공지사항 시작 -->
				<h3>* DB스키마가 변경되었습니다. 이전 데이터가 삭제될 가능성이 있습니다.</h3><br>
				<b style="color: red;">관리자계정: projecta/qwe123<br></b>
				[개발 기능 명세]<br>
				1. 회원가입<br>
				- 관리자는 원하는 만큼 추가할 수 있습니다.<br>
				<!-- 공지사항 끝 -->
			</td>
		</tr>
		<tr>
			<td>
				<p align="right">
					<input type="checkbox" value="1" name="pop_input" style="cursor:hand">
					<a href="javascript:closePopup();window.close();" title="클릭">
						<font size="2" face="돋움"  color="blue"><b>하루동안 열지 않음 [닫기]</b></font>
					</a>
				</p>
			</td>
		</tr>
	</table>
</form>
</body>
</html>