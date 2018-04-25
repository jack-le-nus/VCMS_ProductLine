package sg.edu.nus.iss.vmcs.maintenance;

import sg.edu.nus.iss.vmcs.ApplicationMediator;
import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.store.DrinksBrand;
import sg.edu.nus.iss.vmcs.store.DrinksStoreItem;
import sg.edu.nus.iss.vmcs.util.VMCSException;

public class MaintenanceDrinkController {
	private MaintenanceDrinkPanel mpanel;
	private DrinkStoreController drinkCtrl;
	
	public void setupPanel(MaintenancePanel panel) {
		panel.setMaintenanceDrinkPanel(mpanel);
	}
	
	public MaintenanceDrinkController(DrinkStoreController drinkCtrl) {
		this.drinkCtrl = drinkCtrl;
		mpanel = new MaintenanceDrinkPanel(this, drinkCtrl);
	}
	
	/**
	 * This method will get the drink stock value and prices (for a specific brand) for
	 * display&#46
	 * This method invoked in DrinkDisplayListener.
	 * @param idx the index of the drinks.
	 */
	public void displayDrinks(int idx) {
		DrinksStoreItem item;
		try {
			item = (DrinksStoreItem) drinkCtrl.getStoreItem(idx);
			DrinksBrand db = (DrinksBrand) item.getContent();
			mpanel.getDrinksDisplay().update(idx, item.getQuantity());
			//#if CashPayment
			mpanel.displayPrice(db.getPrice());
			//#endif
		} catch (VMCSException e) {
			System.out.println("MaintenanceController.displayDrink:" + e);
		}

	}

	/**
	 * This method invoked by PriceDisplayListener.
	 * @param pr the price of the drinks.
	 */
	public void setPrice(int pr) {
		int curIdx = mpanel.getCurIdx();
		drinkCtrl.setPrice(curIdx, pr);
		mpanel.getDrinksDisplay().getPriceDisplay().setValue(pr + "C");
	}
}
