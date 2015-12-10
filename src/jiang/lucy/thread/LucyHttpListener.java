package jiang.lucy.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import jiang.lucy.listener.LucyHttpReceiveListener;

public class LucyHttpListener extends Thread {
	
	private ServerSocket socket;
	
	private LucyHttpReceiveListener receiveListener;
	
	private volatile boolean running;

	public LucyHttpListener(int port, int backlog) {
		
		super.setName("httplistener");
		running = true;
		
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
				if (running) {
					receiveListener.actionPerformed(s);
				}
				else {
					break;
				}
			} catch (IOException e) {
				System.out.println("error when accept a socket. " + e.getMessage());
			}
		}
	}

	
	public void addActionListener(LucyHttpReceiveListener receiveListener) {
		this.receiveListener = receiveListener;
	}
	
	public void end() throws IOException {
		running = false;
		new Socket(socket.getInetAddress(), socket.getLocalPort()).close();
	}
}
