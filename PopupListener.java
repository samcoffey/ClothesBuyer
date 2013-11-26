/************************************************************************
 * Author: Sam Coffey
 * SID: C11749921
 * Description: This class is an extension of the MouseAdapter class 
 * which acts as a popup listener for the calling class where the popup is
 * created. The popup is displayed in the component where it was triggered
 * at the point where it was triggered.
 *************************************************************************/

package com.assignment.clothes;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class PopupListener extends MouseAdapter {

	/*
	 * Declare a JPopupMenu object for the popup which was created in the
	 * calling class to be placed in, in the constructor
	 */
	
	private JPopupMenu popup;

	PopupListener(JPopupMenu popup) {
		this.popup = popup;
	}
	
	/*
	 * 2 methods overwritten from the MouseAdapter class. Must check for 
	 * a PopupTrigger on mouse pressed and released as the trigger can be 
	 * generated in both ways by different platforms.
	 */

	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			/*
			 * Display the popup in the component where it was triggered,
			 * at the point where it was triggered
			 */
			popup.show(e.getComponent(), e.getX(), e.getY());
		}//end if()
	}//end mousePressed()
	
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			/*
			 * Display the popup in the component where it was triggered,
			 * at the point where it was triggered
			 */
			popup.show(e.getComponent(), e.getX(), e.getY());
		}//end if()
	}//end mouseReleased()
}//end class