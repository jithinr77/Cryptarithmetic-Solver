package CryptarithmeticSolver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FXMLDocumentController implements Initializable {

    @FXML
    public TextField wordField;
    @FXML
    public TextField summationField;
    @FXML
    private Label wordLabel;
    @FXML
    private Label summationLabel;
    @FXML
    private Label timeTaken;
    @FXML
    Label combinationsTried;
    @FXML
    private ComboBox<String> operatorComboBox;
    @FXML
    private ComboBox<String> baseComboBox;
    @FXML
    private ComboBox<String> algorithmComboBox;
    @FXML
    public TreeView<String> optionsTree;
    @FXML
    public ListView solutionsListView;
    @FXML
    public SplitPane splitPane;

    BruteSolve bruteSolution;
    SmartSolve smartSolution;
    PresentBruteSolution showBruteSolutions;
    PresentSmartSolution showSmartSolutions;
    ObservableList<String> operators = FXCollections.observableArrayList(
            "+", "-", "*", "/"
    );

    ObservableList<String> baseOptions = FXCollections.observableArrayList(
            "10", "11", "12", "13", "14", "15", "16"
    );
    ObservableList<String> algorithmOptions = FXCollections.observableArrayList(
            "Brute Force", "Smart Search"
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //add operators to the operator combobox
        operatorComboBox.setItems(operators);
        //set the default operator as +
        operatorComboBox.getSelectionModel().selectFirst();
        //add different bases to the base combobox
        baseComboBox.setItems(baseOptions);
        //set the default base as 10
        baseComboBox.getSelectionModel().selectFirst();
        algorithmComboBox.setItems(algorithmOptions);
        algorithmComboBox.getSelectionModel().selectFirst();

        //fill the tree with sample input
        SampleInput treeData = new SampleInput(optionsTree);
        optionsTree.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {

                    TreeItem<String> selectedItem = optionsTree.getSelectionModel().getSelectedItem();
                    if (newValue != null && selectedItem.isLeaf()) {
                        System.out.println(newValue.getValue());

                        treeData.updateTextfields(newValue, wordField, summationField);

                    }
                });
    }

    @FXML
    private void solveButtonClicked(ActionEvent event) {
        //clear the solutions listView incase it is showing solutions from previous run
        solutionsListView.getItems().clear();
        //clear the labels from any previous solutions
        wordLabel.setText("");
        summationLabel.setText("");
        //check if input is valid
        Validation input = new Validation(wordField.getText(), summationField.getText());
        //if the input is valid, prepare the input into a problem that needs solving
        if (input.isValid) {
            Preperation problem = new Preperation(
                    wordField.getText(),
                    summationField.getText(),
                    operatorComboBox.getSelectionModel().getSelectedItem().toCharArray()[0],
                    baseComboBox.getSelectionModel().getSelectedItem()
            );

            //create the table to for smart() to use
            problem.makeSmartMatrix();
            //check if the problem created from the input is valid
            input.postPreperation(problem);
            //if the problem is valid, it will be shown in isValid within the input class
            if (input.isValid) {
                //create a bran object and pass in the problem to solve it. The solutions will be held in the brain
                if (algorithmComboBox.getSelectionModel().getSelectedIndex() == 0) {
                    bruteSolution = new BruteSolve(problem); //find all solutions
                    showBruteSolutions = new PresentBruteSolution(bruteSolution, solutionsListView);
                    //change label text to show input
                    showBruteSolutions.updateStructureLabels(wordLabel, problem, bruteSolution);
                    showBruteSolutions.updateAnswerLabels(summationLabel, problem, bruteSolution);
                    timeTaken.setText("Time taken to find all solutions: " + Long.toString(bruteSolution.totalTime) + " milliseconds");
                    combinationsTried.setText("Number of combinations tried: " + bruteSolution.combinationsTried);

                } else if (algorithmComboBox.getSelectionModel().getSelectedIndex() == 1) {
                    smartSolution = new SmartSolve(problem);//find all solutions
                    //present the solutions in listView
                    showSmartSolutions = new PresentSmartSolution(smartSolution, solutionsListView);
                    //change label text to show input
                    showSmartSolutions.updateStructureLabels(wordLabel, problem, smartSolution);
                    showSmartSolutions.updateAnswerLabels(summationLabel, problem, smartSolution);
                    timeTaken.setText("Time taken to find all solutions: " + Long.toString(smartSolution.totalTime) + " milliseconds");
                    combinationsTried.setText("Number of combinations tried: " + smartSolution.combinationsTried);
                }
            }
        } else {
            input.makeErrorAlert("The input contains invalid characters");
        }
    }

    //onClick event for the Reset button
    @FXML
    private void resetButtonClicked(ActionEvent event) {
        //reset the operator to default operation
        operatorComboBox.getSelectionModel().selectFirst();
        //reset the base to default base
        baseComboBox.getSelectionModel().selectFirst();
        //reset the text boxes to default value
        wordField.setText("SEND, MORE");
        summationField.setText("MONEY");

        //clear the solutions listView
        solutionsListView.getItems().clear();
    }

}
