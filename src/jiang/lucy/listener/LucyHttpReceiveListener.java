package jiang.lucy.listener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import jiang.lucy.LucyController;
import jiang.lucy.model.LucyHttpResponse;
import jiang.lucy.model.LucyHttpAction;
import jiang.lucy.model.LucyHttpMessage;

public class LucyHttpReceiveListener {

	LucyController controller;
	
	public LucyHttpReceiveListener(LucyController controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(Socket socket) {
		
		InputStream inputStream;
		try {
			inputStream = socket.getInputStream();
		} catch (IOException e1) {
			System.out.println("error when getInputStream: " + e1.getMessage());
			return;
		}
		LucyHttpMessage message = new LucyHttpMessage(inputStream);
		
		OutputStream outputStream;
		try {
			outputStream = socket.getOutputStream();
		} catch (IOException e2) {
			System.out.println("error when getInputStream: " + e2.getMessage());
			return;
		}
		
		LucyHttpResponse response = new LucyHttpResponse(outputStream, message);
		
		if (message.getAction() == LucyHttpAction.START) {
			doStartCommand();
		}
		else if (message.getAction() == LucyHttpAction.STOP) {
			doStopCommand();
		}
		else if (message.getAction() == LucyHttpAction.BEAT) {
			doBeatCommand();
		}
		
		informState(response);
		Thread responseProcess = new Thread(response);
		responseProcess.start();
	}
	
	private void doStartCommand() {
		if (controller.getIsStop()) {
			controller.raiseSwitch();
		}
	}
	
	private void doStopCommand() {
		if (!controller.getIsStop()) {
			controller.raiseSwitch();
		}
	}
	
	private void doBeatCommand() {
		controller.raiseBeat();
	}
	
	private void informState(LucyHttpResponse response) {
		response.setLucyState(controller.getState());
	}
}