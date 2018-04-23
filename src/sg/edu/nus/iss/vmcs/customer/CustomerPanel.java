/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.customer;

/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;
import sg.edu.nus.iss.vmcs.util.LabelledValue;
import sg.edu.nus.iss.vmcs.util.WarningDisplay;

/**
 * This class Customer Panel display the GUI interactive with the Customer&#46;
 * 
 * This panel simulates the vending machine's customer interface panel&#46; It will
 * enable the user (Customer) to:
 * <ol>
 * <li>
 * insert coins;
 * </li>
 * <li>
 * select Brands;
 * </li>
 * <li>
 * terminate Transaction&#46;  
 * </li> 
 * </ol>
 * It will also provide the following display functions;
 * <ol>
 * <li>
 * display total money inserted.
 * </li>
 * <li>
 * indicate coin not valid.
 * </li>
 * <li>
 * indicate no change available.
 * </li>
 * <li>
 * display the value of the change to be collected.
 * </li>
 * <li>
 * display an icon representing the dispensed drink.
 * </li>
 * </ol>
 *
 * @author Team SE16T5E
 * @version 1.0 2008-10-01
 */
public class CustomerPanel extends Dialog {
	private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
	private int frameX=0;
	private int frameY=0;
	private int frameWidth=300;
	private int frameHeight=400;
	private int screenWidth=screen.width;
	private int screenHeight=screen.height;
	
	private static final String TITLE = "Customer Panel";
	private TransactionController txCtrl;
	private CustomerCoinPanel customerCoinPanel;
	private CustomerDrinkPanel customerDrinkPanel;
	private Panel pan0=new Panel();
    private Label lblTitle=new Label("VMCS Soft Drinks Dispenser");
    private Button btnTerminate=new Button("Terminate and Return Cash");
    /**
     * This constructor creates an instance of the Customer Panel&#46; It further
     * creates Invalid Coin Display, No Change Available Display, Refund/ Change
     * Tray Display, Total Money Inserted Display, Coin Input Box, Drink Selection
     * Box, Can Collection Box and Terminate Button.
     * 
     * @param fr the parent frame
     * @param ctrl the Transaction Controller
     */
	public CustomerPanel(Frame fr, TransactionController ctrl, CustomerCoinPanel coinPanel, CustomerDrinkPanel drinkPanel) {
		super(fr, TITLE, false);
		
		this.customerCoinPanel = coinPanel;
		this.customerDrinkPanel = drinkPanel;
		initialize(ctrl);
	}

	private void initialize(TransactionController ctrl) {
		txCtrl = ctrl;
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				txCtrl.getMainController().getSimulatorControlPanel().setButtonState(SimulatorControlPanel.ACT_CUSTOMER,true);
				dispose();
				txCtrl.nullifyCustomerPanel();
			}
		});
		
		
		customerDrinkPanel.initializeDrink(pan0, txCtrl);
//		customerCoinPanel.initializeCoinPayment(pan0, this.txCtrl.getCoinReceiver(), this.txCtrl.getMainController().getCashStoreController());

		pan0.add(btnTerminate,new GridBagConstraints(0,6,0,1,0.0,0.0,
			    GridBagConstraints.CENTER,GridBagConstraints.NONE,
			    new Insets(5,0,0,0),10,0));
		
		TerminateButtonListener terminateButtonListener=new TerminateButtonListener(txCtrl);
		btnTerminate.addActionListener(terminateButtonListener);
		
		lblTitle.setAlignment(Label.CENTER);
		lblTitle.setFont(new Font("Helvetica", Font.BOLD, 24));
		setLayout(new BorderLayout());
	    add(lblTitle,BorderLayout.NORTH);
	    add(pan0,BorderLayout.CENTER);
	     
		pack();
		frameWidth=this.getWidth();
        frameHeight=this.getHeight();
        frameX=(screenWidth-frameWidth)/2;
        frameY=(screenHeight-frameHeight)/2;
        setBounds(frameX,frameY,frameWidth, frameHeight);
	}

	/**
	 * Display the Customer Panel&#46; This will be achieved by displaying the frame
	 * of the panel and then sending messages to its constituent objects 
	 * instructing them to display themselves.
	 */
	public void display() {
		this.setVisible(true);
	}
	
	/**
	 * Remove the Customer Panel from the display.
	 */
	public void closeDown() {
		dispose();
	}

	/**
	 * This method activates or deactivates the Terminate Button
	 * @param active the active status of the Terminate Button; TRUE to activate,
	 * FALSE to deactivate it.
	 */
	public void setTerminateButtonActive(boolean active){
		btnTerminate.setEnabled(active);
	}
	
	
	/**
	 * This method activates or deactivates the Customer Panel and its component
	 * objects.
	 * 
	 * @param comp the component to be activated or deactivated.
	 * @param st the active status; TRUE to activate, FALSE to deactivate.
	 */
	public void setActive(int comp, boolean st) {
		Component c=this.getComponent(comp);
		c.setEnabled(st);
	}
}//End of class CustomerPanel