/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.machinery;

import sg.edu.nus.iss.vmcs.system.*;
import sg.edu.nus.iss.vmcs.util.*;
import sg.edu.nus.iss.vmcs.ApplicationMediator;
import sg.edu.nus.iss.vmcs.BaseController;
import sg.edu.nus.iss.vmcs.MediatorNotification;
import sg.edu.nus.iss.vmcs.NotificationType;
import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.store.*;

/**
 * This object controls the Change State use case.
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class MachineryController extends BaseController {
	/**This attribute reference to the MainController*/
	public MainController mainCtrl;
	//#if CashPayment
	private MachineryCoinController coinCtrl;
	//#endif
	private MachineryDrinkController drinkCtrl;
	private MachinerySimulatorPanel ml;
	private Door door;

	/**
	 * This constructor creates an instance of 
	 * @param mctrl the MainController.
	 */
	public MachineryController(MainController mctrl, ApplicationMediator mediator) {
		super(mediator);
		this.mainCtrl = mctrl;
		//#if CashPayment
		coinCtrl = new MachineryCoinController(mainCtrl.getCashStoreController());
		//#endif
		drinkCtrl = new MachineryDrinkController(mainCtrl.getDrinksStoreController());
	}

	/**
	 * This method returns the MainController.
	 * @return the MainController.
	 */
	public MainController getMainController() {
		return mainCtrl;
	}

	/**
	 * This method creates the Door.
	 * @throws VMCSException if fails to instantiate Door.
	 */
	public void initialize() throws VMCSException {
		door = new Door();
	}

	/**
	 * This method will close down the machinery functions of the vending machine.
	 */
	public void closeDown() {
		if (ml != null)
			ml.dispose();
	}

	/* ************************************************************
	 * Panel methods
	 */

	/**
	 * This method displays and initializes the MachinerySimulatorPanel.
	 */
	public void displayMachineryPanel() {
		SimulatorControlPanel scp = mainCtrl.getSimulatorControlPanel();
		if (ml == null)
			ml = new MachinerySimulatorPanel(scp, this);
		ml.display();
		MediatorNotification notification = new MediatorNotification(NotificationType.SetActiveSimulatorPanel);
		notification.setObject(SimulatorControlPanel.ACT_MACHINERY, false);
		mediator.controllerChanged(this, notification);
	}

	/**
	 * This method close down the MachineryPanel.
	 */
	public void closeMachineryPanel() {
		if (ml == null)
			return;
		boolean ds = isDoorClosed();

		if (ds == false) {
			MessageDialog msg =
				new MessageDialog(ml, "Please Lock the Door before You Leave");
			msg.setVisible(true);
			return;
		}
		ml.dispose();
		MediatorNotification notification = new MediatorNotification(NotificationType.SetActiveSimulatorPanel);
		notification.setObject(SimulatorControlPanel.ACT_MACHINERY, true);
		mediator.controllerChanged(this, notification);
	}

	/* ************************************************************
	 * Door methods
	 */

	/**
	 * This method determine whether the door is closed.
	 */
	public boolean isDoorClosed() {
		return door.isDoorClosed();
	}

	/**
	 * This method set the door state and display the door state.
	 * @param state TRUE to set the state to open, otherwise, set the state to closed.
	 */
	public void setDoorState(boolean state) {
		door.setState(state);
		displayDoorState();
		
		//Disable Activate Customer Panel Button
//		MediatorNotification notification = new MediatorNotification(NotificationType.SetActiveSimulatorPanel);
//		notification.setObject(SimulatorControlPanel.ACT_CUSTOMER, false);
//		mediator.controllerChanged(this, notification);
	}

	/* ************************************************************
	 * Display methods
	 */

	/**
	 * This method displays the current Door status (open or closed) on the Door Status display.
	 */
	public void displayDoorState() {
		if (ml == null)
			return;
		ml.setDoorState(door.isDoorClosed());
	}
	
	/**
	 * This method instructs the DrinksStore to dispense one drink, and then
	 * updates the MachinerySimulatorPanel.&#46; It returns TRUE or FALSE to indicate
	 * whether dispensing was successful.
	 * @param idx the index of the drinks store item.
	 * @throws VMCSException if fail to update cash store display.
	 */
	public void dispenseDrink(int idx) throws VMCSException {
		this.drinkCtrl.dispenseDrink(idx);
		//#if CashPayment
		this.coinCtrl.displayCoinStock();
		//#endif
	}
	
	/**
	 * This method refresh the MachinerySimulatorPanel.
	 */
	public void refreshMachineryDisplay() {
		if(ml!=null){
			ml.refresh();
		}
	}

	@Override
	public Object handleMessage(MediatorNotification notification) {
		if(notification.getType() == NotificationType.LoginMaintainer) {
			Boolean success = (Boolean)notification.getObject()[0];
			if(success == true) {
				setDoorState(false);
			}
		} else if (notification.getType() == NotificationType.LogoutMaintainer) {
			return isDoorClosed();
		} else if (notification.getType() == NotificationType.TransferAll) {
			//#if CashPayment
			try {
				coinCtrl.displayCoinStock();
			} catch (VMCSException e) {
				System.out.println("transferAll:" + e);
			}
			//#endif
		} else if (notification.getType() == NotificationType.RefreshMachineryPanel) {
			refreshMachineryDisplay();
		} else if (notification.getType() == NotificationType.SetupMachinery) {
			try {
				// activate when not login
				// always diaply the door locked; isOpen false
				displayMachineryPanel();

				// display drink stock;
				drinkCtrl.displayDrinkStock();

				//#if CashPayment
				// display coin quantity;
				coinCtrl.displayCoinStock();
				//#endif

				displayDoorState();
			} catch (VMCSException e) {
				System.out.println("Machinery.setupSimulator:" + e);
			}
		}
		
		return null;
	}
}//End of class MachineryController
