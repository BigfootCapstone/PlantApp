package com.codeup.plantapp.models;

public class PlantMedForm {

    String plantName;
    String stemADJ;
    String leaveADJ;
    String fruitADJ;

    public String getPlantName() {
        return plantName;
    }
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getStemADJ() {
        return stemADJ;
    }
    public void setStemADJ(String stemADJ) {
        this.stemADJ = stemADJ;
    }

    public String getLeaveADJ() {
        return leaveADJ;
    }
    public void setLeaveADJ(String leaveADJ) {
        this.leaveADJ = leaveADJ;
    }

    public String getFruitADJ() {
        return fruitADJ;
    }
    public void setFruitADJ(String fruitADJ) {
        this.fruitADJ = fruitADJ;
    }

    public PlantMedForm() {
    }

    public PlantMedForm(String plantName, String stemADJ, String leaveADJ, String fruitADJ) {
        this.plantName = plantName;
        this.stemADJ = stemADJ;
        this.leaveADJ = leaveADJ;
        this.fruitADJ = fruitADJ;
    }
}