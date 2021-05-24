package better.me.enums;

public enum AgeCategory {
	CHILD("CHILD"), TEEN("TEEN"), YOUNG_ADULT("YOUNG_ADULT"), ADULT("ADULT"), ELDER("ELDER");
	
	private String value;

    AgeCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
