package com.bridgelabz.addressbookjdbc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {

	private List<AddressBookContacts> addressBookList = new ArrayList<AddressBookContacts>();

	public AddressBookService() {

	}

	public AddressBookService(List<AddressBookContacts> addressBookList) {
		this.addressBookList = new ArrayList<>(addressBookList);
	}

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

	public List<AddressBookContacts> readDataByDateRange(Date startDate, Date endDate)
			throws AddressBookServiceException {
		return this.addressBookList = new AddressBookDBService().retrievePersonDataByDateRange(startDate, endDate);
	}

	public int countByCity(String city) throws AddressBookServiceException {
		return new AddressBookDBService().getCountByCity(city);
	}

	public boolean addContacts() {
		return false;

	}

	public long countEntries() {
		return addressBookList.size();
	}

	public void addContactToAddressBook(AddressBookContacts addressBookContacts) {
		addressBookList.add(addressBookContacts);
	}

	public void addContactToAddressBookSystemWithThread(List<AddressBookContacts> addressBookContactsList) {
		Map<Integer, Boolean> addressBookStatus = new HashMap<Integer, Boolean>();
		addressBookContactsList.forEach(contact -> {
			Runnable task = () -> {
				addressBookStatus.put(contact.hashCode(), false);
				System.out.println("contact being added: " + Thread.currentThread().getName());
				this.addContactToAddressBook(contact.getId(), contact.getFirstName(), contact.getLastName(),
						contact.getEmail());
				addressBookStatus.put(contact.hashCode(), true);
				System.out.println("contact added  " + Thread.currentThread().getName());

			};
			Thread thread = new Thread(task, contact.getFirstName());
			thread.start();
		});
		while (addressBookStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		System.out.println(this.addressBookList);
	}

	public void addContactToAddressBook(int id, String firstName, String lastName, String emailId) {
		addressBookList.add(new AddressBookContacts(id, firstName, lastName, emailId));

	}

}