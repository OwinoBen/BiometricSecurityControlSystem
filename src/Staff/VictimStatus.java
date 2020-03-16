package Staff;

public enum VictimStatus {
    Dead,Alive,Injured,Incoma;

    private VictimStatus(){

    }

    public String value(){
        return  name();
    }

    public static VictimStatus fromvalue(String v){
        return  valueOf(v);
    }
}
