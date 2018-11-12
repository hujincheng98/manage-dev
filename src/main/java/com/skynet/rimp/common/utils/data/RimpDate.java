package com.skynet.rimp.common.utils.data;

import java.util.Date;

public class RimpDate {
	private int index;
	private Date date;
	
	public RimpDate(int index, Date date) {
		super();
		this.index = index;
		this.date = date;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
