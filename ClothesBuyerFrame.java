/*************************************************************************
 * Author: Sam Coffey
 * SID: C11749921
 * Description: Class for main frame of the application. Instantiates the
 * ClothesDAO class which is the method by which the program accesses an 
 * external database. The main logic for the program is stored in this
 * class. Instantiates 5 ClothesPanel class which are the main visual
 * components of the application. Instantiates a PopupListener class which
 * listens for a popup trigger and shows the popup. Methods for creating a
 * file menu, creating a content pane, getting the total price of 
 * displayed clothes, creating the popup menu and ActionListeners and 
 * ActionEvents for all the buttons and menu items except the keep button
 * and popup menu are stored in this class. It extends the JFrame class 
 * and implements the ActionListener interface.
 *************************************************************************/

package com.assignment.clothes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ClothesBuyerFrame extends JFrame implements ActionListener {

	// Declare ArrayLists for storing ArrayLists from ClothesDAO object
	private ArrayList<ClothingItem> jacketList;
	private ArrayList<ClothingItem> shoesList;
	private ArrayList<ClothingItem> sweaterList;
	private ArrayList<ClothingItem> trousersList;
	private ArrayList<ClothingItem> tshirtList;

	/*
	 * Declaration of various PopupListener, JMenuItem, JPopupMenu, JLabel
	 * JPanel, JButton, JTextField & Font objects.
	 */
	private PopupListener myPopupListener;
	private JMenuItem resetMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem helpPopup;
	private JMenuItem totalPricePopup;
	private JMenuItem exitPopup;
	private JPopupMenu popup;
	private JLabel totLabel;
	private JPanel totalPanel;
	private JPanel discountPanel;
	private JPanel buttonPanel;
	private JButton resetButton;
	private JButton selectOutfitButton;
	private JButton moreChoicesButton;
	private JButton applyDiscount;
	private JTextField discountField;
	private Font font; // Later used to store standard font used in GUI

	// For confirmation that an initial selection has been made
	private boolean selectionMade = false;
	// Used to store discount to be applied entered by the user
	private double discountToApply;

	// Declaration of 5 ClothesPanel objects
	private ClothesPanel jacketPanel;
	private ClothesPanel shoesPanel;
	private ClothesPanel sweaterPanel;
	private ClothesPanel trousersPanel;
	private ClothesPanel tshirtPanel;

	// Constructor
	public ClothesBuyerFrame(String title) {

		super(title);

		// Set properties of the main frame
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocation(60, 100);
		setSize(1250, 230);

		/*
		 * Instantiate a new Database Access Object to connect and query the
		 * database
		 */
		ClothesDAO clothesDAO = new ClothesDAO();

		/*
		 * Populate ArrayLists to be passed to various ClothesPanels with
		 * ArrayLists created in clothesDAO
		 */
		jacketList = clothesDAO.getJackets();
		shoesList = clothesDAO.getShoes();
		sweaterList = clothesDAO.getSweaters();
		trousersList = clothesDAO.getTrousers();
		tshirtList = clothesDAO.getTshirts();

		// Create the MenuBar and ContentPane for the main frame
		setJMenuBar(createMenu());
		setContentPane(createPane());

		// Instantiate a PopUp menu and PopUp menu Listener
		popup = createPopUp();
		myPopupListener = new PopupListener(popup);
		// Add PopUp menu listener to the main frame
		this.addMouseListener(myPopupListener);

		// Set Visible to true so the frame will be displayed
		setVisible(true);
	}

	// Method to create the menu bar
	private JMenuBar createMenu() {

		// Instantiate a menu Bar
		JMenuBar menuBar = new JMenuBar();

		// Instantiate a Menu to go on the MenuBar
		JMenu fileMenu = new JMenu("Menu");

		// Instantiate 2 menu items
		resetMenuItem = new JMenuItem("Reset");
		exitMenuItem = new JMenuItem("Exit");

		// Add ActionListeners to the menu items
		resetMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);

		// Add menu items to the menu
		fileMenu.add(resetMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		// Add the menu to the menu bar
		menuBar.add(fileMenu);

		// Return the populated menu bar to where it was called from
		return menuBar;
	}// end createMenu()

	// Method to create the content pane
	private Container createPane() {

		// Instantiate the content pane and set the layout
		Container pane = new JPanel();
		pane.setLayout(new BorderLayout());

		/*
		 * Instantiate the panel to hold the 5 ClothesPanel objects and set the
		 * layout to GridLayout to hold 5 panels horizontally
		 */
		JPanel choicesPanel = new JPanel();
		choicesPanel.setLayout(new GridLayout(1, 5));

		/*
		 * Instantiate the 5 ClothesPanel objects, passing them their respective
		 * ArrayLists and titles as parameters.
		 */
		jacketPanel = new ClothesPanel(jacketList, "Jackets");
		shoesPanel = new ClothesPanel(shoesList, "Shoes");
		sweaterPanel = new ClothesPanel(sweaterList, "Sweaters");
		trousersPanel = new ClothesPanel(trousersList, "Trousers");
		tshirtPanel = new ClothesPanel(tshirtList, "T-shirts");

		// Add the 5 ClothesPanel objects to the object panel
		choicesPanel.add(tshirtPanel);
		choicesPanel.add(shoesPanel);
		choicesPanel.add(trousersPanel);
		choicesPanel.add(jacketPanel);
		choicesPanel.add(sweaterPanel);

		// Add the choicesPanel to the content pane
		pane.add(choicesPanel, BorderLayout.CENTER);

		/*
		 * Instantiate a panel to store information and buttons at the bottom of
		 * the content pane and set layout to GridLayout to store 3 panels
		 * horizontally
		 */
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));

		/*
		 * Instantiate a panel to hold a label displaying the total price of the
		 * items displayed and set its alignment to left with a FlowLayout
		 */
		totalPanel = new JPanel();
		totalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		totLabel = new JLabel("Current Items Total:  €0.00");
		totalPanel.add(totLabel);

		/*
		 * Instantiate a panel to hold a text field where the user can enter a
		 * discount to be used and set its alignment to centre with a FlowLayout
		 */
		discountPanel = new JPanel();
		discountPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel enterDiscount = new JLabel("Enter Discount:");
		discountField = new JTextField(2);
		// Add ActionListener to the text field
		discountField.addActionListener(this);
		JLabel percent = new JLabel("%");
		applyDiscount = new JButton("Apply Discount");
		// Add a second ActionListener to the button
		applyDiscount.addActionListener(this);
		// Add labels, text field & button to the panel
		discountPanel.add(enterDiscount);
		discountPanel.add(discountField);
		discountPanel.add(percent);
		discountPanel.add(applyDiscount);

		/*
		 * Instantiate a panel to hold buttons where the user can select an
		 * outfit or reset the GUI to its initial state and set its alignment to
		 * right with a FlowLayout. Later another button will be added where the
		 * user can choose to replace clothing items which they have not chosen
		 * to keep.
		 */
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		selectOutfitButton = new JButton("Select Outfit");
		resetButton = new JButton("Reset");
		// Add ActionListeners to the buttons
		selectOutfitButton.addActionListener(this);
		resetButton.addActionListener(this);
		// Add buttons to the panel
		buttonPanel.add(selectOutfitButton);
		buttonPanel.add(resetButton);

		// Add the previous 3 panels described to the bottom panel
		bottomPanel.add(totalPanel);
		bottomPanel.add(discountPanel);
		bottomPanel.add(buttonPanel);

		// Add the bottom panel to the bottom of the content pane
		pane.add(bottomPanel, BorderLayout.SOUTH);

		// Return the populated content frame to where it was called from
		return pane;
	}// end createPane()

	// Method to get the total price of the clothing items displayed
	public double getTotal() {
		/*
		 * Get the discounted values for the clothing items currently displayed
		 * from the 5 ClothesPanel objects and store them in a double.
		 */
		double total = jacketPanel.getDiscountedValue()
				+ shoesPanel.getDiscountedValue()
				+ sweaterPanel.getDiscountedValue()
				+ trousersPanel.getDiscountedValue()
				+ tshirtPanel.getDiscountedValue();
		// Round to 2 decimal places
		total = (double) (Math.round(total * 100)) / 100;
		// Return the total price to where it was called from
		return total;
	}// end getTotal()

	// Method to create a PopUp
	private JPopupMenu createPopUp() {
		// Instantiate popup
		popup = new JPopupMenu();

		// Give the popup a border
		popup.setBorder(BorderFactory.createLineBorder(Color.blue, 2));

		// Instantiate a label to use as a title for the popup
		JLabel options = new JLabel(" Options");
		// Set the font of the label to italics and slightly larger
		font = options.getFont();
		Font italicFont = new Font(font.getFontName(), Font.ITALIC,
				font.getSize() + 2);
		options.setFont(italicFont);

		// Instantiate 3 menu items
		helpPopup = new JMenuItem("Help");
		totalPricePopup = new JMenuItem("Total Price");
		exitPopup = new JMenuItem("Exit");

		// Add ActionListeners to the menu items
		helpPopup.addActionListener(this);
		totalPricePopup.addActionListener(this);
		exitPopup.addActionListener(this);

		// Add the label and menu items to the popup
		popup.add(options);
		popup.addSeparator();
		popup.add(helpPopup);
		popup.add(totalPricePopup);
		popup.add(exitPopup);

		// Return the popup to where it was called from
		return popup;
	}// end createPopUp()

	/*
	 * Method to call the randomiseClothesList method in the 5 ClothesPanel
	 * objects and get a new total price for the total price label
	 */
	public void randomise() {
		// Call newSelection() method in 5 ClothesPanel objects
		jacketPanel.newSelection(jacketPanel.getClothesList());
		shoesPanel.newSelection(shoesPanel.getClothesList());
		sweaterPanel.newSelection(sweaterPanel.getClothesList());
		trousersPanel.newSelection(trousersPanel.getClothesList());
		tshirtPanel.newSelection(tshirtPanel.getClothesList());
		
		totLabel.setText("Current Items Total:  €"
				+ String.format("%.2f", getTotal()));

	}// end randomise()

	/*
	 * Method to call the setDiscount method in the 5 ClothesPanel objects and
	 * reset the contents of total price label to the total price
	 */
	public void applyDiscount(double discount) {

		trousersPanel.setDiscount(discount);
		sweaterPanel.setDiscount(discount);
		shoesPanel.setDiscount(discount);
		tshirtPanel.setDiscount(discount);
		jacketPanel.setDiscount(discount);
		totLabel.setText("Current Items Total:  €"
				+ String.format("%.2f", getTotal()));
	}

	// Method to override the actionPerformed method from ActionListener
	// interface
	@Override
	public void actionPerformed(ActionEvent e) {

		// If "Select OutFit" button was pressed
		if (e.getSource() == selectOutfitButton) {

			// Checks if this is the 1st time a selection was made
			if (selectionMade == false) {

				/*
				 * If it is the 1st time, adds a "More Choices" button with an
				 * ActionListioner to the button panel
				 */
				moreChoicesButton = new JButton("More Choices");
				moreChoicesButton.addActionListener(this);
				buttonPanel.add(moreChoicesButton);
			}// end inner if()

			/*
			 * As "Select Outfit button ignores whether an item was kept and
			 * picks a whole new outfit, kept boolean is set to false in the 5
			 * ClothesPanel objects
			 */
			jacketPanel.setKept(false);
			shoesPanel.setKept(false);
			sweaterPanel.setKept(false);
			trousersPanel.setKept(false);
			tshirtPanel.setKept(false);

			// Call randomise method
			randomise();

			// Set boolean that a selection has been made to true
			selectionMade = true;
		}// end outer if()

		// If "More Choices" button pressed
		if (e.getSource() == moreChoicesButton) {

			// Call randomise method
			randomise();

		}// end if()

		// If "Reset" button pressed
		if (e.getSource() == resetButton || e.getSource() == resetMenuItem) {

			// If a selection has already been made remove the button panel
			if (selectionMade == true) {
				buttonPanel.remove(moreChoicesButton);
			}

			// Remove any text from the discount text field
			discountField.setText("");

			// Call the reset method in the 5 ClothesPanel objects
			jacketPanel.reset();
			shoesPanel.reset();
			sweaterPanel.reset();
			trousersPanel.reset();
			tshirtPanel.reset();

			// Set the total price label to display €0.00
			totLabel.setText("Current Items Total:  €0.00");
			// Set the boolean that an initial selection has been made to false
			selectionMade = false;
		}// end if()

		/*
		 * If the exit menu item on the menu or popup menu were pressed then
		 * exit the program
		 */
		if (e.getSource() == exitMenuItem || e.getSource() == exitPopup) {
			System.exit(0);
		}// end if()

		/*
		 * If the total price menu item on the popup menu was pressed then
		 * create a JOption pane with a message showing the text from the total
		 * price label
		 */
		if (e.getSource() == totalPricePopup) {
			JOptionPane.showMessageDialog(this, totLabel.getText());
		}// end if()

		/*
		 * If the total price menu item on the popup menu was pressed then
		 * create a JOption pane with a message showing the contents of Strings
		 * with tips on how to use the GUI
		 */
		if (e.getSource() == helpPopup) {

			String select1 = "Press \"Select Outfit\" button to generate 5 new items.\n\n";
			String keep = "Press \"Keep\" button to select items you want to keep.\n\n";
			String moreChoices = "Press \"More Choices\" button to generate new items"
					+ " for categories you have not kept.\n\n";
			String select2 = "Press \"Select Outfit\" button again to ignore kept items"
					+ " and generate 5 new items.\n\n";
			String discount = "Enter a figure from 0 to 100 and press \"Apply Discount\" "
					+ "button or hit Enter to apply a discount.\n\n";
			String reset = "Press \"Reset\" button to return the application to initial "
					+ "state.\n";
			JOptionPane.showMessageDialog(this, select1 + keep + moreChoices
					+ select2 + discount + reset);
		}// end if()

		/*
		 * If the "Apply Discount" button was pressed or enter was hit in the
		 * discount field
		 */
		if (e.getSource() == applyDiscount || e.getSource() == discountField) {

			try {
				// Convert String from discount field to double and store in
				// double
				discountToApply = Double.parseDouble(discountField.getText());
				// If the number enter was from 0 to 100
				if (discountToApply >= 0 && discountToApply <= 100) {
					// Call applyDiscount method with number as a parameter
					applyDiscount(discountToApply);

				} else { // If not
					// Call applyDiscount method with 0 as a parameter
					applyDiscount(0);
					discountField.setText(""); // Remove text in discount field
					// Show JOptionPane with message advising appropriate entry
					JOptionPane.showMessageDialog(this,
							"Discount entered must be between 0% & 100%");

				}// end if else()

				// If a non numeric value was caught in user entry
			} catch (NumberFormatException e1) {
				// Call applyDiscount method with 0 as a parameter
				applyDiscount(0);
				discountField.setText(""); // Remove text in discount field
				// Display JOptionPane with message advising appropriate entry
				JOptionPane.showMessageDialog(this,
						"Discount entered must be numeric");

			}// end try catch()

		}// end if()

	}// end actionPerformed()

}// end class()
