package sg.edu.nus.iss.vmcs.customer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;

import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.util.LabelledValue;

public class CustomerDrinkPanel {
	private DrinkSelectionBox drinkSelectionBox;
	private LabelledValue lbdCollectCan=new LabelledValue("Collect Can Here:","",100);
	private TransactionController txCtrl;
	
	public CustomerDrinkPanel(TransactionController txCtrl) {
		this.txCtrl = txCtrl;
	}
	
	public void initializeDrink(Panel pan0) {
		drinkSelectionBox=new DrinkSelectionBox(txCtrl.getMainController().getDrinksStoreController(), txCtrl);
		drinkSelectionBox.setActive(true);
		
		pan0.setLayout(new GridBagLayout());
		
		pan0.add(drinkSelectionBox,new GridBagConstraints(0,4,0,1,0.0,0.0,
			    GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
			    new Insets(5,0,0,0),10,0));
		pan0.add(lbdCollectCan,new GridBagConstraints(0,8,0,1,0.0,0.0,
			    GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
			    new Insets(2,0,20,0),10,0));
	}
	
	/**
	 * This method activates or deactivates the DrinkSelectionBox in the CustomerPanel.
	 * @param active the active status of the DrinkSelectionBox; TRUE to activate,
	 * FALSE to deactivate it.
	 */
	public void setDrinkSelectionBoxActive(boolean active){
		drinkSelectionBox.setActive(active);
	}
	
	/**
	 * This method returns the DrinkSelectionBox in the CustomerPanel.
	 * @return the DrinkSelectionBox in the CustomerPanel.
	 */
	public DrinkSelectionBox getDrinkSelectionBox(){
		return drinkSelectionBox;
	}
	

	
	/**
	 * This method sets the can name to the collect tray.
	 * @param name the name of the can.
	 */
	public void setCan(String name){
		lbdCollectCan.setValue(name);
	}
	
	/**
	 * This method return the name of the can.
	 * @return the name of the can&#46; 
	 */
	public String getCan(){
		return lbdCollectCan.getValue();
	}
	
	/**
	 * This method resets the drink can display at the collection tray.
	 */
	public void resetCan(){
		setCan("");
	}
}
