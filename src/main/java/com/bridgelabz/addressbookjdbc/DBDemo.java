package com.bridgelabz.addressbookjdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBDemo {

	public Connection getConnection() throws AddressBookServiceException {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		String password = "Abhi@1234";
		Connection connection = null;
		try {
			System.out.println("Connecting database" + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is established!!!" + connection);
		} catch (Exception e) {
			throw new AddressBookServiceException("unable to load drivers",
					AddressBookServiceException.ExceptionType.UNABLE_TO_LOAD_DRIVER);
		}
		return connection;

	}
}
