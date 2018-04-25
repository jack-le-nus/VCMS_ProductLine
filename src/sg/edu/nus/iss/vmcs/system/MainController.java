/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.system;

import java.io.IOException;

import sg.edu.nus.iss.vmcs.ApplicationMediator;
import sg.edu.nus.iss.vmcs.MediatorImpl;
import sg.edu.nus.iss.vmcs.customer.TransactionController;
import sg.edu.nus.iss.vmcs.machinery.MachineryController;
import sg.edu.nus.iss.vmcs.maintenance.MaintenanceController;
//#if CashPayment
//@import sg.edu.nus.iss.vmcs.store.CashStore;
//@import sg.edu.nus.iss.vmcs.store.CashStoreController;
//#endif
import sg.edu.nus.iss.vmcs.store.DrinkStoreController;
import sg.edu.nus.iss.vmcs.store.DrinksStore;
import sg.edu.nus.iss.vmcs.util.VMCSException;

/**
 * This object is the main controller of the vending machine&#46; It coordinates the main operations of the VMCS&#46;
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class MainController {
	private SimulationController  simulatorCtrl;
	private MachineryController   machineryCtrl;
	private MaintenanceController maintenanceCtrl;
	private TransactionController txCtrl;
	private DrinkStoreController drinkStoreCtrl;
	//#if CashPayment
//@	private CashStoreController  cashStoreCtrl;
	//#endif
	private ApplicationMediator mediator;

	private String      propertyFile;

	/**
	 * This constructor creates an instance of MainController object.
	 * @param propertyFile the property file name.
	 */
	public MainController(String propertyFile) {
		if(propertyFile == null) {
			propertyFile = "/Users/jackle/Documents/Materials/Design Patterns/Assignments/Github/DesignPattern.VCMS/vmcs.properties";
		}
		this.propertyFile = propertyFile;
		
	}

	/**
	 * This method will initiate the creation of all the control objects necessary for
	 * simulation of the vending machine, and initiate the display of the SimulatorControlPanel
	 * (the main menu).
	 * @throws VMCSException if fail to initialize.
	 */
	public void start() throws VMCSException {
		try {
			initialize();
			simulatorCtrl.displaySimulatorControlPanel();
			simulatorCtrl.setSimulationActive(false);
		} catch (VMCSException e) {
			throw new VMCSException(e);
		}
	}

	/**
	 * This method creates all the control objects.
	 * @throws VMCSException if fail to load drinks properties or cash properties.
	 */
	public void initialize() throws VMCSException {
		try {
			Environment.initialize(propertyFile);
			//#if CashPayment
//@			CashPropertyLoader cashLoader =
//@				new CashPropertyLoader(Environment.getCashPropFile());
//@			cashLoader.initialize();
			//#endif
			DrinkPropertyLoader drinksLoader =
					new DrinkPropertyLoader(Environment.getDrinkPropFile());
			drinksLoader.initialize();
			mediator = new MediatorImpl();
			//#if CashPayment
//@			this.cashStoreCtrl = new CashStoreController(new CashStore(), cashLoader, mediator);
//@			this.cashStoreCtrl.initialize();
			//#endif
			this.drinkStoreCtrl = new DrinkStoreController(new DrinksStore(), drinksLoader, mediator);
			this.drinkStoreCtrl.initialize();
			simulatorCtrl = new SimulationController(this, mediator);
			machineryCtrl = new MachineryController(this, mediator);
			machineryCtrl.initialize();
			maintenanceCtrl = new MaintenanceController(this, mediator);
			txCtrl=new TransactionController(this, mediator);
			
		} catch (IOException e) {
			throw new VMCSException(
				"MainController.initialize",
				e.getMessage());
		}
	}
	
	public ApplicationMediator getAuthenticationMediator() {
		return mediator;
	}

	/**
	 * This method returns the SimulationController.
	 * @return the SimulationController.
	 */
	public SimulationController getSimulationController() {
		return simulatorCtrl;
	}

	/**
	 * This method returns the SimulatorControlPanel.
	 * @return the SimulatorControlPanel.
	 */
	public SimulatorControlPanel getSimulatorControlPanel() {
		return simulatorCtrl.getSimulatorControlPanel();
	}

	/**
	 * This method returns the StoreController.
	 * @return the StoreController.
	 */
	public DrinkStoreController getDrinksStoreController() {
		return this.drinkStoreCtrl;
	}
	
	//#if CashPayment
//@	/**
//@	 * This method returns the StoreController.
//@	 * @return the StoreController.
//@	 */
//@	public CashStoreController getCashStoreController() {
//@		return this.cashStoreCtrl;
//@	}
	//#endif

	/**
	 * This method returns the MachineryController&#46; 
	 * @return the MachineryController&#46;
	 */
	public MachineryController getMachineryController() {
		return machineryCtrl;
	}

	/**
	 * This method returns the MaintenanceController&#46;
	 * @return the MaintenanceController&#46;
	 */
	public MaintenanceController getMaintenanceController() {
		return maintenanceCtrl;
	}
	
	/**
	 * This method returns the TransactionController.
	 * @return the TransactionController.
	 */
	public TransactionController getTransactionController() {
		return txCtrl;
	}

	/**
	 * This method destroys all the object instances created for opening the vending
	 * machine&#46; It will instruct the SimulationController to close down (by closing
	 * down all the vending machine panels) and the TransactionController to close
	 * down&#46; It will also close down other control objects and the entity objects
	 * created for simulating the vending machine&#46;
	 */
	public void closeDown() {
		try {
			//#if CashPayment
//@			this.cashStoreCtrl.closeDown();
			//#endif
			this.drinkStoreCtrl.closeDown();
		} catch (Exception e) {
			System.out.println("Error closing down the stores: " + e);
		}
		machineryCtrl.closeDown();
		maintenanceCtrl.closeDown();
		simulatorCtrl.closeDown();
	}
}//End of class MainController
