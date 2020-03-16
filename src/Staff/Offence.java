package Staff;

public enum Offence {
    House_breakins,Fraud,Armed_robberies,Pickpocketing,Embezzlement,Mobjustice,Massivelooting,CattleRustling,Humantrafficking,Carjacking;

    private Offence(){

    }

    public String value(){
        return  name();
    }

    public static Offence fromvalue(String v){
        return  valueOf(v);
    }
}
