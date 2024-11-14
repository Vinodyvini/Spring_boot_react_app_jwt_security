package com.example.demo123.Model;

public class LoginResponse {
	private String token;
	private boolean isAdmin;

	public LoginResponse(String token, boolean isAdmin) {
		this.token = token;
		this.isAdmin = isAdmin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
