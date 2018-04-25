//#if CashPayment
package sg.edu.nus.iss.vmcs.customer;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;

import sg.edu.nus.iss.vmcs.store.CashStoreController;
import sg.edu.nus.iss.vmcs.util.LabelledValue;
import sg.edu.nus.iss.vmcs.util.WarningDisplay;

public class CustomerCoinPanel {
	private Label lblEnterCoins=new Label("Enter Coins Here");
    private CoinInputBox coinInputBox;  
    private WarningDisplay wndInvalidCoin=new WarningDisplay("Invalid Coin");
    private LabelledValue lbdTotalMoneyInserted=new LabelledValue("Total Money Inserted:","0 C",50);
    private WarningDisplay wndNoChangeAvailable=new WarningDisplay("No Change Available");
    private LabelledValue lbdCollectCoins=new LabelledValue("Collect Coins:","0 C",50);
    private CoinReceiver coinReceiver;
    private CashStoreController cashStoreController;
	
	public CustomerCoinPanel(CoinReceiver coinReceiver, CashStoreController cashStoreController) {
		this.coinReceiver = coinReceiver;
		this.cashStoreController = cashStoreController;
	}
    
    public void initializeCoinPayment(Panel pan0) {
		coinInputBox=new CoinInputBox(coinReceiver, cashStoreController);
		coinInputBox.setActive(false);
		
		pan0.add(lblEnterCoins,new GridBagConstraints(0,0,1,1,1.0,0.0,
			    GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,
			    new Insets(5,0,0,0),10,0));  
		pan0.add(coinInputBox,new GridBagConstraints(0,1,0,1,1.0,0.0,
			    GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
			    new Insets(2,0,0,0),10,0));  
		pan0.add(wndInvalidCoin,new GridBagConstraints(0,2,1,1,1.0,0.0,
			    GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,
			    new Insets(5,0,0,0),10,0));
		pan0.add(lbdTotalMoneyInserted,new GridBagConstraints(0,3,0,1,0.0,0.0,
			    GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
			    new Insets(5,0,0,0),10,0));
		pan0.add(wndNoChangeAvailable,new GridBagConstraints(0,5,0,1,0.0,0.0,
			    GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
			    new Insets(5,0,0,0),10,0));
		pan0.add(lbdCollectCoins,new GridBagConstraints(0,7,0,1,0.0,0.0,
			    GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
			    new Insets(5,0,0,0),10,0));
	}
    
    /**
	 * This method sets the total money inserted and update the display.
	 * @param i the total money inserted.
	 */
	public void setTotalMoneyInserted(int i) {
		lbdTotalMoneyInserted.setValue(i+ " C");
	}

	/**
	 * This method accumulative adds the money inserted&#46; 
	 * @param i the money to be added to the accumulative total money inserted.
	 * @return the accumulative total money inserted.
	 */
	public int addMoney(int i){
		int intTotal=getTotalMoneyInserted();
		intTotal+=i;
		setTotalMoneyInserted(intTotal);
		return intTotal;
	}
	
	/**
	 * This method returns the accumulative total money inserted.
	 * @return the accumulative total money inserted.
	 */
	public int getTotalMoneyInserted(){
		String strTotal=lbdTotalMoneyInserted.getValue();
		strTotal=strTotal.replace('C', ' ').trim();
		int intTotal=0;
		try{
			intTotal=Integer.parseInt(strTotal);
		}
		catch(NumberFormatException ex){
			intTotal=0;
		}
		return intTotal;
	}
	
	/**
	 * This method sets the change to be display.
	 * @param i the change.
	 */
	public void setChange(int i){
		lbdCollectCoins.setValue(i+" C");
	}
	
	/**
	 * This method sets the change to be display.
	 * @param s the change.
	 */
	public void setChange(String s){
		if(s!=null&&!s.trim().equals(""))
			s=s+" C";
		lbdCollectCoins.setValue(s);
	}
	
	/**
	 * This method return the change displayed on the CustomerPanel.
	 * @return the change.
	 */
	public String getChange(){
		return lbdCollectCoins.getValue().replace('C', ' ').trim();
	}
	
	/**
	 * This method resets the total money inserted display on the CustomerPanel.
	 */
	public void resetTotalInserted(){
		setTotalMoneyInserted(0);
	}
	
	/**
	 * This method resets the change display on the CustomerPanel.
	 */
	public void resetChange(){
		setChange("");
	}
	
	/**
	 * This method turning On or Off the "Invalid Coin" highlight.
	 * @param isOn TRUE to turn on the highlight, otherwise, turn off the highlight.
	 */
	public void displayInvalidCoin(boolean isOn){
		wndInvalidCoin.setState(isOn);
	}
	
	/**
	 * This method turning On or Off the "No Change Available" highlight.
	 * @param isOn TRUE to turn on the highlight, otherwise, turn off the highlight.
	 */
	public void displayChangeStatus(boolean isOn){
		wndNoChangeAvailable.setState(isOn);
	}
	
	/**
	 * This method returns the CoinInputBox in the CustomerPanel.
	 * @return the CoinInputBox display in the CustomerPanel.
	 */
	public CoinInputBox getCoinInputBox(){
		return coinInputBox;
	}
	
	/**
	 * This method activates or deactivates the CoinInputBox.
	 * @param active the active status of the CoinInputBox; TRUE to activate,
	 * FALSE to deactivate it.
	 */
	public void setCoinInputBoxActive(boolean active){
		coinInputBox.setActive(active);
	}
}
//#endif
