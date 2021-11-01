import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class GUI extends Application {
    static ArrayList<Perceptron> perceptrons;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Language App");

        TextArea example = new TextArea();
        example.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                AtomicReference<Perceptron> max = new AtomicReference<>();
                String test  = example.getText().replaceAll("[^a-zA-Z]", "");
                perceptrons.forEach(perceptron -> {
                    double res = perceptron.solveScalar(new Language().addData(test));
                    if (max.get() == null) max.set(perceptron);
                    if (res > max.get().solveScalar(new Language().addData(test))) max.set(perceptron);
                });
                Alert alert = new Alert(Alert.AlertType.INFORMATION, max.get().result);
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> example.clear());
            }
        });


        VBox main = new VBox(example);

        Scene scene = new Scene(main, 360, 240);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void launch(String[] args) {
        Application.launch(args);
    }
}