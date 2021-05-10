package better.me.enums;

public enum ActivityLevel {
	INACTIVE("INACTIVE"), SEDENTARY("SEDENTARY"), LIGHTLY_ACTIVE("LIGHTLY_ACTIVE"), MODERATELY_ACTIVE("MODERATELY_ACTIVE"), VERY_ACTIVE("VERY_ACTIVE");
	
	private String value;

    ActivityLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
