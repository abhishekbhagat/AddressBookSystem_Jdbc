package com.bridgelabz.addressbookjdbc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AddressBookService {
	private List<AddressBookContacts> addressBookList = new ArrayList<AddressBookContacts>();

	public List<AddressBookContacts> readAddressBookContact() throws AddressBookServiceException {

		return this.addressBookList = new AddressBookDBService().retrieveDataFromDB();
	}

	public Boolean updatePhoneNumber(String phoneNumber, int id) throws AddressBookServiceException {
		int result = new AddressBookDBService().updatePhoneNumberOfGivenPersonID(phoneNumber, id);
		if (result == 0)
			return false;
		else
			return true;
	}
	public List<AddressBookContacts> readDataByDateRange(Date startDate, Date endDate) throws AddressBookServiceException {
		return this.addressBookList = new AddressBookDBService().retrievePersonDataByDateRange(startDate, endDate);
	}

}
