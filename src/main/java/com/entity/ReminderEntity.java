package com.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/** 
 * user
 */
@TableName("reminder")
public class ReminderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/**
	 * The user account
	 */
	private String title;
	
	/**
	 * password
	 */
	private String address;
	private String start ;
	private String end ;
	private String grade;
	private String remindertime;
	private String account;//email
	private int remind;//Remind the user to send a reminder before the event starts
	private Boolean finish;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStart() {
		return start;
	}

//	public String getStartString() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		return sdf.format(this.start);
//	}
	public void setStart(String start) {
		this.start = start;
	}

//	public String getEndString() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		return sdf.format(this.end);
//	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}


	public Date getRemindertimeDate() {
//			return new Date(System.currentTimeMillis() + 5*60*1000);//Remind after 5 minutes
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			if(this.remindertime !=null)
				return sdf.parse(this.remindertime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getRemindertime() {
		return remindertime;
	}
	public void setRemindertime(String remindertime) {
		this.remindertime = remindertime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getRemind() {
		return remind;
	}

	public void setRemind(int remind) {
		this.remind = remind;
	}

	public Boolean getFinish() {
		return finish;
	}

	public void setFinish(Boolean finish) {
		this.finish = finish;
	}


}
