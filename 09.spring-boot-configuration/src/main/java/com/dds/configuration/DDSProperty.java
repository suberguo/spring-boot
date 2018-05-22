package com.dds.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:dds-agent.properties")
@ConfigurationProperties("dds")
public class DDSProperty {
	@Value("${dds_agent_url}")
	private String url;
	@Value("${dds_agent_user}")
	private String user;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "DDSProperty [url=" + url + ", user=" + user + "]";
	}

}
