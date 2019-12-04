package a8;

import java.util.ArrayList;
import java.util.List;

public class LifeModel {
	private boolean[][] _input_board;
	private boolean[][] _output_board;
	private boolean[][] _point_test;
	private List<LifeObserver> observers;
	private boolean _torus;
	private int _lbt;
	private int _hbt;
	private int _lst;
	private int _hst;
	
	public LifeModel() {
		observers = new ArrayList<LifeObserver>();
		_torus = false;
		_lbt = 2;
		_hbt = 3;
		_hst = 3;
		_lst = 3;
	}
	
	public void addObserver(LifeObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(LifeObserver observer) {
		observers.remove(observer);
	}
	
	public void evaluateCells() {
		if(!_torus) {
			_output_board = new boolean[_input_board.length-2][_input_board.length-2];
			
			for(int i = 1; i < _input_board.length - 1; i++) {
				for(int j = 1; j < _input_board.length - 1; j++) {
					boolean alive;
					
					_point_test = new boolean[3][3];
					
					_point_test[0][0] = _input_board[i-1][j-1];
					_point_test[0][1] = _input_board[i-1][j];
					_point_test[0][2] = _input_board[i-1][j+1];
					_point_test[1][0] = _input_board[i][j-1];
					//_point_test[1][1] = _input_board[i][j];
					_point_test[1][2] = _input_board[i][j+1];
					_point_test[2][0] = _input_board[i+1][j-1];
					_point_test[2][1] = _input_board[i+1][j];
					_point_test[2][2] = _input_board[i+1][j+1];
					
					alive = _input_board[i][j];
						
					
					int check = 0;
					for(boolean row[] : _point_test) {
						for(boolean point : row) {
							if(point) {
								check++;
							}
						}
					}
					
					_point_test = null;
					
					
					if(alive && (check<=_hst && check>=_lst)) {
						_output_board[i-1][j-1] = true;
					} else if (alive) {
						_output_board[i-1][j-1] = false;
					} else if (!alive && (check >= _lbt && check <= _hbt)) {
						_output_board[i-1][j-1] = true;
					} else {
						_output_board[i-1][j-1] = false;
					}
				}
			}
			notifyObservers();
		} else {
			//torus mode 
		}
	}
	
	private void notifyObservers() {
		for (LifeObserver observer : observers) {
			observer.update(this);
		}
	}
	public void setInputBoard(boolean[][] board, boolean torus) {
		System.out.println(torus);
		_input_board = new boolean[board.length+2][board.length+2];
		for(int i = 1; i<_input_board.length-1; i++) {
			for(int j = 1; j<_input_board.length-1; j++) {
				_input_board[i][j]=board[i-1][j-1];
			}
		}
		if(!torus) return;
			_input_board[0][0]=board[board.length-1][board.length-1];
			_input_board[0][board.length+1]=board[board.length-1][0];
			_input_board[board.length+1][0]=board[0][board.length-1];
			_input_board[board.length+1][board.length+1]=board[0][0];
			for(int i=1; i<board.length; i++) {
				_input_board[i][0] = board[i-1][board.length-1];
				_input_board[i][board.length+1] = board[i-1][0];
				_input_board[0][i] = board[board.length-1][i-1];
				_input_board[board.length+1][i] = board[0][i-1];
			}
	}
	public boolean[][] getOutputBoard() {
		return _output_board;
	}
	
	public void resetModelArrays() {
		_input_board = null;
		_output_board = null;
	}
	
	public void toggleTorus() {
		_torus = !_torus;
		System.out.println(_torus);
	}
	
	public void setLBT(int threshold) {
		_lbt = threshold;
		System.out.println("lbt"+threshold);
	}
	
	public void setHBT(int threshold) {
		_hbt = threshold;
		System.out.println("hbt"+threshold);
	}
	
	public void setLST(int threshold) {
		_lst = threshold;
		System.out.println("lst"+threshold);
	}
	
	public void setHST(int threshold) {
		_hst = threshold;
		System.out.println("hst"+threshold);
	}
}
