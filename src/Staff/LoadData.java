package Staff;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoadData {

   private final StringProperty action;
   private final StringProperty gender;
   private final StringProperty name;
   private final StringProperty status;
   private final StringProperty criminalNO;
   private final StringProperty caseNO;

   public  LoadData(){
       this.caseNO = new SimpleStringProperty("");
       this.status=new SimpleStringProperty("");
       this.action= new SimpleStringProperty("");
       this.gender= new SimpleStringProperty("");
       this.criminalNO= new SimpleStringProperty("");
       this.name=new SimpleStringProperty("");

   }

 public LoadData(StringProperty photo, StringProperty gender, StringProperty name, StringProperty status, StringProperty criminalNO, StringProperty caseNO) {
        this.action = photo;
        this.gender = gender;
        this.name = name;
        this.status = status;
        this.criminalNO = criminalNO;
        this.caseNO = caseNO;
    }

      public  LoadData(String photo, String gender, String name, String criminalNO, String status)
     {
         this.action= new SimpleStringProperty(photo);
         this.gender = new SimpleStringProperty(gender);
         this.name = new SimpleStringProperty(name);
         this.criminalNO= new SimpleStringProperty(criminalNO);
         caseNO= new SimpleStringProperty("");
         this.status =new SimpleStringProperty("status");
     }

      public LoadData(String caseNO)
      {
         this.caseNO = new SimpleStringProperty("caseNO");
         this.status=new SimpleStringProperty("");
         this.action= new SimpleStringProperty("");
         this.gender= new SimpleStringProperty("");
         this.criminalNO= new SimpleStringProperty("");
         this.name=new SimpleStringProperty("");

      }

    public String getAction() {
        return action.get();
    }

    public StringProperty actionProperty() {
        return action;
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getCriminalNO() {
        return criminalNO.get();
    }

    public StringProperty criminalNOProperty() {
        return criminalNO;
    }

    public void setCriminalNO(String criminalNO) {
        this.criminalNO.set(criminalNO);
    }

    public String getCaseNO() {
        return caseNO.get();
    }

    public StringProperty caseNOProperty() {
        return caseNO;
    }

    public void setCaseNO(String caseNO) {
        this.caseNO.set(caseNO);
    }
}
