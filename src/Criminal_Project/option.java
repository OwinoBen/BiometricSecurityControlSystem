package Criminal_Project;



public enum option {
    OCPD, S_sergent, general, majorGeneral, officer, sergent, supretended;

    private option() {
    }

    public String value() {
        return name();
    }

    public static option fromvalue(String v) {
        return valueOf(v);

    }
}
