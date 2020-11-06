package com.bridgelabz.addressbookjdbc;

public class AddressBookServiceException extends Exception {
	enum ExceptionType {
		UNABLE_TO_LOAD_DRIVER, UNABLE_TO_RETRIEVE_DATA, UNABLE_TO_UPDATE_DATA;
	}

	ExceptionType type;

	public AddressBookServiceException(String message, ExceptionType type) {
		super(message);
		type = this.type;
	}
}
