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

    public PlantMedForm() {}

    public PlantMedForm(String plantName, String stemADJ, String leaveADJ, String fruitADJ) {
        switch (stemADJ) {
            case "1" -> stemADJ = "Discoloration";
            case "2" -> stemADJ = "Lesions or cankers";
            case "3" -> stemADJ = "Soft or mushy areas";
            case "4" -> stemADJ = "Swelling or galls";
            case "5" -> stemADJ = "Splitting or cracking";
            case "6" -> stemADJ = "Presence of pests or insects";
            default -> stemADJ = "N/A";
        }

        switch (leaveADJ) {
           case "1" -> leaveADJ = "Yellowing or chlorosis";
           case "2" -> leaveADJ = "Browning or necrosis";
           case "3" -> leaveADJ = "Wilting or drooping";
           case "4" -> leaveADJ = "Curling or distortion";
           case "5" -> leaveADJ = "Spots or lesions";
           case "6" -> leaveADJ = "Holes or chewed edges";
           case "7" -> leaveADJ = "Powdery or fuzzy growth";
           case "8" -> leaveADJ = "Sticky residue (honeydew) or sap";
           case "9" -> leaveADJ = "Presence of pests or insects";
           case "10" -> leaveADJ = "Abnormal growth";
           default -> leaveADJ = "N/A";
        }

        switch (fruitADJ) {
           case "1" -> fruitADJ = "Discoloration";
           case "2" -> fruitADJ = "Lesions or rotting spots";
           case "3" -> fruitADJ = "Wrinkling or shriveling";
           case "4" -> fruitADJ = "Deformed or misshapen";
           case "5" -> fruitADJ = "Stunted growth or smaller size than usual";
           case "6" -> fruitADJ = "Presence of pests or insects";
           default -> fruitADJ = "N/A";
        }

        this.plantName = plantName;
        this.stemADJ = stemADJ;
        this.leaveADJ = leaveADJ;
        this.fruitADJ = fruitADJ;
    }

    /*
  TODO: DIAGNOSIS Form
    STEMS
      N/A
      Discoloration (e.g., brown, black, yellowing)
      Lesions or cankers
      Soft or mushy areas
      Swelling or galls
      Splitting or cracking
      Presence of pests or insects (e.g., holes, tunnels)
    LEAVES
      Yellowing or chlorosis
      Browning or necrosis
      Wilting or drooping
      Curling or distortion
      Spots or lesions (e.g., brown, black, yellow, white)
      Holes or chewed edges
      Powdery or fuzzy growth
      Sticky residue (honeydew) or sap
      Presence of pests or insects (e.g., eggs, larvae, adult insects)
      Abnormal growth (e.g., stunted, dwarfed)
    FRUITS
      Discoloration (e.g., brown, black, yellowing)
      Lesions or rotting spots
      Wrinkling or shriveling
      Deformed or misshapen
      Stunted growth or smaller size than usual
      Presence of pests or insects (e.g., entry holes, feeding damage)
*/

}