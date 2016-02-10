package com.example.service;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton(name="SomeRandonName")
public class AppStartupService {
	
	private static final Logger LOG = Logger.getLogger(AppStartupService.class.getName());

	@PostConstruct
	public void init() {
		LOG.info("************** Hello World !!! *************. Ready to rock and roll");
	}
	
}
