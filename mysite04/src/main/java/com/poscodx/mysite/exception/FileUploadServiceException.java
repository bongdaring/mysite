package com.poscodx.mysite.exception;

@SuppressWarnings("serial")
public class FileUploadServiceException extends RuntimeException {
	public FileUploadServiceException(String messge) {
		super(messge);
	}
	
	public FileUploadServiceException() {
		super("FileUploadService Exception Thrown");
	}
}
