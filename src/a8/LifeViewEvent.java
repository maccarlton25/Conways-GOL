package a8;

abstract public class LifeViewEvent {
	public boolean isAdvanceEvent() { return false; }
	
	public boolean isDimensionEvent() { return false; }
	
	public boolean isRandomFillEvent() { return false; }
	
	public boolean isClearFieldEvent() { return false; }
	
	public boolean isLBTEvent() { return false; }

	public boolean isHBTEvent() { return false; }

	public boolean isLSTEvent() { return false; }

	public boolean isHSTEvent() { return false; }

	public boolean isTorusEvent() { return false; }
	
	public boolean isMSEvent() { return false; }
	
	public boolean isStartEvent() { return false; }
	
	public boolean isStopEvent() { return false; }

}

class AdvanceEvent extends LifeViewEvent {
	public boolean isAdvanceEvent() { return true; }
}

class DimensionEvent extends LifeViewEvent {
	private int _dimension;
	
	public DimensionEvent(String dimension){
		this._dimension = Integer.parseInt(dimension);
		if(_dimension < 10) {
			_dimension = 10;
		}
		if(_dimension > 500) {
			_dimension = 500;
		}
	}
	
	public int getDimension() {
		return _dimension;
	}
	
	public void setDimension(int dim) {
		_dimension = dim;
	}
	
	public boolean isDimensionEvent() { return true; }
}

class RandomFillEvent extends LifeViewEvent {
	
	public boolean isRandomFillEvent() { return true; }
}

class ClearFieldEvent extends LifeViewEvent {
	
	public boolean isClearFieldEvent() { return true; }
}

class LBTEvent extends LifeViewEvent {
	private int _threshold;
	
	public LBTEvent(String threshold) {
		this._threshold = Integer.parseInt(threshold);
		if(_threshold < 0) {
			_threshold = 0;
		}
	}
	
	public int getThreshold() {
		return _threshold;
	}
	
	public void setThreshold(int threshold) {
		 _threshold = threshold;
	}
	public boolean isLBTEvent() { return true; }
}

class HBTEvent extends LifeViewEvent {
	private int _threshold;
	
	public HBTEvent(String threshold) {
		this._threshold = Integer.parseInt(threshold);
		if(_threshold < 0) {
			_threshold = 0;
		}
	}
	
	public int getThreshold() {
		return _threshold;
	}
	
	public void setThreshold(int threshold) {
		 _threshold = threshold;
	}
	public boolean isHBTEvent() { return true; }
}

class LSTEvent extends LifeViewEvent {
	private int _threshold;
	
	public LSTEvent(String threshold) {
		this._threshold = Integer.parseInt(threshold);
		if(_threshold < 0) {
			_threshold = 0;
		}
	}
	
	public int getThreshold() {
		return _threshold;
	}
	
	public void setThreshold(int threshold) {
		 _threshold = threshold;
	}
	public boolean isLSTEvent() { return true; }
}

class HSTEvent extends LifeViewEvent {
	private int _threshold;
	
	public HSTEvent(String threshold) {
		this._threshold = Integer.parseInt(threshold);
		if(_threshold < 0) {
			_threshold = 0;
		}
	}
	
	public int getThreshold() {
		return _threshold;
	}
	
	public void setThreshold(int threshold) {
		 _threshold = threshold;
	}
	public boolean isHSTEvent() { return true; }
}

class TorusEvent extends LifeViewEvent {
	public boolean isTorusEvent() { return true; }
}

class StartEvent extends LifeViewEvent {
	public boolean isStartEvent() { return true; }
}

class StopEvent extends LifeViewEvent {
	public boolean isStopEvent() { return true; }
}

class MSEvent extends LifeViewEvent {
	long _ms;
	public MSEvent(String ms) {
		this._ms = Long.parseLong(ms);
		if(_ms < 10) {
			_ms = 10;
		} 
		if(_ms > 1000) {
			_ms = 1000;
		}
	}
	public long getMS() {
		return _ms;
	}
	public boolean isMSEvent() { return true; }
}