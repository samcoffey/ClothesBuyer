/*************************************************************************
 * Author: Sam Coffey
 * SID: C11749921
 * Description: Class for a clothing item. Constructor uses information 
 * passed to it from another class for creation of new objects. Has 
 * getters and setters to access attributes and an overwritten toString() 
 * method.
 *************************************************************************/

package com.assignment.clothes;

public class ClothingItem {

	//Attributes of a clothing item
	private String name;
	private double price;
	private String image_path; //Path to where image of the object is stored

	//Constructor
	public ClothingItem(String name, double price, String image_path) {
		this.setName(name);
		this.setPrice(price);
		this.setImage_path(image_path);
	}//end constructor()

	//Getters & setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	//Overwritten toString() method
	@Override
	public String toString() {
		return "ClothingItem [name=" + name + ", price=" + price + ", image path=" + image_path + "]";
	}
	
}//end class
