package com.bridgelabz.addressbookjdbc;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import org.junit.Test;

public class AddressBookServiceTest {

	/**
	 * uc16
	 * 
	 * @throws AddressBookServiceException
	 */
	@Test
	public void givenDatabase_WhenRetrievePayroll_ShouldMatchEmployeeCount() throws AddressBookServiceException {
		AddressBookService addressBookService = new AddressBookService();
		List<AddressBookContacts> addressBookContactList = addressBookService.readAddressBookContact();
		System.out.println(addressBookContactList.size());
		Assert.assertEquals(2, addressBookContactList.size());
	}

	/**
	 * uc17
	 * 
	 * @throws AddressBookServiceException
	 */
	@Test
	public void givenNewPhoneNumberForPerson_WhenUpdated_ShouldSyncWitnhDb() throws AddressBookServiceException {
		AddressBookService addressBookService = new AddressBookService();
		List<AddressBookContacts> addressBookContactList = addressBookService.readAddressBookContact();
		Assert.assertEquals(true, addressBookService.updatePhoneNumber("7209300456", 2));
	}

	/**
	 * uc18
	 * 
	 * @throws AddressBookServiceException
	 */
	@Test
	public void givenDatabase_WhenRetrievePersonByGivenDateRange_ShouldMatchPersonCount()
			throws AddressBookServiceException {
		AddressBookService addressBookService = new AddressBookService();
		Date startDate = Date.valueOf("2018-03-01");
		Date endDate = Date.valueOf("2019-10-05");
		List<AddressBookContacts> addressBookContactList = addressBookService.readDataByDateRange(startDate, endDate);
		Assert.assertEquals(3, addressBookContactList.size());
	}

	/**
	 * uc19
	 * 
	 * @throws AddressBookServiceException
	 */
	@Test
	public void givenDatabase_WhenCountContactInGivenCity_ShouldMatchCount() throws AddressBookServiceException {
		AddressBookService addressBookService = new AddressBookService();
		int NoOFContact = addressBookService.countByCity("dhanbad");
		Assert.assertEquals(3, NoOFContact);
	}

	/**
	 * uc20
	 * 
	 * @throws AddressBookServiceException
	 * @throws SQLException
	 */
	@Test
	public void givenDatabase_whenAddedRecords_ShouldReturnTrue() throws AddressBookServiceException, SQLException {
		Date start = Date.valueOf("2020-09-04");
		AddressBookDBService addressBookDBService = new AddressBookDBService();
		boolean result = addressBookDBService.addNewContact("abc", 5, 100, "abhishek", "bhagat", "abds@gmail.com",
				"72093045", "Friends", "dhanbad", "jharkhand", "828113");
		Assert.assertTrue(result);
	}

	/**
	 * uc21
	 * 
	 * @throws AddressBookServiceException
	 */
	@Test
	public void givenDatabase_WhenaddedMultipleAddress_ShouldReturnTrue() throws AddressBookServiceException {
		AddressBookContacts[] arrayOfContact = { new AddressBookContacts(1, "naman", "kumar", "adfj@gmail.com"),
				new AddressBookContacts(2, "abhisehk", "kumar", "adfj@gmail.com"),
				new AddressBookContacts(3, "gaurav", "kumar", "adfj@gmail.com") };
		Instant start = Instant.now();
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.addContactToAddressBookSystemWithThread(Arrays.asList(arrayOfContact));
		Instant end = Instant.now();
		System.out.println("Time taken without thread: " + Duration.between(start, end));
		// addressContacts = addressService.readAddressBookContact(IOService.DB_IO);
		Assert.assertEquals(3, addressBookService.countEntries());
	}
}
