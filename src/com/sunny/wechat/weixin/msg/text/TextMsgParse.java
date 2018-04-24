package com.sunny.wechat.weixin.msg.text;

import com.sunny.wechat.weixin.bean.BaseBean;
import com.sunny.wechat.weixin.bean.TextMsgBean;

public class TextMsgParse {

	public TextMsgParse() {

	}

	public String dealMsg(TextMsgBean bean) {

		String replyStr = "success";
		String content = bean.getContent();
		if (content == null || content.isEmpty()) {
			replyStr = doTextReply(bean, "你说话了么？宝宝没有听到，再说一遍吧！");
 		}

		return replyStr;
	}

	private String doTextReply(TextMsgBean bean, String content) {

		String replayContent = bean.getContent();

		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		doFillBaseReplayMsg(sb, bean);
		sb.append("<Content><![CDATA[").append(replayContent).append("]]></Content>");
		sb.append("</xml>");
		return sb.toString();
	}

	private static void doFillBaseReplayMsg(StringBuilder sb, BaseBean baseBean) {
		sb.append("<ToUserName><![CDATA[").append(baseBean.getFromUserName()).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(baseBean.getToUserName()).append("]]></FromUserName>");
		sb.append("<CreateTime><![CDATA[").append(getCurTimeSec()).append("]]></CreateTime>");
		sb.append("<MsgType><![CDATA[").append(baseBean.getMsgType()).append("]]></MsgType>");
	}

	private static long getCurTimeSec() {
		long time = System.currentTimeMillis();
		return time / 1000;
	}
}
