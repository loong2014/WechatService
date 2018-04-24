package com.sunny.wechat.info;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunny.wechat.base.BaseSunnyClass;
import com.sunny.wechat.info.manager.ServerInfoManager;
import com.sunny.wechat.info.manager.UserInfoManager;

public class ServerInfoParse extends BaseSunnyClass {

	private static final String INFO_GET_USER_INFO = "getUserInfo";
	private static final String INFO_GET_SERVER_INFO = "getServerInfo";

	public ServerInfoParse() {
		setLogTag("ServerInfoParse");
	}

	public void parseGetPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		logI("parseGetPost  uri :" + uri);

		String[] uriList = uri.split("/");
		int uriLen = uriList.length;
		if (uriLen < 4) {
			logI("uriLen :" + uriLen);
			response.getWriter().append("ServerInfoManager parseGetPost error at: ").append(request.getRequestURI());
			return;
		}

		String uriLevel2 = uriList[3];
		logI("uri uriLevel2 :" + uriLevel2);

		String resultStr = "";
		switch (uriLevel2) {

		case INFO_GET_SERVER_INFO:
			resultStr = ServerInfoManager.getInstance().getServerInfoJson();
			break;
		case INFO_GET_USER_INFO:
			resultStr = UserInfoManager.getInstance().getUserInfoJson();
			break;

		default:
			resultStr = "error request with " + uriLevel2;
			break;
		}

		logI("parseGetPost  resultStr :" + resultStr);
		response.getWriter().write(resultStr);
	}
}
