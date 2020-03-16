package Staff;


public enum Rank {
    OCPD, S_sergent, general, majorGeneral, officer, sergent, supretended;

    private Rank() {
    }

    public String value() {
        return name();
    }

    public static Rank fromvalue(String v) {
        return valueOf(v);

    }
}
