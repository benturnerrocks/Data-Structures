import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Country {

    private String name;
    //of each country/instance
    private int PopulationTotal;
    //in hundreds of thousands
    private int PopulationGrowth;
    //annual %
    private int UrbanPopulationGrowth;
    //annual %
    private int CO2Emissions;
    //metric tons per capita
    private int AccessToElectricity;
    //% of population
    private int RenewableEnergy;
    //% of total final energy consumed
    private int ProtectedAreas;
    //% of total area protected

    public Country(ArrayList<String> myList) {
        this.setName(myList.get(0));
        this.setPopulationTotal(Integer.parseInt(myList.get(1)));
        this.setPopulationGrowth(Integer.parseInt(myList.get(2)));
        this.setUrbanPopulationGrowth(Integer.parseInt(myList.get(3)));
        this.setCO2Emissions(Integer.parseInt(myList.get(4)));
        this.setAccessToElectricity(Integer.parseInt(myList.get(5)));
        this.setRenewableEnergy(Integer.parseInt(myList.get(6)));
        this.setProtectedAreas(Integer.parseInt(myList.get(7)));
    }

// setter methods
    public void setName(String newName) {
        name = newName;
    }
    public void setPopulationTotal(int newPopulationTotal) {
        PopulationTotal = newPopulationTotal;
    }
    public void setPopulationGrowth(int newPopulationGrowth) {
        PopulationGrowth = newPopulationGrowth;
    }
    public void setUrbanPopulationGrowth(int newUrbanPopulationGrowth) {
        UrbanPopulationGrowth = newUrbanPopulationGrowth;
    }
    public void setCO2Emissions(int newCO2Emissions) {
        CO2Emissions = newCO2Emissions;
    }
    public void setAccessToElectricity(int newAccessToElectricity) {
        AccessToElectricity = newAccessToElectricity;
    }
    public void setRenewableEnergy(int newRenewableEnergy) {
        RenewableEnergy = newRenewableEnergy;
    }
    public void setProtectedAreas(int newProtectedAreas) {
        ProtectedAreas = newProtectedAreas;
    }
    //now the getter methods
    public String getName() {
        return name;
    }
    public int getPopulationTotal() {
        return PopulationTotal;
    }
    public int getPopulationGrowth() {
        return PopulationGrowth;
    }
    public int getUrbanPopulationGrowth() {
        return UrbanPopulationGrowth;
    }
    public int getCO2Emissions() {
        return CO2Emissions;
    }
    public int getAccessToElectricity() {
        return AccessToElectricity;
    }
    public int getRenewableEnergy() {
        return RenewableEnergy;
    }
    public int getProtectedAreas() {
        return ProtectedAreas;
    }
    //this is for the sorting algorithm
    public int getIndicatorValue(String indicator){
        if (indicator.equals("PopulationTotal")){
          return PopulationTotal;
        }
        else if (indicator.equals("PopulationGrowth")){
          return PopulationGrowth;
        }
        else if (indicator.equals("UrbanPopulationGrowth")){
          return UrbanPopulationGrowth;
        }
        else if (indicator.equals("CO2Emissions")){
          return CO2Emissions;
        }
        else if (indicator.equals("AccessToElectricity")){
          return AccessToElectricity;
        }
        else if (indicator.equals("RenewableEnergy")){
          return RenewableEnergy;
        }
        else if (indicator.equals("ProtectedAreas")){
          return ProtectedAreas;
        }
        else{
          System.err.println("Wrong indicator, please use an accepted term.");
          System.exit(1);
          return 0;
      }
    }
    //this is for the BarChart Display :)
    public String getCountry(){
        String countryString = (name + "," + PopulationTotal + "," + PopulationGrowth + "," + UrbanPopulationGrowth + "," + CO2Emissions + "," + AccessToElectricity + "," + RenewableEnergy + "," + ProtectedAreas);
        return countryString;    
    } 
    public static void main(String[] args){
    }
}
