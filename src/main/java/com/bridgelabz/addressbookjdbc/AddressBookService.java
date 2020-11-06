package com.bridgelabz.addressbookjdbc;

import java.util.ArrayList;
import java.util.List;

public class AddressBookService {
	private List<AddressBookContacts> addressBookList = new ArrayList<AddressBookContacts>();

	public List<AddressBookContacts> readAddressBookContact() throws AddressBookServiceException {

		return this.addressBookList = new AddressBookDBService().retrieveDataFromDB();
	}
}
