import java.time.*;

public class Flashcard implements Comparable<Flashcard>{

    //instance variables for each card
    private LocalDateTime dueDate;
    private String front;
    private String back;

    /**
    * Creates a new flashcard with the given dueDate, text for the front
    * of the card (front), and text for the back of the card (back).
    * dueDate must be in the format YYYY-MM-DDTHH:MM. For example,
    * 2020-05-04T13:03 represents 1:03PM on May 4, 2020. It's
    * okay if this method crashes if the date format is incorrect.
    * In the format above, the time may or may not include milliseconds. 
    * The parse method in LocalDateTime can deal with this situation
    *  without any changes to your code.
    */

    public Flashcard(String dueDate, String front, String back) {
        //initialize each instance variable
        this.dueDate = this.dueDate.parse(dueDate);
        this.front = front;
        this.back = back;
    }
    
    /**
    * Gets the text for the front of this flashcard.
    */
    public String getFrontText() {
        return this.front;
    }
    
    /**
    * Gets the text for the Back of this flashcard.
    */
    public String getBackText() {
        return this.back;
    }
    
    /**
    * Gets the time when this flashcard is next due.
    */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime newDueDate) {
        this.dueDate = newDueDate;
    }
    //comparison method that we have since we are implementing comparable
    public int compareTo(Flashcard card){
        // will implement later, we're thinking we will compare by due date
        return this.getDueDate().compareTo(card.getDueDate());

    }
}