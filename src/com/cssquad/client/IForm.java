package com.cssquad.client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public interface IForm {
	
	 Label header();

	 TextField username();
	
	 TextField email();
	 
	 TextField password();
	 
	 Button submit();
	 
	 
}
