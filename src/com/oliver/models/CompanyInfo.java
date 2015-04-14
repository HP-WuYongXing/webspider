package com.oliver.models;

import java.util.List;

public class CompanyInfo {

	private int id;
	private String abbreviation;
	private String stockCode;
	private String fullName;
	private String startDate;
	private String business;
	private String conception;
	private String companyAddr;
	private String representative;
	private String enquiryAgency;
	private float regAssets;
	private String regAddress;
	private String officeAddress;
	private String companyFax;
	private String email;
	private String website;
	private String contacts;
	private String zipCode;
	private String businessScope;
	private String companyInfo;
	private StockInfo stockInfo;
	private List<Manager> managerList;
	private List<Participation> participationList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getConception() {
		return conception;
	}
	public void setConception(String conception) {
		this.conception = conception;
	}
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getEnquiryAgency() {
		return enquiryAgency;
	}
	public void setEnquiryAgency(String enquiryAgency) {
		this.enquiryAgency = enquiryAgency;
	}
	public float getRegAssets() {
		return regAssets;
	}
	public void setRegAssets(float regAssets) {
		this.regAssets = regAssets;
	}
	public String getRegAddress() {
		return regAddress;
	}
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getCompanyFax() {
		return companyFax;
	}
	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	public StockInfo getStockInfo() {
		return stockInfo;
	}
	public void setStockInfo(StockInfo stockInfo) {
		this.stockInfo = stockInfo;
	}
	public List<Manager> getManagerList() {
		return managerList;
	}
	public void setManagerList(List<Manager> managerList) {
		this.managerList = managerList;
	}
	public List<Participation> getParticipationList() {
		return participationList;
	}
	public void setParticipationList(List<Participation> participationList) {
		this.participationList = participationList;
	}
	
	@Override
	public String toString() {
		return "CompanyInfo [id=" + id + ", abbreviation=" + abbreviation
				+ ", stockCode=" + stockCode + ", fullName=" + fullName
				+ ", startDate=" + startDate + ", business=" + business
				+ ", conception=" + conception + ", companyAddr=" + companyAddr
				+ ", representative=" + representative + ", enquiryAgency="
				+ enquiryAgency + ", regAssets=" + regAssets + ", regAddress="
				+ regAddress + ", officeAddress=" + officeAddress
				+ ", companyFax=" + companyFax + ", email=" + email
				+ ", website=" + website + ", contacts=" + contacts
				+ ", zipCode=" + zipCode + ", businessScope=" + businessScope
				+ ", companyInfo=" + companyInfo + ", stockInfo=" + stockInfo
				+ ", managerList=" + managerList + ", participationList="
				+ participationList + "]";
	}
	
	
	
	
}
