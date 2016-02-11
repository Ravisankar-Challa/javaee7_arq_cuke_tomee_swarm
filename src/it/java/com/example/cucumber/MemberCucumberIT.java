package com.example.cucumber;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;

import cucumber.runtime.arquillian.CukeSpace;
import cucumber.runtime.arquillian.api.Features;
import cucumber.runtime.arquillian.api.Glues;

@Glues({MemberCucumberSteps.class})
@Features({"features/1_add_member.feature",
		   "features/2_update_member.feature",
		   "features/3_find_member.feature",
		   "features/4_find_all_members.feature",
		   "features/5_delete_member.feature"
		   })
@RunWith(CukeSpace.class)
public class MemberCucumberIT {

	@Deployment
	public static Archive<WebArchive> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "membertest.war")
						 .addPackages(true, "com.example")
						 .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
						 .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
						 .addAsLibraries(Maven.resolver().resolve("net.javacrumbs.json-unit:json-unit:1.9.0").withTransitivity().asFile());
	}	
}
	
