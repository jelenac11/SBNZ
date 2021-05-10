package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import better.me.dto.BodyInfoDTO;
import better.me.enums.BodyType;
import better.me.facts.BodyTypeDTO;
import better.me.util.MyLogger;

public class BodyTypeDeterminationTest {

	private static Integer ectoScore;
    private static Integer endoScore;
    private static Integer mesoScore;
    private final MyLogger myLogger = new MyLogger();
    
    protected final String ksessionName = "session";
    
    private KieSession kieSession;
    
    @Before
	public void setUp() {
    	KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        kieSession = kContainer.newKieSession("session");
		kieSession.getAgenda().getAgendaGroup("bodyType").setFocus();
	}
    
    @Test
    public void bodyTypeRule_answersForEctomorphGiven_shouldDetermineEctomorphBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("NARROWER THAN YOUR HIPS", "TIGHT AROUND YOUR GLUTES", "SMALL", "STAY SKINNY", "MOSTLY RECTANGLE", "FIND IT DIFFICULT TO GAIN AND MAINTAIN WEIGHT", "THE FINGERS OVERLAP");
        BodyTypeDTO bodyType = new BodyTypeDTO();
        
        getTypesScore(infoDTO);
        		
        assertEquals((Integer) 7, ectoScore);
        assertEquals((Integer) 0, endoScore);
        assertEquals((Integer) 0, mesoScore);
        
        kieSession.setGlobal("ectoScore", ectoScore);
		kieSession.setGlobal("endoScore", endoScore);
		kieSession.setGlobal("mesoScore", mesoScore);
		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(infoDTO);
		kieSession.insert(bodyType);
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(BodyType.ECTOMORPH, bodyType.getBodyType());
    }
    
    @Test
    public void bodyTypeRule_answersForEndomorphGiven_shouldDetermineEndomorphBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("WIDER THAN YOUR HIPS", "LOOSE AROUND YOUR GLUTES", "BIG", "CARRY SOME EXTRA FAT", "PEAR", "GAIN WEIGHT EASILY BUT HAVE A HARD TIME LOSING IT", "THE FINGERS DON'T TOUCH");
        BodyTypeDTO bodyType = new BodyTypeDTO();
        
        getTypesScore(infoDTO);
        		
        assertEquals((Integer) 0, ectoScore);
        assertEquals((Integer) 7, endoScore);
        assertEquals((Integer) 0, mesoScore);
        
        kieSession.setGlobal("ectoScore", ectoScore);
		kieSession.setGlobal("endoScore", endoScore);
		kieSession.setGlobal("mesoScore", mesoScore);
		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(infoDTO);
		kieSession.insert(bodyType);
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(BodyType.ENDOMORPH, bodyType.getBodyType());
    }
    
    @Test
    public void bodyTypeRule_answersForMesomorphGiven_shouldDetermineMesomorphBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("SAME AS YOUR HIPS", "PERFECT AROUND YOUR GLUTES", "AVERAGE", "STAY FIT AND MUSCULAR", "HOURGLASS", "HAVE AN EASY TIME LOSING OR GAINING WEIGHT", "THE FINGERS JUST TOUCH");
        BodyTypeDTO bodyType = new BodyTypeDTO();
        
        getTypesScore(infoDTO);
        		
        assertEquals((Integer) 0, ectoScore);
        assertEquals((Integer) 0, endoScore);
        assertEquals((Integer) 7, mesoScore);
        
        kieSession.setGlobal("ectoScore", ectoScore);
		kieSession.setGlobal("endoScore", endoScore);
		kieSession.setGlobal("mesoScore", mesoScore);
		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(infoDTO);
		kieSession.insert(bodyType);
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(1, firedRules);
        assertEquals(BodyType.MESOMORPH, bodyType.getBodyType());
    }
    
    @Test
    public void bodyTypeRule_randomAnswersGiven_shouldDetermineNoBodyType() {
        BodyInfoDTO infoDTO = new BodyInfoDTO("SAME AS YOUR HIPS", "LOOSE AROUND YOUR GLUTES", "BIG", "STAY SKINNY", "MOSTLY RECTANGLE", "FIND IT DIFFICULT TO GAIN AND MAINTAIN WEIGHT", "THE FINGERS OVERLAP");
        BodyTypeDTO bodyType = new BodyTypeDTO();
        
        getTypesScore(infoDTO);
        		
        assertEquals((Integer) 0, ectoScore);
        assertEquals((Integer) 0, endoScore);
        assertEquals((Integer) 0, mesoScore);
        
        kieSession.setGlobal("ectoScore", ectoScore);
		kieSession.setGlobal("endoScore", endoScore);
		kieSession.setGlobal("mesoScore", mesoScore);
		kieSession.setGlobal("myLogger", myLogger);
	
		kieSession.insert(infoDTO);
		kieSession.insert(bodyType);
		
        int firedRules = kieSession.fireAllRules();
        assertEquals(0, firedRules);
        assertEquals(null, bodyType.getBodyType());
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
