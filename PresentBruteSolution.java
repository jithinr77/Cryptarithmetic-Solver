
package CryptarithmeticSolver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class PresentBruteSolution {

    ObservableList<String> solutionsObserverList = FXCollections.observableArrayList();

    public PresentBruteSolution(BruteSolve solution, ListView solutionsListView) {

        if (solution.correctSolutionsArray.size() > 0) {
            for (int i = 0; i < solution.correctSolutionsArray.size(); i++) {
                solutionsObserverList.add(i + 1 + ": " + solution.correctSolutionsArray.get(i) + "");
            }
        } else {
            solutionsObserverList.add("No solutions were found");
        }
        solutionsListView.setItems(solutionsObserverList);
    }

    public void updateStructureLabels(Label wordLabel, Preperation problem, BruteSolve solution) {
        String labelText = "";
        for (int i = 0; i < problem.wordsArray.length; i++) {
            //add the operator symbon before the last word
            if (i == problem.wordsArray.length - 1) {

                labelText = labelText + problem.operator;
            }
            labelText = labelText + problem.wordsArray[i] + "\n";
        }
        labelText += "----------" + "\n";
        labelText += problem.summationWord;
        wordLabel.setText(labelText);
    }

    public void updateAnswerLabels(Label summationLabel, Preperation problem, BruteSolve solution) {
        String labelText = "";
        for (int i = 0; i < problem.wordMap.size(); i++) {
            if (i == problem.wordsArray.length - 1) {

                labelText = labelText + problem.operator;
            }
            for (int j = 0; j < problem.wordMap.get("word" + i).length; j++) {
                labelText = labelText + solution.correctSolutionsArray.get(0).get(problem.letterMap.get("W" + i + "L" + j));
            }
            labelText += "\n";
        }
        labelText += "----------" + "\n";
        for (int i = 0; i < problem.summationWord.length(); i++) {
            labelText += solution.correctSolutionsArray.get(0).get(problem.summationWord.substring(i, i + 1));
        }
        summationLabel.setText(labelText);
    }

    public void updateTimeTakenLabels(Label summationLabel, Preperation problem, BruteSolve solution) {
    }

}
