package cs.skuniv.HCH.request;

import java.sql.Date;

public class MemberRegisterRequest {
	
	private String id;
	private String password;
	private String confirmPassword;
	private String email;
	private String name;
	private Date birth;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	/* 비밀번호 일치 확인 */
	public boolean isPasswordEqualToConfirmPassword() {
		return password.equals(confirmPassword);
	}

}
