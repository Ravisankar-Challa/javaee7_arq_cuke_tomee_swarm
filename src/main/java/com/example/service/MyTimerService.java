package com.example.service;

import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class MyTimerService {
	private static final Logger LOG = Logger.getLogger(AppStartupService.class.getName());
	
	@Schedule(second="*/15", minute="*", hour="*", persistent=false, info="Some Basic Info About This Timer")
	public void run() {
		LOG.info("############################ Timer is running ###########################");
	}
	
}
