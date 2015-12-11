package jiang.lucy.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LucyHttpMessage {

	private LucyHttpAction action;
	
	public LucyHttpMessage(InputStream inputStream) {
		parse(inputStream);
	}
	
	public LucyHttpAction getAction() {
		return this.action;
	}
	
	
	private void parse(InputStream inputStream) {
		String requestLine;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			requestLine = reader.readLine();
		} catch (IOException e) {
			System.out.println("error when reading input from socket. " + e.getMessage());
			requestLine = "noset";
		}
	
		System.out.println("http request: " + requestLine);
		String uri = requestLine.split(" ")[1];
		Pattern pattern = Pattern.compile(LucyHttpAction.VALUE_PATTERN);
		Matcher matcher = pattern.matcher(uri);
		
		if (matcher.find()) {
			this.action = LucyHttpAction.valueOf(matcher.group().toUpperCase());
		}
		else {
			this.action = LucyHttpAction.NOSET;
		}
	}
}
