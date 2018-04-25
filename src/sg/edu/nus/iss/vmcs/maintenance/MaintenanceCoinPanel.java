//#if CashPayment
//@package sg.edu.nus.iss.vmcs.maintenance;
//@
//@import java.awt.Button;
//@import java.awt.FlowLayout;
//@import java.awt.Panel;
//@
//@import sg.edu.nus.iss.vmcs.store.CashStoreController;
//@import sg.edu.nus.iss.vmcs.store.Store;
//@import sg.edu.nus.iss.vmcs.util.LabelledDisplay;
//@import sg.edu.nus.iss.vmcs.util.VMCSException;
//@
//@public class MaintenanceCoinPanel {
//@	private CoinDisplay cDisplay; // need to be access from other class.
//@	private ButtonItem totalCash;
//@	private Button transferCash;
//@	private LabelledDisplay collectCash;
//@	private CashStoreController mctrl;
//@	
//@	public MaintenanceCoinPanel(CashStoreController mctrl) {
//@		this.mctrl = mctrl;
//@	}
//@	
//@	public void initialize(Panel tp5, Panel pp) {
//@		cDisplay = new CoinDisplay(mctrl);
//@		totalCash = new ButtonItem("Show Total Cash Held", 5, ButtonItem.FLOW);
//@		TotalCashButtonListener tl;
//@
//@		tl = new TotalCashButtonListener();
//@		totalCash.addListener(tl);
//@
//@		transferCash = new Button("Press to Collect All Cash");
//@		transferCash.addActionListener(new TransferCashButtonListener(mctrl));
//@
//@		Panel tp6 = new Panel();
//@		tp6.setLayout(new FlowLayout());
//@		tp6.add(transferCash);
//@
//@		collectCash = new LabelledDisplay("Collect Cash:", 5, LabelledDisplay.FLOW);
//@
//@		tp5.add(totalCash);
//@		tp5.add(tp6);
//@		tp5.add(collectCash);
//@		pp.add(cDisplay);
//@	}
//@	
//@	/**
//@	 * This method returns the CoinDisplay.
//@	 * 
//@	 * @return the CoinDisplay.
//@	 */
//@	public CoinDisplay getCoinDisplay() {
//@		return cDisplay;
//@	}
//@		
//@	/**
//@	 * This method displays the received value as the total cash held in the
//@	 * CashStore of the vending machine.
//@	 * 
//@	 * @param tc
//@	 *            the total cash.
//@	 */
//@	public void displayTotalCash(int tc) {
//@		String stc;
//@
//@		stc = new String(tc + " C");
//@		totalCash.setValue(stc);
//@	}
//@
//@	/**
//@	 * This method displays the amount of money to be issued on the Cash
//@	 * Collection Tray Display.
//@	 * 
//@	 * @param cc
//@	 *            the collected cash.
//@	 */
//@	public void displayCoins(int cc) {
//@		collectCash.setValue(cc);
//@	}
//@
//@	/**
//@	 * This method initiate the collect cash.
//@	 */
//@	public void initCollectCash() {
//@		collectCash.setValue("");
//@	}
//@
//@	/**
//@	 * This method initiate the total cash.
//@	 */
//@	public void initTotalCash() {
//@		totalCash.setValue("");
//@	}
//@	
//@	public void setActive(boolean st) {
//@		collectCash.setActive(st);
//@		cDisplay.setActive(st);
//@		totalCash.setActive(st);
//@		transferCash.setEnabled(st);
//@	}
//@	
//@	public void updateQtyDisplay(int idx, int qty) throws VMCSException {
//@		cDisplay.update(idx, qty);
//@	}
//@	
//@	public void updateCurrentQtyDisplay(int qty) throws VMCSException {
//@		int curIdx;
//@		curIdx = cDisplay.getCurIdx();
//@		updateQtyDisplay(curIdx, qty);
//@	}
//@}
//#endif
