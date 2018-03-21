package com.hpen.value;

import java.util.Properties;

public class Version {
	private static Version instance = new Version();
	public static Version getInstance() {
		return instance;
	}
	private Version() {}
	
	public static final String version = "hpen.version";
	public static final String appName = "hpen.appname";
	
	private Properties values = new Properties();
	public void setValues(Properties values) {
		this.values = values;
	}
	public Properties getValues() {
		return values;
	}
	public String getVersion() {
		return values.getProperty(version);
	}
	public String getAppName() {
		return values.getProperty(appName);
	}
	
	@Override
	public String toString() {
		return getAppName() + "["+getVersion()+"]";
	}
}










