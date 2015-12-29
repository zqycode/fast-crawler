package org.fast.crawler.demo.crawler.sina;

import org.fast.crawler.core.auth.AbstractAuthProvider;
import org.fast.crawler.core.auth.User;
import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.fetcher.response.HttpResponseWrapper;
import org.fast.crawler.core.fetcher.response.JsonResponseWrapper;
import org.fast.crawler.core.job.CrawlContext;
import org.fast.crawler.core.utils.Coder;
import org.fast.crawler.core.utils.HttpUtil;
import org.fast.crawler.core.utils.JsonUtil;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SinaAuthProvider extends AbstractAuthProvider {

	public static final String PRE_LOGIN_URL = "http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=&rsakt=mod&client=ssologin.js(v1.4.18)&_=1442557623175";
	public static final String LOGIN_URL = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.18)";

	public static final String PUB_KEY = "EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090CB2D245A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F6D63C55FE2F08A49B353F444AD3993CACC02DB784ABBB8E42A9B1BBFFFB38BE18D78E87A0E41B9B8F73A928EE0CCEE1F6739884B9777E4FE9E88A1BBE495927AC4A799B3181D6442443";

	@Override
	public CookieStore doAuth() {
		CrawlerSiteConfig siteConfig = CrawlContext.getSiteConfig();
		Coder.RSACoder rsaCoder = Coder.newRSACoder();
		CookieStore cookieStore = new BasicCookieStore();

		HttpHost proxy = null;
		if (siteConfig.getUseProxy()) {
			proxy = siteConfig.getProxyProducer().generateProxy();
		}
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		try {
			HttpResponse response = HttpUtil.get(PRE_LOGIN_URL, null, config, null);
			HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response);
			String responseData = responseWrapper.getResponse();
			responseData = responseData.replaceAll("sinaSSOController.preloginCallBack\\(", "");
			responseData = responseData.replace(")", "");
			Map<String, Object> tmp = JsonUtil.toMap(responseData);

			String username = Coder.encryptBASE64(this.getUser().getUsername().getBytes());
			Integer servertime = (Integer) tmp.get("servertime") + 200;
			String nonce = (String) tmp.get("nonce");
			String rsakv = (String) tmp.get("rsakv");
			String orgPass = servertime + "\t" + nonce + "\n" + this.getUser().getPassword();
			byte[] bytes = rsaCoder.encryptByPublicKey(orgPass.getBytes(), PUB_KEY);
			String password = Coder.bytesToHex(bytes);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("entry", "weibo"));
			params.add(new BasicNameValuePair("gateway", "1"));
			params.add(new BasicNameValuePair("from", ""));
			params.add(new BasicNameValuePair("savestate", "7"));
			params.add(new BasicNameValuePair("useticket", "1"));
			params.add(new BasicNameValuePair("pagerefer", "http://d.weibo.com/100803?from=unlogin_home&mod=pindao&type=hothuati"));
			params.add(new BasicNameValuePair("vsnf", "1"));
			params.add(new BasicNameValuePair("su", username));
			params.add(new BasicNameValuePair("service", "miniblog"));
			params.add(new BasicNameValuePair("servertime", servertime.toString()));
			params.add(new BasicNameValuePair("nonce", nonce));
			params.add(new BasicNameValuePair("rsakv", rsakv));
			params.add(new BasicNameValuePair("sp", password));
			params.add(new BasicNameValuePair("pwencode", "rsa2"));
			params.add(new BasicNameValuePair("sr", "1680*1050"));
			params.add(new BasicNameValuePair("encoding", "UTF-8"));
			params.add(new BasicNameValuePair("cdult", "2"));
			params.add(new BasicNameValuePair("domain", "weibo.com"));
			params.add(new BasicNameValuePair("prelt", "27"));
			params.add(new BasicNameValuePair("returntype", "TEXT"));

			HttpResponse loginResponse = HttpUtil.post(LOGIN_URL, params, config, cookieStore);
			JsonResponseWrapper loginResponseData = new JsonResponseWrapper(loginResponse);
			tmp = loginResponseData.getResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return cookieStore;
	}

	@Override
	public String getCookieName() {
		return "sina.cookie";
	}

	@Override
	public User loadUser() {
		return null;
	}

}
