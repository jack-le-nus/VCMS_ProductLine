package sg.edu.nus.iss.vmcs;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import sg.edu.nus.iss.vmcs.maintenance.ButtonItem;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.store.StoreObject;

public class ButtonItemDisplayBuilder implements Builder {
	private ControlElement buttonItemDisplay = new ControlElement();
	
	public void addLabel(String title) {
		Panel tp1 = new Panel();

		Label lb = new Label(title);
		tp1.add(lb);

		buttonItemDisplay.setLayout(new GridLayout(0, 1));

		buttonItemDisplay.setLabel(lb);
		buttonItemDisplay.add(tp1);
	}

	public void addViewItems(StoreItem[] sitems, int len) {
		int i;
		ButtonItem items[] = new ButtonItem[len];

		for (i = 0; i < len; i++) {
			StoreObject ob = sitems[i].getContent();

			items[i] =
				new ButtonItem(
					ob.getName(),
					ButtonItem.DEFAULT_LEN,
					ButtonItem.GRID);
			buttonItemDisplay.add(items[i]);
		}
		
		buttonItemDisplay.setItems(items);
		buttonItemDisplay.setStoreItems(sitems);
		buttonItemDisplay.setItemSize(len);
	}

	public ControlElement getResult() {
		return buttonItemDisplay;
	}

}
