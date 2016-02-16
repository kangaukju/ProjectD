package kr.co.projecta.matching.controller;

import kr.co.projecta.matching.exception.AuthenticationException;
import kr.co.projecta.matching.exception.NotAccessableException;
import kr.co.projecta.matching.exception.NotNullException;
import kr.co.projecta.matching.exception.RSAException;
import kr.co.projecta.matching.exception.UserNotFoundException;

public class Responser {

	static enum TYPE {
		SUCCESS(1),
		FAILURE(0);
		private int value;
		TYPE(int value) {
			this.value = value;
		};
	}
	static enum CODE {
		FAILURE_AUTHENTICATION(6, "failure authentication"),
		USER_NOT_FOUND(5, "can't found user"),
		PARAMETERS_ERROR(4, "can't acceptable parameters"),
		NOT_ACCESSABLE(3, "can't access"),
		SERVER_ERROR(2, "server error"),
		GENERAL_ERROR(1, "general error"),
		DUPLICATE_KEY(0, "duplicated key");		
		private int value;
		String text;
		CODE(int value, String text) {
			this.value = value;
			this.text = text;
		};
	}
	
	TYPE type;
	CODE code;
	String success;
	String failure;
	String error;
	String data;
	boolean popup;
	
	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public Responser() {
		this("/home.do");
	}
	
	public Responser(String success) {
		this.type = TYPE.SUCCESS;
		this.success = success;
		this.popup = false;
	}
	
	public Responser(String success, boolean popup) {
		this.type = TYPE.SUCCESS;
		this.success = success;
		this.popup = popup;
	}
	
	public Responser(CODE code) {
		this(code, "");
	}
	
	public Responser(CODE code, boolean popup) {
		this(code, "");
		this.popup = popup;
	}
	
	public Responser(CODE code, Throwable e) {
		this(code, e.getMessage());
	}
	
	public Responser(CODE code, String data) {
		this.type = TYPE.FAILURE;
		this.code = code;
		this.error = code.text;
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public CODE getCode() {
		return code;
	}

	public void setCode(CODE code) {
		this.code = code;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	/*
	public static String failure(CODE code, String failurePage, String errorMessage) {
		return failure(code, failurePage, errorMessage, null);
	}
	public static String failure(CODE code, String failurePage, String errorMessage, Object extData) {
		JSONObject obj = new JSONObject();
		
		obj.put("type", TYPE.FAILURE);
		if (code != null) obj.put("code", code);		
		if (failurePage != null) obj.put("failure", failurePage);
		if (errorMessage != null) obj.put("error", errorMessage);
		if (extData != null) obj.put("ext", extData);
		
		return obj.toJSONString();
	}
	
	public static String success(String successPage) {
		return success(successPage);
	}
	public static String success(String successPage, Object extData) {
		JSONObject obj = new JSONObject();
		
		obj.put("type", TYPE.SUCCESS);
		if (successPage != null) obj.put("success", successPage);
		if (extData != null) obj.put("ext", extData);
		
		return obj.toJSONString();
	}
	*/
	
	public static interface Callback {
		public void callback() throws Exception;
	}
	
	public static Responser login(Callback callback) {
		try {
			callback.callback();
		} catch (NullPointerException e) {
			return new Responser(CODE.PARAMETERS_ERROR);
		} catch (NotAccessableException e) {
			return new Responser(CODE.NOT_ACCESSABLE);
		} catch (RSAException e) {
			return new Responser(CODE.SERVER_ERROR);
		} catch (NotNullException e) {
			return new Responser(CODE.PARAMETERS_ERROR);
		} catch (UserNotFoundException e) {
			return new Responser(CODE.USER_NOT_FOUND);
		} catch (AuthenticationException e) {
			return new Responser(CODE.FAILURE_AUTHENTICATION);
		}  
		catch (Exception e) {
			return new Responser(CODE.SERVER_ERROR);
		}
		return new Responser();
	}
}
