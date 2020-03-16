package Staff;

public enum caseStatus {
    Open,Closed,Pending;

    private caseStatus(){

    }

    public String value(){
        return  name();
    }

    public static caseStatus fromvalue(String v){
        return  valueOf(v);
    }
}
