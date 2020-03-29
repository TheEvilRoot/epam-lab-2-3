package com.theevilroot.epam.lab23.gui;

import com.theevilroot.epam.lab23.api.ListObserver;
import com.theevilroot.epam.lab23.api.ObservableValue;
import com.theevilroot.epam.lab23.api.Observer;
import com.theevilroot.epam.lab23.model.Human;
import com.theevilroot.epam.lab23.model.exceptions.TooDifficultException;
import com.theevilroot.epam.lab23.model.expression.ExpressionOperator;
import com.theevilroot.epam.lab23.model.expression.NumericExpression;
import com.theevilroot.epam.lab23.model.numbers.AComplexNumber;
import com.theevilroot.epam.lab23.model.numbers.EComplexNumber;
import com.theevilroot.epam.lab23.model.numbers.Number;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUI extends Application {

    private ObservableValue<Human> human = new ObservableValue<>();
    private Observer<Human> humanObserver = this::onHumanChanged;

    private NumericExpression expression = new NumericExpression(ExpressionOperator.ADD);

    private Observer<ExpressionOperator> operatorObserver = this::onOperatorChanged;
    private ListObserver<Number> operandsObserver = new ListObserver<Number>() {
        @Override
        public void onAdded(int index, Number newValue) {
            expressionList.getItems().add(newValue.toString());
        }

        @Override
        public void onRemoved(int index) {
            expressionList.getItems().remove(index);
        }

        @Override
        public void onCleared() {
            expressionList.getItems().clear();
        }

        @Override
        public void onValueChanged(List<Number> newValue) {  }
    };

    private GridPane root;
    private Button humanButton;
    private Label humanDifficultyLabel;
    private ListView<String> expressionList;
    private Button calculateButton;
    private Button expressionClearButton;
    private Button expressionOperatorButton;
    private Button numberAddButton;
    private Button numberRemoveButton;


    @Override
    public void start(Stage stage) throws Exception {
        // Compile hierarchy
        // root setup
        root = new GridPane();
        root.setHgap(10d);
        root.setVgap(10d);
        root.setPadding(new Insets(10d));

        // humanButton setup
        humanButton = new Button();

        // humanDifficultyLabel setup
        humanDifficultyLabel = new Label();

        // expressionList setup
        expressionList = new ListView<>();

        // calculateButton setup
        calculateButton = new Button("Calculate");

        // expressionClearButton setup
        expressionClearButton = new Button("Clear");

        // expressionOperatorButton setup
        expressionOperatorButton = new Button();

        // numberAddButton setup
        numberAddButton = new Button("Add number");

        // numberRemoveButton
        numberRemoveButton = new Button("Remove number");

        root.add(humanButton, 0, 0);
        root.add(humanDifficultyLabel, 1, 0, 4, 1);
        root.add(expressionList, 0, 1, 3, 3);
        root.add(calculateButton, 3, 1, 1, 1);
        root.add(expressionOperatorButton, 3, 2, 1, 1);
        root.add(numberAddButton, 0, 4, 1, 1);
        root.add(numberRemoveButton, 1, 4, 1, 1);
        root.add(expressionClearButton, 2, 4, 1, 1);

        // Setup scene
        Scene scene = new Scene(root);

        // Setup stage
        stage.setScene(scene);

        // Create events
        human.subscribe(humanObserver);
        expression.getOperator().subscribe(operatorObserver);
        expression.getOperands().subscribe(operandsObserver);

        numberAddButton.setOnMouseClicked(e -> addNumber());
        numberRemoveButton.setOnMouseClicked(e -> removeNumber());
        expressionOperatorButton.setOnMouseClicked(e -> setOperator());
        expressionClearButton.setOnMouseClicked(e -> clearExpression());
        humanButton.setOnMouseClicked(e -> createHuman(human.getValue()));
        calculateButton.setOnMouseClicked(e -> calculate());
        // Run
        stage.show();
    }

    private void calculate() {
        if (human.getValue() == null) {
            showError("No human!", "Create a human to perform calculations.");
        } else {
            try {
                String result = human.getValue().evaluateExpression(expression);
                showMessage("Result", human.getValue().getName() + " just calculated this expression.", "Result is " + result);
            } catch (TooDifficultException e) {
                showError("Too difficult", e.getLocalizedMessage());
            }
        }
    }

    private void createHuman(Human prevHuman) {
        Dialog<Human> dialog = new Dialog<>();
        dialog.setTitle("Create human");

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField difficulty = new TextField();
        difficulty.setPromptText("Max difficulty");

        if (prevHuman != null) {
            name.setText(prevHuman.getName());
            difficulty.setText(String.valueOf(prevHuman.getMaxDifficulty()));
        }


        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Max difficulty:"), 0, 1);
        grid.add(difficulty, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(name::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                int diff;
                try {
                    diff = Integer.parseInt(difficulty.getText());
                } catch (NumberFormatException e) {
                    showError("Create human", "Invalid input");
                    return null;
                }

                if (name.getText().isEmpty())
                    return null;
                return new Human(name.getText(), diff);
            }
            return null;
        });

        Optional<Human> result = dialog.showAndWait();

        result.ifPresent(value -> human.setValue(value));
    }

    private void removeNumber() {
        if (expressionList.getSelectionModel().getSelectedIndex() >= 0) {
            expression.removeOperand(expressionList.getSelectionModel().getSelectedIndex());
        }
    }

    private void clearExpression() {
        expression.clear();
    }

    private void addNumber() {
        String choice = enumChoice("Add number", "Choose type of the number.",
                Stream.of(NumberType.values()).map(s -> s.value).collect(Collectors.toList()), 0);
        if (choice != null) {
            addNumber(NumberType.fromValue(choice));
        }
    }

    private void addNumber(NumberType type) {
        Dialog<Number> dialog = new Dialog<>();
        dialog.setTitle("Add " + type.value);

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField first = new TextField();
        TextField second = new TextField();

        switch (type) {
            case ALGEBRAIC:
                first.setPromptText("Real");
                second.setPromptText("Imaginary");
            break;
            case EXPONENTIAL:
                first.setPromptText("Modulus");
                second.setPromptText("Exponent");
                break;
            case COMMON:
                first.setPromptText("Value");
                second.setVisible(false);
                second.setText("0");
                break;
        }


        grid.add(new Label(first.getPromptText() + ":"), 0, 0);
        grid.add(first, 1, 0);
        if (type != NumberType.COMMON) {
            grid.add(new Label(second.getPromptText() + ":"), 0, 1);
            grid.add(second, 1, 1);
        }

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(first::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Double a;
                Double b;
                try {
                    a = Double.valueOf(first.getText());
                    b = Double.valueOf(second.getText());
                } catch (NumberFormatException e) {
                    showError("Add number", "Invalid input");
                    return null;
                }

                switch (type) {
                    case ALGEBRAIC:
                        return new AComplexNumber(a, b);
                    case EXPONENTIAL:
                        return new EComplexNumber(a, b);
                    case COMMON:
                        return new Number(a);
                }
            }
            return null;
        });

        Optional<Number> result = dialog.showAndWait();
        result.ifPresent(number -> expression.addOperands(number));
    }

    private void setOperator() {
        showExpressionDialog();
    }

    private void onHumanChanged(Human newHuman) {
        if (newHuman == null) {
            humanButton.setText("[No human]");
            humanDifficultyLabel.setText("Click the button to create a new one");
        } else {
            humanButton.setText(newHuman.getName());
            humanDifficultyLabel.setText("Max difficulty " + newHuman.getMaxDifficulty());
        }
    }

    private void onOperatorChanged(ExpressionOperator newOperator) {
        expressionOperatorButton.setText("Operator " + newOperator.value);
    }

    private void showExpressionDialog() {
        String choice = enumChoice("Change operator", "Choose a new operator.",
                Stream.of(ExpressionOperator.values()).map(s -> s.value).collect(Collectors.toList()), 0);
        if (choice != null) {
            expression.setOperator(ExpressionOperator.fromValue(choice));
        }
    }

    private String enumChoice(String title, String content, List<String> values, int def) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(values.get(def), values);
        dialog.setTitle(title);
        dialog.setContentText(content);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
