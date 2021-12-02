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

    public Country(String[] countryData) {
        this.setName(countryData[0]);
        this.setPopulationTotal(Integer.parseInt(countryData[1]));
        this.setPopulationGrowth(Integer.parseInt(countryData[2]));
        this.setUrbanPopulationGrowth(Integer.parseInt(countryData[3]));
        this.setCO2Emissions(Integer.parseInt(countryData[4]));
        this.setAccessToElectricity(Integer.parseInt(countryData[5]));
        this.setRenewableEnergy(Integer.parseInt(countryData[6]));
        this.setProtectedAreas(Integer.parseInt(countryData[7]));
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
    public boolean equals(Country otherCountry){
      if((this.getCountry()).equals(otherCountry.getCountry())){
        return true;
        }
      else{
        return false;
        }
    
    }

    public static void main(String[] args){
      Country country1 = new Country("ahkaten,500,3,5,4,90,45,35".split(","));
      Country country2 = new Country("ahkaten,500,3,5,4,90,45,35".split(","));
      Country country3 = new Country("trenzalore,450,3,5,4,65,45,35".split(","));
      if (country1.equals(country2)){
        System.out.println("Your method's true function might work!");
      }
      if (country2.equals(country3)){
        System.out.println("Your method is super broken!");
      }
      else{
        System.out.println("Excellent work!");
      }
      
    }
}
