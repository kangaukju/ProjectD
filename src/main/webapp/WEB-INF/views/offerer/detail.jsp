<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<script language="JavaScript">
$(document).ready(function() {
});
</script>
</head>
<body>
<div>
	<form method="post" action="#" id="form">
		<table class="data-table">
			<tr>
				<td>
				<table>
					<tr>
						<th>아이디</th>
						<td><c:out value="${offerer.id}" /></td>
					</tr>
					<tr>
						<th>이름</th>
						<td><c:out value="${offerer.name}" /></td>
					</tr>
					<tr>
						<th>상호</th>
						<td><c:out value="${offerer.offererName}" /></td>
					</tr>
					<tr>
						<th>사업자번호</th>
						<td><c:out value="${offerer.offererNumber}" /></td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td><c:out value="${offerer.phone}" /></td>
					</tr>
					<tr>
						<th>휴대폰</th>
						<td><c:out value="${offerer.cellPhone}" /></td>
					</tr>
					<tr>
						<th>업종</th>
						<td><c:out value="${offerer.businessType}" /></td>
					</tr>
					<tr>
						<th>설명</th>
						<td><c:out value="${offerer.businessType}" /></td>
					</tr>
					<tr>
						<th>등록일자</th>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${offerer.registerDate}" /></td>						
					</tr>
				</table>
				</td>
				<td>
					<table>
						<tr>
							<th>주소</th>
							<td>
								<c:out value="${offerer.address1}" /><br>
								<c:out value="${offerer.address2}" />
							</td>
						</tr>
						<tr>
							<th></th>
							<td><img src="${offerer.mapFilename}"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>