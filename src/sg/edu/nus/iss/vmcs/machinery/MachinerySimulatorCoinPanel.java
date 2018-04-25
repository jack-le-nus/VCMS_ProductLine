//#if CashPayment
//@package sg.edu.nus.iss.vmcs.machinery;
//@
//@import java.awt.Panel;
//@
//@import sg.edu.nus.iss.vmcs.ControlElement;
//@import sg.edu.nus.iss.vmcs.Director;
//@import sg.edu.nus.iss.vmcs.StoreViewerBuilder;
//@import sg.edu.nus.iss.vmcs.store.CashStoreController;
//@import sg.edu.nus.iss.vmcs.util.VMCSException;
//@
//@public class MachinerySimulatorCoinPanel {
//@	/** This constant attribute holds the cash view title */
//@	public static final String CASH_VIEW_TITLE = "Quantity of Coins Available";
//@	private ControlElement cashDisplay;
//@	private CashStoreController cashStoreCtrl;
//@	
//@	public MachinerySimulatorCoinPanel(CashStoreController cashStoreCtrl) {
//@		this.cashStoreCtrl = cashStoreCtrl;
//@	}
//@	
//@	public void initializeCashDisplay(Panel tp) {
//@		StoreViewerBuilder builder = new StoreViewerBuilder(cashStoreCtrl);
//@		Director director = new Director(builder);
//@		director.construct(CASH_VIEW_TITLE, cashStoreCtrl.getStoreItems(), cashStoreCtrl.getStoreSize());
//@		cashDisplay = builder.getResult();
//@		
//@		tp.add(cashDisplay);
//@	}
//@
//@	/**
//@	 * This method returns the CashDisplay:StoreViewer.
//@	 * 
//@	 * @return the CashDisplay:StoreViewer.
//@	 */
//@	public ControlElement getCashStoreDisplay() {
//@		return cashDisplay;
//@	}
//@	
//@	public void setActive(boolean state) {
//@		cashDisplay.setActive(state);
//@	}
//@	
//@	public void refresh() {
//@		cashDisplay.update();
//@	}
//@}
//#endif
