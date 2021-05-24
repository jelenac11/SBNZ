package better.me.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import better.me.util.MyLogger;

@Configuration
public class KieConfiguration {
	
	@Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10_000);
        return kContainer;
    }

	@Bean
	@SessionScope
	public KieSession kieSession() {
		KieSession kieSession = this.kieContainer().newKieSession("session");
		kieSession.setGlobal("myLogger", new MyLogger());
		return kieSession;
	}

}
