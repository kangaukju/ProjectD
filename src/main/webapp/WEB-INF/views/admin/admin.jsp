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
	<%@ include file="../menu.jspf" %>
	
	<div id="splash">
		<div class="col3 left">
			<table>
				<tr>
					<td>
						<h2 class="label label-green"><a href="/admin/select/seeker.do">구직자조회</a></h2>
					</td>
				</tr>
				<tr>
					<td>
						<h2 class="label label-green"><a href="/offerer/offererlist.do">업체조회</a></h2>
					</td>
				</tr>
				<tr>
					<td>
						<h2 class="label label-green"><a href="/offerer/requirement.do">배정요청</a></h2>
					</td>
				</tr>
			</table>
		</div>
		<div class="col3-mid left">
			<table>
				<tr>
					<td>
						<h2 class="label label-orange"><a href="/admin/match/auto.do">자동배정</a></h2>
					</td>
				</tr>
				<tr>
					<td>
						<h2 class="label label-orange"><a href="/admin/match/manual.do">수동배정</a></h2>
					</td>
				</tr>
				<tr>
					<td>
						<h2 class="label label-orange"><a href="/admin/match/matchresult.do">배정결과</a></h2>
					</td>
				</tr>
				<tr>
					<td>
						<h2 class="label label-orange"><a href="/admin/select/requirementlist.do">배정조회</a></h2>
					</td>
				</tr>
			</table>
		</div>
		<div class="col3 right">
			<table>
				<tr>
					<td>
						<h2 class="label label-blue"><a href="/admin/select/juso.do">주소 조회</a></h2>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>