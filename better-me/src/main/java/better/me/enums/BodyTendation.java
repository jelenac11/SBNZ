package better.me.enums;

public enum BodyTendation {
	CARRY_FAT("CARRY SOME EXTRA FAT"), STAY_SKINNY("STAY SKINNY"), STAY_MUSCULAR("STAY FIT AND MUSCULAR");
	
	private String value;

    BodyTendation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
