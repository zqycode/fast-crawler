package org.fast.crawler.core.auth;

import org.apache.http.client.CookieStore;

public interface AuthProvider {

    public User getUser();

    public CookieStore getCookies();

	public String getCookieName();

}
