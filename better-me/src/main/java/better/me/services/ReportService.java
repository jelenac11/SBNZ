package better.me.services;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.AdminReportDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.model.AdminReport;
import better.me.model.RegisteredUser;
import better.me.model.Report;
import better.me.modelDB.AdminReportDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.repositories.IRegisteredUser;
import better.me.repositories.IReportRepository;

@Service
public class ReportService {
	
	@Autowired
	public IReportRepository reportRepository;
	
	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	@Autowired
	private KieSession kieSession;

	public void save(AdminReport report) {
		this.reportRepository.save(new AdminReportDB(report));
	}

	public AdminReportDTO getAdminReport() {
		return new AdminReportDTO(reportRepository.findAllByOrderByDateDesc().get(0));
	}

	public Report getUserReport() throws NotLoggedInException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null)
			throw new NotLoggedInException("You must login first. No logged in user found!");

		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null)
			throw new NotLoggedInException("Registered user must be logged in!");
		
		kieSession.getAgenda().getAgendaGroup("user-report").setFocus();
		Report report = new Report();
		kieSession.insert(new RegisteredUser(rUser));
		kieSession.insert(report);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
		return report;
	}

}
