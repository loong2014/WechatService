package com.sunny.wechat.weixin.msg;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunny.wechat.tools.LogUtil;
import com.sunny.wechat.weixin.bean.BaseBean;
import com.sunny.wechat.weixin.bean.ImageMsgBean;
import com.sunny.wechat.weixin.bean.TextMsgBean;
import com.sunny.wechat.weixin.bean.VideoMsgBean;
import com.sunny.wechat.weixin.bean.VoiceMsgBean;
import com.sunny.wechat.weixin.msg.text.TextMsgParse;
import com.sunny.wechat.weixin.tools.WechatRequestDataParse;

public class WechatMsgParse {

	private static final String TAG = "WechatMsgManager";

	private static final String MSG_TYPE_EVENT = "event";
	private static final String MSG_TYPE_TEXT = "text";
	private static final String MSG_TYPE_IMAGE = "image";
	private static final String MSG_TYPE_VOICE = "voice";
	private static final String MSG_TYPE_VIDEO = "video";
	private static final String MSG_TYPE_SHORTVIDEO = "shortvideo";
	private static final String MSG_TYPE_LOCATION = "location";
	private static final String MSG_TYPE_LINK = "link";

	private static void doFillBaseBean(BaseBean bean, Map<String, String> map) {
		bean.setToUserName(map.get("ToUserName"));
		bean.setFromUserName(map.get("FromUserName"));
		bean.setCreateTime(map.get("CreateTime"));
		bean.setMsgType(map.get("MsgType"));
	}

	public static String doParseRequestData(HttpServletRequest request, HttpServletResponse response) {
		String backMsg = null;
		try {
			// 将解析结果存储在HashMap中
			Map<String, String> map = WechatRequestDataParse.parseXml(request);

			String msgType = map.get("MsgType");
			logI("doParseRequestData  msgType :" + msgType);

			if (msgType == null || msgType.isEmpty()) {
				logI("doParseRequestData  other msg");
				return "success";
			}

			if (MSG_TYPE_EVENT.equals(msgType)) {
				backMsg = dealEventRequestData(request, response, map);
			} else {
				backMsg = dealMsgRequestData(request, response, map);
			}

		} catch (Exception e) {
			logI("doParseRequestData  error");
			e.printStackTrace();
		}
		if (backMsg == null) {
			logI("doParseRequestData  backMsg is null");
			backMsg = "success";
		}
		return backMsg;
	}

	/**
	 * 处理event消息
	 */
	private static String dealEventRequestData(HttpServletRequest request, HttpServletResponse response,
			Map<String, String> map) {
		logI("dealEventRequestData");
		return "success";
	}

	private static long getCurTimeSec() {
		long time = System.currentTimeMillis();
		return time / 1000;
	}

	private static void doFillBaseReplayMsg(StringBuilder sb, BaseBean baseBean) {
		sb.append("<ToUserName><![CDATA[").append(baseBean.getFromUserName()).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(baseBean.getToUserName()).append("]]></FromUserName>");
		sb.append("<CreateTime><![CDATA[").append(getCurTimeSec()).append("]]></CreateTime>");
		sb.append("<MsgType><![CDATA[").append(baseBean.getMsgType()).append("]]></MsgType>");
	}

	private static String dealMsgRequestData(HttpServletRequest request, HttpServletResponse response,
			Map<String, String> map) {
		logI("dealMsgRequestData");
		try {
			String backMsg = "success";
			String msgType = map.get("MsgType");
			logI("doParseRequestData  msgType :" + msgType);

			/* text */
			if (MSG_TYPE_TEXT.equals(msgType)) {
				TextMsgBean bean = new TextMsgBean();
				doFillBaseBean(bean, map);
				bean.setContent(map.get("Content"));
				TextMsgParse msgParse = new TextMsgParse();
				backMsg = msgParse.dealMsg(bean);
			}
			/* image */
			else if (MSG_TYPE_IMAGE.equals(msgType)) {
				ImageMsgBean bean = new ImageMsgBean();
				doFillBaseBean(bean, map);
				bean.setMediaId(map.get("MediaId"));
				bean.setPicUrl(map.get("PicUrl"));
				backMsg = doImageReplay(bean);
			}
			/* voice */
			else if (MSG_TYPE_VOICE.equals(msgType)) {
				VoiceMsgBean bean = new VoiceMsgBean();
				doFillBaseBean(bean, map);
				bean.setMediaId(map.get("MediaId"));
				bean.setFormat(map.get("Format"));
				bean.setRecognition(map.get("Recognition"));
				backMsg = doVoiceReplay(bean);
			}
			/* video */
			else if (MSG_TYPE_VIDEO.equals(msgType)) {
				VideoMsgBean bean = new VideoMsgBean();
				doFillBaseBean(bean, map);
				bean.setMediaId(map.get("MediaId"));
				bean.setThumbMediaId(map.get("ThumbMediaId"));
				backMsg = doVideoReplay(bean);
			}

			logI("doReplay msgType :" + msgType + " , backMsg :" + backMsg);

			return backMsg;

		} catch (Exception e) {
			logI("doParseRequestData  error");
			e.printStackTrace();
		}

		return null;
	}

	private static String doVideoReplay(VideoMsgBean bean) {

		String mediaId = bean.getMediaId();
		String videoTitle = "SunnySay";
		String videoDescrition = "你的就是我的";

		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		doFillBaseReplayMsg(sb, bean);
		sb.append("<MediaId><![CDATA[").append(mediaId).append("]]></MediaId>");
		sb.append("<Title><![CDATA[").append(videoTitle).append("]]></Title>");
		sb.append("<Description><![CDATA[").append(videoDescrition).append("]]></Description>");
		sb.append("</xml>");
		return sb.toString();
	}

	private static String doVoiceReplay(VoiceMsgBean bean) {

		if (bean.getRecognition() == null) {
			logI("doVoiceReplay without speech recognition ");
		} else {
			logI("doVoiceReplay with speech recognition ");
		}

		String mediaId = bean.getMediaId();

		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		doFillBaseReplayMsg(sb, bean);
		sb.append("<MediaId><![CDATA[").append(mediaId).append("]]></MediaId>");
		sb.append("</xml>");
		return sb.toString();
	}

	private static String doImageReplay(ImageMsgBean bean) {

		String mediaId = bean.getMediaId();

		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		doFillBaseReplayMsg(sb, bean);
		sb.append("<MediaId><![CDATA[").append(mediaId).append("]]></MediaId>");
		sb.append("</xml>");
		return sb.toString();
	}

	private static void logI(String msg) {
		LogUtil.logI(TAG, msg);
	}

}
