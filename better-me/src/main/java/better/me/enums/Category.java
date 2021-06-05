package better.me.enums;

public enum Category {
	BEGINNER("BEGINNER"), INTERMEDIATE("INTERMEDIATE"), ADVANCED("ADVANCED"), PRO("PRO");
	
	private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
