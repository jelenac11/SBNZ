package better.me.enums;

public enum Goal {
	LOSE_WEIGHT("LOSE_WEIGHT"), GAIN_WEIGHT("GAIN_WEIGHT"), MAINTAIN_WEIGHT("MAINTAIN_WEIGHT");
	
	private String value;

    Goal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
