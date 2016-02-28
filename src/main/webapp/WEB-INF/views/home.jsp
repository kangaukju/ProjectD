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
		<div class="sub-nav-img">일꾼 소개</div>
		<div class="main" id="main-two-columns">
			<div class="left" id="main-content">
				<form method="post" id="form">
					<table>
						<tr>
							<td>메인 화면1</td>
						</tr>
						<tr>
							<td>메인 화면2</td>
						</tr>
					</table>
				</form>
			</div>	
			<div class="right sidebar" id="sidebar">
				사이드 바
			</div>	
			<div class="clearer">&nbsp;</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>