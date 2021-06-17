package better.me.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.events.Alarm;
import better.me.modelDB.AlarmDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.repositories.IAlarmRepository;

@Service
public class AlarmService {

	@Autowired
	private IAlarmRepository alarmRepo;
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	public void deleteAll() {
		this.alarmRepo.deleteAll();
	}

	public void save(Alarm alarm) {
		RegisteredUserDB rUser = registeredUserService.findByEmail(alarm.getUser().getEmail());
		AlarmDB adb = new AlarmDB(rUser, alarm.getMessage());
		this.alarmRepo.save(adb);
	}

	public Alarm getAlarm() {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RegisteredUserDB rUser = registeredUserService.findByEmail(current.getEmail());
		AlarmDB a = alarmRepo.findByUserId(rUser.getId());
		if (a != null) {
			return new Alarm(a);
		}
		return new Alarm(null, "");
	}
}

