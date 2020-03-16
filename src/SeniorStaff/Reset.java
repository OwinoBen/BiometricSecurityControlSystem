package SeniorStaff;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reset {

    private final StringProperty ID;
    private final StringProperty Email;
    private final StringProperty Rank;
    private final StringProperty Name;

    public Reset(){
         ID=new SimpleStringProperty("");
         Email=new SimpleStringProperty("");
         Rank=new SimpleStringProperty("");
         Name=new SimpleStringProperty("");

    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getEmail() {
        return Email.get();
    }

    public StringProperty emailProperty() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email.set(email);
    }

    public String getRank() {
        return Rank.get();
    }

    public StringProperty rankProperty() {
        return Rank;
    }

    public void setRank(String rank) {
        this.Rank.set(rank);
    }

    public String getName() {
        return Name.get();
    }

    public StringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }
}
