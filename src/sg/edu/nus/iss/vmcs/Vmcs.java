/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs;

import java.io.FileInputStream;

import sg.edu.nus.iss.vmcs.system.*;
import sg.edu.nus.iss.vmcs.util.*;

/**
 * This object acts as the main program of the application.
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */

public class Vmcs {
	private static String propertiesFile = "/Users/jackle/Documents/Semester 3/Software Product Line/Assignments/Project/vmcs.properties";

	/**
	 * This constructor creates an instance of Vmcs object.
	 * @param propertiesFile the properties file name.
	 */
	private Vmcs(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	/**
	 * This method start the MainController.
	 */
	public void start() {
		MainController mc = new MainController(propertiesFile);
		try {
			mc.start();
		} catch (VMCSException e) {
			System.out.println("Vmcs.start: Error in system initialization: "+ e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Main entry point.
	 * @param args the arguments for the main application.
	 */
	public static void main(String args[]) {
		//Vmcs vmcs = new Vmcs(args[0]);
		// passing file directly instead of runtime arguments
		Vmcs vmcs = new Vmcs(propertiesFile);
		vmcs.start();
	}
	
	public static String getPropertiesFile() {
		return propertiesFile;
	}
}//End of class Vmcs
