package com.sunny.wechat.info.manager;

import com.alibaba.fastjson.JSON;
import com.sunny.wechat.base.BaseSunnyClass;
import com.sunny.wechat.base.bean.ErrorBean;
import com.sunny.wechat.info.bean.UserInfo;

public class UserInfoManager extends BaseSunnyClass {

	private static class SingletonHolder {
		private static final UserInfoManager INSTANCE = new UserInfoManager();
	}

	public static UserInfoManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private UserInfo mUserInfo;

	public UserInfo getUserInfo() {
		if (mUserInfo == null) {
			mUserInfo = buildUserInfo();
		}
		return mUserInfo;
	}

	public String getUserInfoJson() {
		UserInfo info = getUserInfo();
		if (info != null) {
			return JSON.toJSONString(info);
		}
		return buildErrorBeanJson(1, "no user info");
	}

	private UserInfo buildUserInfo() {
		UserInfo info = new UserInfo();
		info.setUserName("Sunny");
		info.setVipLevel(0);
		return info;
	}

	private String buildErrorBeanJson(int code, String errorMsg) {
		ErrorBean errorBean = new ErrorBean();
		errorBean.setCode(code);
		errorBean.setErrorMsg(errorMsg);
		return JSON.toJSONString(errorBean);
	}

}
