package com.sunny.wechat.weixin.bean;

/**
 * MsgType = image
 */
public class ImageMsgBean extends BaseMsgBean {

	/* 图片消息媒体id，可以调用多媒体文件下载接口拉取数据 */
	private String MediaId;

	/* 图片链接（由系统生成） */
	private String PicUrl;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
