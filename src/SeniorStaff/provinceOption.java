package SeniorStaff;

public enum provinceOption {
    Nairobi, Coast, Western, Nyanza, Central, Eastern, NorthEastern, RiftValley;

    private  provinceOption(){

    }

    public String value(){
        return name();
    }
    public static provinceOption fromvalue(String v){
        return valueOf(v);
    }
}
