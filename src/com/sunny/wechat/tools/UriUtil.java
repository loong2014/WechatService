package com.sunny.wechat.tools;

import com.sunny.wechat.base.BaseSunnyClass;

public class UriUtil extends BaseSunnyClass {

	static {
		setLogTag("UriUtil");
	}

	public static String[] getUriPartList(String uri) {
		logI("getUriPartList uri :" + uri);
		String[] uriList = uri.split("/");
		return uriList;
	}

}
