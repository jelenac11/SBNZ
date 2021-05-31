package better.me.cron;

import java.util.Date;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import better.me.model.AdminReport;
import better.me.services.ReportService;

@Controller
public class CronJobs {

	@Autowired
	@Qualifier(value = "cepReportSession")
	private KieSession cepReportSession;
	
	@Autowired
	private ReportService reportService;
	
	@Scheduled(cron="0 0 0 1 1/1 *")
	public void fireReportRules() {
		AdminReport report = new AdminReport();
		report.setDate(new Date());
		FactHandle factHandle = cepReportSession.insert(report);
		cepReportSession.fireAllRules();
		reportService.save(report);
		cepReportSession.delete(factHandle);
	}
	
	
}
