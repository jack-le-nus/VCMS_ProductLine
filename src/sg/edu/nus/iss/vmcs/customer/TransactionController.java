/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.customer;

import java.awt.Frame;

import sg.edu.nus.iss.vmcs.ApplicationMediator;
import sg.edu.nus.iss.vmcs.BaseController;
import sg.edu.nus.iss.vmcs.MediatorNotification;
import sg.edu.nus.iss.vmcs.NotificationType;
import sg.edu.nus.iss.vmcs.store.DrinksBrand;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.system.MainController;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;

/**
 * This control object coordinates the customer transactions for selection of a drink brand,
 * coin input, storage of coins and termination requests for ongoing transactions.
 * @author Team SE16T5E
 * @version 1.0 2008-10-01
 */
public class TransactionController extends BaseController {
	private MainController mainCtrl;
	private CustomerPanel custPanel;
	private DispenseController dispenseCtrl;
	//#if CashPayment
	private TransactionCoinController coinCtrl;
	//#endif
	
	/**
	 * This constructor creates an instance of the TransactionController.
	 * @param mainCtrl the MainController.
	 */
	public TransactionController(MainController mainCtrl, ApplicationMediator mediator) {
		super(mediator);
		this.mainCtrl = mainCtrl;
		dispenseCtrl=new DispenseController(this);
		//#if CashPayment
		coinCtrl = new TransactionCoinController(this);
		//#endif
	}

	/**
	 * This method returns the MainController.
	 * @return the MainController.
	 */
	public MainController getMainController() {
		return mainCtrl;
	}

	/**
	 * This method displays and initialize the CustomerPanel.
	 */
	public void displayCustomerPanel() {
		SimulatorControlPanel scp = mainCtrl.getSimulatorControlPanel();
		
		custPanel = new CustomerPanel((Frame) scp, this);
		dispenseCtrl.setupPanel(custPanel);
		//#if CashPayment
		coinCtrl.setupPanel(custPanel);
		//#endif
	    
		custPanel.display();
		dispenseCtrl.displayCustomerPanel();
		//#if CashPayment
		coinCtrl.displayCustomerPanel();
		//#endif
	}
	
	/**
	 * This method will start the customer transaction&#46; It receives the identification
	 * for the selected drink brand (item) from the Customer Panel&#46; The following
	 * actions are performed in the method:
	 * <br>
	 * 1- The price of the selected item is obtained&#46;
	 * <br>
	 * 2- The Refund/ Change Tray Display is reset&#46;
	 * <br>
	 * 3- The Can Collection Box is reset&#46;
	 * <br>
	 * 4- The Drink Selection Box is deactivated to disallow the selection of further
	 * drinks when the transaction is in progress&#46;
	 * <br>
	 * 5- The Coin Receiver will be instructed to start receiving the coins&#46;
	 * @param drinkIdentifier the drink brand item identifier.
	 */
	public void startTransaction(int drinkIdentifier) {
		dispenseCtrl.startTransaction(drinkIdentifier);
		//#if CashPayment
		coinCtrl.startTransaction();
		//#endif
		
		custPanel.setTerminateButtonActive(true);
	}
	
	/**
	 * This method processes the money received by the Coin Receiver during the progress
	 * of a transaction&#46;  The following actions are performed during this method:
	 * <br>
	 * 1- The current total money inserted is obtained from the Coin Receiver&#46;
	 * <br>
	 * 2- If the received money is more than or equal to the price of the drink, 
	 * method CompleteTransaction of the Transaction Controller is triggered&#46;
	 * <br>
	 * 3- If the received money is less than the price of the drink, the Coin Receiver
	 * is instructed to continue receiving the coin&#46;
	 * @param total the total money received&#46;
	 */
	public void processMoneyReceived(int total){
		//#if CashPayment
		if(total>=dispenseCtrl.getPrice())
			completeTransaction();
		else{
			coinCtrl.processMoneyReceived();
		}
		//#else
//@		completeTransaction();
		//#endif
	}
	
