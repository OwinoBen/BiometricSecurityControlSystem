package SeniorStaff;

public enum genderOption {
    Male,Female;

    private genderOption(){

    }

    public String value(){
        return  name();
    }

    public static genderOption fromvalue(String v){
        return  valueOf(v);
    }
}
