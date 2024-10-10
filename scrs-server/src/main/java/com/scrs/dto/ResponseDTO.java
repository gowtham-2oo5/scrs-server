package com.scrs.dto;

public class ResponseDTO {

	private String message;
	private Object data;
	private boolean success;

	public ResponseDTO(String message, Object data, boolean success) {
		super();
		this.message = message;
		this.data = data;
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "ResponseDTO [message=" + message + ", data=" + data + ", success=" + success + "]";
	}

}
