package better.me.cron;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import better.me.events.MidnightEvent;
import better.me.model.AdminReport;
import better.me.model.RegisteredUser;
import better.me.services.AlarmService;
import better.me.services.RegisteredUserService;
import better.me.services.ReportService;

@Controller
public class CronJobs {

	@Autowired
	@Qualifier(value = "cepReportSession")
	private KieSession cepReportSession;

	@Autowired
	@Qualifier(value = "cepMidnightSession")
	private KieSession cepMidnightSession;

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private AlarmService alarmService;

	@Autowired
	private RegisteredUserService registeredUserService;

	@Scheduled(cron = "0 0 0 1 1/1 *")
	public void fireReportRules() {
		AdminReport report = new AdminReport();
		report.setDate(new Date());
		FactHandle factHandle = cepReportSession.insert(report);
		cepReportSession.fireAllRules();
		reportService.save(report);
		cepReportSession.delete(factHandle);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void fireMidnightRules() {
		alarmService.deleteAll();
		List<RegisteredUser> users = registeredUserService.findAll().stream()
				.filter(user -> user.getWeeks().get(user.getWeeks().size() - 1).getGoal() != null)
				.collect(Collectors.toList());
		List<FactHandle> userFacts = users.stream().map(user -> cepMidnightSession.insert(user))
				.collect(Collectors.toList());
		users.forEach(user -> cepMidnightSession.insert(new MidnightEvent(user, user.getWeeks().size())));
		cepMidnightSession.fireAllRules();
		users.forEach(user -> registeredUserService.save(user));
		userFacts.forEach(user -> cepMidnightSession.delete(user));
	}

}
