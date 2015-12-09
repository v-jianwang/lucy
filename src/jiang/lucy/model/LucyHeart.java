package jiang.lucy.model;

public class LucyHeart {

	private String heartBeat;
	private String prefix;
	private int pointer;
	private final String beats = "0123456789";
	
	public LucyHeart() {
		this("");
	}
	
	public LucyHeart(String prefix) {
		this.prefix = prefix;
		Reset();
	}
	
	public void Beat() {
		if (pointer >= beats.length()) {
			Reset();
		}
		int beginIndex = pointer++;
		this.heartBeat += " " + beats.substring(beginIndex, pointer);
	}
	
	public void Reset() {
		this.heartBeat = "";
		this.pointer = 0;		
	}
	
	public String getHeartBeat() {
		return this.prefix + this.heartBeat;
	}
}
