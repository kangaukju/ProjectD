<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<script language="JavaScript">
</script>
</head>
<body>
<div id="site-wrapper">
	<%@ include file="menu.jspf" %>
	
	<div id="splash">
		<div class="col3 left">
			<table>
				<tr>
					<td>
						<h2 class="label label-green"><a href="/join/seeker.do">구직자 가입</a></h2>
					</td>
				</tr>
				<tr>
					<td>
						<h2 class="label label-green"><a href="/join/offerer.do">고용주 가입</a></h2>
					</td>
				</tr>
			</table>	
		</div>
		
		<div class="col3-mid left">
			<table>
				<tr>
					<td>
						<h2 class="label label-orange"><a href="/offerer/requirement.do">배정신청</a></h2>
					</td>
				</tr>
			</table>	
		</div>
		
		<div class="col3 right">
			<table>
				<tr>
					<td>
						<h2 class="label label-blue"><a href="/join/admin.do">관리자 신청</a></h2>
					</td>
				</tr>
			</table>	
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>