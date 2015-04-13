package com.oliver.models;

public class Picture {
	private int id;
	private int contentId;
	private String imagePath;
	private int orderNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String toString() {
		return "Picture [id=" + id + ", contentId=" + contentId
				+ ", imagePath=" + imagePath + ", orderNumber=" + orderNumber
				+ "]";
	}
	
	

}
