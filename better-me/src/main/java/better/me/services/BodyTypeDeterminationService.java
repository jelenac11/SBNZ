package better.me.services;


import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.BodyInfoDTO;
import better.me.enums.BodyType;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.model.Answers;
import better.me.model.BodyTypeFact;
import better.me.model.Constants;
import better.me.model.UserAnswers;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.repositories.IRegisteredUser;
import better.me.util.MyLogger;

@Service
public class BodyTypeDeterminationService {

	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private IRegisteredUser registeredUserRepository;

	public String determine(BodyInfoDTO dto) throws NotLoggedInException, RequestException {
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUserDB rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		KieSession kieSession = getBodyTypeKieSession();
		kieSession.getAgenda().getAgendaGroup("body-type").setFocus();
		
		kieSession.setGlobal("myLogger", new MyLogger());
	
		Map<String, String> userAnswers = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
		{
	        put("shoulders", dto.getShoulders());
	        put("jeans", dto.getJeans());
	        put("forearms", dto.getForearms());
	        put("bodyTendation", dto.getBodyTendation());
	        put("bodyLook", dto.getBodyLook());
	        put("weightTendation", dto.getWeightTendation());
	        put("encircleHandWrist", dto.getEncircleHandWrist());
	    }};
	    
	    kieSession.insert(new UserAnswers(userAnswers));
		kieSession.insert(new Answers(BodyType.ECTOMORPH, Constants.ectoAnswers));
		kieSession.insert(new Answers(BodyType.ENDOMORPH, Constants.endoAnswers));
		kieSession.insert(new Answers(BodyType.MESOMORPH, Constants.mesoAnswers));
		BodyTypeFact bodyType = new BodyTypeFact();
		kieSession.insert(bodyType);
		
		kieSession.fireAllRules();
		kieSession.dispose();
		
		if (bodyType.getBodyType() == null) {
			throw new RequestException("Answers you entered don't belong to any body type!");
		}
		
		rUser.setBodyType(bodyType.getBodyType());
		registeredUserRepository.save(rUser);
		
		return bodyType.getBodyType().toString();
	}

	private KieSession getBodyTypeKieSession() {
        return kieContainer.newKieSession("session");
    }

}
