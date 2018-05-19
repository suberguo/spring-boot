package com.dds.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:storage.properties")
@ConfigurationProperties
public class StorageProperties {
	/**
	 * Folder location for storing files
	 */
	 private String location = "upload-dir2";
	//private String location = "D:\\edas-config-center\\config-center-db\\log";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
