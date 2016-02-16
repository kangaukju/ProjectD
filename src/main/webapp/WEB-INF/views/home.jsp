<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
</head>
<body>

<div id="site-wrapper">
	<%@ include file="menu.jspf" %>

	<div class="main" id="main-two-columns">	
		<div class="left" id="main-content">Empty main content</div>	
		<div class="right sidebar" id="sidebar">Empty Sidebar</div>	
		<div class="clearer">&nbsp;</div>	
	</div>

	<div id="footer">	
		<div class="left" id="footer-left">	
			<img src="img/logo-small.gif" alt="" class="left" />	
			<p>&copy; 2013 Simple Organization. All rights Reserved</p>	
			<p class="quiet">
				<a href="http://templates.arcsin.se/">Website template</a> by <a
					href="http://arcsin.se/">Arcsin</a>
			</p>	
			<div class="clearer">&nbsp;</div>	
		</div>	
		<div class="right" id="footer-right">	
			<p class="large">
				<a href="#">Blog</a> <span class="text-separator">|</span> <a
					href="#">Events</a> <span class="text-separator">|</span> <a
					href="#">About</a> <span class="text-separator">|</span> <a
					href="#">Archives</a> <span class="text-separator">|</span> <strong><a
					href="#">Join Us!</a></strong> <span class="text-separator">|</span> <a
					href="#top" class="quiet">Page Top &uarr;</a>
			</p>	
		</div>	
		<div class="clearer">&nbsp;</div>	
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>