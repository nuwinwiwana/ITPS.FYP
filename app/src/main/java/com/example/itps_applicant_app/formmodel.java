package com.example.itps_applicant_app;

public class formmodel {
    private String nama;
    private String id;
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

    public formmodel(){

    }






    public String getNama() {
        return nama;
    }
    public String getIc(){
        return id;
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

    public void settravelReas(String travelReasTxt) {
        this.travelReas = travelReasTxt;
    }

    public void setdestinationAdd(String destinationAddTxt) {
        this.destinationAdd = destinationAddTxt;
    }

    public void setreturnDate(String returnDateTxt) {
        this.returnDate = returnDateTxt;
    }

    public void setDepatureDate(String depatureDateTxt) {
        this.DepatureDate = depatureDateTxt;
    }

    public void setdependent(String dependentTxt) {
        this.dependent = dependentTxt;
    }

    public void setVeichleType(String veichleTypeTxt) {
        this.VeichleType = veichleTypeTxt;
    }

    public void setveichlePlate(String veichlePlateTxt) {
        this.veichlePlate = veichlePlateTxt;
    }

    public void setnama(String namaTxt) {
        this.nama = namaTxt;
    }

    public void setid(String idTxt) {
        this.id = idTxt;
    }

    public void setcitizenship(String citizenshipTxt) {
        this.citizenship = citizenshipTxt;
    }

    public void setpoliceStn(String policeStnTxt) {
        this.policeStn = policeStnTxt;
    }

    public void setaddress(String addressTxt) {
        this.address = addressTxt;
    }
}
