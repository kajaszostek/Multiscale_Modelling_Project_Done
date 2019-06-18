package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class SecondWindow extends Application {

    @FXML
    private Canvas canvas;

    @FXML
    private GraphicsContext graphicsContext;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("secondWindow.fxml"));
        primaryStage.setTitle("RESULTS");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
        showGrid();

    }

    void showGrid()
    {
//        Controller controller = new Controller();
//
//        int si = Integer.parseInt(controller.sizeText.getText());
//        int ste = Integer.parseInt(controller.stepText.getText());
//        int ruleNumber = Integer.parseInt(controller.rule.getText());
//
//        Grid grid = new Grid(si, ste, canvas);
//
//        Rule rule = new Rule(ruleNumber, grid.array, si, ste);
//
//        rule.showArray(grid.array, si, ste);
//
//        canvas.setHeight(grid.steps*5);
//        canvas.setWidth(grid.size*5);
//        graphicsContext = canvas.getGraphicsContext2D();
//        grid.draw(graphicsContext, canvas);
    }

}
