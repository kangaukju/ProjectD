<!-- 
	Popuper
 -->
<script language="JavaScript">
$(document).ready(function() {
	{
		if ("x${popuper.name}" == "x") {
			$('#myModal').css("display", "none");
			return;
		}
		var myModalHeight = "${popuper.height}";
		var myModalWidth = "${popuper.width}";
		var myModalHead = "${popuper.head}";
		var myModalBody = "${popuper.body}";
		var myModalFoot = "${popuper.foot}";
				
		if (myModalHeight != 0) {
			//$('#myModal').css("height", myModalHeight);
		}
		if (myModalWidth != 0) {
			//$('#myModal').css("width", myModalWidth);
		}
		if (myModalHead != "") {
			$('#myModalHead').text(myModalHead);
		}
		if (myModalBody != "") {
			$('#myModalBody').text(myModalBody);
		}
		if (myModalFoot != "") {
			$('#myModalFoot').text(myModalFoot);
		}
		
		$('#myModalCloser').click(function(){
			$('#myModal').css("display", "none");
		});
		$('#myModal').css("display", "block");
	}
});
</script>