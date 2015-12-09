package jiang.lucy.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

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
		builder.append("Content-Length: 300" + lineEnd);
		
		builder.append(lineEnd);
	
		builder.append("<html>" + lineEnd);
		builder.append("<head>" + lineEnd);
		builder.append("    <title>Lucy Click</title>" + lineEnd);
		builder.append("</head>" + lineEnd);
		builder.append("<body>" + lineEnd);
		builder.append("    <h1>Lucy received command</h1>" + lineEnd);
		builder.append("    <p>The command you sent to Lucy is " + this.message.getAction() + "</p>" + lineEnd);
		
		if (message.getAction() == LucyHttpAction.CHECK) {
			builder.append("    <ul>" + lineEnd);
			builder.append("      <li>Lucy's ");
			if (this.state.getIsRunning())
				builder.append("running");
			else
				builder.append("stopped");

			builder.append("</li>" + lineEnd);
			
			builder.append("    <li>Beating interval: " + state.getInterval() + " second(s)." + lineEnd);			
			builder.append("    </ul>" + lineEnd);
			
			builder.append("    </ul>" + lineEnd);
		}
		builder.append("</body>" + lineEnd);
		builder.append("</html>" + lineEnd);
		
		try {
			this.content = builder.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("error: " + e.getMessage());
		}
	}

}
