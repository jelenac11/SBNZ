package better.me.enums;

public enum Diet {
	VEGAN("VEGAN"), VEGETARIAN("VEGETARIAN"), OMNIVORE("OMNIVORE");
	
	private String value;

    Diet(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
