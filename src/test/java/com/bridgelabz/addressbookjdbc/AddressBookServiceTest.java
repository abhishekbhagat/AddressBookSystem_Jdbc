package com.bridgelabz.addressbookjdbc;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
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
	@Test
	public void givenNewPhoneNumberForPerson_WhenUpdated_ShouldSyncWitnhDb()throws AddressBookServiceException {
			AddressBookService addressBookService = new AddressBookService();
			List<AddressBookContacts> addressBookContactList = addressBookService.readAddressBookContact();
		    assertEquals(true, addressBookService.updatePhoneNumber("7209300456",2));
	 }
	@Test
	public void givenDatabase_WhenRetrievePersonByGivenDateRange_ShouldMatchPersonCount()
			throws AddressBookServiceException {
		AddressBookService addressBookService = new AddressBookService();
		Date startDate=Date.valueOf("2018-03-01");
		Date endDate=Date.valueOf("2019-10-05");
		List<AddressBookContacts> addressBookContactList = addressBookService.readDataByDateRange(startDate,endDate);
        assertEquals(3, addressBookContactList.size());
	}
}
