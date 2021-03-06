package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="MEMBER_TABLE")
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	@SequenceGenerator(name="SEQ_GEN", allocationSize=1, initialValue=1, sequenceName="MEM_SEQ_GEN")
	private Long id;
	
	@NotNull
	@Size(max=32, message="Name shouldn't be more than 32 characters")
	@Column(name="MEMBER_NAME", length=32, nullable=false)
	private String name;
	
	@NotNull
	@Column(name="MEMBER_AGE")
	private int age;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
}
