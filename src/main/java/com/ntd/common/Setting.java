package com.ntd.common;

public class Setting {
	
	private static Setting setting;
	
	private boolean debug;
	private int[] num;
	
	public Setting() {
		setting = this;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setNum(int[] num) {
		this.num = num;
	}
	
	public static boolean isDebug() {
		return setting.debug;
	}

	public static int[] getNum() {
		return setting.num;
	}

}
