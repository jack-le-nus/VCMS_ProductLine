package sg.edu.nus.iss.vmcs;

import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionListener;

import sg.edu.nus.iss.vmcs.maintenance.ButtonItem;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.util.VMCSException;

public class ControlElement extends Panel {
	private ControlElement items[];
	private StoreItem storeItem[];
	private int len;
	private Label lb;

	public boolean isComposite() {
		return true;
	}
	
	public void setLabel(Label lb) {
		this.lb = lb;
	}

	public void setItemSize(int len) {
		this.len = len;
	}

	/**
	 * This method attaches a listener to the ButtonItemDisplay.
	 * 
	 * @param l
	 *            the ActionListener.
	 */
	public void addListener(ActionListener l) {
		int i;
		for (i = 0; i < len; i++) {
			items[i].addListener(l);
			items[i].setActionCommand(String.valueOf(i));
		}
	}

	/**
	 * This method activates the ButtonItemDisplay if the parameter is TRUE.
	 * Otherwise, the ButtonItemDisplay is deactivated.
	 * 
	 * @param st
	 *            the status of the ButtonItemDisplay.
	 */
	public void setActive(boolean st) {
		int i;
		this.setEnabled(st);
		if (lb != null) {
			lb.setEnabled(st);
			for (i = 0; i < len; i++) {
				items[i].setActive(st);
			}
		}
	}

	/**
	 * This method clear the button items.
	 */
	public void clear() {
		int i;
		for (i = 0; i < len; i++) {
			items[i].clear();
		}
	}

	public void update() {
		for (int i = 0; i < storeItem.length; i++) {
			int val = storeItem[i].getQuantity();
			String sval = String.valueOf(val);
			items[i].setValue(sval);
		}
	}

	/**
	 * This method displays a quantity on to a specific ButtonItem&#46;
	 * 
	 * @param idx
	 *            the index of the specific button item&#46;
	 * @param qty
	 *            the quantity of the specific item&#46;
	 * @throws VMCSException
	 *             if idx is greater or equal than total number of button
	 *             item&#46;
	 */
	public void update(int idx, int qty) throws VMCSException {
		if (idx >= len)
			throw new VMCSException("ButtomDisplay.setQty", "Index over flow");

		items[idx].setValue(qty + "");
	}

	public void setActionCommand(String valueOf) {
		// TODO Auto-generated method stub

	}

	public void setValue(String value) {
		// TODO Auto-generated method stub

	}

	public void setItems(ControlElement[] items) {
		this.items = (ControlElement[])items;
	}

	public void setStoreItems(StoreItem[] sitems) {
		this.storeItem = sitems;
	}
}
