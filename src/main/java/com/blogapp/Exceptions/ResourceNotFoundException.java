package com.blogapp.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{ //unchecked excepiton
	
	String resourceName;
	String fieldNamel;
	String fieldValues;
	long fieldValue;
	String field_value; 
	
	
	public ResourceNotFoundException(String resourceName, String fieldNamel, long fieldValue) {
		super(String.format("%s not found with this %s : %s ", resourceName,fieldNamel,fieldValue));
		this.resourceName = resourceName;
		this.fieldNamel = fieldNamel;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldNamel, String field_value) {
		super(String.format("%s not found with this %s : %s ", resourceName,fieldNamel,field_value));
		this.resourceName = resourceName;
		this.fieldNamel = fieldNamel;
		this.field_value = field_value;
	}
}
