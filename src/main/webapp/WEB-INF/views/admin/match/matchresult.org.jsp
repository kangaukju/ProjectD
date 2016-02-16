<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/dtd.jspf" %>
<html>
<head>
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
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

	$(".data-table a").on("click", function() {
		var offerer = $(this).attr("offerer");
		$.ajax({
			type : "POST",
			url : '/admin/match/matchresult_detail.do?target='+offerer,
			dataType : 'TEXT',
			cache : false,
			contentType: "application/text; charset=UTF-8",
			success : function(data) {
				//alert(decodeURI(data));
				//var jsontext = JSON.stringify(data);
				//alert(JSON.stringify(data))
				alert(data)
				var root = JSON.parse(data);
				var results = root.results;
				
				$("#detail").empty();
				
				var strTable = "<table class='data-table'>";
				
				for(var i1 in results) {
					var result = results[i1];
					var total = result.total;
					
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
					strTable += "</table></td></tr>"
					
					strTable += "<tr align='center'><th colspan='2'>추천 구직자 점수</th></tr>";
					var seekers = result.seeker;
					for (var i2 in seekers) {
						var seeker = seekers[i2];						
						var score = seeker.score;
						strTable += "<tr><td colspan='2' style='padding: 0px; margin: 0px;'><table style='padding: 0px; margin: 0px; border-top: 0px;'><tr>";
						strTable += makeTD(
								{name:"순위", value:seeker.rank},
								{name:"근무일자", value:score.workMday},
								{name:"파트", value:score.workAbility},
								{name:"성별", value:score.gender},
								{name:"국적", value:score.nation});
						strTable += "</tr></table></td></tr>";
						
						strTable += "<tr><td colspan='2' style='padding: 0px; margin: 0px;'><table style='padding: 0px; margin: 0px; border-top: 0px;;'><tr>";
						var matcher = seeker.matcher;
						strTable += makeTR(
								{name:"이름", value:matcher.name+"<br>"+matcher.id}, {name:"국적", value:matcher.nation},
								{name:"출생", value:matcher.birth}, {name:"파트", value:matcher.workAbility}, 
								{name:"요일", value:matcher.workMday}, {name:"시간", value:matcher.workQtime}
							);						
						var jusos = "";
						for (var i3 in matcher.regions) {
							var region = matcher.regions[i3];
							jusos += +region.sidoName+" "+region.sigunguName;	
						}
						strTable += "</tr></table></td></tr>";
					}
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
<div id="site-wrapper">
	<%@ include file="../../menu.jspf" %>
	
	<div id="splash" style="width: 250px; float: left;">
		<h3>매칭결과</h3>
		<table class="data-table">
			<tr>
				<th>업체</th>
			</tr>
			<c:forEach items="${directoryList}" var="row" varStatus="i">
			<tr>
				<th>
					<a href="#" offerer="${row}">
						<c:out value="${offererMap[row].offererName} (${offererMap[row].name})" />
					</a>
				</th>
			</tr>
			</c:forEach>
		</table>
	</div>
	<div id="splash" style="width: 600px; float: left;">
		<h3>자세히</h3>
		<table id="detail">
		</table>
	</div>
	<div id="splash" style="width: 600px; float: left;">
		<h3>자세히</h3>
		<table id="detail">
		</table>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>
</body>
</html>