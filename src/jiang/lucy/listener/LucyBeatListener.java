package jiang.lucy.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jiang.lucy.LucyController;
import jiang.lucy.model.LucyHeart;

public class LucyBeatListener implements ActionListener {
	
	LucyController controller;
	
	jiang.lucy.model.LucyHeart heart;
	
	
	public LucyBeatListener(LucyController controller) {
		this.controller = controller;
		this.heart = new LucyHeart("Lucy's heartbeat: ");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		heart.Beat();
		controller.displayBeat(heart.getHeartBeat());
	}

}
