package sg.edu.nus.iss.vmcs.machinery;

import java.awt.Panel;

import sg.edu.nus.iss.vmcs.ControlElement;
import sg.edu.nus.iss.vmcs.Director;
import sg.edu.nus.iss.vmcs.StoreViewerBuilder;
import sg.edu.nus.iss.vmcs.store.DrinkStoreController;

public class MachinerySimulatorDrinkPanel {
	/** This constant attribute holds the drink view title */
	public static final String DRINK_VIEW_TITLE = "Quantity of Drinks Available";
	private ControlElement drinksDisplay;
	private DrinkStoreController drinkStoreCtrl;
	
	public void initializeDrinkDisplay(Panel tp) {
		StoreViewerBuilder builder;
		Director director;
		builder = new StoreViewerBuilder(drinkStoreCtrl);
		director = new Director(builder);
		director.construct(DRINK_VIEW_TITLE, drinkStoreCtrl.getStoreItems(), drinkStoreCtrl.getStoreSize());
		drinksDisplay = builder.getResult();
		
		tp.add(drinksDisplay);
	}
	
	/**
	 * This method returns the DrinksDisplay:StoreViewer.
	 * 
	 * @return the DrinksDisplay:StoreViewer.
	 */
	public ControlElement getDrinksStoreDisplay() {
		return drinksDisplay;
	}
	
	/**
	 * This method refreshes the cash display and drinks display.
	 */
	public void refresh() {
		drinksDisplay.update();
	}

	/**
	 * THis method activates or deactivates the MachinerySimulatorPanel and its
	 * component objects.
	 * 
	 * @param state
	 *            TRUE to activate, FALSE to deactivate.
	 */
	public void setActive(boolean state) {
		drinksDisplay.setActive(state);
	}
}
