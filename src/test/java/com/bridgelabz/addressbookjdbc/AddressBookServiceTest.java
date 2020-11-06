package com.bridgelabz.addressbookjdbc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class AddressBookServiceTest {
	@Test
	public void givenDatabase_WhenRetrievePayroll_ShouldMatchEmployeeCount() throws AddressBookServiceException {
		AddressBookService addressBookService = new AddressBookService();
		List<AddressBookContacts> addressBookContactList = addressBookService.readAddressBookContact();
		System.out.println(addressBookContactList.size());
		assertEquals(2, addressBookContactList.size());
	}

}
