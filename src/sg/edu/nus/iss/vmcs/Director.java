package sg.edu.nus.iss.vmcs;

import sg.edu.nus.iss.vmcs.store.StoreItem;

public class Director {
	private Builder m_builder;

    public Director(Builder b) {
        m_builder = b;
    }

    public void construct(String title, StoreItem[] items, int length) {
    	m_builder.addLabel(title);
    	m_builder.addViewItems(items, length);
    }
}
