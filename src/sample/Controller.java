package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public TextArea sizeText;

    @FXML
    public TextArea stepText;

    @FXML
    private Canvas canvas;

    @FXML
    public TextArea rule;

    @FXML
    private GraphicsContext graphicsContext;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private ChoiceBox<String> choiceTemplate;

    @FXML
    private ChoiceBox<String> choiceCAStandard;

    @FXML
    private ChoiceBox<String> choiceBorderConditions;

    @FXML
    private TextArea text1;

    @FXML
    private TextArea text2;

    @FXML
    private TextArea percent;


    private ObservableList<String> possibilities = FXCollections.observableArrayList("1D", "Moore", "VonNeumann", "Hexagonal Right", "Hexagonal Left", "Random Hexagonal", "Pentagonal Up", "Pentagonal Down", "Pentagonal Right", "Pentagonal Left", "Random Pentagonal");

    private ObservableList<String> optionsChoiceTemplate = FXCollections.observableArrayList("Glider", "Oscillator", "Random", "Const", "OnClick");

    private ObservableList<String> optionsCAStandard = FXCollections.observableArrayList("Random", "OnClick");

    private ObservableList<String> optionsBC = FXCollections.observableArrayList("Absorbing", "Periodic");

    int size;
    int steps;
    int ruleNumber;
    int durationMoore = 1000;
    int durationNeumann = 1000;
    int durationHexagonalLeft = 1000;
    int durationHexagonalRight = 1000;
    int durationPentagonalUp = 1000;
    int durationPentagonalDown = 1000;
    int durationPentagonalLeft = 1000;
    int durationPentagonalRight = 1000;

    int cellsValue = 2;

    Grid grid, energyGrid;
    Rule ruleForFunction;

    KeyFrame keyFrame;
    LinkedList colors = new LinkedList<Color>();

    Timeline timeline;
    Timeline timelineMooreCA;
    Timeline timelineNumannCA;
    Timeline timelineHexagonalRightCA;
    Timeline timelineHexagonalLeftCA;
    Timeline timelinePentagonalUpCA;
    Timeline timelinePentagonalDownCA;
    Timeline timelinePentagonalLeftCA;
    Timeline timelinePentagonalRightCA;

    @FXML
    private void initialize() {
        choiceBox.setItems(possibilities);
        choiceTemplate.setItems(optionsChoiceTemplate);
        choiceCAStandard.setItems(optionsCAStandard);
        choiceBorderConditions.setItems(optionsBC);
        choiceBorderConditions.setDisable(true);
        colors.add(Color.BLACK);
        colors.add(Color.SANDYBROWN);
//        size = Integer.parseInt(sizeText.getText());
//        steps = Integer.parseInt(stepText.getText());
        setSettings();
    }

    //First part

    @FXML
    public void finalStepZero(ActionEvent actionEvent) {

        if (choiceBox.getValue() == "1D") {
            canvas.setOnMouseClicked(null);
            setSettings();
        } else if (choiceBox.getValue() == "Moore") {
            setSettings();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    grid.cells[i][j].setNeighborhood(i, j, grid, 1);
                }
            }
            setOnClick();
        } else if (choiceBox.getValue() == "VonNeumann") {

            setSettings();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    grid.cells[i][j].setNeighborhood(i, j, grid, 2);
                }
            }
            setOnClick();
        }

    }

    @FXML
    void pauseTimeline(ActionEvent event) {
        timeline.stop();
    }

    void setTimeline() {
        EventHandler<ActionEvent> eventHandler = event -> {

            try {
                checkMooreNeighborhood();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        keyFrame = new KeyFrame(new Duration(durationMoore), eventHandler);
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    void setOnClick() {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double x = event.getScreenX();
                double y = event.getScreenY();
                System.out.println(x);
                System.out.println(y);
                grid.findAndChange(x, y, graphicsContext);
            }
        });
    }

    void setSettings() {
        size = Integer.parseInt(sizeText.getText());
        steps = Integer.parseInt(stepText.getText());
        ruleNumber = Integer.parseInt(rule.getText());

        grid = new Grid(size, steps, canvas);

        ruleForFunction = new Rule(ruleNumber, grid.cells, size, steps);

        ruleForFunction.showArray(grid.cells, size, steps);

        graphicsContext = canvas.getGraphicsContext2D();

        grid.draw(graphicsContext);
    }

    public void startDrawing(ActionEvent actionEvent) throws InterruptedException {

        if (choiceTemplate.getValue() == "Glider") {

            if (grid.size < 6 || grid.steps < 5) {

                gridAlert();

            } else {
                drawGlider();
                setTimeline();
                timeline.play();
            }

        } else if (choiceTemplate.getValue() == "Oscillator") {

            if (grid.size <= 5 || grid.steps <= 5) {

                gridAlert();

            } else {
                drawOscillator();
                setTimeline();
                timeline.play();
            }

        } else if (choiceTemplate.getValue() == "Random") {

            drawRandom();
            setTimeline();
            timeline.play();

        } else if (choiceTemplate.getValue() == "Const") {
            if (grid.size <= 6 || grid.steps <= 5) {

                gridAlert();

            } else {
                drawConst();
                setTimeline();
                timeline.play();
            }
        } else if (choiceTemplate.getValue() == "OnClick") {

            setTimeline();
            timeline.play();

        }


    }

    public void checkMooreNeighborhood() throws InterruptedException {

        int alive;

        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[i][j].value = grid.cells[i][j].value;
            }
        }


        //PAMIETAJ O WARUNKACH BRZEGOWYCH LOVE YOU MY DARLING, KISS, KISS, KISS
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                alive = 0;

                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        alive = alive + grid.cells[modulo(x + i, size)][modulo(y + j, steps)].value;
                    }
                }
                alive = alive - grid.cells[i][j].value;

                if (grid.cells[i][j].value == 0 && alive == 3) tmpGrid.cells[i][j].value = 1;
                else if (grid.cells[i][j].value == 1 && alive < 2) tmpGrid.cells[i][j].value = 0;
                else if (grid.cells[i][j].value == 1 && alive > 3) tmpGrid.cells[i][j].value = 0;
                else tmpGrid.cells[i][j].value = grid.cells[i][j].value;
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }
        grid.draw(graphicsContext);
    }

    private void drawRandom() {

        Grid tmpGrid = grid;

        setGridValuesZeroes(tmpGrid);

        Random generator = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[i][j].value = generator.nextInt(2);
            }
        }

        tmpGrid.draw(graphicsContext);
    }

    private void drawGlider() {
        Grid tmpGrid = grid;
        setGridValuesZeroes(tmpGrid);

        tmpGrid.cells[2][1].value = 1;
        tmpGrid.cells[3][1].value = 1;
        tmpGrid.cells[1][2].value = 1;
        tmpGrid.cells[2][2].value = 1;
        tmpGrid.cells[3][3].value = 1;
    }

    private void drawOscillator() {

        Grid tmpGrid = grid;

        setGridValuesZeroes(tmpGrid);

        tmpGrid.cells[1][1].value = 1;
        tmpGrid.cells[2][1].value = 1;
        tmpGrid.cells[3][1].value = 1;

        tmpGrid.draw(graphicsContext);
    }

    private void drawConst() {

        Grid tmpGrid = grid;

        setGridValuesZeroes(tmpGrid);

        tmpGrid.cells[2][1].value = 1;
        tmpGrid.cells[3][1].value = 1;
        tmpGrid.cells[1][2].value = 1;
        tmpGrid.cells[4][2].value = 1;
        tmpGrid.cells[2][3].value = 1;
        tmpGrid.cells[3][3].value = 1;

        tmpGrid.draw(graphicsContext);

    }

    public void gridAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Grid warning");
        alert.setHeaderText(null);
        alert.setContentText("Your grid is too small!");
        alert.initStyle(StageStyle.UTILITY);


        alert.showAndWait();
    }

    public int modulo(int x, int m) {
        int result = (x % m + m) % m;
        return result;
    }

    public void setGridValuesZeroes(Grid gridToChange) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                gridToChange.cells[i][j].value = 0;
            }
        }
    }

    //Second part - CA

    public void setCA(ActionEvent actionEvent) {

        if (choiceCAStandard.getValue() == "Homogeneous") {

            text2.setDisable(false);
            text1.setDisable(false);
            setHomogeneous();

        } else if (choiceCAStandard.getValue() == "Random") {

            text2.setDisable(true);
            text1.setDisable(false);

            setRandom();

        } else if (choiceCAStandard.getValue() == "OnClick") {

            text2.setDisable(true);
            text1.setDisable(true);
            setOnClickCA();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order warning");
            alert.setHeaderText(null);
            alert.setContentText("You must check order!");

            alert.showAndWait();
        }
    }

    private void setRandom() {

        Grid tmp = grid;

        setGridValuesZeroes(tmp);

        int value = Integer.parseInt(text1.getText());

        Random generateRandomX = new Random();
        Random generateRandomY = new Random();

        List x = new LinkedList();
        List y = new LinkedList();

        for (int i = 0; i < value; i++) {

            boolean isOK = false;

            int ii = 0;
            int jj = 0;

            while (!isOK) {
                ii = generateRandomX.nextInt(size);
                jj = generateRandomY.nextInt(steps);

                if (!x.contains(ii) && !y.contains(jj)) {
                    isOK = true;
                    x.add(ii);
                    y.add(jj);
                }
            }

            tmp.cells[ii][jj].value = cellsValue;
            cellsValue++;

            grid.cellTypeList.add(new CellType(cellsValue));
            grid.cellTypeList.get(grid.cellTypeList.size() - 1).setColor(colors);
            Color c = grid.cellTypeList.get(grid.cellTypeList.size() - 1).color;
            grid.cellTypes.put(cellsValue, c);
        }

        tmp.draw(graphicsContext);
    }

    private void setHomogeneous() {

        int tmpSize = size;
        int tmpSteps = steps;

        int columns = Integer.parseInt(text1.getText());
        int rows = Integer.parseInt(text2.getText());

        while (columns > tmpSteps || rows > tmpSize) {
            if (columns > tmpSteps) {
                gridCAAlert();
            }

            if (rows > tmpSize) {
                gridCAAlert();
            }
        }

        int differenceSize = size / rows;
        int differenceSteps = steps / columns;

        double x, y;

        for (int i = 0; i < size; i++) {
            x = grid.dxdy * i + differenceSize;

            for (int j = 0; j < steps; j++) {

                y = grid.dxdy * j + differenceSteps;

                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(x, y, grid.dxdy, grid.dxdy);

            }

        }


    }

    public void setOnClickCA() {

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double x = event.getScreenX();
                double y = event.getScreenY();
                System.out.println(x);
                System.out.println(y);

                grid.findAndChangeManyColors(colors, x, y, graphicsContext);
            }
        });
    }


    public void startCA(ActionEvent actionEvent) throws InterruptedException {

        if (choiceBox.getValue() == "Moore") {

            setTimelineForMooreCA();
            timelineMooreCA.play();

        } else if (choiceBox.getValue() == "VonNeumann") {

            setTimelineForNeumannCA();
            timelineNumannCA.play();

        } else if (choiceBox.getValue() == "Hexagonal Right") {

            setTimelineForHexagonalRightCA();
            timelineHexagonalRightCA.play();

        } else if (choiceBox.getValue() == "Hexagonal Left") {

            setTimelineForHexagonalLeftCA();
            timelineHexagonalLeftCA.play();

        } else if (choiceBox.getValue() == "Random Hexagonal") {

            Random random = new Random();
            int hex = random.nextInt(2);
            switch (hex) {
                case 0: {
                    setTimelineForHexagonalRightCA();
                    timelineHexagonalRightCA.play();
                    break;
                }
                case 1: {
                    setTimelineForHexagonalLeftCA();
                    timelineHexagonalLeftCA.play();
                    break;
                }
            }


        } else if (choiceBox.getValue() == "Pentagonal Up") {

            setTimelineForPentagonalUpCA();
            timelinePentagonalUpCA.play();

        } else if (choiceBox.getValue() == "Pentagonal Down") {

            setTimelineForPentagonalDownCA();
            timelinePentagonalDownCA.play();

        } else if (choiceBox.getValue() == "Pentagonal Left") {

            setTimelineForPentagonalLeftCA();
            timelinePentagonalLeftCA.play();

        } else if (choiceBox.getValue() == "Pentagonal Right") {

            setTimelineForPentagonalRightCA();
            timelinePentagonalRightCA.play();
        } else if (choiceBox.getValue() == "Random Pentagonal") {
            Random random = new Random();
            int pent = random.nextInt(4);
            switch (pent) {
                case 0: {
                    setTimelineForPentagonalUpCA();
                    timelinePentagonalUpCA.play();
                    break;
                }
                case 1: {
                    setTimelineForPentagonalDownCA();
                    timelinePentagonalDownCA.play();
                    break;
                }
                case 2: {
                    setTimelineForPentagonalLeftCA();
                    timelinePentagonalLeftCA.play();
                    break;
                }
                case 3: {
                    setTimelineForPentagonalRightCA();
                    timelinePentagonalRightCA.play();
                    break;
                }
                default:
                    System.out.println("Error");
                    break;
            }

        }
    }

    private void mooreCA() throws InterruptedException {

        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 1);
                    int mostFreq = mostFrequent(grid.cells[i][j].moore, 8);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);
    }

    private void neumannCA() throws InterruptedException {

        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 2);
                    int mostFreq = mostFrequent(grid.cells[i][j].neumann, 4);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);

    }


    private void hexagonalRightCA() {

        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 3);
                    int mostFreq = mostFrequent(grid.cells[i][j].hexagonalRight, 6);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);


    }

    private void hexagonalLeftCA() {

        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 4);
                    int mostFreq = mostFrequent(grid.cells[i][j].hexagonalLeft, 6);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);

    }


    private void pentagonalUpCA() {

        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 5);
                    int mostFreq = mostFrequent(grid.cells[i][j].pentagonalUp, 5);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);

    }

    private void pentagonalDownCA() {
        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 6);
                    int mostFreq = mostFrequent(grid.cells[i][j].pentagonalDown, 5);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);

    }

    private void pentagonalLeftCA() {

        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 7);
                    int mostFreq = mostFrequent(grid.cells[i][j].pentagonalLeft, 5);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);
    }

    private void pentagonalRightCA() {
        Grid tmpGrid = new Grid(size, steps, canvas);

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                tmpGrid.cells[ii][j].value = grid.cells[ii][j].value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {
                if (grid.cells[i][j].value == 0) {

                    grid.cells[i][j].setNeighborhood(i, j, grid, 8);
                    int mostFreq = mostFrequent(grid.cells[i][j].pentagonalRight, 5);
                    tmpGrid.cells[i][j].value = mostFreq;
                }
            }
        }

        for (int ii = 0; ii < size; ii++) {
            for (int j = 0; j < steps; j++) {
                grid.cells[ii][j].value = tmpGrid.cells[ii][j].value;
            }
        }

        grid.draw(graphicsContext);
    }


    private void setTimelineForPentagonalDownCA() {
        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            pentagonalDownCA();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    if (grid.cells[i][j].value != 0) {
                        sum++;
                    }
                }
            }

            if (sum == size * steps) {
                stopPentagonalDownTimeline();
            }

        };
        keyFrame = new KeyFrame(new Duration(durationPentagonalDown), eventHandler);
        timelinePentagonalDownCA = new Timeline(keyFrame);
        timelinePentagonalDownCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopPentagonalDownTimeline() {
        timelinePentagonalDownCA.stop();
    }


    private void setTimelineForPentagonalRightCA() {
        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            pentagonalRightCA();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    if (grid.cells[i][j].value != 0) {
                        sum++;
                    }
                }
            }

            if (sum == size * steps) {
                stopPentagonalRightTimeline();
            }

        };
        keyFrame = new KeyFrame(new Duration(durationPentagonalRight), eventHandler);
        timelinePentagonalRightCA = new Timeline(keyFrame);
        timelinePentagonalRightCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopPentagonalRightTimeline() {
        timelinePentagonalRightCA.stop();
    }


    private void setTimelineForPentagonalLeftCA() {
        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            pentagonalLeftCA();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    if (grid.cells[i][j].value != 0) {
                        sum++;
                    }
                }
            }

            if (sum == size * steps) {
                stopPentagonalLeftTimeline();
            }

        };
        keyFrame = new KeyFrame(new Duration(durationPentagonalLeft), eventHandler);
        timelinePentagonalLeftCA = new Timeline(keyFrame);
        timelinePentagonalLeftCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopPentagonalLeftTimeline() {
        timelinePentagonalLeftCA.stop();
    }


    private void setTimelineForPentagonalUpCA() {
        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            pentagonalUpCA();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    if (grid.cells[i][j].value != 0) {
                        sum++;
                    }
                }
            }

            if (sum == size * steps) {
                stopPentagonalUpTimeline();
            }

        };
        keyFrame = new KeyFrame(new Duration(durationPentagonalUp), eventHandler);
        timelinePentagonalUpCA = new Timeline(keyFrame);
        timelinePentagonalUpCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopPentagonalUpTimeline() {
        timelinePentagonalUpCA.stop();
    }


    private void setTimelineForHexagonalRightCA() {
        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            hexagonalRightCA();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    if (grid.cells[i][j].value != 0) {
                        sum++;
                    }
                }
            }

            if (sum == size * steps) {
                stopHexagonalRightTimeline();
            }

        };
        keyFrame = new KeyFrame(new Duration(durationHexagonalRight), eventHandler);
        timelineHexagonalRightCA = new Timeline(keyFrame);
        timelineHexagonalRightCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopHexagonalRightTimeline() {

        timelineHexagonalRightCA.stop();
    }


    private void setTimelineForHexagonalLeftCA() {
        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            hexagonalLeftCA();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {
                    if (grid.cells[i][j].value != 0) {
                        sum++;
                    }
                }
            }

            if (sum == size * steps) {
                stopHexagonalLeftTimeline();
            }

        };
        keyFrame = new KeyFrame(new Duration(durationHexagonalLeft), eventHandler);
        timelineHexagonalLeftCA = new Timeline(keyFrame);
        timelineHexagonalLeftCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopHexagonalLeftTimeline() {
        timelineHexagonalLeftCA.stop();
    }


    private void setTimelineForMooreCA() {
        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            try {
                mooreCA();

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < steps; j++) {
                        if (grid.cells[i][j].value != 0) {
                            sum++;
                        }
                    }
                }

                if (sum == size * steps) {
                    stopMooreTimeline();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        keyFrame = new KeyFrame(new Duration(durationHexagonalLeft), eventHandler);
        timelineMooreCA = new Timeline(keyFrame);
        timelineMooreCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopMooreTimeline() {

        timelineMooreCA.stop();
    }


    private void setTimelineForNeumannCA() {

        EventHandler<ActionEvent> eventHandler = event -> {
            int sum = 0;
            try {
                neumannCA();

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < steps; j++) {
                        if (grid.cells[i][j].value != 0) {
                            sum++;
                        }
                    }
                }

                if (sum == size * steps) {
                    stopNeumannTimeline();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        keyFrame = new KeyFrame(new Duration(durationNeumann), eventHandler);
        timelineNumannCA = new Timeline(keyFrame);
        timelineNumannCA.setCycleCount(Animation.INDEFINITE);
    }

    private void stopNeumannTimeline() {

        timelineNumannCA.stop();
    }


    public void startMonteCarlo(ActionEvent actionEvent) throws InterruptedException {

        System.out.println("Start Monte Carlo");

            int counter = size * steps;
            LinkedList<Integer> listOfIds = new LinkedList();
            int cell = 0;
            boolean check;

            Random getValue = new Random();

            //

            while (counter != 0) {

                check = true;

                while (check) {

                    cell = getValue.nextInt(size * steps) + 1;

                    if (!listOfIds.contains(cell)) {
                        listOfIds.add(cell);
                        check = false;
                    } else {
                        cell = getValue.nextInt(size * steps) + 1;
                        check = true;
                    }
                }

                for (int i = 0; i < size; i++)
                    for (int j = 0; j < steps; j++)
                        if (grid.cells[i][j].id == cell && !grid.cells[i][j].isChecked) {
                            grid.cells[i][j].isChecked = true;
                            int value = setEnergy(grid.cells[i][j], i, j, grid);
                            grid.cells[i][j].value = value;
                            // System.out.println("New energy " + grid.cells[i][j].energy);
                        }

                counter--;
            }

            grid.draw(graphicsContext);

            //counter = size*steps;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < steps; j++) {

                    grid.cells[i][j].isChecked = false;
                }
            }
        }


    private int setEnergy(Cell cell, int i, int j, Grid grid) {

        //System.out.println("Set Energy");

        int newValue = 0;

        if (choiceBox.getValue() == "Moore") {

            cell.setNeighborhood(i, j, grid, 1);
            newValue = countEnergy(cell.moore, 8, cell);
            cell.value = newValue;

        } else if (choiceBox.getValue() == "VonNeumann") {

            cell.setNeighborhood(i, j, grid, 2);
            newValue = countEnergy(cell.neumann, 4, cell);
            cell.value = newValue;
        }

        return newValue;
    }

    private int countEnergy(int[] neighborhood, int elements, Cell cell) {

       // System.out.println("Count energy");

        int energy1 = 0, energy2 = 0;
        int result = 0;

        Random random = new Random();

        int tmpValue1 = neighborhood[random.nextInt(elements)];

        for (int i = 0; i < elements; i++) {
            if (neighborhood[i] != tmpValue1) {
                energy1++;
            }
        }

        int tmpValue2 = neighborhood[random.nextInt(elements)];
        for (int i = 0; i < elements; i++) {
            if (neighborhood[i] != tmpValue2) {
                energy2++;
            }
        }

        if (energy2 - energy1 <= 0) {
            result = tmpValue2;
            cell.energy = energy2;
        } else {
            double kt = -2;

            double p = Math.exp(-(energy2 - energy1 / kt));
            double probability = random.nextDouble();

            if (probability <= p) {
                result = tmpValue2;
                cell.energy = energy2;
            } else {
                result = tmpValue1;
                cell.energy = energy1;
            }
        }

        return result;
    }


    private void gridCAAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Your grid is too small!");

        alert.showAndWait();
    }

    static int mostFrequent(int array[], int n) {

        // Sort the array
        Arrays.sort(array);
        List<Integer> list = new LinkedList<>();
        int[] arr;


        // find the max frequency using linear
        // traversal
        int max_count = 1, res;
        int curr_count = 1;


        for (int i = 0; i < n; i++) {
            if (array[i] != 0)
                list.add(array[i]);
        }

        if (list.size() != 0) {

            arr = new int[list.size()];
            res = ((LinkedList<Integer>) list).getFirst();

            for (int i = 0; i < list.size(); i++) {
                arr[i] = list.get(i);
            }

            for (int i = 1; i < arr.length; i++) {
                if (arr[i] == arr[i - 1] && arr[i] != 0)
                    curr_count++;
                else {
                    if (curr_count > max_count) {
                        max_count = curr_count;
                        res = arr[i - 1];
                    }
                    curr_count = 1;
                }
            }

            // If last element is most frequent
            if (curr_count > max_count) {
                max_count = curr_count;
                res = arr[list.size() - 1];
            }
        } else {
            res = 0;
        }

        return res;
    }

    public void clearGrid(ActionEvent actionEvent) {

        grid = new Grid(size, steps, canvas);
        grid.draw(graphicsContext);
    }


    public void drawEnergy(ActionEvent actionEvent) {

        energyGrid = new Grid(size, steps, canvas);
        energyGrid.drawEnergyGrid(grid, graphicsContext);
    }

    public void startDRX(ActionEvent actionEvent) throws IOException {

        Density density = new Density();

        double perc = Double.parseDouble(percent.getText());

        System.out.println("Time\tRo");

        density.countDensity(grid, perc); // const percent

        double criticalDensity = density.criticalDensityForCurrentGrid;

        checkDensityForRecrystalization(criticalDensity);

    }


    public void checkDensityForRecrystalization(double criticalDensity) {

        //System.out.println("*****DENSITY*****");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < steps; j++) {

                // System.out.println(grid.cells[i][j].density);

                setEnergy(grid.cells[i][j],i,j,grid);

                if (grid.cells[i][j].density >= criticalDensity && grid.cells[i][j].energy != 0) {
                    grid.cells[i][j].density = 0;
                    grid.cells[i][j].isRecrystallized = true;
                    grid.cells[i][j].value = -1;
                }
            }
        }

        grid.draw(graphicsContext);

    }

}
