import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Collections;

public class CountryDisplayer{  
    private ArrayList<Country> countryList; 
    private ArrayList<Country> sortedCountryList;

//the default constructor is perfectly fine here
    public CountryDisplayer() {
    }

//makeCountryList makes an ArrayList<Country> from CountryDisplayer.csv
    public void makeCountryList(String fileName){
        countryList = new ArrayList<Country>();
        File inputFile = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
            scanner.nextLine();
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<String> tempArrayList = new ArrayList<String>();
                for(String i : line.split(",")) {
                  tempArrayList.add(i);
                }
                Country country1 = new Country(tempArrayList);
                countryList.add(country1);
              }
            //countryList is a list which has as its elements the lines of the file
            }   
        catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        scanner.close();
        //removes the first line of description
    }

//sortCountryList creates a list called sortedCountryList that sorts by the indicator given into it; least to greatest by default, flips if isGreatestToLeast is true
    public void sortCountryList(String indicator, boolean isGreatestToLeast) {
        sortedCountryList = new ArrayList<Country>();
        for (Country i : countryList){
            if (sortedCountryList.size() == 0){
                sortedCountryList.add(i);
            }
            else{
                for(int h=0; h < sortedCountryList.size(); h++){
                    Country j = sortedCountryList.get(h);
                    if (i.getIndicatorValue(indicator) < j.getIndicatorValue(indicator)){
                      sortedCountryList.add(h, i);
                      break;
                    }
                    else if (i.getIndicatorValue(indicator) == j.getIndicatorValue(indicator)){
                        if (isGreatestToLeast == true){
                          if ((i.getName()).compareTo(j.getName()) > 0){
                            sortedCountryList.add(h, i);
                            break;
                          }
                        }
                        else if (isGreatestToLeast == false){
                          if ((i.getName()).compareTo(j.getName()) < 0){
                            sortedCountryList.add(h, i);
                            break;
                          }
                        }           
                    }
                    else if(sortedCountryList.size() == h+1){
                        sortedCountryList.add(h+1, i);
                        break;  
                    }
                }
            }
        }
        if (isGreatestToLeast){
              Collections.reverse(sortedCountryList);
            }
    }
        
//this prints all the countries in the specified sorted order if only 3 command arguments
    public void displayTextCountries(String indicator, boolean isGreatestToLeast) {
        this.sortCountryList(indicator, isGreatestToLeast);
        for(Country i: sortedCountryList){
            System.out.println(i.getCountry());
        }
    }
 //provides a graphic if there are 4 command line arguments
    public void displayCountryGraph(String indicator1Name, String indicator2Name, boolean isGreatestToLeast) {
        String title = "Top 10 " + indicator1Name + " and corresponding " + indicator2Name;
        BarChart myBarChart = new BarChart(title, "Countries", "Value");
        this.sortCountryList(indicator1Name, isGreatestToLeast);
        ArrayList<Country> countries = this.getSortedCountryList();

        for(int i = 0; i < 10; i++){
            myBarChart.addValue(countries.get(i).getName(), countries.get(i).getIndicatorValue(indicator1Name), indicator1Name);
            myBarChart.addValue(countries.get(i).getName(), countries.get(i).getIndicatorValue(indicator2Name), indicator2Name);
        }
        myBarChart.displayChart();
        
    }

//this is for the BarChart "displayCountryGraph" method
    public ArrayList<Country> getSortedCountryList(){
        return sortedCountryList;
    }

    public static void main(String[] args){
        CountryDisplayer displayer1 = new CountryDisplayer();
        if (args.length == 0) {
           System.err.println("Usage: java CommandLine inputpathfile");
           System.exit(1);
          }
        else if (args.length == 3){
            String indicator = args[1];
            displayer1.makeCountryList(args[0]);
            displayer1.displayTextCountries(indicator, args[2].equals("greatestToLeast"));
        }
        
        else if (args.length == 4){
            String indicator = args[1];
            String indicator2 = args[3];
            displayer1.makeCountryList(args[0]);
            displayer1.displayCountryGraph(indicator, indicator2, args[2].equals("greatestToLeast"));
        }
        
        else{
            System.err.println("Neither 0, 3, nor 4 CommandLine arguments. Try harder next time.");
            System.exit(1);
        }
    }    
}
