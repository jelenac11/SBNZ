package better.me.services;

import java.util.ArrayList;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import better.me.dto.BodyInfoDTO;
import better.me.exceptions.NotLoggedInException;
import better.me.model.User;
import better.me.rules.dto.BodyTypeDTO;

@Service
public class BodyTypeDeterminationService {

	private static int ectoScore;
	private static int endoScore;
	private static int mesoScore;
	
	public String determine(BodyInfoDTO dto) throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (current == null) {
			throw new NotLoggedInException();
		}
		getTypesScore(dto);
		
		return "Tip tela";
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
