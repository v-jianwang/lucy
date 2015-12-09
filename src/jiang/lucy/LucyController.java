package jiang.lucy;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jiang.lucy.listener.LucyBeatListener;
import jiang.lucy.listener.LucyHttpReceiveListener;
import jiang.lucy.listener.LucySwitchListener;
import jiang.lucy.model.LucyState;
import jiang.lucy.thread.LucyHttpListener;

public class LucyController {
	
	LucyView view;
	private boolean isStop;
	private long interval;
	
	
	public long getInterval() {
		return this.interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
	public synchronized void setIsStop(boolean isStop) {
		this.isStop = isStop;
	}
	public synchronized boolean getIsStop() {
		return this.isStop;
	}
	
	public LucyController(LucyView view) {
		this.view = view;
		this.isStop = true;
		this.interval = 10 * 1000; // 10s as default
		
		BindClosingWindow();
		BindBeatButton();
		BindSwitchButton();
		
		AddHttpEvent();
	}
	
	private void BindClosingWindow() {
		Frame frame = view.getFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});
	}
	
	
	private void BindBeatButton() {
		Button sender = view.getBeatButton();
		LucyBeatListener listener = new LucyBeatListener(this);
		sender.addActionListener(listener);
	}
	
	
	private void BindSwitchButton() {
		Button sender = view.getSwitchButton();
		LucySwitchListener switchController = new LucySwitchListener(this);
		sender.addActionListener(switchController);
	}
	
	
	
	
	private void AddHttpEvent() {
		LucyHttpListener listener = new LucyHttpListener(8070, 10);
		LucyHttpReceiveListener receiveListener = new LucyHttpReceiveListener(this);
		listener.addActionListener(receiveListener);
		
		listener.start();
	}
	
	
	public void raiseSwitch() {
		view.switchClick();
	}

	public void raiseBeat() {
		view.beatClick();
	}
	
	public void displayBeat(String beat) {
		view.outputBeat(beat);
	}
	

	public void displayState(String state) {
		if ("stopped".equalsIgnoreCase(state)) {
			view.outputState("Click is stopped.");
			view.setSwitchButtonText("Start");
		}
		else if ("started".equalsIgnoreCase(state)) {
			view.outputState("Starting...");
			view.setSwitchButtonText("Stop");
		}
		else {
			view.outputState(state);
		}
	}
	
	public LucyState getState() {
		LucyState state = new LucyState();
		state.setInterval(this.interval);
		state.setIsRunning(!getIsStop());
		return state;
	}

	private void dispose() {
		if (!getIsStop()) {
			raiseSwitch();
			System.out.println("lucy disposed.");
		}
	}
}
