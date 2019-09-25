package com.vjkratky.helpdesk.api.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private T genericEntity;

	private List<String> errors;

	public T getGenericEntity() {
		return genericEntity;
	}

	public void setGenericEntity(T genericEntity) {
		this.genericEntity = genericEntity;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			return this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
