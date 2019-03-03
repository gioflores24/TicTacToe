package com.cssquad.client;

/**
 * Defines a protocol for all Application GUIs
 * @author giovanniflores
 *
 */
public interface IGUI {
	  //consts for ease of use
	  static final int PANE_WIDTH = 600;
	  static final int PANE_HEIGHT = 600;
	  static final int FIELD_HEIGHT = 40;
	  static final int BUTTON_HEIGHT = 100;
	 
	  void initGUI();
	
}
