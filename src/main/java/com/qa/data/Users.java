package com.qa.data;
//POJO -- > Plain old java Object
//POJO to JSON conversion is called Marshelling. Reverse is called Un marshelling
public class Users {
	
	String name;
	String job;
	String id;
	String createdAt;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Users() {
		
	}
	
public Users(String name, String job) {
	this.name=name;
	this.job=job;
		
	}

//getters and setters

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getJob() {
	return job;
}

public void setJob(String job) {
	this.job = job;
}


	
	
	

}
