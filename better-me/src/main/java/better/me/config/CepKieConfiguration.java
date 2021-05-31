package better.me.config;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import better.me.util.MyLogger;

@Configuration
public class CepKieConfiguration {

	@Autowired
	private KieContainer kieContainer;
	
	@Bean(name = "cepReportSession")
	public KieSession cepReportSessionRealtimeClock() {
		KieSession kieSession = kieContainer.newKieSession("cepReportSession");
		kieSession.setGlobal("myLogger", new MyLogger());
		return kieSession;
	}
	
	@Bean(name = "cepLoginSession")
	public KieSession cepLoginSessionRealtimeClock() {
		KieSession kieSession = kieContainer.newKieSession("cepLoginSession");
		kieSession.setGlobal("myLogger", new MyLogger());
		return kieSession;
	}
	
	
}
