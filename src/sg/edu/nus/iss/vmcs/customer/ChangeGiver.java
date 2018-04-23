/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.store.CashStoreItem;
import sg.edu.nus.iss.vmcs.store.Coin;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.system.MainController;
import sg.edu.nus.iss.vmcs.util.VMCSException;

/**
 * This control object manages the giving of change to the Customer.
 * @author Team SE16T5E
 * @version 1.0 2008-10-01
 */
public class ChangeGiver {
	private TransactionController txCtrl; 

	/**
	 * The constructor creates an instance of the object.
	 * @param txCtrl the TransactionController
	 */
	public ChangeGiver(TransactionController txCtrl){
		this.txCtrl=txCtrl;
	}
	
	/**
	 * This method is used to reset the Refund/ Change Tray display on the
	 * Customer Panel.
	 */
	public void resetChange(){
		CustomerCoinPanel custPanel=txCtrl.getCustomerCoinPanel();
		if(custPanel!=null){
			custPanel.resetChange();
		}
	}
	
	/**
	 * This method manages the issuing of change to the Customer.
	 * @param changeRequired
	 * @return return TRUE if give change use case success, otherwise, return FALSE.
	 */
	public boolean giveChange(int changeRequired){
		if(changeRequired==0)
			return true;
		try{
			int changeBal=changeRequired;
			MainController mainCtrl=txCtrl.getMainController();
			StoreController storeCtrl=mainCtrl.getCashStoreController();
			int cashStoreSize=storeCtrl.getStoreSize(); 
			for(int i=cashStoreSize-1;i>=0;i--){
				StoreItem cashStoreItem=storeCtrl.getStore().getStoreItem(i);
				int quantity=cashStoreItem.getQuantity();
				Coin coin=(Coin)cashStoreItem.getContent();
				int value=coin.getValue();
				int quantityRequired=0;
				while(changeBal>0&&changeBal>=value&&quantity>0){
					changeBal-=value;
					quantityRequired++;
					quantity--;
				}
				txCtrl.getMainController().getMachineryController().giveChange(i,quantityRequired);
			}
			txCtrl.getCustomerCoinPanel().setChange(changeRequired-changeBal);
			if(changeBal>0)
				txCtrl.getCustomerCoinPanel().displayChangeStatus(true);
		}
		catch(VMCSException ex){
			txCtrl.terminateFault();
			return false;
		}
		return true;
	}
	
	/**
	 * This method is used to display the appropriate message on the No Change
	 * Available Display depending on the current change availability.
	 */
	public void displayChangeStatus(){
		CustomerCoinPanel custPanel=txCtrl.getCustomerCoinPanel();
		if(custPanel==null)
			return;
		boolean isAnyDenoEmpty=false;
		MainController mainCtrl=txCtrl.getMainController();
		StoreController storeCtrl=mainCtrl.getCashStoreController();
		StoreItem[] cashStoreItems=storeCtrl.getStore().getItems();
		for(int i=0;i<cashStoreItems.length;i++){
			StoreItem storeItem=cashStoreItems[i];
			CashStoreItem cashStoreItem=(CashStoreItem)storeItem;
			int quantity=cashStoreItem.getQuantity();
			if(quantity==0)
				isAnyDenoEmpty=true;
		}
		custPanel.displayChangeStatus(isAnyDenoEmpty);
	}
}//End of class ChangeGiver