package com.dds.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties("storage")
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
