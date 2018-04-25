//#if CashPayment
//@package sg.edu.nus.iss.vmcs.store;
//@
//@import sg.edu.nus.iss.vmcs.ApplicationMediator;
//@import sg.edu.nus.iss.vmcs.MediatorNotification;
//@import sg.edu.nus.iss.vmcs.NotificationType;
//@
//@public class CashStoreController extends StoreController {
//@	
//@	public CashStoreController(Store store, PropertyLoader loader, ApplicationMediator mediator) {
//@		super(store, loader, mediator);
//@		this.mediator.register("CashStoreController", this);
//@	}
//@
//@	/**
//@	 * This method will instruct the {@link CashStore} to store the {@link Coin} sent as input, and
//@	 * update the display on the Machinery Simulator Panel.
//@	 * @param c the Coin to be stored.
//@	 */
//@	public void storeCoin(Coin c) {
//@		int idx = ((CashStore)this.store).findCashStoreIndex(c);
//@		CashStoreItem item;
//@		item = (CashStoreItem) this.getStoreItem(idx);
//@		item.increment();
//@	}
//@	
//@	/**
//@	 * This method returns the total number of cash held in the {@link CashStore}.
//@	 * @return the total number of cash held.
//@	 */
//@	public int getTotalCash(){
//@		int i;
//@		int size;
//@
//@		size = this.store.getStoreSize();
//@		CashStoreItem item;
//@		int qty;
//@		int val;
//@		int tc = 0;
//@		Coin c;
//@
//@		for (i = 0; i < size; i++) {
//@			item = (CashStoreItem) this.store.getStoreItem(i);
//@			qty = item.getQuantity();
//@			c = (Coin) item.getContent();
//@			val = c.getValue();
//@			tc = tc + qty * val;
//@		}
//@		return tc;
//@	}
//@	
//@	/**
//@	 * This method will instruct the {@link CashStore} to store the {@link Coin} sent as input, and then
//@	 * update the display on the {@link sg.edu.nus.iss.vmcs.machinery.MachinerySimulatorPanel}.
//@	 * @return the number of cash transfered.
//@	 */
//@	public int transferAll()  {
//@		int i;
//@		int cc = 0; // coin quauntity;
//@		int size = this.store.getStoreSize();
//@
//@		CashStoreItem item;
//@		for (i = 0; i < size; i++) {
//@			item = (CashStoreItem) this.store.getStoreItem(i);
//@			cc = cc + item.getQuantity();
//@			item.setQuantity(0);
//@		}
//@
//@		return cc;
//@	}
//@	
//@	/**
//@	 * This method instructs the {@link CashStore} to issue a number of {@link Coin} of a specific
//@	 * denomination, and then updates the {@link sg.edu.nus.iss.vmcs.machinery.MachinerySimulatorPanel}&#46; It return TRUE
//@	 * or FALSE to indicate whether the change issue was successful&#46;
//@	 * @param idx the index of the Coin&#46;
//@	 * @param numOfCoins the number of Coin to deduct&#46; 
//@	 */
//@	public void giveChange(int idx, int numOfCoins)  {
//@		CashStoreItem item;
//@		item = (CashStoreItem) getStoreItem(idx);
//@		for (int i = 0; i < numOfCoins; i++)
//@			item.decrement();
//@	}
//@	
//@	@Override
//@	public Object handleMessage(MediatorNotification notification) {
//@		if (notification.getType() == NotificationType.TransferAll) {
//@			return transferAll();
//@		}
//@		return null;
//@	}
//@}
//#endif
