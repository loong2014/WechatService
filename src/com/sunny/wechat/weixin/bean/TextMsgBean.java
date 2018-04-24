package com.sunny.wechat.weixin.bean;

/**
 * MsgType = text
 */
public class TextMsgBean extends BaseMsgBean {

	/* 文本消息内容 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
