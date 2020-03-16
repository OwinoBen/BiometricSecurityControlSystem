package Staff;

public enum nationalityOption {
    Kenyan,Ugandan, Tanzanian,Egyptian,Nigerian,MozambiAfghans, Albanians, Algerians, Andorrans, Angolans, Antiguans, Argentines, Armenians, Arubans, Australians, Austrians, Azerbaijanis,
    Bahamians, Bahrainis, Bangladeshis, Barbadians, Basques, Belarusians, Belgians, Belizeans, Beninese, Bermudians, Bhutanese, Bolivians, Bosniaks, Bosnians, Botswana, Brazilians, Bretons, British, Virgin , Islanders,
    Bruneians, Bulgarians, Macedonian, Burkinabés, Burmese, Burundians, Cambodians, Cameroonians, Canadians, Catalans, CapeVerdeans, Chadians, Chileans, Chinese, Colombians, Comorians, Congolese, CostaRicans, Croatians,
    Cubans, Cypriots, Czechs, Danes, Greenlanders, Djiboutians, Dominicans, Dutch, EastTimorese, Ecuadorians, Egyptians, Emiratis, English, Equatoguineans, Eritreans, Estonians, Ethiopians, Falkland , Faroese, Fijians, Finns, Finnish,
    Swedish, Filipinos, French, citizens, Gabonese, Gambians, Georgians, Germans, Baltic, Ghanaians, Gibraltarians, Greeks, Greek, Macedonians, Grenadians, Guatemalans, Guianese, Guineans, GuineaBissau, nationals, Guyanese,
    Haitians, Hondurans, HongKongers, Hungarians, Icelanders, IKiribati, Indians, Indonesians, Iranians, Iraqis, Irish, Israelis, Italians, Ivoirians, Jamaicans, Japanese, Jordanians, Kazakhs, Koreans, Kosovars ;

    private nationalityOption(){

    }

    public String value(){
        return name();
    }

    public static nationalityOption fromvalue(String v){
        return valueOf(v);
    }
}
