import java.util.Scanner;
/**
* GuessingGame picks a number between 1 and 100 at random, and asks the user to guess. 
* After each guess, it tells the user if they are too high/too low and then lets them guess again. 
* Ends the program when the user guesses correctly.
*
* Complete the tasks on the accompanying webpage. Submit a zip with your code on Moodle
* when you're done.
*/
public class GuessingGame {
    //Instance variable storing the number the user is guessing

    public static String name; 
    private static int number;
    //Static variable storing the default number to have the user guess.
    //Why have a default number? 
    //  (a) For debugging - you know exactly what number you should be guessing 
    //  and (b) For practice understanding the difference between static and non-static.
    private static int defaultNumber = 27;
    //not local varible but this is is an internal field varible 
    /**
     * Constructs a new GuessingGame. To start game,
     * you must call startNewGame(). Otherwise, the number to guess will always
     * use the same default value to guess.
     */
    public static int guess; 
    
    public GuessingGame() {
        number = defaultNumber;
    }
	
    /**
     * Returns the number the user is supposed to guess.
     */
    public int getNumber() {
        /* IMPLEMENT - YOUR CODE GOES HERE */
        return number;  
    }
	
    /**
     * Returns the default number for guessing (if player tries to play without starting a new game).
     */
    public static int getDefaultNumber() {
        /* IMPLEMENT - YOUR CODE GOES HERE */
        return defaultNumber;
    }
	
    /**
     * Changes the default number for guessing. This number is used if the player tries to play
     * without calling startNewGame().
     * @param defaultNumber
     */
    public void changeDefaultNumber(int defaultNumber)  {
        GuessingGame.defaultNumber = defaultNumber;//What happens if you don't include "GuessingGame."? Make sure you understand why.
     //-  including the guessingame referes to the class varible defined in the constructor. 
    }
	
    /**
     * Starts a new guessing game by randomly selecting an integer between 1 and 100 for the
     * player to guess.
     */
    public void startNewGame() {
        // create an object of Scanner

        GuessingGame.number = (int) Math.ceil(Math.random()*100);
        
    }
        //math.ceil rounds up always. (int) casts it to not be a decimal. DIfferent than declaring a new varible as an integer. 
	
    /**
     * Plays a guessing game by having the player
     * guess until she correctly identifies the computer's number. userInput
     * should be a scanner that gets input from the keyboard. 
     */
    public void playGame() {
    // take input from the user
    Scanner scanner = new Scanner(System.in);

    System.out.print("What's your name?");
    name = scanner.nextLine(); 

    System.out.println("Hello " + this.name + "! The computer's job is to select a number between 1 and 100. Your job is to guess that number! ");
      while (guess != number) {
        System.out.println("Please guess a number:");
        guess = scanner.nextInt();

        if (number < guess) {
          System.out.format("too high \n");  
          } 
        else if (number > guess) {
          System.out.format("too low \n");
          }
      }
      System.out.println("Yay! Congrats big-brain you've got it");
    }
    //we could add more constraints if we wanted to. Say to make sure the input is actually between 0 and 100
    /**
     * GuessingGame main constructs a new game and has the player play a single game in which
     * she must guess the computer's chosen number (from 1-100).
     * @param args the command line arguments aren't used for this class
     */

    public static void main(String[] args) {
      System.out.println("Welcome to the guessing game!"); 

      GuessingGame myGuessGame = new GuessingGame();
      // the information of the said thing. abstract information. This is creating the object, something you can latch onto. 

     // Scanner scanner = new Scanner(System.in); //creating object? this is taking new input

      myGuessGame.startNewGame();
      myGuessGame.playGame();
  
    }
}

