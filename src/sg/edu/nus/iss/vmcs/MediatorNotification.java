package sg.edu.nus.iss.vmcs;

public class MediatorNotification {
	private Object[] objects;
	private NotificationType type;
	
	public MediatorNotification(NotificationType type, Object...objects) {
		this.type = type;
		this.objects = objects;
	}
	
	public void setType(NotificationType type) {
		this.type = type;
	}
	
	public NotificationType getType() {
		return this.type;
	}
	
	public void setObject(Object... objects) {
		this.objects = objects;
	}
	public Object[] getObject() {
		return this.objects;
	}
}