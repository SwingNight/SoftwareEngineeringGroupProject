
package com.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.ReminderDao;
import com.entity.ReminderEntity;
import com.service.ReminderService;


/**
 * Users of the system
 */
@Service("reminderService")
public class ReminderServiceImpl extends ServiceImpl<ReminderDao, ReminderEntity> implements ReminderService {


}
