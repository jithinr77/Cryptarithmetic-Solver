
package CryptarithmeticSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class BruteSolve {

    int base = 0;
    Map<String, Integer> assignedMap = new HashMap<>();
    List<Map<String, Integer>> correctSolutionsArray;
    ArrayList<Integer> availableNumbers;
    ArrayList<String> availableLetters;
    Preperation problem;
    long totalTime;
    int combinationsTried;

    public BruteSolve(Preperation preparedPuzzle) {
        problem = preparedPuzzle;
        base = Integer.parseInt(problem.base);

        availableNumbers = new ArrayList<Integer>();
        correctSolutionsArray = new ArrayList<Map<String, Integer>>();

        for (int i = 0; i < base; i++) {
            availableNumbers.add(i);
        }
        availableLetters = new ArrayList<String>();
        availableLetters = problem.uniqueLettersList;

        long startTime = System.currentTimeMillis();//start timer
        Brute(); //run the AI
        long endTime = System.currentTimeMillis();//end timer
        totalTime = endTime - startTime;//calculate time taken
        System.out.println("total time: " + totalTime);//print time taken
    }

    public boolean Brute() {

        if (availableLetters.isEmpty()) {//if there are no more letters to assign
            combinationsTried++;
            boolean firstLetterZero = false;
            //check if the first letter of every assigned word is greater than 0
            for (int i = 0; i < problem.wordsArray.length; i++) {
                if (assignedMap.get(problem.wordsArray[i].substring(0, 1)) < 1) {
                    firstLetterZero = true;
                }
            }
            if (assignedMap.get(problem.summationLetters[0]) < 1) {
                firstLetterZero = true;
            }

            //if no first letter is zero,
            if (!firstLetterZero) {
                if (solve(assignedMap)) {
                    correctSolutionsArray.add(new HashMap<>(assignedMap));
                }
            }
            return false;
        } else {
            ArrayList<String> currentList;
            String currentLetter;

            for (int digit = 0; digit < base; digit++) {
                currentList = new ArrayList<String>(availableLetters);
                currentLetter = currentList.get(0);

                if (assign(currentLetter, digit)) {
                    if (Brute()) {
                        return true;
                    } else {
                        unAssign(currentLetter, digit);
                    }
                }
            }
            return false;
        }

    }

    public boolean assign(String currentLetter, int digit) {

        if (availableNumbers.contains(digit)) {

            assignedMap.put(currentLetter, digit);
            availableLetters.remove(currentLetter);
            availableNumbers.remove(availableNumbers.indexOf(digit));
            return true;
        } else {
            return false;
        }
    }

    public void unAssign(String lettersToUnAssign, int digit) {

        assignedMap.remove(lettersToUnAssign);
        availableLetters.add(0, lettersToUnAssign);
        availableNumbers.add(digit);

    }

    public boolean solve(Map<String, Integer> assignedMap) {
        int singleWord = 0;
        int summationWord = 0;
        int wordSum = 0;
        for (int i = 0; i < problem.wordsArray.length; i++) {

            for (int j = 0; j < problem.wordsArray[i].length(); j++) {
                singleWord += assignedMap.get(problem.wordsArray[i].substring(j, j + 1)) * Math.pow(base, problem.wordsArray[i].length() - j - 1);
            }

            if (i == 0) {
                wordSum = singleWord;
            } else {
                switch (problem.operator) {
                    case '+':
                        wordSum += singleWord;
                        break;
                    case '-':
                        wordSum = wordSum - singleWord;
                        break;
                    case '*':
                        wordSum = wordSum * singleWord;
                        break;
                    case '/':
                        wordSum = wordSum / singleWord;
                        break;
                }
            }

            singleWord = 0;
        }

        for (int i = 0; i < problem.summationLetters.length; i++) {
            summationWord += assignedMap.get(problem.summationLetters[i]) * Math.pow(base, problem.summationLetters.length - i - 1);
        }
        return wordSum == summationWord;
    }
}
