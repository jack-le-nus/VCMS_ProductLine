/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.maintenance;

import java.awt.Frame;
import sg.edu.nus.iss.vmcs.ApplicationMediator;
import sg.edu.nus.iss.vmcs.BaseController;
import sg.edu.nus.iss.vmcs.Dispatcher;
import sg.edu.nus.iss.vmcs.MediatorNotification;
import sg.edu.nus.iss.vmcs.NotificationType;
import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.machinery.MachineryController;
//#if CashPayment
//@import sg.edu.nus.iss.vmcs.store.CashStoreController;
//@import sg.edu.nus.iss.vmcs.store.CashStoreItem;
//#endif
import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.store.DrinksBrand;
import sg.edu.nus.iss.vmcs.store.DrinksStoreItem;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.system.MainController;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;
import sg.edu.nus.iss.vmcs.util.MessageDialog;
import sg.edu.nus.iss.vmcs.util.VMCSException;

/**
 * This control object handles the system maintenance use case.
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class MaintenanceController extends BaseController {
	private MainController mCtrl;
	private MaintenancePanel mpanel;
	//#if CashPayment
//@	private MaintenanceCoinPanel coinPanel;
//@	private MaintenanceCoinController coinCtrl;
	//#endif
	private MaintenanceDrinkPanel drinkPanel;
	private MaintenanceDrinkController drinkCtrl;
	private AccessManager am;

	/**
	 * This constructor creates an instance of the MaintenanceController.
	 * @param mctrl the MainController.
	 */
	public MaintenanceController(MainController mctrl, ApplicationMediator mediator) {
		super(mediator);
		mCtrl = mctrl;
		am = new AccessManager(this);
		
		drinkCtrl = new MaintenanceDrinkController(mCtrl.getDrinksStoreController());
		
		//#if CashPayment
//@		coinCtrl = new MaintenanceCoinController(mCtrl.getCashStoreController(), this, mediator);
		//#endif
		
		//#if CashPayment
//@		this.configureCommands();
		//#endif
	}

	/**
	 * This method returns the MainController.
	 * @return the MainController.
	 */
	public MainController getMainController() {
		return mCtrl;
	}

	/**
	 * This method setup the maintenance panel and display it.
	 */
	public void displayMaintenancePanel() {
		SimulatorControlPanel scp = mCtrl.getSimulatorControlPanel();
		if (mpanel == null) {
			mpanel = new MaintenancePanel((Frame) scp, this);
			drinkCtrl.setupPanel(mpanel);
			//#if CashPayment
//@			coinCtrl.setupPanel(mpanel);
			//#endif
			mpanel.initialize();
		}
		mpanel.display();
		mpanel.setActive(MaintenancePanel.DIALOG, true);
		// setActive of password, invalid and valid display.
	}

	/**
	 * This method returns the AccessManager.
	 * @return the AccessManager.
	 */
	public AccessManager getAccessManager() {
		return am;
	}

	/**
	 * This method sets whether the maintainer login&#46;
	 * When the MaintenanceController receives a message saying that the Maintainer has
	 * successfully logged-in, the following will occur;
	 * <br>
	 * 1- Messages will be sent to activate the following elements of the MaintenancePanel:
	 * CoinDisplay, DrinkDisplay, TotalCashDisplay, ExitButton, Transfer Cash Button and
	 * Total Cash Button&#46;
	 * <br>
	 * 2- The Door will be instructed to set its status as unlocked, and then inform the 
	 * MachinerySimulatorPanel to update the Door Status Display&#46;
	 * <br>
	 * 3- The SimulatorControlPanel will be instructed to deactivate the
	 * ActivateCustomerPanelButton&#46;
	 * <br>
	 * 4- The TransactionController will be instructed to terminate and suspend
	 * Customer Transactions&#46 Operation TerminateTransaction of TransactionController
	 * will be used to accomplish this&#46
	 * @param st If TRUE then login successfully, otherwise login fails.
	 */
	public boolean loginMaintainer(boolean success) {
		this.mediator.controllerChanged(this, new MediatorNotification(NotificationType.LoginMaintainer, success));
		mpanel.displayPasswordState(success);
		mpanel.clearPassword();
		if (success == true) {
			// login successful
			mpanel.setActive(MaintenancePanel.WORKING, true);
			mpanel.setActive(MaintenancePanel.PSWD, false);
		}
		
		return success;
	}
	
	public void logoutMaintainer() {
		this.mediator.controllerChanged(this, new MediatorNotification(NotificationType.LogoutMaintainer));
	}

	/**
	 * When the MaintenanceController receives a message saying that the Maintainer
	 * has correctly logged-out, the following will occur:
	 * <br>
	 * 1- Check the door status of Door to determine whether the vending machine
	 * door is locked&#46 if the door is unlocked, then the exit request is ignored&#46
	 * <br>
	 * 2- Re-set the MaintenancePanel (initial values set, buttons activated/ deactivated)&#46
	 * <br>
	 * 3- Update the CustomerPanel and permit Customer transaction to re-start&#46
	 * This method is invoked by the exit button listener.
	 */
	public void logoutMaintainer(boolean isMachineryDoorClosed) {
		if (isMachineryDoorClosed == false) {
			MessageDialog msg =
				new MessageDialog(
					mpanel,
					"Please Lock the Door before You Leave");
			msg.setLocation(500, 500);
			return;
		}

		mpanel.setActive(MaintenancePanel.DIALOG, true);
	}

	/**
	 * This method will close down the maintenance functions of the vending machine&#46
	 * This method close down the MaintenancePanel.
	 */
	public void closeDown() {
		if (mpanel != null)
			mpanel.closeDown();
	}

	@Override
	public Object handleMessage(MediatorNotification notification) {
		if (notification.getType() == NotificationType.LogoutMaintainer) {
			this.logoutMaintainer((Boolean)notification.getObject()[0]);
		} else if (notification.getType() == NotificationType.TransferAll) {
			//#if CashPayment
//@			coinCtrl.transferAll((Integer)notification.getObject()[0]);
			//#endif
		} else if (notification.getType() == NotificationType.SetupMaintainer) {
			displayMaintenancePanel();
		}
		return null;
	}
	
	//#if CashPayment
//@	private void configureCommands() {
//@		Dispatcher.getInstance().addCommand("getTotalCash", new GetTotalCashCommand(this.coinCtrl));
//@		Dispatcher.getInstance().addCommand("displayCoin", new DisplayCoinCommand(this.coinCtrl));
//@	}
	//#endif
}//End of class MaintenanceController
