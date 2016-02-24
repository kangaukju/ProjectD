<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<%@ include file="/WEB-INF/views/include/rsa.jspf" %>
<script language="JavaScript">
function makeTD() {
	var html = "";
	for(var i in arguments) {
		var pair = arguments[i];
		html += "<th>"+pair.name+"</th><td>"+pair.value+"</td>";
    }
	return html;
}
function makeTR() {
	var html = "<tr>";
	for(var i in arguments) {
		html += makeTD(arguments[i]);
    }
	return html+"</tr>";
}
$(document).ready(function() {
	var myUrl = "/admin/match/matchresult_offerer.do";
	
	setValueDefault($("#line"), '${line}', 10);
	setValue($("#id"), '${id}');
	setValue($("#name"), '${name}');
	
	$("#search").click(function() {
		var form = $("#form");
		$("#page").val(1);
		form.action = myUrl;
		form.submit();
	});
	
	$('#paging').paging({
		current : '${page}',
		max : '${navCount}',
		onclick : function(e, page) {
			var form = $("#form");
			$("#page").val(page);
			form.action = myUrl;
			form.submit();
		}
	});
	
	$("#line").change(function(){
		$("#search").trigger('click');
	});
	
	$(".data-table a").on("click", function() {
		var id = $("#id").val();
		var target = $(this).text();
		var url = "/admin/match/matchresult_detail.do";
		
		$.ajax({
			type : "POST",
			url : url+"?id="+id+"&target="+target,
			dataType : 'TEXT',
			cache : false,
			contentType: "application/text; charset=UTF-8",
			success : function(data) {
				//alert(decodeURI(data));
				//var jsontext = JSON.stringify(data);
				//alert(JSON.stringify(data))
				//alert(data)
				var result = JSON.parse(data);
				
				
				$("#detail").empty();
				
				var strTable = "<table class='data-table'>";
				
				var total = result.total;
				var seekers = result.seeker;
				var requirement = result.requirement;
				
				strTable += "<tr><td colspan='2' style='padding: 0px; margin: 0px;'><table><tr>";
				strTable += makeTR({name:"배정정보",value:""},
									{name:"상태", value:requirement.matchStatus});
				strTable += makeTR({name:"파트", value:requirement.workAbility}, 
									{name:"연령", value:requirement.workAbility});
				strTable += makeTR({name:"성별", value:requirement.gender}, 
									{name:"국적", value:requirement.nation});
				strTable += makeTR({name:"출근일자", value:requirement.workDate}, 
									{name:"주소", value:requirement.location.sidoName+" "+requirement.location.sigunguName});
				strTable += makeTR({name:"인원", value:requirement.person}, 
									{name:"", value:""});
				strTable += "</table></td></tr>"
				strTable += "<tr align='center'><th colspan='2'>추천 구직자 점수</th></tr>";
				
				for (var i=0; i<total; i++) {
					var seeker = seekers[i];				
					var score = seeker.score;
					strTable += "<tr style='border-top: 0px;'><td colspan='2' style='padding: 10px 0px 0px 0px; margin: 0px; border-top: 0px;'><table style='padding: 0px; margin: 0px; border-top: 0px;'><tr style='border-top: 0px;'>";
					strTable += makeTD(
						{name:"순위", value:seeker.rank},
						{name:"일자", value:score.workMday},
						{name:"파트", value:score.workAbility},
						{name:"성별", value:score.gender},
						{name:"국적", value:score.nation});
					strTable += "</tr></table></td></tr>";
					
					strTable += "<tr><td colspan='2' style='padding: 0px; margin: 0px;'><table style='padding: 0px; margin: 0px; border-top: 0px;;'><tr>";
					var matcher = seeker.matcher;
					strTable += makeTR(
						{name:"이름", value:matcher.name}, {name:"", value:""}
					);
					strTable += makeTR(
						{name:"전화", value:matcher.id}, {name:"국적", value:matcher.nation}
					);
					strTable += makeTR(
						{name:"출생", value:matcher.birth}, {name:"파트", value:matcher.workAbility}
					);
					strTable += makeTR(
						{name:"요일", value:matcher.workMday}, {name:"시간", value:matcher.workQtime}
					);						
					var jusos = "";
					for (var k in matcher.regions) {
						var region = matcher.regions[k];
						jusos += +region.sidoName+" "+region.sigunguName;	
					}
					strTable += "</tr></table></td></tr>";
				}
				strTable += "</table>";
				 $('#detail').append(strTable);
				
			},
			complete : function(data) {
				// 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
			},
			error : function(xhr, status, error) {
				alert("xhr="+xhr.status+"\nstatus="+status+"\nerror="+error);
			}
		});
	});
});
</script>
</head>
<body>
<div id="splash" style="float: left;">
	<h3>구직자 조회</h3>
	<form method="post" action="#" id="form">
		<input type="hidden" id="id" value="${param.id}">
		<table class="data-table">
			<tbody>
				<tr>
					<th>배정결과 내역</th>
				</tr>
				<c:forEach items="${list}" var="file">
				<tr>
					<td><a href="#"><c:out value="${fn:replace(file.name, '.json', '')}" /></a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

		<%@ include file="/WEB-INF/views/include/pagenumber.jspf" %>
		
	</form>
	<%@ include file="/WEB-INF/views/include/popup_close.jspf" %>
</div>
<div id="splash" style="width:10px; float: left;">
</div>
<div id="splash" style="float: left;">
	<h3>자세히</h3>
	<table id="detail">
	</table>
</div>
</body>
</html>