package jiang.lucy.thread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import jiang.lucy.model.LucyHttpMessage;
import jiang.lucy.model.LucyState;

public class LucyHttpResponse implements Runnable {
	
	private byte[] content;
	
	private OutputStream output;
	private LucyHttpMessage message;
	
	private LucyState state;
	
	public LucyHttpResponse(OutputStream output, LucyHttpMessage message) {
		this.output = output;
		this.message = message;
	}
	
	public void setLucyState(LucyState state) {
		this.state = state;
	}

	@Override
	public void run() {
		buildContent();
		
		DataOutputStream stream = new DataOutputStream(output);
		try {
			stream.write(this.content);
			stream.close();
		} catch (IOException e) {
			System.out.println("error: " + e.getMessage());
		}
	}
	

	private void buildContent() {
		StringBuilder builder = new StringBuilder();
		String lineEnd = "\r\n";
		String space = " ";
		
		builder.append("HTTP/1.1" + space + "200" + space + "OK" + lineEnd);
		builder.append("Date: " + new Date() + lineEnd);
		builder.append("Content-Type: text/html" + lineEnd);
		
		File page = new File("./resources/manager.html");
		builder.append("Content-Length: " + page.length() + lineEnd);
		
		builder.append(lineEnd);
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(page));
			String line;
			
			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("\\{action\\}", String.valueOf(message.getAction()));
				line = line.replaceAll("\\{isrunning\\}", state.getIsRunning() ? "running" : "stopped");
				line = line.replaceAll("\\{interval\\}", String.valueOf(state.getInterval()));
				builder.append(line);
				builder.append(lineEnd);
			}
			reader.close();
		} catch (Exception e1) {
			System.out.println("error: " + e1.getMessage());
		}
		
		try {
			this.content = builder.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("error: " + e.getMessage());
		}
	}
}
