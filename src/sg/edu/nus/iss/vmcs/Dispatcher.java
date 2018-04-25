package sg.edu.nus.iss.vmcs;

import java.util.HashMap;

public class Dispatcher {
	
	private HashMap<String, Command> commandObjects = new HashMap<String, Command>();
	private static Dispatcher instance;
	
	public static Dispatcher getInstance() {
		if(instance == null) {
			instance = new Dispatcher();
		}
		
		return instance;
	}
	
	private Dispatcher() {
		
	}
	
	public void addCommand(String commandString, Command command)
	{
		commandObjects.put(commandString, command);
	}
	
	public void dispatchCommand(String commandString, Object... object)
	{
		System.out.println(commandString);
		commandObjects.get(commandString).execute(object);
	}


}
