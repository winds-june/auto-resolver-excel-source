package com.winds.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :WindsJune/博客园：WindsJune
 * @version :1.1.0
 * @date :2018年9月20日 下午6:05:57
 * @comments :
 */

public class Student implements Cloneable{
	
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 学号
	 */
	private String no;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 学院
	 */
	private String age;
	/**
	 * 成绩
	 */
	private float score;

	/**
	 * 出生日期
	 */
	private Date borthDate;

	/**
	 * 地址
	 */
	private String adress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Date getBorthDate() {
		return borthDate;
	}

	public void setBorthDate(Date borthDate) {
		this.borthDate = borthDate;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", no='" + no + '\'' +
				", name='" + name + '\'' +
				", age='" + age + '\'' +
				", score=" + score +
				", borthDate=" + borthDate +
				", adress='" + adress + '\'' +
				'}';
	}
}
