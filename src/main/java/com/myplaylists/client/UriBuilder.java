package com.myplaylists.client;

import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class UriBuilder {

    private StringBuilder uri;
    private boolean first;

    public UriBuilder(String uri) throws MalformedURLException {
        URL url = new URL(uri);
        if (StringUtils.hasText(url.getQuery())) {
            this.first = false;
        } else {
            this.first = true;
        }

        this.uri = new StringBuilder(uri);
    }

    public UriBuilder addParam(String key, Object value) {
        if (first) {
            uri.append("?").append(key).append("=").append(value);
            first = false;
        } else {
            uri.append("&").append(key).append("=").append(value);
        }
        return this;
    }

    public String build() {
        return uri.toString();
    }
}
