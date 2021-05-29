package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ReminderEntity;
import com.service.ReminderService;
@Component
public class ReminderScheduler implements ApplicationRunner {
	@Autowired
	private ReminderService reminderService;
    private ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	
	public ReminderScheduler() {
		taskScheduler.initialize();
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		EntityWrapper<ReminderEntity> wrapper = new EntityWrapper<ReminderEntity>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String now = sdf.format(new Date());
		wrapper.ge("remindertime", now);
		List<ReminderEntity> reminders = reminderService.selectList(wrapper);
		for (ReminderEntity reminderEntity : reminders) {
			ReminderTask task = new ReminderTask();
			task.setReminder(reminderEntity);
			taskScheduler.schedule(task,reminderEntity.getRemindertimeDate());
		}
	}
}


