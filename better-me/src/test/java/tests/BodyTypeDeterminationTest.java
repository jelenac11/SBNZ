package tests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.dto.BodyInfoDTO;
import better.me.enums.BodyType;
import better.me.model.Answers;
import better.me.model.BodyTypeFact;
import better.me.model.Constants;
import better.me.model.UserAnswers;
import better.me.util.MyLogger;

public class BodyTypeDeterminationTest {

    private final MyLogger myLogger = new MyLogger();
    
    protected final String ksessionName = "session";
    
    private KieSession kieSession;
    
    @Before
	public void setUp() {
    	KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("body-type").setFocus();
	}
    
    @Test
    public void bodyTypeRule_answersForEctomorphGiven_shouldDetermineEctomorphBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("NARROWER THAN YOUR HIPS", "TIGHT AROUND YOUR GLUTES", "SMALL", "STAY SKINNY", "MOSTLY RECTANGLE", "FIND IT DIFFICULT TO GAIN AND MAINTAIN WEIGHT", "THE FINGERS OVERLAP");
        BodyTypeFact bodyType = new BodyTypeFact();
       
		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(bodyType);
		kieSession.insert(new UserAnswers(convertToMap(infoDTO)));
		kieSession.insert(new Answers(BodyType.ECTOMORPH, Constants.ectoAnswers));
		kieSession.insert(new Answers(BodyType.ENDOMORPH, Constants.endoAnswers));
		kieSession.insert(new Answers(BodyType.MESOMORPH, Constants.mesoAnswers));
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(BodyType.ECTOMORPH, bodyType.getBodyType());
    }
    
    @Test
    public void bodyTypeRule_answersForEndomorphGiven_shouldDetermineEndomorphBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("WIDER THAN YOUR HIPS", "LOOSE AROUND YOUR GLUTES", "BIG", "CARRY SOME EXTRA FAT", "PEAR", "GAIN WEIGHT EASILY BUT HAVE A HARD TIME LOSING IT", "THE FINGERS DON'T TOUCH");
        BodyTypeFact bodyType = new BodyTypeFact();

		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(bodyType);
		kieSession.insert(new UserAnswers(convertToMap(infoDTO)));
		kieSession.insert(new Answers(BodyType.ECTOMORPH, Constants.ectoAnswers));
		kieSession.insert(new Answers(BodyType.ENDOMORPH, Constants.endoAnswers));
		kieSession.insert(new Answers(BodyType.MESOMORPH, Constants.mesoAnswers));
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(BodyType.ENDOMORPH, bodyType.getBodyType());
    }
    
    @Test
    public void bodyTypeRule_answersForMesomorphGiven_shouldDetermineMesomorphBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("SAME AS YOUR HIPS", "PERFECT AROUND YOUR GLUTES", "AVERAGE", "STAY FIT AND MUSCULAR", "HOURGLASS", "HAVE AN EASY TIME LOSING OR GAINING WEIGHT", "THE FINGERS JUST TOUCH");
        BodyTypeFact bodyType = new BodyTypeFact();
        		
		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(bodyType);
		kieSession.insert(new UserAnswers(convertToMap(infoDTO)));
		kieSession.insert(new Answers(BodyType.ECTOMORPH, Constants.ectoAnswers));
		kieSession.insert(new Answers(BodyType.ENDOMORPH, Constants.endoAnswers));
		kieSession.insert(new Answers(BodyType.MESOMORPH, Constants.mesoAnswers));
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(BodyType.MESOMORPH, bodyType.getBodyType());
    }
    
    @Test
    public void bodyTypeRule_randomAnswersGiven_shouldDetermineNoBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("SAME AS YOUR HIPS", "LOOSE AROUND YOUR GLUTES", "BIG", "STAY SKINNY", "MOSTLY RECTANGLE", "FIND IT DIFFICULT TO GAIN AND MAINTAIN WEIGHT", "THE FINGERS OVERLAP");
        BodyTypeFact bodyType = new BodyTypeFact();
        
		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(bodyType);
		kieSession.insert(new UserAnswers(convertToMap(infoDTO)));
		kieSession.insert(new Answers(BodyType.ECTOMORPH, Constants.ectoAnswers));
		kieSession.insert(new Answers(BodyType.ENDOMORPH, Constants.endoAnswers));
		kieSession.insert(new Answers(BodyType.MESOMORPH, Constants.mesoAnswers));
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(0, firedRules);
        assertEquals(null, bodyType.getBodyType());
    }
    
    private HashMap<String, String> convertToMap(BodyInfoDTO dto) {
    	return new HashMap<String, String>() {
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
    }
    
}
