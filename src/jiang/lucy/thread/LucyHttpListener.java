package jiang.lucy.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import jiang.lucy.listener.LucyHttpReceiveListener;

public class LucyHttpListener extends Thread {
	
	private ServerSocket socket;
	
	private LucyHttpReceiveListener receiveListener;

	public LucyHttpListener(int port, int backlog) {
		
		try {
			socket = new ServerSocket(port, backlog);
		} catch (IOException e) {
			System.out.println("error when create server socket. " + e.getMessage());
		}
	}
	
	
	@Override
	public void run() {

		while (true) {
			
			try {
				Socket s = this.socket.accept();
				receiveListener.actionPerformed(s);
				
			} catch (IOException e) {
				System.out.println("error when accept a socket. " + e.getMessage());
			}
		}
	}

	
	public void addActionListener(LucyHttpReceiveListener receiveListener) {
		this.receiveListener = receiveListener;
	}
}
