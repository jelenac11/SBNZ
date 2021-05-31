package better.me.config;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CepKieConfiguration {

	@Autowired
	private KieContainer kieContainer;
	
	@Bean(name = "cepReportSession")
	public KieSession cepConfigKsessionRealtimeClock() {
		return kieContainer.newKieSession("cepReportSession");
	}
	
	
}
