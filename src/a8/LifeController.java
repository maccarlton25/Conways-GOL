package a8;

public class LifeController implements LifeObserver, LifeViewListener {
	LifeView view;
	LifeModel model;
	AutoAdvance thread;
	public LifeController(LifeModel _model, LifeView _view) {
		this.view = _view;
		this.model = _model;
		
		view.addLifeViewListener(this);
		
		//initialize other (if any) instance fields here 
		
		model.addObserver(this);
	}

	@Override
	public void handleLifeViewEvent(LifeViewEvent e) {
		if (e.isAdvanceEvent()) {
			model.setInputBoard(view.getBoard(), view.getTorus());
			model.evaluateCells();
		} else if(e.isClearFieldEvent()) {
			model.resetModelArrays();
			view.clearDisplay();
		} else if(e.isDimensionEvent()) {
			DimensionEvent dimension = (DimensionEvent) e;
			view.resizeBoard(dimension.getDimension());
			model.setInputBoard(view.getBoard(), view.getTorus());
		} else if(e.isRandomFillEvent()) {
			view.randomFill();
			model.setInputBoard(view.getBoard(), view.getTorus());
		} else if(e.isTorusEvent()) {
			view.setTorus();
			model.setInputBoard(view.getBoard(), view.getTorus());
		} else if(e.isLBTEvent()) {
			LBTEvent lbt = (LBTEvent) e;
			model.setLBT(lbt.getThreshold());
			view.setLBT(lbt.getThreshold());
		} else if(e.isHBTEvent()) {
			HBTEvent hbt = (HBTEvent) e;
			model.setHBT(hbt.getThreshold());
			view.setHBT(hbt.getThreshold());
		} else if(e.isLSTEvent()) {
			LSTEvent lst = (LSTEvent) e;
			model.setLST(lst.getThreshold());
			view.setLST(lst.getThreshold());
		} else if(e.isHSTEvent()) {
			HSTEvent hst = (HSTEvent) e;
			model.setHST(hst.getThreshold());
			view.setHST(hst.getThreshold());
		} else if(e.isStartEvent()) {
			thread = new AutoAdvance(this, view.getDelay());
			thread.start();
		} else if(e.isStopEvent()) {
			thread.halt();
			try {
				thread.join();
			} catch (InterruptedException e1) {
			}
		} else if(e.isMSEvent()) {
			MSEvent ms = (MSEvent) e;
			view.setDelay(ms.getMS());
			if(thread.isAlive()) {
				view.fireEvent(new StopEvent());
				view.setStartText();
			}
		}
	}

	@Override
	public void update(LifeModel model) {
		view.setDisplay(model.getOutputBoard());
	}
	
	
}
