package better.me.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import better.me.model.AdminReport;
import better.me.modelDB.AdminReportDB;
import better.me.repositories.IReportRepository;

@Service
public class ReportService {
	
	@Autowired
	public IReportRepository reportRepository;

	public void save(AdminReport report) {
		this.reportRepository.save(new AdminReportDB(report));
	}

}
