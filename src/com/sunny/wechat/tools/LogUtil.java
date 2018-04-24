package com.sunny.wechat.tools;

public class LogUtil {

	public static void logI(String msg) {
		logI("Sunny", msg);
	}

	public static void logE(String msg) {
		logE("Sunny", msg);
	}

	public static void logI(String tag, String msg) {
		System.out.println(tag + "-I:" + msg);
	}

	public static void logE(String tag, String msg) {
		System.out.println(tag + "-E:" + msg);
	}
}
