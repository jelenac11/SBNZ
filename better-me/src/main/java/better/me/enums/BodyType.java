package better.me.enums;

public enum BodyType {
	ECTOMORPH("ECTOMORPH"), ENDOMORPH("ENDOMORPH"), MESOMORPH("MESOMORPH");
	
	private String value;

    BodyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
