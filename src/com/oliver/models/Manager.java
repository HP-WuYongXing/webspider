package com.oliver.models;

public class Manager {

	private int id;
	private String name;
	private String jobName;
	private String termDay;
	private String dismissDay;
	private String education;
	private int jobType;
	private int companyId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getTermDay() {
		return termDay;
	}
	public void setTermDay(String termDay) {
		this.termDay = termDay;
	}
	public String getDismissDay() {
		return dismissDay;
	}
	public void setDismissDay(String dismissDay) {
		this.dismissDay = dismissDay;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public int getJobType() {
		return jobType;
	}
	public void setJobType(int jobType) {
		this.jobType = jobType;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "Manager [id=" + id + ", name=" + name + ", jobName=" + jobName
				+ ", termDay=" + termDay + ", dismissDay=" + dismissDay
				+ ", education=" + education + ", jobType=" + jobType
				+ ", companyId=" + companyId + "]";
	}
	
	
	
	
}
