package jiang.lucy;

public class LucyApp {
	LucyController controller;
	LucyView window;

	public LucyApp() {
		window = new LucyView();
		controller = new LucyController(window);
	}
	
	public void execute(long interval, boolean autoStart) {
		controller.setInterval(interval);
		if (autoStart) {
			controller.raiseSwitch();
		}
	}
	
	
	public static void main(String[] args) {
		try {		
			long step = 1000;
			long interval = step * 10;
			if (args.length > 0) {
				try {
					interval = Long.parseLong(args[0]);
				}
				catch (NumberFormatException e) {
					interval = step * 10;
				}
			}
			
			boolean autoStart = false;
			if (args.length > 1) {
				autoStart = Boolean.parseBoolean(args[1]);
			}
			
			LucyApp app = new LucyApp();			
			app.execute(interval, autoStart);
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
