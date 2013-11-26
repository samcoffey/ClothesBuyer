/*************************************************************************
 * Author: Sam Coffey
 * SID: C11749921
 * Description: Class for the ClothesPanel panel. This class extends 
 * the JPanel class and implements the ActionListener interface. It has
 * 2 possible layouts depending on whether an initial selection has been
 * made by the user (both inside a panel with a BorderLayout displaying the
 * category of clothing) - A single panel with a BoxLayout displaying an 
 * image of a question mark or another panel with a GridLayout displaying 
 * an image, the name and the price of the current item and a button to 
 * keep the current item if required. It is the main visual component of
 * the program. It has methods for initial setup, randomising the current 
 * clothes item and reseting the panel to its initial state. It has an
 * ActionListener and ActionEvent for the "Keep" button.
 *************************************************************************/

package com.assignment.clothes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClothesPanel extends JPanel implements ActionListener {

	// Declare ArrayList to hold the ArrayList passed by the ClothesBuyerFrame
	private ArrayList<ClothingItem> clothesList;
	// Instantiate a second ArrayList to hold original ArrayList before
	// randomising
	private ArrayList<ClothingItem> originalList = new ArrayList<ClothingItem>();
	// Declare a new ClothingItem object
	private ClothingItem currentItem;

	private boolean kept; // For verifying if a user has kept an item
	// For verifying if an initial selection made
	private boolean selectionMade = false;

	private double discount = 0; // Set default value of discount to 0
	// For holding revised value after discount applied
	private double discountedValue;

	// Declaration of various JLabel, JPanel, JButton & JTextField objects.
	private JButton keepButton;
	private JPanel picAndInfoPanel;
	private JPanel infoPanel;
	private JPanel namePanel;
	private JPanel pricePanel;
	private JPanel keepButtonPanel;
	private JPanel picPanel;
	private JPanel qPanel;
	private JLabel qLabel;
	private JLabel name;
	private JLabel price;
	private JLabel picLabel;

	// Getter for ClothesList so it can be accessed by another class
	public ArrayList<ClothingItem> getClothesList() {
		return clothesList;
	}

	// Getter for current clothing item so it can be accessed by another class
	public ClothingItem getCurrentItem() {
		return currentItem;
	}

	// Getter for kept boolean so it can be accessed by another class
	public boolean isKept() {
		return kept;
	}

	// Setter for kept boolean so it can be set by another class
	public void setKept(boolean kept) {
		this.kept = kept;
	}

	/*
	 * Setter for discount so it can be set by another class. If an initial
	 * selection has already been made, this method also sets the value of
	 * discounted Value for the current item following discount calculation and
	 * sets the text of the price label accordingly.
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
		if (selectionMade == true) {
			discountedValue = currentItem.getPrice()
					- (currentItem.getPrice() * (discount / 100));
			discountedValue = (double) (Math.round(discountedValue * 100)) / 100;
			price.setText("€" + String.format("%.2f", discountedValue));
		}
	}

	// Getter to access discountedValue from another class
	public double getDiscountedValue() {
		return discountedValue;
	}

	// Constructor
	public ClothesPanel(ArrayList<ClothingItem> clothesList, String title) {
		super();
		kept = false;
		this.clothesList = clothesList;

		// Populate the original list with contents of passed clothes list
		for (int i = 0; i < clothesList.size(); i++) {
			originalList.add(clothesList.get(i));
		}

		setBorder(BorderFactory.createEtchedBorder());// Give panel a border
		setLayout(new BorderLayout());

		/*
		 * Instantiate a new panel with an empty border to hold a title label
		 * and give it a horizontally central layout with FlowLayout
		 */
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.setBorder(BorderFactory.createEmptyBorder());
		titlePanel.setBackground(Color.magenta);

		// Instantiate a title label with String passed to constructor as text
		JLabel titleLabel = new JLabel(title);
		titlePanel.add(titleLabel);// Add label to panel

		// Add title panel to the north of the clothes panel
		add(titlePanel, BorderLayout.NORTH);

		initialSetup();// Call initialSetup() method

	}// end constructor()

	// Method to setup the display of ClothesPanel before a selection is made
	public void initialSetup() {

		// Instantiate a JPanel with BoxLayout
		qPanel = new JPanel();
		qPanel.setLayout(new BoxLayout(qPanel, BoxLayout.X_AXIS));
		qPanel.setBackground(Color.white);

		// Instantiate a JLabel with a question mark image as an icon
		qLabel = new JLabel();
		qLabel.setIcon(new ImageIcon("src\\res\\questionMark.png"));

		// Add the label and horizontal glue on either side to centre it
		qPanel.add(Box.createHorizontalGlue());
		qPanel.add(qLabel);
		qPanel.add(Box.createHorizontalGlue());

		add(qPanel);// Add panel to ClothesPanel
	}

	// Method for new clothing item to be generated in the ClothesPanel
	public void newSelection(ArrayList<ClothingItem> clothesList) {

		if (selectionMade == false) {// If 1st time selection made

			remove(qPanel); // Remove question mark panel

			// Instantiate a panel with a gridLayout to replace qPanel
			picAndInfoPanel = new JPanel(new GridLayout(1, 2));

			/*
			 * Instantiate a new panel and label to hold a picture. Add the
			 * label to the panel with a boxLayout and use horizontal glue to
			 * place in centre
			 */
			picPanel = new JPanel();
			picPanel.setLayout(new BoxLayout(picPanel, BoxLayout.X_AXIS));
			picLabel = new JLabel();
			picPanel.add(Box.createHorizontalGlue());
			picPanel.add(picLabel);
			picPanel.add(Box.createHorizontalGlue());

			// Instantiate a new info panel to hold 3 other panels
			infoPanel = new JPanel();
			infoPanel.setLayout(new GridLayout(3, 1));

			/*
			 * Instantiate a new panel and label. Add the label to the panel
			 * with a boxLayout and use horizontal glue to place in centre
			 */
			namePanel = new JPanel();
			namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
			name = new JLabel("");
			namePanel.add(Box.createHorizontalGlue());
			namePanel.add(name);
			namePanel.add(Box.createHorizontalGlue());

			/*
			 * Instantiate a new panel and label. Add the label to the panel
			 * with a boxLayout and use horizontal glue to place in centre
			 */
			pricePanel = new JPanel();
			pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
			price = new JLabel("");
			pricePanel.add(Box.createHorizontalGlue());
			pricePanel.add(price);
			pricePanel.add(Box.createHorizontalGlue());

			/*
			 * Instantiate a new panel and button. Add button to the panel with
			 * a boxLayout and use horizontal glue to place in centre
			 */
			keepButtonPanel = new JPanel();
			keepButtonPanel.setLayout(new BoxLayout(keepButtonPanel,
					BoxLayout.X_AXIS));
			keepButton = new JButton("Keep");
			keepButton.addActionListener(this);
			keepButtonPanel.add(Box.createHorizontalGlue());
			keepButtonPanel.add(keepButton);
			keepButtonPanel.add(Box.createHorizontalGlue());

			// Add previous three panels to info panel
			infoPanel.add(namePanel);
			infoPanel.add(pricePanel);
			infoPanel.add(keepButtonPanel);

			// Add picture panel and info panel to pic & info panel
			picAndInfoPanel.add(picPanel);
			picAndInfoPanel.add(infoPanel);

			// add pic & info panel to ClothesPanel
			add(picAndInfoPanel, BorderLayout.CENTER);

			// Call changeColorPicAndInfo() method
			changeColorPicAndInfo(Color.white);

			randomiseClothesList();// Call randomiseClothesList method()

			selectionMade = true;// Confirm a selection has been made

		} else {// If not 1st time selection made
			/*
			 * If an item in the category has not been kept by the user and
			 * there is another item to select in the list...
			 */
			if (kept == false && clothesList.size() > 0) {
				// Set the text in the keep button to "Keep"
				keepButton.setText("Keep");
				// Call changeColorPicAndInfo() method
				changeColorPicAndInfo(Color.white);
				randomiseClothesList();// Call randomiseClothesList method()
			} else {// Otherwise return to where method called from
				return;
			}// end inner if else()

		}// end outer if else()

	}// end newSelection()

	// Method to select a random item from clothesList
	public void randomiseClothesList() {
		// Get random int from 0 to 1 less than number of items in clothesList
		int randomNumber = (int) (Math.random() * clothesList.size());
		// Set currentItem to item at index of that number in clothes list
		currentItem = clothesList.get(randomNumber);
		// Set discountedValue to currentItem price minus discount percentage
		discountedValue = currentItem.getPrice()
				- (currentItem.getPrice() * (discount / 100));
		// Round discounted value to 2 decimal places
		discountedValue = (double) (Math.round(discountedValue * 100)) / 100;
		// Remove currentItem from the clothesList using the random int
		clothesList.remove(randomNumber);
		// Set the text in name label to the name variable in currentItem
		name.setText(currentItem.getName());
		/*
		 * Set the text in price label to the price variable in currentItem
		 * formatted to a monetary form
		 */
		price.setText("€" + String.format("%.2f", discountedValue));
		/*
		 * Set the image in pic label to the image at the path in the image_path
		 * variable in currentItem
		 */
		picLabel.setIcon(new ImageIcon(currentItem.getImage_path()));
	}// end randomiseClothesList()

	// Method to change the background colour of the panels in picAndInfoPanel
	public void changeColorPicAndInfo(Color color) {
		picPanel.setBackground(color);
		namePanel.setBackground(color);
		pricePanel.setBackground(color);
		keepButtonPanel.setBackground(color);
	}// end changeColorPicAndInfo()

	// Method to reset the ClothesPanel to its state before a selection was made
	public void reset() {

		if (selectionMade == true) {
			remove(picAndInfoPanel);
			selectionMade = false;// Confirm 1st selection has now not been made
			discount = 0;//Set discount variable back to zero
			discountedValue = 0;//Set discountedValue variable back to 0
			kept = false;//Confirm no item has been kept

			//Remove any items from clothesList
			for (int i = (clothesList.size() - 1); i >= 0; i--) {
				clothesList.remove(i);
			}

			//Re-populate clothesList with contents of originalList
			for (int i = 0; i < originalList.size(); i++) {
				clothesList.add(originalList.get(i));
			}

			initialSetup();//Call initialSetup() method
		} else {//If no selection made, return to where method was called from
			return;
		}
	}// end reset()

	//Method to override the actionPerformed() method
	@Override
	public void actionPerformed(ActionEvent e) {
		//If "Keep" button has been pressed
		if (e.getSource() == keepButton) {
			if (kept == false) {//If nothing already kept
				keepButton.setText("Cancel");//Set text in button to "Cancel"
				kept = true;//Confirm item kept
				//Call changeColorPicAndInfo() method
				changeColorPicAndInfo(Color.green);
			} else {//If something already kept
				keepButton.setText("Keep");//Set text in button to "Keep"
				kept = false;//Confirm nothing kept
				//Call changeColorPicAndInfo() method
				changeColorPicAndInfo(Color.white);
			}
		}

	}// end actionPerformed()

}// end class
