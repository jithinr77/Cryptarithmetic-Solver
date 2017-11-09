package CryptarithmeticSolver;

import java.util.regex.Pattern;
import javafx.scene.control.Alert;

public final class Validation {

    boolean isValid = false;
    final Pattern pattern = Pattern.compile("^[A-Za-z, ]++$");

    /*
    constructor checks if there are any invalid characters 
    in the input strings and changes the flag isValid accordingly
     */
    public Validation(String input1, String input2) {
        boolean temp1 = pattern.matcher(input1).matches();
        boolean temp2 = pattern.matcher(input2).matches();
        if (temp1 && temp2) {
            isValid = true;
        }
    }

    /*
    funtion to make alert box
     */
    public void makeErrorAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error!");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    //check if the input is valid after preparing the input into words
    public void postPreperation(Preperation problem) {
        //check if any of the words are too long
        switch (problem.operator) {
            //validation for addition operation
            case '+':
                int longestPositiveWordIndex = 0;
                //for every word from the addents, 
                for (int i = 0; i < problem.wordsArray.length; i++) {

                    //check if it is longer than the current longest word and update if it is not
                    if (problem.wordsArray[longestPositiveWordIndex].length() < problem.wordsArray[i].length()) {
                        longestPositiveWordIndex = i;
                    }

                    //check if it is longer than the summation word
                    if (problem.wordsArray[i].length() > problem.summationLetters.length) {
                        makeErrorAlert("The summation word needs to be the longest word.");
                        isValid = false;
                    }
                }
                //check if the summation word is too long#
                if (problem.summationLetters.length > problem.wordsArray[longestPositiveWordIndex].length() + 1) {
                    makeErrorAlert("The summation word is too long");
                    isValid = false;
                }

                break;
            //validation for substraction operation
            case '-':
                int longestNegativeWordIndex = 0;
                //for every word from the addents, 
                for (int i = 0; i < problem.wordsArray.length; i++) {

                    //check if it is longer than the current longest word and update if it is not
                    if (problem.wordsArray[longestNegativeWordIndex].length() < problem.wordsArray[i].length()) {
                        longestNegativeWordIndex = i;
                    }
                }
                //check if the summation word is too long#
                if (problem.summationLetters.length > problem.wordsArray[longestNegativeWordIndex].length()) {
                    makeErrorAlert("The summation word is too long");
                    isValid = false;
                }
                break;
        }
        /*
        check if there are too many characters or not
         */
        if (problem.uniqueLettersList.size() > Integer.parseInt(problem.base)) {
            makeErrorAlert("Too many unique characters. No more than the base is allowed");
            isValid = false;
        }
        System.out.println("unique Letters: " + problem.uniqueLettersList);
        System.out.println("Integer.parseInt(problem.base): " + Integer.parseInt(problem.base));

    }
}
