/***********************************************
 * 
 ***********************************************/
function checkvalue(val) {
	if (val == undefined || 
		val == 'undefined' || 
		val == '') {
		return false;
	}
	return true;
}

function getvalue(val) {
	if (val == undefined || 
		val == 'undefined' || 
		val == '') {
		return '';
	}
	return val;
}

function setValue(component, val) {
	setValueDefault(component, val, '');
}

function setValueDefault(component, val, def) {
	if (component == undefined || 
		component == 'undefined' || 
		component == '') {
		return;
	}
	if (val == undefined || val == '') {
		val = def;
	}
	component.val(val);
}

function isPhoneNumber(phone) {
	var phoneRegexp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
	return phoneRegexp.test(phone);
}

/***********************************************
 * 
 ***********************************************/
function validatorAdd(elementID, message) {
	this.checkArray.push({"id":elementID, "msg":message});
}

function validatorIsValid() {
	var i;
	var element;
	for (i=0; i<this.checkArray.length; i++) {
		element = this.checkArray[i];
		
		if (!checkvalue(element.id.val())) {
			alert(element.msg);
			element.id.focus();
			return false;
		}
	}
	return true;
}

function Validator() {
	this.checkArray = new Array();
}

Validator.prototype.add = validatorAdd;
Validator.prototype.isValid = validatorIsValid;
