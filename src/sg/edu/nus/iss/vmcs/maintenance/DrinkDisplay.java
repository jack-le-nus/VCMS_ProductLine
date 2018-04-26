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
import sg.edu.nus.iss.vmcs.commonality.*;
import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.ControlElement;
import sg.edu.nus.iss.vmcs.util.*;

/**
 * This interface object is part of the MaintenancePanel&#46; It is used by the Maintainer to 
 * display the total number of cans of each DrinksBrand that are currently held in the
 * DrinksStore&#46; This object contains buttons which represent (and to select) the DrinksBrand
 * served by the vending machine.
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class DrinkDisplay extends ControlElement { 
	public final static String TITLE = "Quantity of Drinks Available";

	private DrinkStoreController storeCtrl;
	private MaintenanceDrinkController mCtrl;
	private ControlElement bi;
	//#if CashPayment
	private ControlElement price;
	//#endif
	private int curIdx; //current displayed item index;

	/**
	 * This constructor creates an instance of the DrinkDisplay object.
	 * @param mctrl the MaintenanceController.
	 */
	public DrinkDisplay(MaintenanceDrinkController mctrl, DrinkStoreController storeCtrl) {
		mCtrl = mctrl;

		this.setLayout(new BorderLayout());
		int len;
		len = storeCtrl.getStoreSize();
		StoreItem[] items = storeCtrl.getStoreItems();

		ButtonItemDisplayBuilder builder = new ButtonItemDisplayBuilder();
		Director director = new Director(builder);
		director.construct(TITLE, items, len);
		bi = builder.getResult();
		bi.addListener(new DrinkDisplayListener(mCtrl));
		bi.clear();
		
		//#if CashPayment
		price = new LabelledDisplay("Brand Price", 4, LabelledDisplay.FLOW);
		PriceDisplayListener pdl = new PriceDisplayListener(mCtrl);
		price.addListener(pdl);
		//#endif
		Panel tp = new Panel();
		tp.setLayout(new FlowLayout(FlowLayout.CENTER));
		tp.add(bi);
		curIdx = 0;
		this.add("Center", tp);
		//#if CashPayment
		this.add("South", (Panel)price);
		price.setActive(false);
		//#endif
	}

	//#if CashPayment
	/**
	 * This method returns the LabelledDisplay of the price.
	 * @return LabelledDisplay of the price.
	 */
	public ControlElement getPriceDisplay() {
		return price;
	}
	//#endif

	/**
	 * This method set the active status of the LabelledDisplay and ButtonItemDisplay.
	 * @param st the active status of the LabelledDisplay and ButtonItemDisplay.
	 */
	public void setActive(boolean st) {
		//#if CashPayment
		price.setActive(st);
		//#endif
		bi.setActive(st);
	}

	/**
	 * This method displays the stock value received for the currently selected brand.
	 * This display will be done on a text field associated with the DrinkDisplay object.
	 * @param idx the index of the stock.
	 * @param qty the quantity of the stock.
	 * @throws VMCSException if fail to display quantity.
	 */
	public void update(int idx, int qty) throws VMCSException {
		curIdx = idx;
		bi.clear();
		//#if CashPayment
		price.setActive(true);
		//#endif
		bi.update(idx, qty);
	}

	/**
	 * This method returns the current index.
	 * @return the current index.
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
}//End of class DrinkDisplay
