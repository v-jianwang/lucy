package jiang.lucy.thread;

import jiang.lucy.LucyController;

public class LucyBeater extends Thread {
	
	private LucyController controller;
	
	private long delay;
	
	public LucyBeater(LucyController controller) {
		this.controller = controller;
		this.delay = controller.getInterval();
	}
	
	@Override
	public void run() {	
		long step = 1000;		
		long idelay = delay - step;
	
		while (!controller.getIsStop()) {
			
			controller.displayState("Next click in ("+ (idelay / step) +") sec.");
			
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
