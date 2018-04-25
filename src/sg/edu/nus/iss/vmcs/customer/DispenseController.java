/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.MediatorNotification;
import sg.edu.nus.iss.vmcs.NotificationType;
import sg.edu.nus.iss.vmcs.store.DrinksBrand;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.store.StoreObject;
import sg.edu.nus.iss.vmcs.system.MainController;
import sg.edu.nus.iss.vmcs.util.VMCSException;

/**
 * This control object is for handling the dispense drink use case.
 * @author Team SE16T5E
 * @version 1.0 2008-10-01
 */
public class DispenseController {
    private TransactionController txCtrl;
    private CustomerDrinkPanel custDrinkPanel;
	/**Set to TRUE when the drink is successfully dispensed during the transaction.*/
	private boolean drinkDispensed=false;
	/**Price of the selected drink.*/
	private int price=0;
	/**Identifier of the selected drink.*/
	private int selection=-1;
	
	private int drinkSelection = 0;
	
    /**
     * This constructor creates an instance of the object.
     * @param txCtrl the Transaction Controller
     */
    public DispenseController(TransactionController txCtrl) {
    	this.txCtrl=txCtrl;
    }
    
    /**
     * This method updates the whole Drink Selection Box with current names, stocks and prices.
     */
	public void updateDrinkPanel(){
		CustomerDrinkPanel custPanel=getCustomerDrinkPanel();
		if(custPanel==null){
			return;
		}
		updateDrinkSelection(-1);
		int storeSize=txCtrl.getMainController().getDrinksStoreController().getStoreSize();
		for(int i=0;i<storeSize;i++){
			StoreItem storeItem=txCtrl.getMainController().getDrinksStoreController().getStoreItem(i);
			int quantity=storeItem.getQuantity();
			DrinksBrand drinksBrand=(DrinksBrand)storeItem.getContent();
			String name=drinksBrand.getName();
			int price=drinksBrand.getPrice();
			custPanel.getDrinkSelectionBox().update(i, quantity, price, name);
		}
	}
	
	/**
     * This method is used to display the latest stock and price information on the Drink Selection Box.
	 * @param index
	 */
	public void updateDrinkSelection(int index){
		this.drinkSelection=index;
	}
	
	/**
	 * This method will be used to activate or deactivate (as indicated through a parameter)
	 * the Drink Selection Box so that transactions can continue or will be disallowed.
	 * @param allow TRUE to activate, FALSE to deactivate the Drink Selection Box.
	 */
	public void allowSelection(boolean allow) {
		MainController mainCtrl=txCtrl.getMainController();
		CustomerDrinkPanel custPanel=getCustomerDrinkPanel();
		if(custPanel==null) {
			return;
		}
		
		DrinkSelectionBox drinkSelectionBox=custPanel.getDrinkSelectionBox();
		StoreController storeCtrl=mainCtrl.getDrinksStoreController();
		int storeSize=storeCtrl.getStoreSize();
		for(int i=0;i<storeSize;i++) {
			drinkSelectionBox.setState(i,allow);
			StoreItem storeItem=storeCtrl.getStoreItem(i);
			int quantity=storeItem.getQuantity();
			if(quantity==0)
				drinkSelectionBox.setItemState(i,true);
		}
	}
	
	/**
	 * This method will be used to instruct the Can Collection Box to remove the 
	 * drinks can shape or drink brand name from being displayed.
	 */
	public void ResetCan(){
		drinkSelection=-1;
		getCustomerDrinkPanel().resetCan();
	}
	
	/**
	 * This method will be used to dispense a drink&#46;  It will:
	 * <br>
	 * 1- Instruct the Drinks Store to dispense the drink&#46; It will also instruct the
	 * Can Collection Box to display a can shape&#46;
	 * <br>
	 * 2- Instruct the Store Controller to update the Drink Store Display on the
	 * Machinery Simulator Panel&#46;
	 * <br>
	 * 3- In case of fault detection, it will send a "fault detected" response to the 
	 * Transaction Controller&#46;
	 * @param selectedBrand the selected brand&#46;
	 */
	public boolean dispenseDrink(){
		int selectedBrand = selection;
		try{
			txCtrl.getMainController().getMachineryController().dispenseDrink(selectedBrand);
			MainController mainCtrl=txCtrl.getMainController();
			StoreController storeCtrl=mainCtrl.getDrinksStoreController();
			StoreItem drinkStoreItem=storeCtrl.getStore().getStoreItem(selectedBrand);
			StoreObject storeObject=drinkStoreItem.getContent();
			DrinksBrand drinksBrand=(DrinksBrand)storeObject;
			String drinksName=drinksBrand.getName();
			int price=drinksBrand.getPrice();
			int quantity=drinkStoreItem.getQuantity();
			getCustomerDrinkPanel().setCan(drinksName);
			updateDrinkSelection(selectedBrand);
			getCustomerDrinkPanel().getDrinkSelectionBox().update(selectedBrand, quantity, price, drinksName);
		}
		catch(VMCSException ex){
			txCtrl.terminateFault();
			return false;
		}
		return true;
	}
	
	public void setupPanel(CustomerPanel custPanel) {
		this.custDrinkPanel = new CustomerDrinkPanel(this.txCtrl);
		custPanel.setCustomerDrinkPanel(this.custDrinkPanel);
	}
	
	/**
	 * This method displays and initialize the CustomerPanel.
	 */
	public void displayCustomerPanel() {
		updateDrinkPanel();
		allowSelection(true);
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
		setSelection(drinkIdentifier);
		StoreItem storeItem=this.txCtrl.getMainController().getDrinksStoreController().getStoreItem(drinkIdentifier);
		DrinksBrand drinksBrand=(DrinksBrand)storeItem.getContent();
		setPrice(drinksBrand.getPrice());
		ResetCan();
		allowSelection(false);
	}
	
	/**
	 * This method sets whether the drink is dispensed.
	 * @param drinkDispensed TRUE the drink is dispensed, otherwise, FALSE.
	 */
	public void setDrinkDispensed(boolean drinkDispensed) {
		this.drinkDispensed = drinkDispensed;
	}

	/**
	 * This method returns whether the drink is dispensed.
	 * @return TRUE if the drink is dispensed, otherwise FALSE.
	 */
	public boolean isDrinkDispensed() {
		return drinkDispensed;
	}

	/**
	 * This method sets the price of the selected drink.
	 * @param price the price of the selected drink.
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * This method returns the price of the selected drink.
	 * @return the price of the selected drink.
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * This method sets the selected drink index.
	 * @param selection the selected drink index.
	 */
	public void setSelection(int selection) {
		this.selection = selection;
	}

	/**
	 * This method returns the selected drink index.
	 * @return the selected drink index.
	 */
	public int getSelection() {
		return selection;
	}

	public CustomerDrinkPanel getCustomerDrinkPanel() {
		return custDrinkPanel;
	}

	public void setCustomerDrinkPanel(CustomerDrinkPanel custDrinkPanel) {
		this.custDrinkPanel = custDrinkPanel;
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
		dispenseDrink();
		allowSelection(true);
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
		updateDrinkPanel();
		allowSelection(true);
	}
}//End of class DispenseController