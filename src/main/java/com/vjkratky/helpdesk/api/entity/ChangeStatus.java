package com.vjkratky.helpdesk.api.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vjkratky.helpdesk.api.enums.StatusEnum;

@Document
public class ChangeStatus {

	@Id
	private String id;

	@DBRef
	private Ticket ticked;

	@DBRef
	private User userChange;

	private Date dateChangeStatus;

	private StatusEnum status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Ticket getTicked() {
		return ticked;
	}

	public void setTicked(Ticket ticked) {
		this.ticked = ticked;
	}

	public User getUserChange() {
		return userChange;
	}

	public void setUserChange(User userChange) {
		this.userChange = userChange;
	}

	public Date getDateChangeStatus() {
		return dateChangeStatus;
	}

	public void setDateChangeStatus(Date dateChangeStatus) {
		this.dateChangeStatus = dateChangeStatus;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
