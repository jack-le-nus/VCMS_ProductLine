package sg.edu.nus.iss.vmcs.maintenance;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;

import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.util.VMCSException;

public class MaintenanceDrinkPanel {
	private DrinkDisplay dDisplay; // need to be access from other class.
	private MaintenanceDrinkController mctrl;
	private DrinkStoreController drinkCtrl;
	
	public MaintenanceDrinkPanel(MaintenanceDrinkController mctrl, DrinkStoreController drinkCtrl) {
		this.mctrl = mctrl;
		this.drinkCtrl = drinkCtrl;
	}
	
	public void initialize(Panel pp) {
		dDisplay = new DrinkDisplay(mctrl, drinkCtrl);
		pp.add(dDisplay);
	}
	
	/**
	 * This method returns the DrinksDisplay.
	 * 
	 * @return the DrinksDisplay.
	 */
	public DrinkDisplay getDrinksDisplay() {
		return dDisplay;
	}
	
	/**
	 * Use when machinery simulator panel changes qty; It is used to
	 * automatically update the displayed quantity in maintenance panel&#46; It
	 * is called by Maintenance Controller&#46; Not required in requirement&#46;
	 * 
	 * @throws VMCSException
	 *             if fail to update quantity display.
	 */
	public void updateQtyDisplay(int idx, int qty) throws VMCSException {
		dDisplay.update(idx, qty);
	}

	/**
	 * When transfer all button is pushed, the current display needs to be
	 * updated&#46; not required in requirement&#46;
	 * 
	 * @throws VMCSException
	 *             if fail to update quantity display.
	 */
	public void updateCurrentQtyDisplay(int qty) throws VMCSException {
		int curIdx;
		curIdx = dDisplay.getCurIdx();
		updateQtyDisplay(curIdx, qty);
	}
	
	//#if CashPayment
	/**
	 * This method display the price for the DrinkDisplay.
	 * 
	 * @param price
	 *            the price of the Drinks.
	 */
	public void displayPrice(int price) {
		dDisplay.getPriceDisplay().setValue(price + "C");
	}
	//#endif

	/**
	 * This method returns the current drinks index.
	 * 
	 * @return the current drinks index.
	 */
	public int getCurIdx() {
		return dDisplay.getCurIdx();
	}
	
	public void setActive(boolean st) {
		dDisplay.setActive(st);
	}
}
