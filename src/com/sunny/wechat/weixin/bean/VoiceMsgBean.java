package com.sunny.wechat.weixin.bean;

/**
 * MsgType = voice
 */
public class VoiceMsgBean extends BaseMsgBean {

	/* 语音消息媒体id，可以调用多媒体文件下载接口拉取数据 */
	private String MediaId;

	/* 语音格式-未开启语音识别，如amr，speex等 */
	/* 语音格式-开启语音识别，amr */
	private String Format;

	/* 语音识别结果，UTF8编码 */
	private String Recognition;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
}