	/**
	 * This method is performed when the Transaction Controller is informed that coin
	 * entry is complete and the money received is sufficient to dispense the drink.
	 * The following actions are performed.
	 * <br>
	 * 1- Dispense the drink.
	 * <br>
	 * 2- Give change if necessary.
	 * <br>
	 * 3- Store the Coins that have been entered into the Cash Store.
	 * <br>
	 * 4- Reset the Drink Selection Box to allow further transactions.
	 */
	public void completeTransaction(){
		System.out.println("CompleteTransaction: Begin");
		//#if CashPayment
		coinCtrl.completeTransaction(dispenseCtrl.getPrice());
		//#endif
		dispenseCtrl.completeTransaction();
		
		mediator.controllerChanged(this, new MediatorNotification(NotificationType.RefreshMachineryPanel));
		System.out.println("CompleteTransaction: End");
	}
	
	/**
	 * If the TransactionController is informed that a fault was discovered while
	 * dispensing a drink, giving change or storing Coins, it will use this method
	 * to deactivate the Drink Selection Box and instruct the CoinReceiver to refund the
	 * money inserted by the customer.
	 */
	public void terminateFault(){
		System.out.println("TerminateFault: Begin");
		dispenseCtrl.allowSelection(false);
		//#if CashPayment
		coinCtrl.terminateFault();
		//#endif
		mediator.controllerChanged(this, new MediatorNotification(NotificationType.RefreshMachineryPanel));
		System.out.println("TerminateFault: End");
	}
	
	/**
	 * If the TransactionController receivers a request to terminate the current
	 * transaction then following will occur:
	 * <br>
	 * 1- If there is no transaction in progress or coin input is completed then the 
	 * CustomerPanel will be instructed to deactivate the DrinkSelectionBox&#46;
	 * <br>
	 * 2- If coin input is not yet complete, the CoinReceiver will be instructed to stop
	 * coin input and refund the money entered so far&#46;
	 * <br>
	 * 3- The DrinkSelectionBox is then reset to allow further transactions&#46;
	 */
	public void terminateTransaction(){
		System.out.println("TerminateTransaction: Begin");
		dispenseCtrl.allowSelection(false);
		//#if CashPayment
		coinCtrl.terminateTransaction();
		//#else
//@		if(custPanel!=null){
//@			custPanel.setSubmitButtonActive(false);
//@		}
		//#endif
		if(custPanel!=null){
			custPanel.setTerminateButtonActive(false);
		}
		System.out.println("TerminateTransaction: End");
	}
	
	/**
	 * This method will cancel an ongoing customer transaction.
	 */
	public void cancelTransaction() {
		System.out.println("CancelTransaction: Begin");
		//#if CashPayment
		coinCtrl.cancelTransaction();
		//#endif
		dispenseCtrl.allowSelection(true);
		mediator.controllerChanged(this, new MediatorNotification(NotificationType.RefreshMachineryPanel));
		System.out.println("CancelTransaction: End");
	}
	
	/**
	 * This method refreshes the CustomerPanel when maintainer logs-out.
	 */
	public void refreshCustomerPanel(){
		/*
		if(custPanel==null){
			mainCtrl.getSimulatorControlPanel().setActive(SimulatorControlPanel.ACT_CUSTOMER,true);
		}
		*/
		dispenseCtrl.refreshCustomerPanel();
		//#if CashPayment
		coinCtrl.refreshCustomerPanel();
		//#endif
		custPanel.setTerminateButtonActive(true);
	}
	
	/**
	 * This method will close down the transaction control function of the vending
	 * machine.
	 */
	public void closeDown() {
		if (custPanel != null)
			custPanel.closeDown();
	}
	
	/**
	 * This method returns the CustomerPanel.
	 * @return the CustomerPanel.
	 */
	public CustomerPanel getCustomerPanel(){
		return custPanel;
	}
	
	/**
	 * This method returns the DispenseController.
	 * @return the DispenseController.
	 */
	public DispenseController getDispenseController(){
		return dispenseCtrl;
	}
	
	/**
	 * This method will nullify reference to customer panel.
	 */
	public void nullifyCustomerPanel(){
		custPanel=null;
	}

	@Override
	public Object handleMessage(MediatorNotification notification) {
		if(notification.getType() == NotificationType.LoginMaintainer) {
			Boolean success = (Boolean)notification.getObject()[0];
			if(success == true) {
				terminateTransaction();
			}
		} else if (notification.getType() == NotificationType.LogoutMaintainer) {
			CustomerPanel custPanel= getCustomerPanel();
			if(custPanel!=null) {
				refreshCustomerPanel();
			}
			return custPanel == null;
		} else if (notification.getType() == NotificationType.SetupCustomer) {
			displayCustomerPanel();
		}
		return null;
	}
}//End of class TransactionController
