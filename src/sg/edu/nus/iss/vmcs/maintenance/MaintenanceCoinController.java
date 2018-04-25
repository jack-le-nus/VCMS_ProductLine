//#if CashPayment
package sg.edu.nus.iss.vmcs.maintenance;

import sg.edu.nus.iss.vmcs.ApplicationMediator;
import sg.edu.nus.iss.vmcs.MediatorNotification;
import sg.edu.nus.iss.vmcs.NotificationType;
import sg.edu.nus.iss.vmcs.store.CashStoreController;
import sg.edu.nus.iss.vmcs.store.CashStoreItem;
import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.util.VMCSException;

public class MaintenanceCoinController {
	private MaintenanceCoinPanel mpanel; 
	private CashStoreController cashCtrl;
	private MaintenanceController mCtrl;
	private ApplicationMediator mediator;
	
	public void setupPanel(MaintenancePanel panel) {
		panel.setMaintenanceCoinPanel(mpanel);
	}
	
	public MaintenanceCoinController(CashStoreController cashCtrl, MaintenanceController mCtrl, ApplicationMediator mediator) {
		this.cashCtrl = cashCtrl;
		this.mediator = mediator;
		this.mCtrl = mCtrl;
		mpanel = new MaintenanceCoinPanel(cashCtrl);
	}
	
	/**
	 * This method is invoked by the StoreViewerListener.
	 * @param type the type of the Store.
	 * @param idx the index of the StoreItem.
	 * @param qty the quantity of the StoreItem.
	 */
	public void changeStoreQty(int idx, int qty) {
		try {
			mpanel.updateQtyDisplay(idx, qty);
			mpanel.initCollectCash();
			mpanel.initTotalCash();
		} catch (VMCSException e) {
			System.out.println("MaintenanceController.changeStoreQty:" + e);
		}
	}
	
	/**
	 * This method sends the total cash held in the CashStore to the MaintenancePanel&#46
	 * This method is invoked by the TotalCashButtonListener.
	 */
	public void getTotalCash() {
		int tc = cashCtrl.getTotalCash();
		mpanel.displayTotalCash(tc);
	}
	
	/**
	 * This method will be used to get the total number of coins of a selected denomination&#46
	 * This method invoked in CoinDisplayListener.
	 * @param idx the index of the Coin.
	 */
	public void displayCoin(int idx) {
		CashStoreItem item;
		try {
			item = (CashStoreItem) cashCtrl.getStoreItem(idx);
			mpanel.getCoinDisplay().update(idx, item.getQuantity());
		} catch (VMCSException e) {
			System.out.println("MaintenanceController.displayCoin:" + e);
		}
	}

	/**
	 * This method is to facilitate the transfer of all cash in CashStore to the maintainer&#46
	 * This method is invoked by the TransferCashButtonListener&#46
	 * It get all the cash from store and set store cash 0.
	 */
	public void transferAll(int cc) {
		try {
			mpanel.displayCoins(cc);
			// the cash qty current is displayed in the Maintenance panel needs to be update to be 0;
			// not required.
			mpanel.updateCurrentQtyDisplay(0);
		} catch (VMCSException e) {
			System.out.println("MaintenanceController.transferAll:" + e);
		}
	}
	
	public void transferAll() {
		this.mediator.controllerChanged(this.mCtrl, new MediatorNotification(NotificationType.TransferAll));
	}
}
//#endif
