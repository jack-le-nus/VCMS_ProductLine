package sg.edu.nus.iss.vmcs.customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubmitButtonListener implements ActionListener{
	private TransactionController txCtrl;
	
	/**
	 * This constructor creates an instance of the Terminate Button Listener.
	 * @param txCtrl the Transaction Controller.
	 */
	public SubmitButtonListener(TransactionController txCtrl){
		this.txCtrl=txCtrl;
	}
	
	/**
	 * This method performs actions in response to the terminate button being pressed.
	 */
	public void actionPerformed(ActionEvent ev){
		txCtrl.processMoneyReceived(0);
	}
}//End of class TerminateButtonListener
