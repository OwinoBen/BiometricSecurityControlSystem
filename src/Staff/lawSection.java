package Staff;

public enum lawSection {
    Cap203,Cap204,Cap205,Cap206,Cap207,Cap208,Cap209,Cap210,Cap211,Cap212,Cap213,Cap214,Cap215;

    private lawSection(){

    }

    public String value(){
        return  name();
    }

    public static lawSection fromvalue(String v){
        return  valueOf(v);
    }
}
