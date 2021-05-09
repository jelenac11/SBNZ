package better.me.enums;

public enum Jeans {
	LOOSE("LOOSE AROUND YOUR GLUTES"), TIGHT("TIGHT AROUND YOUR GLUTES"), PERFECT("PERFECT AROUND YOUR GLUTES");
	
	private String value;

	Jeans(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
