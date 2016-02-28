/**
 * 
 */
function responserValue(data) {
	return responserValueDef(data, null);
}
function responserValueDef(data, defValue) {
	if (data == undefined || data == null || data == "") {
		return defValue;
	}
	return data;
}
function responserAction() {
	responserAction(this.json);
}
function responserActionAfter(json, after) {
	var type = responserValue(json.type);
	var code = responserValue(json.code);
	var success = responserValueDef(json.success, '/home.do');
	var failure = responserValueDef(json.failure, '/error.do');
	var error = responserValue(json.error);
	var data = responserValue(json.data);
	var popup = responserValue(json.popup);
	var width = 300;
	var height = 150;
	
	var x = (screen.availWidth - width) / 2;
	var y = (screen.availHeight - height) / 2;
	
	if (type == null) {
		return;
	}
	if (type == 'SUCCESS') {
		if (after != null && after != '' && after != 'undefined') {
			after();
			return;
		}
		if (success == "nohup") {
			return;
		}
		if (popup) {
			window.open(success, "", "width="+width+",height="+height+",directories=0,titlebar=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,left="+x+",top="+y);
		} else {
			if ($(location).attr('pathname') == success) {
				window.location.reload();
			} else {
				window.location.replace(success);
			}
		}
	} else 
	if (type == 'FAILURE') {
		var url = failure+"?code="+code+"&error="+error+"&data="+data;
		if (popup) {
			window.open(url, "", "width="+width+",height="+height+",directories=0,titlebar=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,left="+x+",top="+y);
		} else {
			window.location.replace(url);
		}
	}
}
function responserAction(json) {
	responserActionAfter(json, null);
}
function Responser() { }

function Responser(json) {
	this.ok = false;
	
	if (json == null || json == '' || json == 'undefined') {
		return;
	}
	
	this.json = json;
		
	if (this.json.type == 'SUCCESS') {
		this.ok = true;
	}
}

Responser.prototype.action = responserAction;
Responser.prototype.actionAfter = responserActionAfter;
var Responser = new Responser();
