package CryptarithmeticSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class SmartSolve {

    Map<String, Integer> assignedMap = new HashMap<>();
    Map<String, Integer> carryMap = new HashMap<>();
    List<Map<String, Integer>> correctSolutionsArray;
    ArrayList<Integer> availableNumbers;
    ArrayList<String> availableLetters;
    Preperation problem;
    int base;
    long totalTime;
    int combinationsTried;

    public SmartSolve(Preperation preparedPuzzle) {
        problem = preparedPuzzle;
        base = Integer.parseInt(problem.base);
        //create an array list to hold the list of numbers that can be assigned. This will act as the domain for all variables
        availableNumbers = new ArrayList<Integer>();
        //create and aray to 
        correctSolutionsArray = new ArrayList<Map<String, Integer>>();

        for (int i = 0; i < base; i++) {
            availableNumbers.add(i);
        }

        for (int i = 0; i < problem.findLongestWordLength(); i++) {
            carryMap.put("Carry" + i, 0);
        }
        availableLetters = new ArrayList<String>();
        availableLetters = problem.uniqueLettersList;

        long startTime = System.currentTimeMillis();//start timer
        Smart();
        long endTime = System.currentTimeMillis();//end timer
        totalTime = endTime - startTime;//calculate and store time taken  
    }

    public boolean Smart() {
        int summationLetterValue = 0;
        boolean assigned = false;
        boolean summationValueAssigned = false;
        int assignedCoordinates[] = new int[2];
        ArrayList<String> smmationLettersAssigned = new ArrayList<String>(problem.summationLetters.length);
        for (int i = 0; i < problem.summationLetters.length; i++) {
            smmationLettersAssigned.add("");
        }
        if (availableLetters.isEmpty()) {//if there are no more letters to assign
            //increase the number of combinations tried variable by 1
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
                if (solve(assignedMap)) {//check if the puzzle is solved
                    correctSolutionsArray.add(new HashMap<>(assignedMap));
                }
            }
            return false;
        } else {
            ArrayList<String> currentList;
            String currentLetter = "";
            for (int digit = 0; digit < base; digit++) {
                currentList = new ArrayList<String>(availableLetters);
                int rowNumber = 0;
                int colNumber = 0;
                matrixLoop:
                for (int j = problem.findLongestWordLength() - 1; j >= 0; j--) {
                    for (int i = 1; i < problem.smartMatrix.length; i++) {
                        //if current matrix position is for summation letter
                        summationValueAssigned = !availableLetters.contains(problem.smartMatrix[problem.smartMatrix.length - 1][j]);
                        if (i == problem.smartMatrix.length - 1) {
                            //summation letter is not assigned
                            if (availableLetters.contains(problem.smartMatrix[i][j])) {
                                summationLetterValue = 0;
                                summationLetterValue = getSummationValue(i, j);

                                if (getSummationValue(i, j) >= base) {
                                    summationLetterValue = summationLetterValue % base;
                                }
                                if (assign(problem.smartMatrix[i][j], summationLetterValue)) {
                                    if (getSummationValue(i, j) >= base) {
                                        carryMap.put("Carry" + (j - 1), Math.floorDiv(getSummationValue(i, j), base));
                                    }
                                    assigned = true;
                                    assignedCoordinates[0] = i;
                                    assignedCoordinates[1] = j;

                                } else {
                                    return false;
                                }
                            } else if (getSummationValue(i, j) >= base) {
//                                return false;
                                carryMap.put("Carry" + (j - 1), Math.floorDiv(getSummationValue(i, j), base));
                            } else if (getSummationValue(i, j) < base) {
                                carryMap.put("Carry" + (j - 1), 0);
                            }
                        } else if (availableLetters.contains(problem.smartMatrix[i][j])) {
                            currentLetter = problem.smartMatrix[i][j];
                            rowNumber = i;
                            colNumber = j;
                            summationValueAssigned = !availableLetters.contains(problem.smartMatrix[problem.smartMatrix.length - 1][j]);

                            for (int k = 0; k < problem.summationLetters.length; k++) {
                                if (!availableLetters.contains(problem.summationLetters[k])) {
                                    smmationLettersAssigned.add(k, problem.summationLetters[k]);
                                }
                            }

                            break matrixLoop;
                        }
                    }
                }

                if (assign(currentLetter, digit)) {
                    if (Smart()) {

                        return true;
                    } else {
                        unAssign(currentLetter, digit);
                        for (int k = 0; k < problem.summationLetters.length; k++) {
                            if (!smmationLettersAssigned.contains(problem.summationLetters[k]) && assignedMap.containsKey(problem.summationLetters[k])) {
                                unAssign(problem.summationLetters[k], assignedMap.get(problem.summationLetters[k]));
                                carryMap.put("Carry" + (k - 1), 0);
                            }
                        }

                    }
                } else if ("".equals(currentLetter)) {
                    if (Smart()) {
                        return true;
                    } else {
                        if (assignedCoordinates[0] == problem.smartMatrix.length - 1 && assignedCoordinates[1] == 0) {
                            rowNumber = problem.smartMatrix.length - 1;
                            colNumber = 0;

                            unAssign(problem.smartMatrix[rowNumber][colNumber], assignedMap.get(problem.smartMatrix[rowNumber][colNumber]));
                        }
                        return false;
                    }
                }
            }
            return false;
        }
    }

    public int getSummationValue(int i, int j) {
        int summationLetterValue = 0;
        for (int k = 1; k <= problem.wordsArray.length; k++) {
            if ("blank".equals(problem.smartMatrix[i - k][j])) {

            } else {
                summationLetterValue += assignedMap.get(problem.smartMatrix[i - k][j]);
            }
        }
        summationLetterValue += carryMap.get("Carry" + j);
        return summationLetterValue;
    }

    public boolean assign(String currentLetter, int digit) {
        if (availableNumbers.contains(digit) && availableLetters.contains(currentLetter)) {

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
//        System.out.println("sboolge: " + problem.wordsArray[0].substring(0, 1));
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
