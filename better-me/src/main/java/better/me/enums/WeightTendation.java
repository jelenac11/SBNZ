package better.me.enums;

public enum WeightTendation {
	HARD_LOSE("GAIN WEIGHT EASILY BUT HAVE A HARD TIME LOSING IT"), HARD_GAIN("FIND IT DIFFICULT TO GAIN AND MAINTAIN WEIGHT"), EASY_BOTH("HAVE AN EASY TIME LOSING OR GAINING WEIGHT");
	
	private String value;

	WeightTendation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
