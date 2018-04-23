package sg.edu.nus.iss.vmcs;

import java.awt.Panel;

import sg.edu.nus.iss.vmcs.store.StoreItem;

public interface Builder {
    void addLabel(String title);

    void addViewItems(StoreItem[] items, int length);
}

