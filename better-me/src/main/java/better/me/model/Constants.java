package better.me.model;

import java.util.HashMap;
import java.util.Map;
import better.me.enums.*;

public class Constants {

	public static Map<String, String> ectoAnswers = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

	{
        put("shoulders", Shoulders.NARROWER_THAN_HIPS.getValue());
        put("jeans", Jeans.TIGHT.getValue());
        put("forearms", Forearms.SMALL.getValue());
        put("bodyTendation", BodyTendation.STAY_SKINNY.getValue());
        put("bodyLook", BodyLook.MOSTLY_RECTANGLE.getValue());
        put("weightTendation", WeightTendation.HARD_GAIN.getValue());
        put("encircleHandWrist", EncircleHandWrist.OVERLAP.getValue());
    }};
    
    public static Map<String, String> endoAnswers = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

	{
        put("shoulders", Shoulders.WIDER_THAN_HIPS.getValue());
        put("jeans", Jeans.LOOSE.getValue());
        put("forearms", Forearms.BIG.getValue());
        put("bodyTendation", BodyTendation.CARRY_FAT.getValue());
        put("bodyLook", BodyLook.PEAR.getValue());
        put("weightTendation", WeightTendation.HARD_LOSE.getValue());
        put("encircleHandWrist", EncircleHandWrist.DONT_TOUCH.getValue());
    }};
    
    public static Map<String, String> mesoAnswers = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

	{
        put("shoulders", Shoulders.SAME_AS_HIPS.getValue());
        put("jeans", Jeans.PERFECT.getValue());
        put("forearms", Forearms.AVERAGE.getValue());
        put("bodyTendation", BodyTendation.STAY_MUSCULAR.getValue());
        put("bodyLook", BodyLook.HOURGLASS.getValue());
        put("weightTendation", WeightTendation.EASY_BOTH.getValue());
        put("encircleHandWrist", EncircleHandWrist.TOUCH.getValue());
    }};
    
    
} 
