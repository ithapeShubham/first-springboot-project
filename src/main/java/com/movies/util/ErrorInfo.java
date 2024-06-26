package com.movies.util;

import java.time.LocalDateTime;

public class ErrorInfo {
private int code;
private String message;
private LocalDateTime timestamp;
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public LocalDateTime getTimestamp() {
	return timestamp;
}
public void setTimestamp(LocalDateTime timestamp) {
	this.timestamp = timestamp;
}
@Override
public String toString() {
	return "ErrorInfo [code=" + code + ", message=" + message + ", timestamp=" + timestamp + "]";
}

}
