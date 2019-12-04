package a8;

public class AutoAdvance extends Thread {
	private boolean _flag;
	private LifeController _controller;
	private long _millis;
	
	public AutoAdvance(LifeController controller, long millis) {
		this._controller = controller;
		this._flag = false;
		this._millis = millis;
	}
	
	public void halt() {
		_flag = true;
	}
	
	public void run() {
		while(!_flag) {
			try {
				Thread.sleep(_millis);
			} catch (InterruptedException e) {
			}
			_controller.handleLifeViewEvent(new AdvanceEvent());
		}
		_controller.handleLifeViewEvent(new AdvanceEvent());
	}
}
