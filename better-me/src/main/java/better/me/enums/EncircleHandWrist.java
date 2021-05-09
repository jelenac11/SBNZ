package better.me.enums;

public enum EncircleHandWrist {
	DONT_TOUCH("THE FINGERS DON'T TOUCH"), OVERLAP("THE FINGERS OVERLAP"), TOUCH("THE FINGERS JUST TOUCH");
	
	private String value;

	EncircleHandWrist(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
