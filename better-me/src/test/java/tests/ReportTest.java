package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.model.RegisteredUser;
import better.me.model.Report;
import better.me.model.Week;
import better.me.util.MyLogger;

public class ReportTest {
	
	private final MyLogger myLogger = new MyLogger();

	protected final String ksessionName = "session";

	private KieSession kieSession;

	@Before
	public void setUp() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("user-report").setFocus();
		kieSession.setGlobal("myLogger", myLogger);
	}
	
	@Test
	public void userReportRule_allValuesGiven_shouldGenerateUserReport() {
		RegisteredUser user = new RegisteredUser();
		
		Week w1 = new Week();
		w1.setGoal("LOSE_WEIGHT");
		Week w2 = new Week();
		w2.setGoal("MAINTAIN_WEIGHT");
		Week w3 = new Week();
		w3.setGoal("MAINTAIN_WEIGHT");
		Week w4 = new Week();
		w4.setGoal("MAINTAIN_WEIGHT");
		Week w5 = new Week();
		w5.setGoal("GAIN_WEIGHT");
		Week w6 = new Week();
		w6.setGoal("GAIN_WEIGHT");
		Week w7 = new Week();
		w7.setGoal("MAINTAIN_WEIGHT");
		Week w8 = new Week();
		w8.setGoal("LOSE_WEIGHT");
		
		ArrayList<Week> weeks1 = new ArrayList<Week>();
		weeks1.add(w1);
		weeks1.add(w2);
		weeks1.add(w3);
		weeks1.add(w4);
		weeks1.add(w5);
		weeks1.add(w6);
		weeks1.add(w7);
		weeks1.add(w8);
		
		user.setWeeks(weeks1);
		
		Report report = new Report();
		
		kieSession.insert(user);
		kieSession.insert(report);
    	
        int firedRules = kieSession.fireAllRules();
        
        assertEquals(1, firedRules);
        assertEquals(weeks1.size() - 1, report.getSuccessfulWeeks().size());
        assertEquals(true, report.getSuccessfulWeeks().get(0));
        assertEquals(true, report.getSuccessfulWeeks().get(1));
        assertEquals(true, report.getSuccessfulWeeks().get(2));
        assertEquals(false, report.getSuccessfulWeeks().get(3));
        assertEquals(false, report.getSuccessfulWeeks().get(4));
        assertEquals(true, report.getSuccessfulWeeks().get(5));
        assertEquals(false, report.getSuccessfulWeeks().get(6));
       
    	kieSession.dispose();
	}

}
