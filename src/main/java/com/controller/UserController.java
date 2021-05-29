
package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.UserEntity;
import com.service.UserService;
import com.utils.EmailUtil;

import cn.hutool.json.JSONObject;

/**
 * Login related
 */
@RequestMapping("users")
@RestController
public class UserController{
	
	@Autowired
	private UserService userService;

	/**
	 * Login
	 */
	@PostMapping(value = "/login")
	public Integer login(String email, String password) {
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("email", email));
		if(user==null) {
			return 10010;
		}else if(!user.getPassword().equals(password)) {
			return 10001;
		}
		return 10000;
	}
	/**
	 * EmailCheck
	 */
	@PostMapping(value = "/emailCheck")
	public Integer emailCheck(String Email) {
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("email", Email));
		if(user==null) {
			return 10010;
		}
		return 10000;
	}
	/**
	 * registered
	 */
	@PostMapping(value = "/register")
	public Integer register(String email){
		UserEntity tuser = userService.selectOne(new EntityWrapper<UserEntity>().eq("email", email));
		if(tuser!=null && tuser.getVerified()==1) {
			return 10010;
		}
		UserEntity user = new UserEntity();
		user.setEmail(email);
		//sent email
		 boolean r = EmailUtil.sendEmail(user);
	     if(r) {
	    	 userService.insert(user);
	    	 return 10000;
	     } else {
	    	 return 10010;
	     }
    }
	/**
	 * registered
	 */
	@PostMapping(value = "/registerCheck")
	public Integer registerCheck(String email,String password,String code){
		UserEntity tuser = userService.selectOne(new EntityWrapper<UserEntity>().eq("email", email));
    	if(tuser==null || !tuser.getCode().equals(code)) {
    		return 10001;
    	}
    	tuser.setPassword(password);
    	tuser.setCode(null);
    	tuser.setVerified(1);
        boolean r = userService.update(tuser,new EntityWrapper<UserEntity>().eq("email", email));
        if(r) return 10000; else return 10010;
        
    }
	
	/**
     * Password reset
     */
	@RequestMapping(value = "/resetPass")
    public Integer resetPass(String email){
    	UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("email", email));
    	if(user==null) {
    		return 10010;
    	}
    	user.setPassword(null);
    	user.setVerified(0);
    	boolean r = EmailUtil.sendEmail(user);
    	if(r) {
	    	 userService.update(user,new EntityWrapper<UserEntity>().eq("email", email));
	    	 return 10000;
	     } else {
	    	 return 10001;
	     }
    }
	
	/**
     * Complete password reset
     */
	@RequestMapping(value = "/finishResetPass")
    public Integer finishResetPass(String email,String password,String code){
		return registerCheck(email,password,code);
    }
	
	
	/**
     * Loading user information
     */
	@RequestMapping(value = "/getUser")
    public JSONObject getUser(String account){
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("email", account));
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("acc", user.getEmail());
		json.put("name", user.getName());
		json.put("birthday", user.getBirthday());
		json.put("cons", user.getConstellation());
		return json;
    }
	/**
	 * Modify personal information
	 */
	@PostMapping(value = "/update")
	public Integer update(String account,String birthday,String name,String cons){
		UserEntity tuser = userService.selectOne(new EntityWrapper<UserEntity>().eq("email", account));
		if(tuser==null) {
			return 0;
		}
		tuser.setBirthday(birthday);
		tuser.setName(name);
		tuser.setConstellation(cons);
		boolean r = userService.update(tuser, new EntityWrapper<UserEntity>().eq("email", account));
		 return r?0:1;
    }
}
