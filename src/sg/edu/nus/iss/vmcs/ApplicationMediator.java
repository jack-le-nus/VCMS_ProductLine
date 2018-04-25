package sg.edu.nus.iss.vmcs;

import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.TransactionController;
import sg.edu.nus.iss.vmcs.machinery.MachineryController;
import sg.edu.nus.iss.vmcs.machinery.MachinerySimulatorPanel;
import sg.edu.nus.iss.vmcs.maintenance.MaintenanceController;
import sg.edu.nus.iss.vmcs.maintenance.MaintenancePanel;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.system.SimulationController;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;

public interface ApplicationMediator {
    public void controllerChanged(BaseController controller, MediatorNotification notification);
    public void register(String name, BaseController control);
}
