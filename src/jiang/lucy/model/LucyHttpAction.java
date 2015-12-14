package jiang.lucy.model;

public enum LucyHttpAction {
	NOSET,
	START,
	STOP,
	SET,
	BEAT,
	CHECK;
	
	public static final String VALUE_PATTERN = "(start|stop|set|beat|check)";
	
	public static final String PARAM_PATTERN = "[\\?|\\&](?<key>\\S+)=(?<value>\\S+)";

}
