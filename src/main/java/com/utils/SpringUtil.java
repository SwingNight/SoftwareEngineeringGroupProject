package com.utils;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;

import org.springframework.context.ApplicationContextAware;

import org.springframework.stereotype.Component;

@Component

public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		if (SpringUtil.applicationContext == null) {

			SpringUtil.applicationContext = applicationContext;

		}

	}

//getapplicationContext

	public static ApplicationContext getApplicationContext() {

		return applicationContext;

	}

//use name get Bean.

	public static Object getBean(String name) {

		return getApplicationContext().getBean(name);

	}

//sue class get Bean.

	public static <T> T getBean(Class<T> clazz) {

		return getApplicationContext().getBean(clazz);

	}

//use name,andClazz return the Bean

	public static <T> T getBean(String name, Class<T> clazz) {

		return getApplicationContext().getBean(name, clazz);

	}

}
