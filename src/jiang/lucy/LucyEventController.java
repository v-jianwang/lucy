package jiang.lucy;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LucyEventController {
	
	LucyView view;
	private boolean isStop;
	private long interval;
	
	public long getInterval() {
		return this.interval;
	}
	
	public synchronized void setIsStop(boolean isStop) {
		this.isStop = isStop;
	}
	public synchronized boolean getIsStop() {
		return this.isStop;
	}
	
	public LucyEventController(LucyView view) {
		this.view = view;
		this.isStop = true;
		this.interval = 10 * 1000; // 10s as default
	}
	
	public void BindClosingWindow() {
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
	
	public void BindBeatButton() {
		LucyHeart heart = new LucyHeart("Lucy's heartbeat: ");
		
		Button sender = view.getBeatButton();
		sender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				heart.Beat();
				view.outputBeat(heart.getHeartBeat());
			}
		});
	}
	
	public void BindSwitchButton() {
		Button sender = view.getSwitchButton();
		LucySwitchListener switchListener = new LucySwitchListener(this);
		sender.addActionListener(switchListener);
	}
	
	public void launch(long interval, boolean autoStart) {
		this.interval = interval;
		if (autoStart) {
			raiseSwitch();
		}
	}
	
	public void raiseSwitch() {
		view.switchClick();
	}

	public void raiseBeat() {
		view.beatClick();
	}

	public void notifyState(String state) {
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

	private void dispose() {
		if (!getIsStop()) {
			raiseSwitch();
			System.out.println("lucy disposed.");
		}
	}
}
