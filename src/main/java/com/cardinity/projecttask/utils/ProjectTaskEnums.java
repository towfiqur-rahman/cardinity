package com.cardinity.projecttask.utils;

public enum ProjectTaskEnums {

	ADMIN("ADMIN"), USER("USER"), OPEN("open"), IN_PROGRESS("inProgress"), CLOSED("closed");

	private String value;

	ProjectTaskEnums(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
