package com.sunny.wechat.weixin.signature;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunny.wechat.base.BaseSunnyClass;

public class SignatureVerifyManager extends BaseSunnyClass {

	public static final String TOKEN = "123qweasdzxcvfr4"; // 开发者自行定义Tooken

	static {
		setLogTag("SignatureVerifyManager");
	}

	/**
	 * 判断是否是微信URL验证请求
	 * 
	 * 根据请求中是否包含echostr字段来判断是否是URL验证请求
	 */
	public static boolean isWechatVer(HttpServletRequest request) {
		String echostr = request.getParameter("echostr");
		logI("isWechatVer  echostr :" + echostr);
		return echostr != null;
	}

	/**
	 * 服务器验证，在公众号上绑定服务器后会对服务器进行一次验证请求
	 */
	public static String doTokenCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logI("=====================");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		logI("doTokenCheck info :\n" +

				"\t sign :" + signature +

				"\t time :" + timestamp +

				"\t nonce :" + nonce +

				"\t echostr :" + echostr);

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (checkSignature(signature, timestamp, nonce)) {
			logI("doTokenCheck success echostr :" + echostr);
			return echostr;
		}else {
			logI("doTokenCheck faile");
			return "success";
		}
	}

	/**
	 * 验证签名
	 */
	private static boolean checkSignature(String signature, String timestamp, String nonce) {
		// 1.将token、timestamp、nonce三个参数进行字典序排序
		String[] arr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(arr);

		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

}
