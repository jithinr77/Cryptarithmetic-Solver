package CryptarithmeticSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Preperation extends FXMLDocumentController {

    String[] summationLetters; //array of letter from textfield2(summation).
    char operator; //variable to store the chosen operator;
    String base; //variable to store the chosen operator;
    public String[] wordsArray; //array of words from textfield
    public String summationWord; //array of words from textfiel1

    public String[][] smartMatrix;

    ArrayList<String> uniqueLettersList = new ArrayList<String>();

    Map<String, Word> wordMap = new HashMap<>();
    Map<String, String> letterMap = new HashMap<>();

    //map to store all the words from wordField
    //constructor function
    public Preperation(String wordField, String summationField, char operation, String baseValue) {

        operator = operation;
        base = baseValue;
        System.out.println("base: " + base);
        splitWord(wordField);//fill wordMap with words from the first text field
        summationLetters = splitLetters(summationField);//split the letters from the summation field into an array of strings
        summationWord = summationField.toUpperCase();
        // split the words in the wordMap into letters and create the letterMap
        for (int i = 0; i < wordMap.size(); i++) {
            for (int j = 0; j < wordMap.get("word" + i).length; j++) {
                String temp;
                temp = splitLetters(wordMap.get("word" + i).getName())[j];
                letterMap.put("W" + i + "L" + j, temp);
                if (!uniqueLettersList.contains(temp)) {
                    uniqueLettersList.add(temp);
                }
            }

        }
        //add the letters from the summation field into a map
        for (int i = 0; i < summationLetters.length; i++) {
            letterMap.put("S1l" + i, summationLetters[i]);
            if (!uniqueLettersList.contains(summationLetters[i])) {
                uniqueLettersList.add(summationLetters[i]);
            }
        }
        System.out.println(letterMap);
        System.out.println(letterMap.get("W0l0"));
    }

    //function to split wordField text into an array of words
    private void splitWord(String wordField) {
        wordsArray = new String[wordField.length()];
        wordsArray = wordField.split(", *");
        System.out.println("chk1: wordsArray: " + Arrays.toString(wordsArray));
        //wordMap(words);
        for (int i = 0; i < wordsArray.length; i++) {
            wordsArray[i] = wordsArray[i].toUpperCase();
            wordMap.put("word" + i, new Word(wordsArray[i]));//convert each word to upperCase and add it to wordMap
        }

    }

    //function that returns an array of all characters from a word in uppercase
    private String[] splitLetters(String word) {
        String[] temp = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            temp[i] = Character.toString(word.charAt(i)).toUpperCase();
        }
        return temp;
    }

    //function that return the length of the longest word in the puzzle
    public int findLongestWordLength() {
        int longestWordLength = wordsArray[0].length();

        for (int i = 1; i < wordsArray.length; i++) {
            if (wordsArray[i].length() > wordsArray[i - 1].length()) {
                longestWordLength = wordsArray[i].length();
            }
        }
        if (summationWord.length() > longestWordLength) {
            longestWordLength = summationWord.length();
        }
        return longestWordLength;
    }

    //function to move the word 'empty' to the first column
    public void lastToFirst(String[] array) {
        System.out.println("" + Arrays.deepToString(array));
        String temp = array[array.length - 1];
        for (int i = array.length - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = temp;
        System.out.println("" + Arrays.deepToString(array));
    }

    public void makeSmartMatrix() {
        //create matrix with apropriate sizes
        int colCount = findLongestWordLength();
        int rowCount = wordsArray.length + 2; //a row for each word and one for the summation word and a row for the carry over numbers
        smartMatrix = new String[rowCount][colCount];
        //fill the matrix
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                //do something
                if (i == 0) {
                    //carry row
                    smartMatrix[i][j] = "Carry" + Math.abs(colCount - 1 - j);
                } else if (i == rowCount - 1) {
                    //summation row
                    if (j < summationLetters.length) {
                        smartMatrix[i][j] = summationLetters[j];
                    } else {
                        smartMatrix[i][j] = "blank";
                    }
                } else {
                    //words rows
                    if (j < wordsArray[i - 1].length()) {
                        smartMatrix[i][j] = wordsArray[i - 1].substring(j, j + 1);
                    } else {
                        smartMatrix[i][j] = "blank";
                    }
                }
            }
        }
        
        for (int j = 0; j < findLongestWordLength(); j++) {
            for (int i = 1; i <= wordsArray.length; i++) {
                if ("blank".equals(smartMatrix[i][findLongestWordLength() - 1])) {
                    lastToFirst(smartMatrix[i]);
                }
            }
        }
        System.out.println(Arrays.deepToString(smartMatrix));
    }
}
