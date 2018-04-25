//#if CashPayment
/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.maintenance;

import java.awt.*;
import java.awt.event.ActionListener;

import sg.edu.nus.iss.vmcs.Builder;
import sg.edu.nus.iss.vmcs.ButtonItemDisplayBuilder;
import sg.edu.nus.iss.vmcs.Director;
import sg.edu.nus.iss.vmcs.ControlElement;
import sg.edu.nus.iss.vmcs.store.*;
import sg.edu.nus.iss.vmcs.util.VMCSException;

/**
 * This interface object is part of the Maintenance Panel&#46; It is used by the Maintainer to
 * display the total number of Coins of each denomination that are currently held in the
 * CashStore&#46; The object contains buttons to represent (and select) the Coin denominations
 * used in the vending machine&#46;
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class CoinDisplay extends ControlElement {
	public final static String TITLE = "Quantity of Coins Available";

	private CashStoreController storeCtrl;
	private ControlElement bi;
	private int len;
	private int curIdx;

	/**
	 * This constructor creates an instance of CoinDisplay object.
	 * @param mctrl the MainController.
	 */
	public CoinDisplay(CashStoreController storeCtrl) {

		len = storeCtrl.getStoreSize();
		StoreItem[] items = storeCtrl.getStoreItems();

		ButtonItemDisplayBuilder builder = new ButtonItemDisplayBuilder();
		Director director = new Director(builder);
		director.construct(TITLE, items, len);
		bi = builder.getResult();

		bi.addListener(new CoinDisplayListener());

		bi.clear();

		this.add(bi);
	}

	/**
	 * This method activates the CoinDisplay if the parameter is TRUE&#46; Otherwise,
	 * the CoinDisplay is deactivated.
	 * @param st the active status of the CoinDisplay.
	 */
	public void setActive(boolean st) {
		bi.setActive(st);
	}

	/**
	 * Display the quantity of selected coin, clear other display.
	 * @throws VMCSException if fail to display quantity.
	 */
	public void update(int idx, int qty) throws VMCSException {
		curIdx = idx;
		bi.clear();
		bi.update(idx, qty);
	}

	/**
	 * Get the current displayed coin index&#46 This is used for updating when store MachinerySimulatorPanel is changed.
	 * Not required.
	 */
	public int getCurIdx() {
		return curIdx;
	}

	public void addListener(ActionListener l) {
		// TODO Auto-generated method stub
		
	}

	public void setActionCommand(String valueOf) {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public void setValue(String value) {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void setItems(ControlElement[] items) {
		// TODO Auto-generated method stub
		
	}

	public void setStoreItems(StoreItem[] items) {
		// TODO Auto-generated method stub
		
	}
}//End of class CoinDisplay
//#endif
