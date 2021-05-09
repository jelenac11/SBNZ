package better.me.enums;

public enum Forearms {
	BIG("BIG"), SMALL("SMALL"), AVERAGE("AVERAGE");
	
	private String value;

    Forearms(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
