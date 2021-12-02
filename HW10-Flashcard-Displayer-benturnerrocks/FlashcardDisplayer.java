import java.io.*;
import java.util.*;
import java.time.*;

public class FlashcardDisplayer {

    FlashcardPriorityQueue myFlashcards;

    /**
 * Creates a flashcard displayer with the flashcards in file.
 * File has one flashcard per line. On each line, the date the flashcard 
 * should next be shown is first (format: YYYY-MM-DDTHH-MM), followed by a comma, 
 * followed by the text for the front of the flashcard, followed by another comma,
 * followed by the text for the back of the flashcard. You can assume that the 
 * front/back text does not itself contain commas. (I.e., a properly formatted file
 * has exactly 2 commas per line.)
 */
    public FlashcardDisplayer(String file) {
        //load the file
        File flashcardFile = new File(file);
        //tests to make sure there is a file, else it will catch to no file error and print a statement
        Scanner flashcardScanner = null;
        //initialize the heap
        this.myFlashcards = new FlashcardPriorityQueue();
        try {
            flashcardScanner = new Scanner(flashcardFile);
        } catch (FileNotFoundException e) {
            System.err.println("Unfortunately there was not a file found. Please check the file exists and is spelled correctly and try again.");
            System.exit(1);
        }
        //next we will load the file into the heap
        String[] infoArray;
        Flashcard newFlashcard;
        while (flashcardScanner.hasNextLine()) {
            //split line at commas
            infoArray = flashcardScanner.nextLine().split(",");
            //create a new flashcard with the new info
            newFlashcard = new Flashcard(infoArray[0],infoArray[1],infoArray[2]);
            //add that flashcard to the heap
            this.myFlashcards.add(newFlashcard);
        }
    }
 
    /**
    * Writes out all flashcards to a file so that they can be loaded
    * by the FlashcardDisplayer(String file) constructor. Returns true
    * if the file could be written. The FlashcardDisplayer should still
    * have all of the same flashcards after this method is called as it
    * did before the method was called. However, it may be that flashcards
    * with the same exact next display date and time are removed in a different order.
    */
    public boolean saveFlashcards(String outFile) {
        //create a temporary queue that we can poll the flashcards from without messing up our flashcard set
        FlashcardPriorityQueue tempQueue = this.myFlashcards;
        if (tempQueue.isEmpty()) {
            //only case we would not write a file is if the flashcard set is empty
            return false;
        }
        //initialize all the variables that will be used
        Flashcard curCard;
        String flashcardContent = null;
        String fileContent = null;
        //load in the file
        File flashcardFile = new File(outFile);
        BufferedWriter newBuff = null;
        //loop over the number of items in heap, polling each one and adding it to a string of everything
        while (tempQueue.getNumItems() != 0) {
            curCard = tempQueue.poll();
            flashcardContent = (curCard.getDueDate().toString() + "," + curCard.getFrontText() + "," + curCard.getBackText());
            fileContent += (flashcardContent + "\n");
        }
        try {
            //try to create a new BufferedWriter and FileWriter to write the flashcard file
            newBuff = new BufferedWriter(new FileWriter(flashcardFile));
            //writes the string in correct format to the file 
            newBuff.write(fileContent);
            newBuff.close();
        } catch (IOException e) {
            //catches an error if cannot write
            e.printStackTrace();
        }

        //returns true since we created the file
        return true;
    }
    
    /**
    * Displays any flashcards that are currently due to the user, and 
    * asks them to report whether they got each card correct. If the
    * card was correct, it is added back to the deck of cards with a new
    * due date that is one day later than the current date and time; if
    * the card was incorrect, it is added back to the card with a new due
    * date that is one minute later than that the current date and time.
    */
    public void displayFlashcards() {
        //initialize scanner and place to take in the input string
        Scanner displayerScan = new Scanner(System.in);
        String input;
        //if we don't have any flashcards
        if (this.myFlashcards.isEmpty()) {
            System.out.println("There are no flashcards in your set.");
        } else {
            //if we have flashcards, check current time
            LocalDateTime curTime = LocalDateTime.now();
            //check time of the top flashcard
            Flashcard curCard = this.myFlashcards.poll();
            //while the dueDate is equal or less than the current time
            while (curCard.getDueDate().equals(curTime) || curCard.getDueDate().compareTo(curTime) < 0) {
                //display the front of the card
                System.out.println("Card:\n" + curCard.getFrontText());
                //wait until user wants to go onto the definition/back of the card
                System.out.println("[Press return for back of card]");
                //ask user to press return to satisfy the scanner intake
                input = displayerScan.nextLine();
                //we then proceed to display the back of the card
                System.out.println(curCard.getBackText());
                //takes in input for if they got the flashcard right
                System.out.println("Press 1 if you got the card correct and 2 if you got the card incorrect.");
                input = displayerScan.nextLine();
                //changes dueDate of the card and returns it to the heap
                if (input.equals("1")) {
                    //if user gets card correct, make dueDate large so it comes due later
                    curCard.setDueDate(LocalDateTime.now().minusDays(-1));
                    //add it back into the set so user can still study it if we want
                    this.myFlashcards.add(curCard);
                    
                } else if (input.equals("2")) {
                    //we add a minute to the dueDate of the current card
                    curCard.setDueDate(LocalDateTime.now().minusMinutes(-1));
                    //add it back into the flashcard heap
                    this.myFlashcards.add(curCard);
                } else {
                    System.out.println("I'm sorry, that was not a valid input. We went ahead and readded the flashcard for you, but please provide a correct input next time.");
                    this.myFlashcards.add(curCard);
                }
                //we know we want to display the card so poll it from the top of the heap
                curCard = this.myFlashcards.poll();
            }
            System.out.println("Come back in a little to study more flashcards!");
        }
    }
    public static void main(String[] args) {
        Scanner newScanner = new Scanner(System.in);
        //make sure the user gives a file to load from
        if (args.length==0) {
            System.out.println("Please include a file of flashcards to load from.");
            System.exit(1);
        }
        //create FlashcardDisplayer
        FlashcardDisplayer myFlashcardSet = new FlashcardDisplayer(args[0]);
        //print out info
        System.out.println("\nTime to practice flashcards! The computer will display your flashcards, you generate the response in your head, and then see if you got it right. The computer will show you cards that you miss more often than those you know!\n");
        //to take in input from user
        String input = "";
        //loop until user says to exit
        while (!input.equals("exit")) {
            System.out.println("Enter a command:");
            input = newScanner.next();
            if (input.equals("quiz")) {
                myFlashcardSet.displayFlashcards();
            } else if (input.equals("save")) {
                System.out.println("Type a filename where you'd like to save the flashcards:");
                input = newScanner.next();
                myFlashcardSet.saveFlashcards(input);
            } else if (input.equals("exit")) {
                System.out.println("Goodbye!");
                System.exit(1);
            } else {
                System.out.println("Invalid command, please try again.\n");
            }
        }
        newScanner.close();
    }
}