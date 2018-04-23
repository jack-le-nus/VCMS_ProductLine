package sg.edu.nus.iss.vmcs;

import java.util.HashMap;

import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.TransactionController;
import sg.edu.nus.iss.vmcs.machinery.MachineryController;
import sg.edu.nus.iss.vmcs.machinery.MachinerySimulatorPanel;
import sg.edu.nus.iss.vmcs.maintenance.AccessManager;
import sg.edu.nus.iss.vmcs.maintenance.MaintenanceController;
import sg.edu.nus.iss.vmcs.maintenance.MaintenancePanel;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.system.SimulationController;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;
import sg.edu.nus.iss.vmcs.util.MessageDialog;
import sg.edu.nus.iss.vmcs.util.VMCSException;

public class MediatorImpl implements ApplicationMediator {
	private HashMap<String, BaseController> controllers =
			new HashMap<String, BaseController>();
	
	public void register(String name, BaseController control) {
		controllers.put(name, control);
	}
	
	public void controllerChanged(BaseController controller, MediatorNotification notification) {
		if(notification.getType() == NotificationType.LoginMaintainer) {
			this.controllers.get("MachineryController").handleMessage(notification);
			this.controllers.get("TransactionController").handleMessage(notification);
			
		} else if (notification.getType() == NotificationType.LogoutMaintainer) {
			Boolean ds = (Boolean)this.controllers.get("MachineryController").handleMessage(notification);
			this.controllers.get("MaintenanceController").handleMessage(new MediatorNotification(NotificationType.LogoutMaintainer, ds));
			if (ds == false) {
				return;
			}
			
			Boolean isCustomerPanelNull = (Boolean)this.controllers.get("TransactionController").handleMessage(notification);
			if(isCustomerPanelNull) {
				this.controllers.get("SimulationController").handleMessage(notification);
			}
		} else if (notification.getType() == NotificationType.TransferAll) {
			Integer cc = (Integer)this.controllers.get("CashStoreController").handleMessage(notification);
			this.controllers.get("MachineryController").handleMessage(notification);
			this.controllers.get("MaintenanceController").handleMessage(new MediatorNotification(NotificationType.TransferAll, cc));
			
		} 
		else if (notification.getType() == NotificationType.RefreshMachineryPanel) {
			this.controllers.get("MachineryController").handleMessage(notification);
		} else if (notification.getType() == NotificationType.SetActiveSimulatorPanel) {
			this.controllers.get("SimulationController").handleMessage(notification);
		} else if (notification.getType() == NotificationType.SetupCustomer) {
			this.controllers.get("TransactionController").handleMessage(notification);
		} else if (notification.getType() == NotificationType.SetupMachinery) {
			this.controllers.get("MachineryController").handleMessage(notification);
		} else if (notification.getType() == NotificationType.SetupMaintainer) {
			this.controllers.get("MaintenanceController").handleMessage(notification);
		}
	}
	
}
