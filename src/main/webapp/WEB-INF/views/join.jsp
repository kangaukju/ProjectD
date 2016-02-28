<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="menu.jspf" %>
	<div id="splash" class="subline">
		<div class="sub-nav-img">
			<div class="chart3">화면가입</div>
		</div>
		<table>
			<tr>
				<td style="text-align: center; padding-left: 150px;">
					<a href="/join/seeker.do" style="text-decoration: none;">
						<img class="login" src="/img/seeker.png"><br>
						<span style="font: bold 2.5em sans-serif;">일반회원</span>
					</a>
				</td>
				<td></td>
				<td style="text-align: center; padding-right: 150px;">
					<a href="/join/offerer.do" style="text-decoration: none;">
						<img  class="login" src="/img/offerer.png"><br>
						<span style="font: bold 2.5em sans-serif;">고용주</span>
					</a>
				</td>
				<td></td>
				<td style="text-align: center; padding-right: 150px;">
					<a href="/join/admin.do" style="text-decoration: none;">
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