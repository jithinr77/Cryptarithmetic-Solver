
package CryptarithmeticSolver;

import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public final class SampleInput {

    @FXML
    public TextField wordField;
    @FXML
    private TextField summationField;

    public SampleInput(TreeView<String> optionsTree) {

        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);
        TreeItem<String> Easy, Medium, Hard, Addition;

        Addition = makeBranch("Addition", root);        

        Easy = makeBranch("EASY", Addition);
        Medium = makeBranch("MEDIUM", Addition);
        Hard = makeBranch("HARD", Addition);

        makeBranch("A _ B = C", Easy);
        makeBranch("AB _ CA = BD", Easy);
        makeBranch("AB _ CD = EF", Easy);
        makeBranch("SEND + MORE = MONEY", Easy);
        makeBranch("DO + YOU + FEEL = LUCKY", Easy);
        makeBranch("COUNT - COIN = SNUB", Easy);
        makeBranch("BUT + ITS + GOOD = MUSIC", Medium);
        makeBranch("HOCUS _ POCUS = PRESTO", Hard);
        makeBranch("BATMAN _ NIGHT = GOTHAM", Hard);
        makeBranch("SATURN + URANUS = PLANETS", Hard);
        makeBranch("GET _ ALONG _ LITTLE = DOGIES", Hard);
        makeBranch("THREE + THREE + TWO + TWO + ONE = ELEVEN", Hard);
        

        optionsTree.setRoot(root);
        optionsTree.setShowRoot(false);

    }

    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    public void updateTextfields(TreeItem<String> newValue, TextField wordField, TextField summationField) {
//        wordField.setText(newValue.getValue());
        wordField.clear();
        for (int i = 0; i < newValue.getValue().split("  *").length; i++) {
            String sampleInputObject = newValue.getValue().split("  *")[i];

            final Pattern pattern = Pattern.compile("^[A-Za-z, ]++$");

            if (i == newValue.getValue().split("  *").length - 1) {
                if (pattern.matcher(sampleInputObject).matches()) {
                    summationField.setText(sampleInputObject);
                }
            } else {
                if (pattern.matcher(sampleInputObject).matches()) {
                    wordField.setText(wordField.getText() + sampleInputObject + ",");
                }
            }
        }
    }
}
