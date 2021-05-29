package com.utils;

import com.entity.ReminderEntity;

public class ReminderTask implements Runnable{
	private ReminderEntity reminder;

	@Override
	public void run() {
		String msg = reminder.getTitle()+"\r\n"+reminder.getAddress()+"\r\n"+
				reminder.getStart()+"\r\n"+reminder.getEnd();
		EmailUtil.sendRemindEmail(reminder.getAccount(), msg);
	}

	public ReminderEntity getReminder() {
		return reminder;
	}

	public void setReminder(ReminderEntity reminder) {
		this.reminder = reminder;
	}

}
