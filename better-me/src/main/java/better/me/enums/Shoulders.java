package better.me.enums;

public enum Shoulders {
	WIDER_THAN_HIPS("WIDER THAN YOUR HIPS"), NARROWER_THAN_HIPS("NARROWER THAN YOUR HIPS"), SAME_AS_HIPS("SAME AS YOUR HIPS");
	
	private String value;

    Shoulders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
