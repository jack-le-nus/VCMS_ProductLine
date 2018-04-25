package sg.edu.nus.iss.vmcs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import sg.edu.nus.iss.vmcs.machinery.StoreViewerListener;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.util.LabelledDisplay;

public class StoreViewerBuilder implements Builder {

	private ControlElement storeViewerDisplay = new ControlElement();
	private StoreController storeCtrl;
	
	public StoreViewerBuilder(StoreController storeCtrl) {
		this.storeCtrl = storeCtrl;
	}
	
	public void addLabel(String title) {
		Panel pl = new Panel(new FlowLayout(FlowLayout.LEFT));
		pl.add(new Label(title));
		storeViewerDisplay.setLayout(new GridLayout(0, 1));
		storeViewerDisplay.add(pl);
	}

	public void addViewItems(StoreItem[] storeItem, int length) {
		LabelledDisplay[]viewItems = new LabelledDisplay[length];

		for (int i = 0; i < length; i++) {
			String name = storeItem[i].getContent().getName();
			viewItems[i] = new LabelledDisplay(name,
						LabelledDisplay.DEFAULT,
						LabelledDisplay.GRID);
			viewItems[i].addListener(
                        new StoreViewerListener(i, storeCtrl));
			storeViewerDisplay.add(viewItems[i]);
		}
		
		storeViewerDisplay.setItems(viewItems);
		storeViewerDisplay.setStoreItems(storeItem);
		storeViewerDisplay.setItemSize(length);
		storeViewerDisplay.update();
	}

	public ControlElement getResult() {
		return storeViewerDisplay;
	}

}
