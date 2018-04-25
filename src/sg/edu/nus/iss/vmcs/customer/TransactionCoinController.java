//#if CashPayment
//@package sg.edu.nus.iss.vmcs.customer;
//@
//@import sg.edu.nus.iss.vmcs.ApplicationMediator;
//@import sg.edu.nus.iss.vmcs.BaseController;
//@import sg.edu.nus.iss.vmcs.MediatorNotification;
//@import sg.edu.nus.iss.vmcs.NotificationType;
//@import sg.edu.nus.iss.vmcs.system.MainController;
//@import sg.edu.nus.iss.vmcs.store.Store;
//@import sg.edu.nus.iss.vmcs.store.StoreItem;
//@
//@public class TransactionCoinController {
//@	private CustomerCoinPanel custCoinPanel;
//@	private ChangeGiver changeGiver;
//@	private CoinReceiver coinReceiver;
//@	private TransactionController trCtrl;
//@
//@	/**Set to TRUE when change is successfully issued during the transaction.*/
//@	private boolean changeGiven=false;
//@	
//@	public TransactionCoinController(TransactionController trCtrl) {
//@		this.trCtrl = trCtrl;
//@		coinReceiver=new CoinReceiver(this, trCtrl);
//@		changeGiver=new ChangeGiver(this, trCtrl);
//@	}
//@	
//@	public void displayCustomerPanel() {
//@		changeGiver.displayChangeStatus();
//@		coinReceiver.setActive(false);
//@	}
//@	
//@	public void startTransaction() {
//@		changeGiver.resetChange();
//@		changeGiver.displayChangeStatus();
//@		coinReceiver.startReceiver();
//@	}
//@	
//@	/**
//@	 * This method returns the ChangeGiver.
//@	 * @return the ChangeGiver.
//@	 */
//@	public ChangeGiver getChangeGiver(){
//@		return changeGiver;
//@	}
//@	
//@	/**
//@	 * This method returns the CoinReceiver.
//@	 * @return the CoinReceiver.
//@	 */
//@	public CoinReceiver getCoinReceiver(){
//@		return coinReceiver;
//@	}
//@
//@	/**
//@	 * This method sets whether the change is given.
//@	 * @param changeGiven TRUE the change is given, otherwise FALSE.
//@	 */
//@	public void setChangeGiven(boolean changeGiven) {
//@		this.changeGiven = changeGiven;
//@	}
//@
//@	/**
//@	 * This method returns whether the change is given.
//@	 * @return TRUE if the change is given, otherwise FALSE.
//@	 */
//@	public boolean isChangeGiven() {
//@		return changeGiven;
//@	}
//@	
//@	public CustomerCoinPanel getCustomerCoinPanel() {
//@		return custCoinPanel;
//@	}
//@
//@	public void setCustomerCoinPanel(CustomerCoinPanel custCoinPanel) {
//@		this.custCoinPanel = custCoinPanel;
//@	}
//@	
//@	/**
//@	 * This method refreshes the CustomerPanel when maintainer logs-out.
//@	 */
//@	public void refreshCustomerPanel(){
//@		changeGiver.displayChangeStatus();
//@	}
//@	
//@	/**
//@	 * This method will cancel an ongoing customer transaction.
//@	 */
//@	public void cancelTransaction() {
//@		coinReceiver.stopReceive();
//@		coinReceiver.refundCash();
//@	}
//@	
//@	/**
//@	 * If the TransactionController receivers a request to terminate the current
//@	 * transaction then following will occur:
//@	 * <br>
//@	 * 1- If there is no transaction in progress or coin input is completed then the 
//@	 * CustomerPanel will be instructed to deactivate the DrinkSelectionBox&#46;
//@	 * <br>
//@	 * 2- If coin input is not yet complete, the CoinReceiver will be instructed to stop
//@	 * coin input and refund the money entered so far&#46;
//@	 * <br>
//@	 * 3- The DrinkSelectionBox is then reset to allow further transactions&#46;
//@	 */
//@	public void terminateTransaction(){
//@		coinReceiver.stopReceive();
//@		coinReceiver.refundCash();
//@	}
//@	
//@	/**
//@	 * If the TransactionController is informed that a fault was discovered while
//@	 * dispensing a drink, giving change or storing Coins, it will use this method
//@	 * to deactivate the Drink Selection Box and instruct the CoinReceiver to refund the
//@	 * money inserted by the customer.
//@	 */
//@	public void terminateFault(){
//@		coinReceiver.refundCash();
//@	}
//@	
//@	/**
//@	 * This method is performed when the Transaction Controller is informed that coin
//@	 * entry is complete and the money received is sufficient to dispense the drink.
//@	 * The following actions are performed.
//@	 * <br>
//@	 * 1- Dispense the drink.
//@	 * <br>
//@	 * 2- Give change if necessary.
//@	 * <br>
//@	 * 3- Store the Coins that have been entered into the Cash Store.
//@	 * <br>
//@	 * 4- Reset the Drink Selection Box to allow further transactions.
//@	 */
//@	public void completeTransaction(int price){
//@		int totalMoneyInserted=coinReceiver.getTotalInserted();
//@		int change=totalMoneyInserted-price;
//@		if(change>0){
//@			changeGiver.giveChange(change);
//@		}
//@		else{
//@			getCustomerCoinPanel().setChange(0);
//@		}
//@		coinReceiver.storeCash();
//@	}
//@	
//@	public void processMoneyReceived() {
//@		coinReceiver.continueReceive();
//@	}
//@	
//@	public void setupPanel(CustomerPanel custPanel) {
//@		this.custCoinPanel = new CustomerCoinPanel(this.coinReceiver, this.trCtrl.getMainController().getCashStoreController());
//@		custPanel.setCustomerCoinPanel(this.custCoinPanel);
//@	}
//@}
//#endif
