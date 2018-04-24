package com.sunny.wechat.weixin.msg.text;

import com.sunny.wechat.weixin.bean.BaseBean;
import com.sunny.wechat.weixin.bean.TextMsgBean;

public class TextMsgParse {

	public TextMsgParse() {

	}

	public String dealMsg(TextMsgBean bean) {

		/* 异常信息处理 */
		String content = bean.getContent();
		if (content == null || content.isEmpty()) {
			return doTextReply(bean, "你说话了么？宝宝没有听到，再说一遍吧！");
		}

		/* 消息处理 */
		String replyStr = "success";
		if (content.contains("海苔")) {
			replyStr = doReplyBySeaweed(bean, content);
		} else if (content.contains("天气")) {
			replyStr = doReplyByWeather(bean, content);
		}

		return replyStr;
	}

	private String doReplyByWeather(TextMsgBean bean, String content) {
		String str = "今天天气很好";
		return doTextReply(bean, str);
	}

	private String doReplyBySeaweed(TextMsgBean bean, String content) {
		String str = "海苔真好吃，营养又健康";
		return doTextReply(bean, str);
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
