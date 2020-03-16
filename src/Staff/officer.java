package Staff;

public enum officer {
    OFDS256,OFDS563;

    private officer(){

    }

    private String value(){
        return name();
    }
    private static officer fromvalue(String v){
        return valueOf(v);
    }
}
