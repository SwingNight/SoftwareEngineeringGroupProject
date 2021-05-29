
package com.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ReminderEntity;
import com.service.ReminderService;
import com.utils.EmailUtil;
import com.utils.ReminderTask;


/**
 * Calendar Event Correlation
 */
@RequestMapping("event")
@RestController
public class ReminderController{
	
	@Autowired
	private ReminderService reminderService;

	/**
	 * add
	 */
	@PostMapping(value = "/add")
	public Integer add(ReminderEntity reminder) {
		ReminderEntity en = reminderService.selectOne(new EntityWrapper<ReminderEntity>().eq("title", reminder.getTitle()));
		if(en!=null) {
			return 10010;
		}

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); 
		//front end time params has nouse data ,need format before save=========
		String startnew = reminder.getStart().replace("T", " ");
		try {
			Date startDate = sdf.parse(startnew);
			startnew = sdf.format(startDate);
			reminder.setStart(startnew);
		} catch (ParseException e) {
			try {
				//another formatter try again
				Date startDate2 = sdf2.parse(startnew);
				startnew = sdf2.format(startDate2);
				reminder.setStart(startnew);
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
		}
		String endnew = reminder.getEnd().replace("T", " ");
		try {
			Date endDate = sdf.parse(endnew);
			endnew = sdf.format(endDate);
			reminder.setEnd(endnew);
		} catch (ParseException e) {
			try {
				//another formatter try again
				Date endDate2 = sdf2.parse(endnew);
				endnew = sdf2.format(endDate2);
				reminder.setEnd(endnew);
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
		}	
			//format time params end===============
		Date remindtime = new Date();//default
		try {
			remindtime = sdf.parse(reminder.getStart());
		} catch (ParseException e) {
			try {
				remindtime = sdf2.parse(reminder.getStart());
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
		}
		remindtime = new Date(remindtime.getTime()-reminder.getRemind()*60*60*1000) ;//按start提前remind小时提醒
		reminder.setRemindertime(sdf.format(remindtime));
		reminderService.insert(reminder);
		setReminder(reminder);
		return 10000;
	}
	/**
	 * delete
	 */
	@PostMapping(value = "/delete")
	public Integer delete(String event){
		JSONObject json = (JSONObject) JSONObject.parse(event);
		ReminderEntity e = reminderService.selectOne(new EntityWrapper<ReminderEntity>().eq("title", json.getString("title")));
		if(e==null) {
			return 10002;
		}
		reminderService.deleteById(e.getId());
	    	 return 1001;
    }
	/**
	 * Check all events of the specified account
	 */
	@PostMapping(value = "/getAll")
	public List<ReminderEntity> getAll(String account){
		List<ReminderEntity> elist = reminderService.selectList(new EntityWrapper<ReminderEntity>().eq("account", account));
	    	 return elist;
    }
	/**
	 * Look up a recent incident
	 */
	@RequestMapping(value = "/getLatest")
	public JSONObject getLatest(String account){
		//System.out.println(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String nowstr = sdf.format(new Date());
		Wrapper<ReminderEntity> w = new EntityWrapper<ReminderEntity>().eq("account", account);
		w.gt("start", nowstr);
		w.orderBy("start");
		w.last("limit 1");
		ReminderEntity e = reminderService.selectOne(w);
		JSONObject json = new JSONObject();
		if(e!=null) {
			json.put("newDate", e.getStart());
		}
	    	return json;
    }
	public void setReminder(ReminderEntity reminderEntity){
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.initialize();
			ReminderTask task = new ReminderTask();
			task.setReminder(reminderEntity);
			taskScheduler.schedule(task,reminderEntity.getRemindertimeDate());
	}
	
	/**
	 * get events of the specified account and specified date
	 */
	@RequestMapping(value = "/getEventData")
	public List<ReminderEntity> getEventData(String account,String date){
		Wrapper<ReminderEntity> w =new EntityWrapper<ReminderEntity>();
		w.eq("account", account);
		w.like("start", date);
		List<ReminderEntity> elist = reminderService.selectList(w);
	    	 return elist;
    }
	

	/**
	 * set event state to finished of the specified details
	 */
	@RequestMapping(value = "/addFinish")
	public int addFinish(String account,String title,String place,String start,String end,String finish){
		Wrapper<ReminderEntity> w =new EntityWrapper<ReminderEntity>();
		w.eq("account", account);
		w.eq("start", start);
		w.eq("end", end);
		w.eq("title",title);
		
		boolean result = reminderService.updateForSet("finish='"+finish+"'", w);
		return result?10:11;
    }
	
	/**
	 * get events of the specified account and specified title
	 */
	@RequestMapping(value = "/getEventBySearch")
	public List<ReminderEntity> getEventBySearch(String account,String title){
		Wrapper<ReminderEntity> w =new EntityWrapper<ReminderEntity>();
		w.eq("account", account);
		w.like("title", title);
		List<ReminderEntity> elist = reminderService.selectList(w);
	    	 return elist;
    }
	@RequestMapping(value = "/sendEmail")
	public JSONObject sendEmail(String account,String email,String content){
		JSONObject json = new JSONObject();
		json.put("code",200);
		try {
			EmailUtil.sendRemindEmail(email, content);
		}catch (Exception e) {
			json.put("msg","Email send failed");
		}
		json.put("msg","Email send successfully");
		return json;
    }
}
