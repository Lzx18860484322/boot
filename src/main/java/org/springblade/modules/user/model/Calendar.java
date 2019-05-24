package org.springblade.modules.user.model;


import org.springblade.core.mp.base.BaseEntity;

import java.io.Serializable;
import java.util.Objects;

public class Calendar implements Serializable {
	private String id;
	private String title;
	private String start;
	private String end;
	private Boolean allDay;
	private String color;
	private String url;

	private String orderId;
	private String accountId;
	private String type;

	public Calendar() {
	}


	public Calendar(String title, String starttime, String endtime, Boolean allday, String color, String orderId, String accountId, String type) {
		this.title = title;
		this.start = starttime;
		this.end = endtime;
		this.allDay = allday;
		this.color = color;
		this.accountId = accountId;
		this.type = type;
		this.orderId = orderId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Calendar calendar = (Calendar) o;
		return id == calendar.id &&
			start == calendar.start &&
			allDay == calendar.allDay &&
			type == calendar.type &&
			accountId == calendar.accountId &&
			orderId == calendar.orderId &&
			url == calendar.url &&
			Objects.equals(title, calendar.title) &&
			Objects.equals(end, calendar.end) &&
			Objects.equals(color, calendar.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, orderId, accountId, type, title, start, end, allDay, color, url);
	}

	public void edit(String event, String starttime, String endtime, Boolean isalldays) {
		this.title = event;
		this.start = starttime;
		this.end = endtime;
		this.allDay = isalldays;
	}
}

