package com.dds.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalProperty {
	@Value("${dds_store_path}")
	private String storePath;

	@Value("${dds_store_max}")
	private double storeMaxSize;

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public double getStoreMaxSize() {
		return storeMaxSize;
	}

	public void setStoreMaxSize(double storeMaxSize) {
		this.storeMaxSize = storeMaxSize;
	}

	@Override
	public String toString() {
		return "GlobalProperty [storePath=" + storePath + ", storeMaxSize=" + storeMaxSize + "]";
	}

	
}
