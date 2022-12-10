package cs.skuniv.HCH.dto;

public class Nation {
	
	private int nationid;
	private String country;
	private String group;
	
	public Nation(int nationid, String country, String group) {
		this.nationid = nationid;
		this.country = country;
		this.group = group;
	}

	public int getNationid() {
		return nationid;
	}

	public void setNationid(int nationid) {
		this.nationid = nationid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
