package better.me.enums;

public enum Sex {
	MALE("MALE"), FEMALE("FEMALE");
	
	private String value;

    Sex(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
