package com.scrs.model;

public enum CourseCategory {
	C0(""),
	C1(""),
	C3(""),
	C4(""),
	C5("");
	
	private String desc;
	
	private CourseCategory(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
}
