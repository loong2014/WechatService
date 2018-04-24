package com.sunny.wechat.weixin.bean;

public class BaseMsgBean extends BaseBean {

	/* 消息id，64位整型 */
	private String MsgId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
