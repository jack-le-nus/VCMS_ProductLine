/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.store;

import java.io.IOException;

/**
 * This object is storage, in the vending machine's memory, for the total number of
 * cans of each DrinksBrand held by the vending machine&#46;
 * 
 * @see CashStore
 * @see CashStoreItem
 * @see Coin
 * @see DrinksBrand
 * @see DrinksStoreItem
 * @see Store
 * @see StoreController1
 * @see StoreItem
 * @see StoreObject
 * 
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class DrinksStore extends Store {

	/**
	 * This constructor creates an instance of DrinksStore object.
	 */
	public DrinksStore() {
	}
	
	public void initialize(PropertyLoader drinksLoader)  throws IOException {

		// get the drink file from the environment property file;
		int numOfItems = drinksLoader.getNumOfItems();
		this.setStoreSize(numOfItems);

		for (int i = 0; i < numOfItems; i++) {
            DrinksStoreItem item = (DrinksStoreItem) drinksLoader.getItem(i);
			StoreObject brand = item.getContent();
			StoreObject existingBrand = this.findObject(brand.getName());
			if (existingBrand != null) {
			    item.setContent(existingBrand);
			}
			this.addItem(i, item);
		}
	}
}//End of class DrinksStore
