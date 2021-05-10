package better.me.services;

import java.util.ArrayList;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.BodyInfoDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.exceptions.RequestException;
import better.me.facts.BodyTypeDTO;
import better.me.model.RegisteredUser;
import better.me.model.User;
import better.me.repositories.IRegisteredUser;
import better.me.util.MyLogger;

@Service
public class BodyTypeDeterminationService {

	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private IRegisteredUser registeredUserRepository;
	
	private static int ectoScore;
	private static int endoScore;
	private static int mesoScore;
	
	public String determine(BodyInfoDTO dto) throws NotLoggedInException, RequestException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) throw new NotLoggedInException("You must login first. No logged in user found!");
		
		RegisteredUser rUser = registeredUserRepository.findByEmail(current.getEmail());
		if (rUser == null) throw new NotLoggedInException("Registered user must be logged in!");
		
		getTypesScore(dto);
		KieSession kieSession = getBodyTypeKieSession();
		
		kieSession.setGlobal("ectoScore", ectoScore);
		kieSession.setGlobal("endooScore", endoScore);
		kieSession.setGlobal("mesoScore", mesoScore);
		kieSession.setGlobal("myLogger", new MyLogger());
	
		kieSession.insert(dto);
		BodyTypeDTO bodyType = new BodyTypeDTO();
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
        return kieContainer.newKieSession("bodyTypeSession");
    }
	
	private void getTypesScore(BodyInfoDTO dto) {
		ArrayList<String> endoAnswers = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;

			{
                add("PEAR");
                add("CARRY SOME EXTRA FAT");
                add("THE FINGERS DON'T TOUCH");
                add("BIG");
                add("LOOSE AROUND YOUR GLUTES");
                add("WIDER THAN YOUR HIPS");
                add("GAIN WEIGHT EASILY BUT HAVE A HARD TIME LOSING IT");
            }
        };
		ArrayList<String> ectoAnswers = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;

			{
                add("MOSTLY RECTANGLE");
                add("STAY SKINNY");
                add("THE FINGERS OVERLAP");
                add("SMALL");
                add("TIGHT AROUND YOUR GLUTES");
                add("NARROWER THAN YOUR HIPS");
                add("FIND IT DIFFICULT TO GAIN AND MAINTAIN WEIGHT");
            }
        };
        ArrayList<String> mesoAnswers = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;

			{
                add("HOURGLASS");
                add("STAY FIT AND MUSCULAR");
                add("THE FINGERS JUST TOUCH");
                add("AVERAGE");
                add("PERFECT AROUND YOUR GLUTES");
                add("SAME AS YOUR HIPS");
                add("HAVE AN EASY TIME LOSING OR GAINING WEIGHT");
            }
        };
        ectoScore = 0;
        endoScore = 0;
        mesoScore = 0;
        if (endoAnswers.contains(dto.getBodyLook()))
        	endoScore += 1;
        if (ectoAnswers.contains(dto.getBodyLook()))
        	ectoScore += 1;
        if (mesoAnswers.contains(dto.getBodyLook()))
        	mesoScore += 1;
        
        if (endoAnswers.contains(dto.getBodyTendation()))
        	endoScore += 1;
        if (ectoAnswers.contains(dto.getBodyTendation()))
        	ectoScore += 1;
        if (mesoAnswers.contains(dto.getBodyTendation()))
        	mesoScore += 1;
        
        if (endoAnswers.contains(dto.getForearms()))
        	endoScore += 1;
        if (ectoAnswers.contains(dto.getForearms()))
        	ectoScore += 1;
        if (mesoAnswers.contains(dto.getForearms()))
        	mesoScore += 1;
        
        if (endoAnswers.contains(dto.getEncircleHandWrist()))
        	endoScore += 1;
        if (ectoAnswers.contains(dto.getEncircleHandWrist()))
        	ectoScore += 1;
        if (mesoAnswers.contains(dto.getEncircleHandWrist()))
        	mesoScore += 1;
        
        if (endoAnswers.contains(dto.getJeans()))
        	endoScore += 1;
        if (ectoAnswers.contains(dto.getJeans()))
        	ectoScore += 1;
        if (mesoAnswers.contains(dto.getJeans()))
        	mesoScore += 1;
        
        if (endoAnswers.contains(dto.getShoulders()))
        	endoScore += 1;
        if (ectoAnswers.contains(dto.getShoulders()))
        	ectoScore += 1;
        if (mesoAnswers.contains(dto.getShoulders()))
        	mesoScore += 1;
        
        if (endoAnswers.contains(dto.getWeightTendation()))
        	endoScore += 1;
        if (ectoAnswers.contains(dto.getWeightTendation()))
        	ectoScore += 1;
        if (mesoAnswers.contains(dto.getWeightTendation()))
        	mesoScore += 1;
        
        if (ectoScore * endoScore * mesoScore != 0 ) {
        	ectoScore = 0;
            endoScore = 0;
            mesoScore = 0;
        } 
	}

}
