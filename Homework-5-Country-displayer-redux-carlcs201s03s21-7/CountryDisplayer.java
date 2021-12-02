import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Collections;

public class CountryDisplayer{  
  private String filePath;
  private String indicator;
  private boolean isGreatestToLeast;
  private SortedList<Country> sortedList;

  public CountryDisplayer(String indicator, boolean isGreatestToLeast) {
    this.indicator = indicator;
    this.isGreatestToLeast = isGreatestToLeast;
  }

  public CountryDisplayer(String filePath, String indicator, boolean isGreatestToLeast) {
    this.indicator = indicator;
    this.isGreatestToLeast = isGreatestToLeast;
    this.filePath = filePath;
    }
  
  public void makeCountryList(){
    CountryComparator countryComparator = new CountryComparator(indicator, isGreatestToLeast);
    sortedList = new SortedLinkedList(countryComparator);
  }
  
  public void makeCountryList(String fileName){
    CountryComparator countryComparator = new CountryComparator(indicator, isGreatestToLeast);
    sortedList = new SortedLinkedList(countryComparator);
    File inputFile = new File(fileName);
    Scanner scanner = null;
    try {
        scanner = new Scanner(inputFile);
        scanner.nextLine();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tempArray = line.split(",");
            Country country1 = new Country(tempArray);
            sortedList.add(country1);
        }
        }   
    catch (FileNotFoundException e) {
        System.err.println(e);
        System.exit(1);
    }
    scanner.close();
  }

  //sorts the countries in the file provided, then displays them as text in sorted order
  public void displayCountriesAsText(){
      for (int i=0; i < sortedList.size(); i++){
        System.out.println(sortedList.get(i).getCountry());
    }
  }
  
  /**
  * Displays a graph with the top 10 countries (based on the sorting criteria)
  * and a second series showing the additional indicator.
  * @param secondaryIndicator indicator to show as the second series in the graph
  */
  public void displayGraph(String secondaryIndicator) {
      int numCountries = 10;
      if (sortedList.size()<10){
          numCountries = sortedList.size();
          System.out.println(numCountries);
      }
      String title = "Top " + numCountries + this.indicator + " and corresponding " + secondaryIndicator;
      BarChart myBarChart = new BarChart(title, "Countries", "Value");
      for(int i = 0; i < numCountries; i++){
          myBarChart.addValue(sortedList.get(i).getName(), sortedList.get(i).getIndicatorValue(this.indicator), this.indicator);
          myBarChart.addValue(sortedList.get(i).getName(), sortedList.get(i).getIndicatorValue(secondaryIndicator), secondaryIndicator);
      }
      myBarChart.displayChart();
      
  }
  
  /**
  * Changes the criteria for sorting 
  * @param indicator indicator to use for sorting the countries Valid values are: CO2Emissions, 
  *        TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, 
  *        PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth
  * @param isGreatestToLeast whether the countries should be sorted from greatest to least
  */
  public void changeSortingCriteria(String indicator, boolean isGreatestToLeast){
      this.indicator = indicator;
      this.isGreatestToLeast = isGreatestToLeast;
      CountryComparator comparator = new CountryComparator(indicator, isGreatestToLeast);
      sortedList.resort(comparator);
  }
  
  /**
  * Adds the given country to the data.
  * @param country country to add
  */
  public void addCountry(Country country){
    sortedList.add(country);
  }

  public static void main(String[] args){
    //used to take input from user
    Scanner scanner = new Scanner(System.in);
    //to keep track of the input from the user
    String input = "";
    CountryDisplayer displayer1;
    //initializes our SortedLinkedList in either case when we do or don't have a dataset
    //if dataset is not given, run it to get an empty sortedList
    if(args.length==0){
      displayer1 = new CountryDisplayer("PopulationTotal", true);
      displayer1.makeCountryList();
    }
    //if dataset is given, load it
    else{
      displayer1 = new CountryDisplayer(args[0], "PopulationTotal", true);
      displayer1.makeCountryList(args[0]);
    }
    //continue to ask for input until they say to exit
    while(!input.equals("exit")){
        //prints prompt
        System.out.println("What would you like to do? (You can set sorting criteria, add country, display graph, display countries, or exit.");
        //ask for input from user
        input = scanner.nextLine();
        //the following are what we allow to user to do
        if(input.equals("set sorting criteria")) {
          System.out.println("Please input the indicator and direction. Valid indicators include: PopulationTotal, PopulationGrowth, UrbanPopulationGrowth, CO2Emissions, AccessToElectricity, RenewableEnergy, or ProtectedAreas. Valid directions include greatestToLeast and leastToGreatest. Do so as one word where indicator and direction are separated by a comma (indicator,direction).");
          String prompt = scanner.next();
          String[] promptArray = prompt.split(",");
          displayer1.changeSortingCriteria(promptArray[0], promptArray[1].equals("greatestToLeast"));
        } 
        else if (input.equals("add country")){
          System.out.println("Please input a line containing the new country information. It should follow the format: Country Name,Population (total in hundred thousands),Population growth (annual %),Urban population growth (annual %),CO2 emissions (metric tons per capita),Access to electricity (% of population),Renewable energy consumption (% of total final energy consumption),Terrestrial protected areas (% of total land area). NO SPACES AND SEPARATED BY COMMAS");
          String prompt = scanner.next();
          String[] promptArray = prompt.split(",");
          Country countryA = new Country(promptArray);
          displayer1.addCountry(countryA);
        }  
        else if(input.equals("display graph")){
          System.out.println("Please input the second indicator.");
          String prompt = scanner.next();
          displayer1.displayGraph(prompt);
        }
        else if(input.equals("display countries")){
          displayer1.displayCountriesAsText();
        }
        else if (input.equals("exit")){
            System.exit(0);
        }
        else{
          System.out.println("Please input either: set sorting criteria, add country, display graph, or exit.");
        }
      
    
  }
  }    
}
