import java.util.Comparator;

/**
 * Compares two countries according to a specified ordering.
 */
public class CountryComparator implements Comparator<Country>{
    private String indicator;
    private boolean greaterComesFirst;
    
    /**
     * Constructs a new CountryComparator that makes comparisons based on the
     * given indicator (using the standardized names in the assignment)
     * and treats the country with the greater value as coming first if
     * greaterComesFirst is true. If greaterComesFirst is false, the country
     * with the smaller value is treated as coming first.
     */
    public CountryComparator(String indicator, boolean greaterComesFirst) {
        this.indicator = indicator;
        this.greaterComesFirst = greaterComesFirst;
    }
    
    /**
     * Returns a negative number if country1 comes before country2,
     * a positive number if country1 comes after country2, and
     * 0 if no ordering is specified for country1 versus country2.
     * Ordering is based on the values of the indicator that was specified
     * when this comparator was constructed, whether greater values are
     * coming first or not, and in the case of ties for the indicator value,
     * the name (with the alphabetically first country coming first in the 
     * case of a tie).
     */
    public int compare(Country country1, Country country2) {
        if (country1.getIndicatorValue(indicator) > country2.getIndicatorValue(indicator)){
          if (greaterComesFirst){
            return -1;
          }
          else{
             return 1;
          }
        }
        else if (country1.getIndicatorValue(indicator) < country2.getIndicatorValue(indicator)){
          if (greaterComesFirst){
            return 1;
          }
          else{
             return -1;
          }
        }
        else{
          if (country1.getName().compareTo(country2.getName()) < 0){
            return -1;
          }
          else if (country1.getName().compareTo(country2.getName()) > 0){
            return 1;
          }
          else{
            return 0;
          }
        } 
    }
    
    /**
     * Tests the CountryComparator implementation.
     * You may modify this however you like!
     */ 
    public static void main(String[] args) {
        //Uncomment the code below once you've added the constructor in
        //Country as a start to testing that your comparator works properly.
        Country akhaten = new Country("ahkaten,500,3,5,4,90,45,35".split(","));
        Country trenzalore = new Country("trenzalore,450,3,5,4,65,45,35".split(","));
        Country gallifrey = new Country("gallifrey,15,-5,-10,4,95,45,35".split(","));
        
        boolean allTestsPassed = true;
        
        Comparator<Country> countryComparator = new CountryComparator("AccessToElectricity", false);
        
        int akhatenVersusTrenzalore = countryComparator.compare(akhaten, trenzalore);
        if(akhatenVersusTrenzalore <= 0) {
            allTestsPassed = false;
            System.err.println("Akhaten has less access than Trenzalore, so "
             + "comparison should be positive. Actual value: " + akhatenVersusTrenzalore);
        }

        int akhatenVersusGallifrey = countryComparator.compare(akhaten, gallifrey);
        if(akhatenVersusGallifrey >= 0) {
            allTestsPassed = false;
            System.err.println("Akhaten has the same access as Gallifrey, and "
             + "comes alphabetically first, so comparison should be negative. "
             + " Actual value: " + akhatenVersusGallifrey);
        }

        if(allTestsPassed) {
            System.err.println("All tests passed!");
        }

    }
}