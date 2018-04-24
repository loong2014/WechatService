package com.sunny.wechat.base;

import com.sunny.wechat.tools.LogUtil;

public class BaseSunnyClass {

	private static String logTag = "";

	protected static void setLogTag(String tag) {
		logTag = tag;
	}

	protected static void logI(String msg) {
		LogUtil.logI(logTag, msg);
	}

	protected static void logE(String msg) {
		LogUtil.logE(logTag, msg);
	}

	protected static void logI(String tag, String msg) {
		LogUtil.logI(tag, msg);
	}

	protected static void logE(String tag, String msg) {
		LogUtil.logE(tag, msg);
	}

}
