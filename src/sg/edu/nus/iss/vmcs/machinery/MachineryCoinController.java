//#if CashPayment
//@package sg.edu.nus.iss.vmcs.machinery;
//@
//@import sg.edu.nus.iss.vmcs.store.CashStoreController;
//@import sg.edu.nus.iss.vmcs.store.Coin;
//@import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
//@import sg.edu.nus.iss.vmcs.util.VMCSException;
//@
//@public class MachineryCoinController {
//@	
//@	public CashStoreController cashStoreCtrl;
//@	private MachinerySimulatorCoinPanel ml;
//@	
//@	public MachineryCoinController(CashStoreController cashStoreCtrl) {
//@		this.cashStoreCtrl = cashStoreCtrl;
//@	}
//@	
//@	/**
//@	 * This method update coin stock view after transfer all cash&#46;
//@	 * This method will get the stock values of coin denominations from the CashStore and
//@	 * display them on the MachinerySimulatorPanel.
//@	 * @throws VMCSException if fail to update cash store display.
//@	 */
//@	public void displayCoinStock() throws VMCSException {
//@		if (ml == null)
//@			return;
//@		ml.getCashStoreDisplay().update();
//@	}
//@	
//@	/* ************************************************************
//@	 * Interactions with the Store that need to update the display
//@	 */
//@
//@	/**
//@	 * This method will instruct the CashStore to store the Coin sent as input, and
//@	 * update the display on the MachinerySimulatorPanel.
//@	 * @throws VMCSException if fail to update cash store display.
//@	 */
//@	public void storeCoin(Coin c) throws VMCSException {
//@		this.cashStoreCtrl.storeCoin(c);
//@		if (ml != null)
//@			ml.getCashStoreDisplay().update();
//@	}
//@	
//@	/**
//@	 * This method instructs the CashStore to issue a number of coins of a specific
//@	 * denomination, and hen updates the MachinerySimulatorPanel&#46; It returns 
//@	 * TRUE or FALSE to indiate whether the change issue was successful.
//@	 * @param idx the index of the cash store item.
//@	 * @param numOfCoins the number of coins to change.
//@	 * @throws VMCSException if fail to update cash store display.
//@	 */
//@	public void giveChange(int idx, int numOfCoins) throws VMCSException {
//@		this.cashStoreCtrl.giveChange(idx, numOfCoins);
//@		if (ml != null)
//@			ml.getCashStoreDisplay().update();
//@	}
//@}
//#endif
