package com.cardinity.projecttask.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

public class UpdateTaskDto {

	@NotNull
	private String description;

	@NotNull
	private String status;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Temporal(TemporalType.DATE)
	private Date dueDate;

}
