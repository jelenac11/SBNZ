package better.me.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import better.me.repositories.IAlarmRepository;

@Service
public class AlarmService {

	@Autowired
	private IAlarmRepository alarmRepo;
	
	public void deleteAll() {
		this.alarmRepo.deleteAll();
	}
}

