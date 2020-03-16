package SeniorStaff;

public enum statusOption {
    Arrested, Escaped, Bailed, Released;

    private  statusOption(){

    }
    public String value(){
        return name();
    }

    public static statusOption fromvalue(String v){
        return valueOf(v);
    }
}
