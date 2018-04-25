package sg.edu.nus.iss.vmcs.machinery;

import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.util.VMCSException;

public class MachineryDrinkController {
	/**This attribute reference to the StoreController*/
	public DrinkStoreController drinkStoreCtrl;
	private MachinerySimulatorDrinkPanel ml;

	public MachineryDrinkController(DrinkStoreController drinkStoreCtrl) {
		this.drinkStoreCtrl = drinkStoreCtrl;
		
	}
	
	public void dispenseDrink(int idx) throws VMCSException {
		this.drinkStoreCtrl.dispenseDrink(idx);
	}
	
	/**
	 * This method update drink stock view&#46;
	 * This method will get the stock values of drinks brands from the Drinks Store
	 * and display them on the Machinery SimulatorPanel.
	 * @throws VMCSException if fail to update drinks store display.
	 */
	public void displayDrinkStock() throws VMCSException {
		if (ml == null)
			return;
		ml.getDrinksStoreDisplay().update();
	}
}
