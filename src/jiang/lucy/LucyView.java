package jiang.lucy;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class LucyView {
	private Robot robot;
	
	private Frame frame;
	
	private Button beatButton;
	private Button switchButton;
	
	private Label beatLabel;
	private Label statLabel;
	
	public Button getBeatButton() {
		return this.beatButton;
	}
	
	public Button getSwitchButton() {
		return this.switchButton;
	}
	
	public Frame getFrame() {
		return this.frame;
	}
	
	public LucyView() {
		frame = new Frame("Lucy Click");
		frame.setSize(300, 300);
		
		// Label Top
		beatLabel = new Label();
		beatLabel.setAlignment(Label.LEFT);
		frame.add(beatLabel, BorderLayout.PAGE_START);
		
		
		// Button Left
		beatButton = new Button("Lucy Click");
		frame.add(beatButton, BorderLayout.CENTER);
		
		// Button right
		switchButton = new Button("Start");
		frame.add(switchButton, BorderLayout.LINE_END);
		
		// Label Bottom
		statLabel = new Label();
		statLabel.setAlignment(Label.RIGHT);
		frame.add(statLabel, BorderLayout.PAGE_END);
		
		frame.setVisible(true);	
		
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}
	}

	
	public void beatClick() {
		frame.setState(Frame.NORMAL);
		frame.toFront();
		
		Point point = beatButton.getLocationOnScreen();
		robot.mouseMove(point.x + 30, point.y + 30);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void switchClick() {
		frame.setState(Frame.NORMAL);
		frame.toFront();
		
		Point point = switchButton.getLocationOnScreen();
		robot.mouseMove(point.x + 10, point.y + 10);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);		
	}
	
	public void outputBeat(String text) {
		beatLabel.setText(text);
	}
	
	public void outputState(String text) {
		statLabel.setText(text);
	}

	public void setSwitchButtonText(String text) {
		switchButton.setLabel(text);
	}

}
