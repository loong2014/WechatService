package com.sunny.wechat.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.function.Predicate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunny.wechat.info.ServerInfoParse;
import com.sunny.wechat.tools.LogUtil;
import com.sunny.wechat.weixin.WechatParse;
import com.sunny.wechat.weixin.msg.WechatMsgParse;

/**
 * Servlet implementation class WechatServlet
 */
@WebServlet("/WechatServlet")
public class WechatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URI_LEVEL_1_INFO = "info";
	private static final String URI_LEVEL_1_WECHAT = "wechat";

	private ServerInfoParse mServerInfoParse;
	private WechatParse mWechatParse;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WechatServlet() {
		super();
		myLogI("structure");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		myLogI("init");
		mServerInfoParse = new ServerInfoParse();
		mWechatParse = new WechatParse();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		myLogI("doGet");
		doGetPostRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		myLogI("doPost");
		doGetPostRequest(request, response);
	}

	/**
	 * 处理doGet/doPost请求
	 */
	private void doGetPostRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		myLogI("doGetPostRequest  uri :" + uri);

		String[] uriList = uri.split("/");
		int uriLen = uriList.length;
		if (uriLen < 3) {
			myLogI("uriLen :" + uriLen);
			response.getWriter().append("Served at: ").append(request.getRequestURI());
			return;
		}

		String uriLevel1 = uriList[2];

		myLogI("uri uriLevel1 :" + uriLevel1);

		/* 服务信息 */
		if (URI_LEVEL_1_INFO.equals(uriLevel1)) {
			mServerInfoParse.parseGetPost(request, response);
		}
		/* wechat信息 */
		else if (URI_LEVEL_1_WECHAT.equals(uriLevel1)) {
			mWechatParse.parseGetPost(request, response);
		} else {
			response.getWriter().append("Served at: ").append(request.getRequestURI());
		}
	}

	private void myLogI(String msg) {
		LogUtil.logI("WechatServlet", msg);
	}

}
