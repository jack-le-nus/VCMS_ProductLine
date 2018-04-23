package sg.edu.nus.iss.vmcs.maintenance;

import sg.edu.nus.iss.vmcs.Command;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;

public class GetTotalCashCommand implements Command {
	
	MaintenanceController mctrl;
	
	public GetTotalCashCommand(MaintenanceController mctrl)
	{
		this.mctrl = mctrl;
	}

	public void execute(Object... objects) {
		mctrl.getTotalCash();
	}

}
