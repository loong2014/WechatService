package com.sunny.wechat.info.manager;

import com.alibaba.fastjson.JSON;
import com.sunny.wechat.base.BaseSunnyClass;
import com.sunny.wechat.base.bean.ErrorBean;
import com.sunny.wechat.info.bean.ServerInfo;

public class ServerInfoManager extends BaseSunnyClass {

	private static class SingletonHolder {
		private static final ServerInfoManager INSTANCE = new ServerInfoManager();
	}

	public static ServerInfoManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private ServerInfo mServerInfo;

	public ServerInfo getServerInfo() {
		if (mServerInfo == null) {
			mServerInfo = buildServerInfo();
		}
		return mServerInfo;
	}

	public String getServerInfoJson() {
		ServerInfo info = getServerInfo();
		if (info != null) {
			return JSON.toJSONString(info);
		}
		return buildErrorBeanJson(1, "no server info");
	}

	private ServerInfo buildServerInfo() {
		ServerInfo info = new ServerInfo();
		info.setServerIp("47.104.29.233");
		info.setServerName("CentOS 7.4 64位");
		return info;
	}

	private String buildErrorBeanJson(int code, String errorMsg) {
		ErrorBean errorBean = new ErrorBean();
		errorBean.setCode(code);
		errorBean.setErrorMsg(errorMsg);
		return JSON.toJSONString(errorBean);
	}

}
