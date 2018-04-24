package com.sunny.wechat.weixin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunny.wechat.base.BaseSunnyClass;
import com.sunny.wechat.weixin.msg.WechatMsgParse;
import com.sunny.wechat.weixin.signature.SignatureVerifyManager;

public class WechatParse extends BaseSunnyClass {

	public WechatParse() {
		setLogTag("WechatParse");
	}

	public void parseGetPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		logI("parseGetPost  uri :" + uri);

		String respStr = "success";

		try {
			/* 判断是否是URL验证请求 */
			if (SignatureVerifyManager.isWechatVer(request)) {
				respStr = SignatureVerifyManager.doTokenCheck(request, response);
			} else {
				respStr = WechatMsgParse.doParseRequestData(request, response);
			}

		} catch (Exception e) {
			logI("request parameters parse error");
			e.printStackTrace();
		}

		response.setContentType("text/html;charset=utf-8");
		logI("back respStr :" + respStr);
		response.getWriter().write(respStr);

	}

}
