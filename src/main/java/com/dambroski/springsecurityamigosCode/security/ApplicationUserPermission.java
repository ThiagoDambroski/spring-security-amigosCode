package com.dambroski.springsecurityamigosCode.security;

public enum ApplicationUserPermission {
	STUDENT_READ("student:read"),STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),COURSE_WRITE("course:write");
	
	
	public String getPermission() {
		return permission;
	}

	private final String permission;

	private ApplicationUserPermission(String permission) {
		this.permission = permission;
	}
	
}
