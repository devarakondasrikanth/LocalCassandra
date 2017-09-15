package com.srikanth.springboot.db;


import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.stereotype.Component;


@Table(value = "record")
@Component
public class Record{
	/**
	 * 
	 */
	@PrimaryKey
    private String attribute_name;

    private String attribute_displayName;
    
    private String attribute_value;
    
    
    public Record() {
		
	}
    

	public Record(String attribute_name, String attribute_displayName, String attribute_value) {
		super();
		this.attribute_name = attribute_name;
		this.attribute_displayName = attribute_displayName;
		this.attribute_value = attribute_value;
	}

	public String getAttribute_name() {
		return attribute_name;
	}

	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}

	public String getAttribute_displayName() {
		return attribute_displayName;
	}

	public void setAttribute_displayName(String attribute_displayName) {
		this.attribute_displayName = attribute_displayName;
	}

	public String getAttribute_value() {
		return attribute_value;
	}

	public void setAttribute_value(String attribute_value) {
		this.attribute_value = attribute_value;
	}
    
    
	
	
}
