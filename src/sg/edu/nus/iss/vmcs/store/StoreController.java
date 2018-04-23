package sg.edu.nus.iss.vmcs.store;

import java.io.IOException;

import sg.edu.nus.iss.vmcs.ApplicationMediator;
import sg.edu.nus.iss.vmcs.BaseController;
import sg.edu.nus.iss.vmcs.MediatorNotification;

public class StoreController extends BaseController {
	protected Store store;
	protected PropertyLoader loader;
	
	public StoreController(Store store, PropertyLoader loader, ApplicationMediator mediator) {
		super(mediator);
		this.store = store;
		this.loader = loader;
	}

	@Override
	public Object handleMessage(MediatorNotification notification) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * This method instantiate the {@link CashStore}, {@link DrinksStore} and initialize it.
	 * @throws IOException if fail to initialize stores; reading properties.
	 */
	public void initialize() throws IOException {
		this.store.initialize(this.loader);
	}

	/**
	 * This method return the total size of the {@link Store} of the given type of {@link Store}.
	 * @param type the type of the Store (either CASH or DRINK).
	 * @return the size of the store of the given type of Store.
	 */
	public int getStoreSize() {
		return store.getStoreSize();
	}
	
	/**
	 * This method returns an array of {@link StoreItem} of the given type of {@link Store}.
	 * @param type the type of Store.
	 * @return an array of StoreItem.
	 */
	public StoreItem[] getStoreItems() {
		return store.getItems();
	}
	
	/**
	 * This method will either:
	 * <br>
	 * - instruct the {@link CashStore} to update the quantity of a {@link Coin} denomination to new
	 * value supplied and update the total cash held in the {@link CashStore}; or
	 * <br>
	 * - instruct the {@link DrinksStore} to update the drinks stock for a {@link DrinksBrand} required
	 * to a new value supplied.
	 * @param type the type of Store.
	 * @param idx the index of the StoreItem.
	 * @param qty the quantity of the StoreItem.
	 */
	public void changeStoreQty(int idx, int qty) {
			System.out.println("StoreController.changeStoreQty: type:"+ this.getClass().getSimpleName() + " qty:"+ qty);
			store.setQuantity(idx, qty);
	}
	
	/**
	 * This method returns the {@link StoreItem} with the given {@link Store} type and index of {@link StoreItem}.
	 * @param type the type of Store.
	 * @param idx the index of the StoreItem.
	 * @return the StoreItem.
	 */
	public StoreItem getStoreItem(int idx) {
		return store.getStoreItem(idx);
	}
	
	/**
	 * This method will close down the store management function of the vending machine.
	 * This involves saving the attributes of the stores to the property file.
	 * @throws IOException if fail to save cash properties and drinks properties.
	 */
	public void closeDown() throws IOException {
		// save back cash property;
		saveProperties();
	}

	/**
	 * This method saves the attributes of the {@link CashStore} to the input file.
	 * @throws IOException if fail to save cash properties.
	 */
	private void saveProperties() throws IOException {
		int size = store.getStoreSize();
		loader.setNumOfItems(size);
		for (int i = 0; i < size; i++) {
			loader.setItem(i, store.getStoreItem(i));
		}
		loader.saveProperty();
	}
	
	/**
	 * This method returns a {@link Store} of a specified type (i&#46;e&#46; Cash or Drink)&#46;
	 * @param type the type of Store&#46;
	 * @return the Store of the specified type&#46;
	 */
	public Store getStore() {
		return (Store) store;
	}
}
