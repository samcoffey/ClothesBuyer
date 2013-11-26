/*************************************************************************
 * Author: Sam Coffey
 * SID: C11749921
 * Description: This class acts as a Database Access Object which accesses
 * and queries the clothes database. The details of the database for the
 * connection are read from a file which adds to the re-usability of the 
 * class. Information from each row in the database is stored in a
 * ClothesItem object and each ClothesItem object is stored in a 
 * particular ArrayList depending on the category of clothing.
 *************************************************************************/

package com.assignment.clothes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ClothesDAO {

	/*
	 * Objects for reading from file, storing connection properties, storing the
	 * connection to the database, storing a prepared statement for querying the
	 * database and storing the results of that query
	 */
	private InputStream in;
	private Properties props = new Properties();
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// Instantiate ArrayLists for storing ClothingItems taken from the database
	private ArrayList<ClothingItem> jackets = new ArrayList<ClothingItem>();
	private ArrayList<ClothingItem> shoes = new ArrayList<ClothingItem>();
	private ArrayList<ClothingItem> sweaters = new ArrayList<ClothingItem>();
	private ArrayList<ClothingItem> trousers = new ArrayList<ClothingItem>();
	private ArrayList<ClothingItem> tshirts = new ArrayList<ClothingItem>();

	// Created getters for accessing these ArrayLists from another class
	public ArrayList<ClothingItem> getJackets() {
		return jackets;
	}

	public ArrayList<ClothingItem> getShoes() {
		return shoes;
	}

	public ArrayList<ClothingItem> getSweaters() {
		return sweaters;
	}

	public ArrayList<ClothingItem> getTrousers() {
		return trousers;
	}

	public ArrayList<ClothingItem> getTshirts() {
		return tshirts;
	}

	// Constructor
	public ClothesDAO() {

		/*
		 * Instantiate a FileInputStream object to read the connection
		 * properties from a properties file
		 */
		try {
			in = new FileInputStream("src\\res\\sql.properties");
			System.out.println("File Found.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}// end try catch()

		try {
			props.load(in); // Upload properties from properties file
			System.out.println("Properties loaded from properties file.");
			in.close();// Close the file
		} catch (IOException e) {
			System.out.println("IO Exception.");
		}// end try catch()

		// Load the driver from the property object
		try {
			Class.forName(props.getProperty("connection.driver"));
			System.out.println("Driver loaded.");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not load the driver.");
		}

		try {
			/*
			 * Get connection to the database using the DriverManager class
			 */
			con = DriverManager.getConnection(
					props.getProperty("connection.url"),
					props.getProperty("user"), props.getProperty("password"));
			System.out.println("Connection made");
			/*
			 * Instantiate a prepare statement to select all information from
			 * the Clothing_item table in the database.
			 */
			pstmt = con.prepareStatement("SELECT * FROM Clothing_item");
			// Execute the prepare statement and store result in a result set
			// object
			rs = pstmt.executeQuery();
			// While loop for each row in the result set
			while (rs.next()) {
				/*
				 * Store the information from the current row of the result set
				 * Strings & a double
				 */
				String name = rs.getString("NAME");
				String clothes_type = rs.getString("CLOTHES_TYPE");
				double price = rs.getDouble("PRICE");
				String image_path = rs.getString("IMAGE_PATH");

				// Instantiate a new ClothingItem object with Strings & double
				ClothingItem c = new ClothingItem(name, price, image_path);

				/*
				 * Add clothing item to a particular ArrayList depending on its
				 * category.
				 */
				if (clothes_type.equals("Jacket")) {
					jackets.add(c);
				} else if (clothes_type.equals("Shoes")) {
					shoes.add(c);
				} else if (clothes_type.equals("Sweater")) {
					sweaters.add(c);
				} else if (clothes_type.equals("Trousers")) {
					trousers.add(c);
				} else if (clothes_type.equals("T-shirt")) {
					tshirts.add(c);
				}// end if else()
			}// end while()

			con.close();// Close connection
			System.out.println("Connection closed");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			while (e.getNextException() != null)
				System.out.println(e.getMessage());
		}// end try catch()

	}// end constructor()

}// end class
