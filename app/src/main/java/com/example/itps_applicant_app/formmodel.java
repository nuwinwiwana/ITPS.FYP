package com.example.itps_applicant_app;

public class formmodel {
    private String fullname;
    private String ic;
    private String policeStn;
    private String travelReas;
    private String destinationAdd;
    private String returnDate;
    private String DepatureDate;
    private String dependent;
    private String veichlePlate;
    private String VeichleType;
    private String address;
    private String citizenship;




    public formmodel(String email, String password, String phone, String icTxt, String fullnameTxt, String travelReasTxt, String destinationAddTxt, String returnDateTxt, String depatureDateTxt, String dependentTxt, String veichlePlateTxt, String veichleTypeTxt) {


        this.fullname = fullname;
        this.policeStn = policeStn;
        this.travelReas = travelReas;
        this.ic = ic;
        this.destinationAdd = destinationAdd;
        this.returnDate = returnDate;
        this.DepatureDate = DepatureDate;
        this.dependent = dependent;
        this.veichlePlate = veichlePlate;
        this.VeichleType = VeichleType;
        this.address = address;
        this.citizenship = citizenship;


    }

    public String getFullname() {
        return fullname;
    }


    public String getIc(){
        return ic;
    }

    public String getPoliceStn(){
        return policeStn;
    }
    public String getTravelReas(){
        return travelReas;
    }
    public String getDestinationAdd(){
        return destinationAdd;
    }
    public String getReturnDate(){
        return returnDate;
    }
    public String getDepatureDate(){
        return DepatureDate;
    }
    public String getVeichlePlate(){
        return veichlePlate;
    }
    public String getVeichleType(){
        return VeichleType;
    }
    public String getAddress(){
        return address;
    }
    public String getCitizenship(){
        return citizenship;
    }
    public String getDependent(){ return dependent;    }
}
