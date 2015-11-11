package jiang.lucy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jiang.lucy.thread.LucyBeater;

public class LucySwitchListener implements ActionListener {
	
	LucyEventController controller;
	LucyBeater beater;
	
	public LucySwitchListener(LucyEventController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (controller.getIsStop()) {
			// bind event handler to start
			controller.setIsStop(false);
			startBeater();
		}
		else {
			// bind event to do stop
			controller.setIsStop(true);
			stopBeater();
		}
	}
	
	private void startBeater() {
		beater = new LucyBeater(controller);	
		
		beater.start();	
		
		controller.notifyState("started");
	}

	private void stopBeater() {
		synchronized(beater) {
			beater.notify();
		}
		
		controller.notifyState("stopped");
	}

}
