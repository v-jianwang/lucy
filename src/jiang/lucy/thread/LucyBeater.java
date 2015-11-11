package jiang.lucy.thread;

import jiang.lucy.LucyEventController;

public class LucyBeater extends Thread {
	
	private LucyEventController controller;
	
	private long delay;
	
	public LucyBeater(LucyEventController controller) {
		this.controller = controller;
		this.delay = controller.getInterval();
	}
	
	@Override
	public void run() {	
		long step = 1000;		
		long idelay = delay - step;
	
		while (!controller.getIsStop()) {
			
			controller.notifyState("Next click in ("+ (idelay / step) +") sec.");
			
			if (idelay < step) {
				idelay = delay - step;;
				controller.raiseBeat();
			}
			else {
				idelay -= step;
			}
			
			synchronized(this) {
				try {
					this.wait(step);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		System.out.println("beater thread is over.");
	}
}
