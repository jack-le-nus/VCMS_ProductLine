package sg.edu.nus.iss.vmcs.maintenance;

import sg.edu.nus.iss.vmcs.Command;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;

public class DisplayCoinCommand implements Command {
	
	MaintenanceController mctrl;
	
	public DisplayCoinCommand(MaintenanceController mctrl)
	{
		this.mctrl = mctrl;
	}

	public void execute(Object... objects) {
		if(objects[0] instanceof Integer) {
			mctrl.displayCoin((Integer) objects[0]);
		}
	}

}