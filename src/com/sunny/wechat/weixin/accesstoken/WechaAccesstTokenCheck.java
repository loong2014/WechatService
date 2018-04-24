package com.sunny.wechat.weixin.accesstoken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.sunny.wechat.tools.LogUtil;
import com.sunny.wechat.weixin.bean.AccessTokenBean;

public class WechaAccesstTokenCheck {

	private static final int HTTP_SUCCESS = 200;

	public static final String AppID = "wx5e2b92eadde582c5";
	public static final String AppSecret = "f97094955553bdd922a19dcf66f12311";

	public static final String token = "123qweasdzxcvfr4"; // 开发者自行定义Tooken

	private String mAccessToken = null;
	private long mTokenTime = 0L;
	private static final long TokenValidityTime = 90 * 60 * 1000 * 1000; // token有效期是2小时(120m)，我们1.5小时(90m)刷新一次

	private static final String UpdateAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token"
			+ "?grant_type=client_credential" + "&appid=" + AppID + "&secret=" + AppSecret;

	public static final class SingletonHolder {
		private static final WechaAccesstTokenCheck INSTANCE = new WechaAccesstTokenCheck();
	}

	private WechaAccesstTokenCheck() {
	}

	public static WechaAccesstTokenCheck getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void initAccessToken() {
		doUpdateAccessToken();
	}

	public String getAccessToken() {

		if (mAccessToken == null) {
			doUpdateAccessToken();
		} else {
			long curTime = System.currentTimeMillis();
			if (mTokenTime + TokenValidityTime < curTime) {
				doUpdateAccessToken();
			}
		}
		return mAccessToken;
	}

	/**
	 * https请求方式: GET
	 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	 * 
	 * 返回格式JSON : {"access_token":"ACCESS_TOKEN","expires_in":7200}
	 */
	private void doUpdateAccessToken() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					logI("doUpdateAccessToken  begin");
					HttpClient client = HttpClients.createDefault();
					HttpGet httpGet = new HttpGet(UpdateAccessTokenUrl);

					HttpResponse response = client.execute(httpGet);

					int code = response.getStatusLine().getStatusCode();
					logI("doUpdateAccessToken  end  code :" + code);

					if (code == HTTP_SUCCESS) {

						HttpEntity entity = response.getEntity();
						long len = entity.getContentLength();
						String result = EntityUtils.toString(entity, "UTF-8");

						System.out.println("len :" + len + " , result :" + result);

						JSONReader jsonReader = new JSONReader(new DefaultJSONParser(result));

						AccessTokenBean tokenBean = jsonReader.readObject(AccessTokenBean.class);
						jsonReader.close();

						mAccessToken = tokenBean.getAccess_token();
						mTokenTime = System.currentTimeMillis();
						System.out.println("doUpdateAccessToken success  mAccessToken :" + mAccessToken
								+ " , mTokenTime :" + mTokenTime);
					} else {
						logI("doUpdateAccessToken error  code :" + code);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	/**
	 * 方法名：checkSignature</br>
	 * 详述：验证签名</br>
	 * 开发人员：souvc</br>
	 * 创建时间：2015-9-29 </br>
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return @throws
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		// 1.将token、timestamp、nonce三个参数进行字典序排序
		String[] arr = new String[] { token, timestamp, nonce };
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
	 * 方法名：byteToStr</br>
	 * 详述：将字节数组转换为十六进制字符串</br>
	 * 开发人员：souvc </br>
	 * 创建时间：2015-9-29 </br>
	 * 
	 * @param byteArray
	 * @return @throws
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 方法名：byteToHexStr</br>
	 * 详述：将字节转换为十六进制字符串</br>
	 * 开发人员：souvc</br>
	 * 创建时间：2015-9-29 </br>
	 * 
	 * @param mByte
	 * @return @throws
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	private void logI(String msg) {
		LogUtil.logI("WechatTokenCheck", msg);
	}

	private void logE(String msg) {
		LogUtil.logE("WechatTokenCheck", msg);
	}
}
