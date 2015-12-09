package jiang.lucy.model;

public enum LucyHttpAction {
	NOSET,
	START,
	STOP,
	BEAT,
	CHECK;
	
	public static final String VALUE_PATTERN = "(start|stop|beat|check)";

}
