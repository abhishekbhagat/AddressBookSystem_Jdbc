package com.bridgelabz.addressbookjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
	public List<AddressBookContacts> retrieveDataFromDB() throws AddressBookServiceException {
		String query = "select * from contact_person c inner join complete_address on c.contact_id=a.contact_id inner join contact_type ct on ct.contact_id=a.contact_id";
		List<AddressBookContacts> addressBookContactList = new ArrayList<AddressBookContacts>();
		try (Connection connection = new DBDemo().getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("contact_id");
				int bookId = resultSet.getInt("address_book_id");
				String type = resultSet.getString("type_name");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");
				addressBookContactList.add(
						new AddressBookContacts(firstName, lastName, id, phone, email, city, state, zip, bookId, type));
			}
		} catch (SQLException e) {
			throw new AddressBookServiceException("unable to retrieve data",
					AddressBookServiceException.ExceptionType.UNABLE_TO_RETRIEVE_DATA);
		}
		return addressBookContactList;
	}

	public int updatePhoneNumberOfGivenPersonID(String phoneNumber, int id) throws AddressBookServiceException {
		String query = String.format("update contact_person set phone_number = '%s' where contact_id = '%d';",
				phoneNumber, id);
		try (Connection connection = new DBDemo().getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new AddressBookServiceException("unable to update data",
					AddressBookServiceException.ExceptionType.UNABLE_TO_UPDATE_DATA);
		}

	}

	public List<AddressBookContacts> retrievePersonDataByDateRange(Date startDate, Date endDate)
			throws AddressBookServiceException {
		try (Connection connection = new DBDemo().getConnection()) {
			List<AddressBookContacts> addressBookContactList = new ArrayList<>();
			String sql = String.format("select * from contact_person where startDate between '%s' and '%s';", startDate,
					endDate);
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("contact_id");
				int bookId = resultSet.getInt("address_book_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");
				Date date = resultSet.getDate("startDate");
				addressBookContactList
						.add(new AddressBookContacts(firstName, lastName, id, phone, email, bookId, date));
			}
			return addressBookContactList;

		} catch (SQLException e) {
			throw new AddressBookServiceException("unable to retrieve data",
					AddressBookServiceException.ExceptionType.UNABLE_TO_RETRIEVE_DATA_BY_GIVEN_RANGE);
		}

	}

	public int getCountByCity(String city) throws AddressBookServiceException {
		try (Connection connection = new DBDemo().getConnection()) {
			int count = 0;
			String sql = String.format("select count(contact_id) from complete_address where city='%s';", city);
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			throw new AddressBookServiceException("unable to count",
					AddressBookServiceException.ExceptionType.UNABLE_TO_COUNT_PERSONS_OF_GIVEN_CITY);
		}

	}

	public boolean addNewContact(String addressBookName, int contact_id, int address_book_id, String first_name,
			String last_name, String email_id, String phone_number, String typeName, String city, String state,
			String zip) throws AddressBookServiceException, SQLException {
		int addedAdress_book, addedAddress, addedContactType, addedContactPerson;
		Connection connection = null;
		connection = new DBDemo().getConnection();
		connection.setAutoCommit(false);

		try (Statement statement = connection.createStatement()) {
			String sql = String.format(
					"INSERT INTO address_book_system(address_book_id,address_book_name) " + " VALUES('%d','%s')",
					address_book_id, addressBookName);
			addedAdress_book = statement.executeUpdate(sql);

		} catch (SQLException e) {
			connection.rollback();
			throw new AddressBookServiceException("unable to add new contact",
					AddressBookServiceException.ExceptionType.UNABLE_TO_ADD_NEW_CONTACT);
		}
		try (Statement statement = connection.createStatement()) {

			String sql = String.format(
					"INSERT INTO contact_person(contact_id,address_book_id,first_name,last_name,email_id,phone_number) "
							+ " VALUES('%d','%d','%s','%s','%s','%s')",
					contact_id, address_book_id, first_name, last_name, email_id, phone_number);
			addedContactPerson = statement.executeUpdate(sql);

		} catch (SQLException e) {
			connection.rollback();
			throw new AddressBookServiceException("unable to add new contact",
					AddressBookServiceException.ExceptionType.UNABLE_TO_ADD_NEW_CONTACT);
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format("INSERT INTO contact_type(contact_id,type_name) " + " VALUES('%d','%s')",
					contact_id, typeName);
			addedContactType = statement.executeUpdate(sql);

		} catch (SQLException e) {
			connection.rollback();
			throw new AddressBookServiceException("unable to add new contact",
					AddressBookServiceException.ExceptionType.UNABLE_TO_ADD_NEW_CONTACT);
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format(
					"INSERT INTO complete_address(contact_id,city,state,zip) " + " VALUES('%d','%s','%s','%s')",
					contact_id, city, state, zip);
			addedAddress = statement.executeUpdate(sql);

		} catch (SQLException e) {
			connection.rollback();
			throw new AddressBookServiceException("unable to add new contact",
					AddressBookServiceException.ExceptionType.UNABLE_TO_ADD_NEW_CONTACT);
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.close();
		}
		if (addedAdress_book == 1 && addedAddress == 1 && addedContactType == 1 && addedContactPerson == 1) {
			return true;
		} else
			return false;

	}
}
