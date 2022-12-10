package cs.skuniv.HCH.dto;

public class Note {
	
	private int noteid;
	private String major;
	private String middle;
	private String minor;
	
	public Note(int noteid, String major, String middle, String minor) {
		this.noteid = noteid;
		this.major = major;
		this.middle = middle;
		this.minor = minor;
	}

	public int getNoteid() {
		return noteid;
	}

	public void setNoteid(int noteid) {
		this.noteid = noteid;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}	

}
