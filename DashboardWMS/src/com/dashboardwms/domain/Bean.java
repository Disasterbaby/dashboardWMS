package com.dashboardwms.domain;


public class Bean implements Comparable<Bean> {
	private String key;
	private Integer value;
	
	public Bean(String key, Integer value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public Bean(String key, Double value) {
		super();
		this.key = key;
		this.value = value.intValue();
	}

	public Bean() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setValue(Double value) {
		this.value = value.intValue();
	}

	public int compareTo(Bean o)
	{
	     return(o.value - value);
	}
}
