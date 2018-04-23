package sg.edu.nus.iss.vmcs;

public abstract class BaseController {
	protected ApplicationMediator mediator;
	public abstract Object handleMessage(MediatorNotification notification);
	
	public BaseController(ApplicationMediator mediator) {
		this.mediator = mediator;
		this.mediator.register(this.getClass().getSimpleName(), this);
	}
}
