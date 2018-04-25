//#if CashPayment
//@/*
//@ * Copyright 2003 ISS.
//@ * The contents contained in this document may not be reproduced in any
//@ * form or by any means, without the written permission of ISS, other
//@ * than for the purpose for which it has been supplied.
//@ *
//@ */
//@package sg.edu.nus.iss.vmcs.maintenance;
//@
//@import java.awt.event.*;
//@
//@import sg.edu.nus.iss.vmcs.Dispatcher;
//@
//@import java.awt.*;
//@
//@/**
//@ * This control object monitors the events in the CoinDisplay and performs actions
//@ * in response to the buttons being pressed.
//@ *
//@ * @version 3.0 5/07/2003
//@ * @author Olivo Miotto, Pang Ping Li
//@ */
//@public class CoinDisplayListener implements ActionListener {
//@
//@	/**
//@	 * This constructor create an instance of the CoinDisplayListener object.
//@	 * @param mc the MaintenanceController.
//@	 */
//@	public CoinDisplayListener() {
//@	}
//@	
//@	/**
//@	 * This method performs actions in response to the coin button being pressed.
//@	 */
//@	public void actionPerformed(ActionEvent e) {
//@
//@		String cmd;
//@		int idx;
//@		Button btn;
//@
//@		btn = (Button) e.getSource();
//@		cmd = btn.getActionCommand();
//@		idx = Integer.parseInt(cmd);
//@
//@		Dispatcher.getInstance().dispatchCommand("displayCoin", idx);
//@	}
//@}//End of class CoinDisplayListener
//#endif
