/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.machinery;

import java.awt.*;
import java.awt.event.*;

import sg.edu.nus.iss.vmcs.Builder;
import sg.edu.nus.iss.vmcs.Director;
import sg.edu.nus.iss.vmcs.ControlElement;
import sg.edu.nus.iss.vmcs.StoreViewerBuilder;
import sg.edu.nus.iss.vmcs.commonality.*;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;

/**
 * This panel simulates the physical actions of the maintainer&#46; It enables
 * the user to: <br>
 * 1- Display and enter new values for the number of cans of each DrinksBrand
 * held by the DrinksStore; <br>
 * 2- Display and enter new values for the number of coins of each denomination
 * held by the CashStore; <br>
 * 3- Display whether the vending machine Door is unlocked, and enable it to be
 * locked.
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class MachinerySimulatorPanel extends Dialog {
	private static final String TITLE = "Machinery Panel";

	private Checkbox doorDisplay;
	private MachinerySimulatorDrinkPanel drinkPanel;
	// #if CashPayment
	private MachinerySimulatorCoinPanel coinPanel;
	// #endif
	private MachineryController machineryCtrl;

	/**
	 * This constructor creates an instance of MachinerySimulatorPanel.
	 * 
	 * @param fr
	 *            the parent frame.
	 * @param machCtrl
	 *            the MachineryController.
	 */
	public MachinerySimulatorPanel(Frame fr, MachineryController machCtrl) {
		super(fr, TITLE, false);

		machineryCtrl = machCtrl;
	}

	public void initialize() {
		Label lb = new Label(TITLE);
		lb.setFont(new Font("Helvetica", Font.BOLD, 24));
		lb.setAlignment(Label.CENTER);

		Panel tp = new Panel();
		tp.setLayout(new GridLayout(0, 1));
		
		// #if CashPayment
		coinPanel.initializeCashDisplay(tp);
		// #endif
		drinkPanel.initializeDrinkDisplay(tp);

		Panel dp = new Panel();
		doorDisplay = new Checkbox();
		doorDisplay.addItemListener(new DoorListener(machineryCtrl));
		doorDisplay.setLabel("Door Locked");
		dp.add(doorDisplay);

		this.setLayout(new BorderLayout());
		this.add("North", lb);
		this.add("Center", tp);
		this.add("South", dp);
		pack();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				machineryCtrl.closeMachineryPanel();
			}
		});
	}

	/**
	 * Display the MachinerySimulatorPanel&#46; This will achieved by displaying
	 * the frame of the panel and then sending messages to its constituent
	 * objects instructing them to: <br>
	 * 1- Display themselves; <br>
	 * 2- Set initial values; <br>
	 * 3- Deactivate themselves.
	 */
	public void display() {
		this.setVisible(true);
	}

	/**
	 * Remove the panel objects and MachinerySimulatorPanel from the display.
	 */
	public void closeDown() {
		dispose();
	}

	/**
	 * This method set the door state to Open or Closed.
	 * 
	 * @param state
	 *            TRUE to set the door state to open, otherwise, set the door
	 *            state to close.
	 */
	public void setDoorState(boolean state) {
		doorDisplay.setState(state);
		this.setActive(!state);
	}

	/**
	 * This method refreshes the cash display and drinks display.
	 */
	public void refresh() {
		// #if CashPayment
		coinPanel.refresh();
		// #endif
		drinkPanel.refresh();
	}

	/**
	 * THis method activates or deactivates the MachinerySimulatorPanel and its
	 * component objects.
	 * 
	 * @param state
	 *            TRUE to activate, FALSE to deactivate.
	 */
	public void setActive(boolean state) {
		// #if CashPayment
		coinPanel.setActive(state);
		// #endif
		drinkPanel.setActive(state);
		doorDisplay.setEnabled(state);
	}
	
	public void setMachinerySimulatorDrinkPanel(MachinerySimulatorDrinkPanel drinkPanel) {
		this.drinkPanel = drinkPanel;
	}
	
	//#if CashPayment
	public void setMachinerySimulatorCoinPanel(MachinerySimulatorCoinPanel coinPanel) {
		this.coinPanel = coinPanel;
	}
	//#endif
}// End of class MachinerySimulatorPanel
