package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LifeView extends JPanel implements SpotListener, ActionListener {
	private JSpotBoard _board;		/* SpotBoard playing area. */
	private JPanel utility_panel;
	private JPanel threshold_panel;
	private JPanel thread_panel;
	private JPanel west_panel;
	private List<LifeViewListener> listeners;
	private int _dimension;
	private JTextField dimension_field;
	private int _birth_threshold_low;
	private int _birth_threshold_high;
	private int _survive_threshold_low;
	private int _survive_threshold_high;
	private JTextField _lbt;
	private JTextField _hbt;
	private JTextField _lst;
	private JTextField _hst;
	private JTextField _delay;
	private JButton start_stop;
	private boolean torus;
	private long delay;
	
	public LifeView() {
		this._dimension = 30;
		this._birth_threshold_low = 2;
		this._birth_threshold_low = 3;
		this._survive_threshold_low = 3;
		this._survive_threshold_low = 3;
		this.torus = false;
		this.delay = 1000;
		/* Create SpotBoard and message label. */
		
		_board = new JSpotBoard(_dimension, _dimension);
		
		/* Set layout and place SpotBoard at center. */
		
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);
		
		/* Create subpanel for message area and reset button. */
		
		utility_panel = new JPanel();
		utility_panel.setLayout(new BorderLayout());

		/* Reset button. Add ourselves as the action listener. */
		
		JButton advance = new JButton("advance");
	    advance.addActionListener(this);
	    utility_panel.add(advance, BorderLayout.EAST);
	    
	    JButton clear = new JButton("clear field");
	    clear.addActionListener(this);
	    utility_panel.add(clear, BorderLayout.WEST);

	    JButton random = new JButton("random fill");
	    random.addActionListener(this);
	    utility_panel.add(random, BorderLayout.CENTER);
	    
	    JTextField dimensions = new JTextField("set grid dimensions (50-500)");
	    dimensions.addActionListener(this);
	    utility_panel.add(dimensions, BorderLayout.SOUTH);
	    dimension_field = dimensions;
	    
	    
	    threshold_panel = new JPanel();
	    threshold_panel.setLayout(new BorderLayout());
	    
	    JTextField lbt = new JTextField("low birth threshold");
	    lbt.addActionListener(this);
	    threshold_panel.add(lbt, BorderLayout.NORTH);
	    _lbt = lbt;
	    
	    JTextField hbt = new JTextField("high birth threshold");
	    hbt.addActionListener(this);
	    threshold_panel.add(hbt, BorderLayout.SOUTH);
	    _hbt = hbt;
	    
	    JTextField lst = new JTextField("low survival threshold");
	    lst.addActionListener(this);
	    threshold_panel.add(lst, BorderLayout.EAST);
	    _lst = lst;
	    
	    JTextField hst = new JTextField("high survival threshold");
	    hst.addActionListener(this);
	    threshold_panel.add(hst, BorderLayout.WEST);
	    _hst = hst;
	    
	    JButton torus = new JButton("toggle torus mode");
	    torus.addActionListener(this);
	    utility_panel.add(torus, BorderLayout.NORTH);
	    
	    west_panel = new JPanel();
	    west_panel.setLayout(new BorderLayout());
	    west_panel.add(threshold_panel, BorderLayout.WEST);
	    
	    thread_panel = new JPanel();
	    thread_panel.setLayout(new BorderLayout());
	    
	    start_stop = new JButton("start");
	    start_stop.setActionCommand("start");
	    start_stop.addActionListener(this);
	    thread_panel.add(start_stop, BorderLayout.EAST);
	    
	    
	    JTextField delay = new JTextField("delay in ms");
	    _delay = delay;
	    delay.addActionListener(this);
	    thread_panel.add(delay, BorderLayout.WEST);
	    
		/* Add subpanel in south area of layout. */
		utility_panel.add(torus, BorderLayout.NORTH);
		west_panel.add(utility_panel, BorderLayout.CENTER);
		west_panel.add(thread_panel, BorderLayout.EAST);
		//add(utility_panel, BorderLayout.SOUTH);
		add(west_panel, BorderLayout.SOUTH);
		/* Add ourselves as a spot listener for all of the
		 * spots on the spot board.
		 */
		_board.addSpotListener(this);
		
		listeners = new ArrayList<LifeViewListener>();
		
		this.setFocusable(true);
		this.grabFocus();
		
	}
	public void resizeBoard(int dimension) {
		_dimension = dimension;
		remove(_board);
		_board = new JSpotBoard(_dimension, _dimension);
		//System.out.println("one");
		add(_board, BorderLayout.CENTER);
		//System.out.println("two");
		validate();
		System.out.println("three");
		repaint();
		//System.out.println("four");
		_board.addSpotListener(this);
	}
	public void randomFill() {
		clearDisplay();
		for(Spot spot : _board) {
			double random = (Math.random());
			if(random > 0.8) {
				spot.toggleSpot();
			}
		}
	}
	@Override
	public void spotClicked(Spot spot) {
		spot.toggleSpot();
	}
	@Override
	public void spotEntered(Spot spot) {
	
	}
	@Override
	public void spotExited(Spot spot) {
		
	}
	
	public boolean[][] getBoard() {
		int width = _board.getSpotWidth();
		int height = _board.getSpotHeight();
		boolean[][] board_array = new boolean[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(!(_board.getSpotAt(i, j).isEmpty())) {
					board_array[i][j] = true;
				} else {
					board_array[i][j] = false;
				}
			}
		}
		
		return board_array;
	}
	public void setDisplay(boolean[][] input) {
		for(Spot s : _board) {
			s.clearSpot();
		}
		for(int i = 0; i < input.length; i++) {
			for(int j = 0; j < input[0].length; j++) {
				if(input[i][j]) {
					_board.getSpotAt(i, j).toggleSpot();
				}
			}
		}
	}
	public void clearDisplay() {
		for(Spot s : _board) {
			s.clearSpot();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == dimension_field) {
			JTextField field = (JTextField) e.getSource();
			String value = field.getText();
			fireEvent(new DimensionEvent(value));
			return;
		}
		if(e.getSource() == _lbt) {
			JTextField field = (JTextField) e.getSource();
			String value = field.getText();
			fireEvent(new LBTEvent(value));
			return;
		}
		if(e.getSource() == _hbt) {
			JTextField field = (JTextField) e.getSource();
			String value = field.getText();
			fireEvent(new HBTEvent(value));
			return;
		}
		if(e.getSource() == _lst) {
			JTextField field = (JTextField) e.getSource();
			String value = field.getText();
			fireEvent(new LSTEvent(value));
			return;
		}
		if(e.getSource() == _hst) {
			JTextField field = (JTextField) e.getSource();
			String value = field.getText();
			fireEvent(new HSTEvent(value));
			return;
		}
		if(e.getSource() == _delay) {
			JTextField field = (JTextField) e.getSource();
			String value = field.getText();
			fireEvent(new MSEvent(value));
			return;
		}
		if(e.getActionCommand().equals("start")) {
			fireEvent(new StartEvent());
			start_stop.setText("stop");
			start_stop.setActionCommand("stop");
		}
		if(e.getActionCommand().equals("stop")) {
			fireEvent(new StopEvent());
			start_stop.setText("start");
			start_stop.setActionCommand("start");
		}
		
		JButton button = (JButton) e.getSource();
		char first_char = button.getText().charAt(0);
		dispatchEventByChar(first_char);
	}
	
	private void dispatchEventByChar(char c) {
		switch(c) {
		case 'a':
			fireEvent(new AdvanceEvent());
			break;
		case 'c':
			fireEvent(new ClearFieldEvent());
			break;
		case 'r':
			fireEvent(new RandomFillEvent());
			break;
		case 't':
			fireEvent(new TorusEvent());
			break;
		}
	}
	
	public void fireEvent(LifeViewEvent e) {
		for (LifeViewListener l : listeners) {
			l.handleLifeViewEvent(e);
		}
	}
	
	public void addLifeViewListener(LifeViewListener listen) {
		listeners.add(listen);
	}
	
	public void removeCalculatorViewListener(LifeViewListener listen) {
		listeners.remove(listen);
	}
	
	public int getLBT() {
		return _birth_threshold_low;
	}
	
	public int getHBT() {
		return _birth_threshold_high;
	}
	
	public int getLST() {
		return _survive_threshold_low;
	}
	
	public int getHST() {
		return _survive_threshold_high;
	}
	
	public void setLBT(int threshold) {
		_birth_threshold_low = threshold;
	}
	
	public void setHBT(int threshold) {
		_birth_threshold_high = threshold;
	}
	
	public void setLST(int threshold) {
		_survive_threshold_low = threshold;
	}
	
	public void setHST(int threshold) {
		_survive_threshold_high = threshold;
	}
	
	public boolean getTorus() {
		return torus;
	}
	
	public void setTorus() {
		torus = !torus;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long del) {
		delay = del;
	}
	public void setStartText() { 
		start_stop.setText("Start");
		start_stop.setText("Start");
	}
}
