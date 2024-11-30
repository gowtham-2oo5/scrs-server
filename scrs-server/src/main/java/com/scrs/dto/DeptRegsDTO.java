package com.scrs.dto;

public class DeptRegsDTO {

	private String name;
	private String sn;
	private String hodId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getHodId() {
		return hodId;
	}

	public void setHodId(String hodId) {
		this.hodId = hodId;
	}

	@Override
	public String toString() {
		return "DeptRegsDTO [name=" + name + ", sn=" + sn + ", hodId=" + hodId + ", getName()=" + getName()
				+ ", getSn()=" + getSn() + ", getHodId()=" + getHodId() + "]";
	}

}
