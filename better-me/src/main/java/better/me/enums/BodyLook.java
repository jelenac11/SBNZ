package better.me.enums;

public enum BodyLook {
	PEAR("PEAR"), MOSTLY_RECTANGLE("MOSTLY RECTANGLE"), HOURGLASS("HOURGLASS");
	
	private String value;

    BodyLook(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
