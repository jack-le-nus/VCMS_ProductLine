//#if CashPayment
package sg.edu.nus.iss.vmcs.commonality;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.vmcs.MediatorImpl;
import sg.edu.nus.iss.vmcs.Vmcs;
import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.store.DrinksBrand;
import sg.edu.nus.iss.vmcs.store.DrinksStore;
import sg.edu.nus.iss.vmcs.store.DrinksStoreItem;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.system.DrinkPropertyLoader;
import sg.edu.nus.iss.vmcs.system.Environment;
import sg.edu.nus.iss.vmcs.system.MainController;

public class StoreControllerTest extends TestCase{
	private String propertyFilename=Vmcs.getPropertiesFile();
	
	@Before
	public void setup() throws Exception{
		
	}

	@After
	public void tearDown() throws Exception{
	}
	
	@Test
	public void testStoreControllerConstructor() throws Exception{
		
		Environment.initialize(propertyFilename);
		DrinkPropertyLoader drinksLoader =
			new DrinkPropertyLoader(Environment.getDrinkPropFile());
		drinksLoader.initialize();
		//Act
		StoreController storeController=new DrinkStoreController(new DrinksStore(), drinksLoader, new MediatorImpl());
		storeController.initialize();
		//Assert
		assertNotNull(storeController);
		assertNotNull(storeController.getStore());
	}
	
	@Test
	public void testInitialize() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		
		StoreController storeController=mainCtrl.getDrinksStoreController();
		//Act initialize
		storeController.initialize();	
		DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		
		//Assert
		assertNotNull(drinksStore);
	}

	@Test
	public void testInitializeStores() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		
		StoreController storeController=mainCtrl.getDrinksStoreController();
		
		//Act initialize indirect Act initializeStores
		storeController.initialize();
		//Get Drinks Store and test its elements
		DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		int storeSize=drinksStore.getStoreSize();
		for(int i=0;i<storeSize;i++){
			DrinksStoreItem drinksStoreItem=(DrinksStoreItem)drinksStore.getStoreItem(i);
			DrinksBrand drinksBrand=(DrinksBrand)drinksStoreItem.getContent();
			//Assert
			assertNotNull(drinksBrand);
		}
	}

	@Test
	public void testInitializeDrinkStore() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		StoreController storeController=mainCtrl.getDrinksStoreController();
		
		//Act initialize indirect Act initializeDrinkStore
		storeController.initialize();
		
		//Get Drinks Store and test its elements
		DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		int storeSize=drinksStore.getStoreSize();
		for(int i=0;i<storeSize;i++){
			DrinksStoreItem drinksStoreItem=(DrinksStoreItem)drinksStore.getStoreItem(i);
			DrinksBrand drinksBrand=(DrinksBrand)drinksStoreItem.getContent();
			//Assert
			assertNotNull(drinksBrand);
		}
	}

	@Test
	public void testGetStoreSize() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		
		StoreController storeController=mainCtrl.getDrinksStoreController();
		
		//Initializing the Store
		storeController.initialize();
		//Get Drinks Store and test its elements
		DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		//Act getStoreSize and test looping the store with the store size
		int storeSize=storeController.getStoreSize();
		for(int i=0;i<storeSize;i++){
			DrinksStoreItem drinksStoreItem=(DrinksStoreItem)drinksStore.getStoreItem(i);
			DrinksBrand drinksBrand=(DrinksBrand)drinksStoreItem.getContent();
			//Assert
			assertNotNull(drinksBrand);
		}
	}

	@Test
	public void testGetStoreItems() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		
		StoreController storeController=mainCtrl.getDrinksStoreController();
		
		storeController.initialize();
		//Act getStoreItems
		StoreItem[] drinksStoreItems=storeController.getStoreItems();
		for(int i=0;i<drinksStoreItems.length;i++){
			DrinksStoreItem drinksStoreItem=(DrinksStoreItem)drinksStoreItems[i];
			//Assert
			assertNotNull(drinksStoreItem);
		}
	}

	@Test
	public void testChangeStoreQty() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		
		StoreController storeController=mainCtrl.getDrinksStoreController();
		storeController.initialize();
		DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		int storeSize=drinksStore.getStoreSize();
		for(int i=0;i<storeSize;i++){
			int qty1=14+i;
			//Act changeStoreQty
			storeController.changeStoreQty(i, qty1);
			DrinksStoreItem drinksStoreItem=(DrinksStoreItem)drinksStore.getStoreItem(i);
			int qty2=drinksStoreItem.getQuantity();
			//Assert
			assertEquals(qty1,qty2);
		}
	}

	@Test
	public void testGetStoreItem() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		
		StoreController storeController=mainCtrl.getDrinksStoreController();
		storeController.initialize();
		DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		int storeSize=drinksStore.getStoreSize();
		for(int i=0;i<storeSize;i++){
			//Act getStoreItem
			DrinksStoreItem drinksStoreItem=(DrinksStoreItem)storeController.getStoreItem(i);
			//Assert
			assertNotNull(drinksStoreItem);
		}
	}

	@Test
	public void testSetPrice() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		DrinkStoreController storeController=mainCtrl.getDrinksStoreController();
		storeController.initialize();
		
	    DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		int storeSize=drinksStore.getStoreSize();
		for(int i=0;i<storeSize;i++){
			int price1=60+i;
			//Act setPrice
			storeController.setPrice(i, price1);
			DrinksStoreItem drinksStoreItem=((DrinksStoreItem)storeController.getStoreItem( i));
			DrinksBrand drinksBrand=(DrinksBrand)drinksStoreItem.getContent();
			int price2=drinksBrand.getPrice();
			//Assert
			assertEquals(price1,price2);
		}
	}

	@Test
	public void testCloseDown() throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		StoreController storeController=mainCtrl.getDrinksStoreController();
		storeController.initialize();
		int cashStoreSize1=storeController.getStoreSize();
		int drinksStoreSize1=storeController.getStoreSize();
		//Act closeDown
		storeController.closeDown();
		storeController.initialize();
		int cashStoreSize2=storeController.getStoreSize();
		int drinksStoreSize2=storeController.getStoreSize();
		//Assert
		assertEquals(cashStoreSize1,cashStoreSize2);
		assertEquals(drinksStoreSize1,drinksStoreSize2);
	}

	@Test
	public void testDispenseDrink(int idx) throws Exception{
		MainController mainCtrl=new MainController(propertyFilename);
		mainCtrl.initialize();
		DrinkStoreController storeController=mainCtrl.getDrinksStoreController();
		storeController.initialize();
		DrinksStore drinksStore=(DrinksStore)storeController.getStore();
		int storeSize=drinksStore.getStoreSize();
		for(int i=0;i<storeSize;i++){
			DrinksStoreItem drinksStoreItem=(DrinksStoreItem)drinksStore.getStoreItem(i);
			int qty1=drinksStoreItem.getQuantity();
			if(qty1==0)
				continue;
			//Act dispenseDrink
			storeController.dispenseDrink(i);
			int qty2=drinksStoreItem.getQuantity();
			//Assert
			assertEquals(qty1,qty2+1);
		}
	}
}//End of class StoreControllerTest
//#endif