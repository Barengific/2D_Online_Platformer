package mainClient;

import Messages.PlayerMove;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import musicgame.MusicClientThread;
import Messages.Scoreboard;
import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientStart extends Application {

    //objects and varaible creation and initialisation
    RunConn runn;
    PlayerRun pRun;
    MusicClientThread mct;

    Label labelStartTime = new Label();
    Label finishMsg = new Label();
    StackPane roots = new StackPane();
    Circle recP1;
    Circle recP2;

    double m = 10;
    long pTimers = 5;
    long pTimerAdd = 0;
    boolean tblViewer = true;

    //starts game main menu
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        Label svrWaitAlert = new Label();
        svrWaitAlert.setTranslateX(0);
        svrWaitAlert.setTranslateY(-30);
        svrWaitAlert.setScaleX(3);
        svrWaitAlert.setScaleY(1.5);

        Label labName = new Label("Enter Name Below");
        labName.setTranslateX(0);
        labName.setTranslateY(-80);
        labName.setScaleX(3);
        labName.setScaleY(1.5);

        TextField pName = new TextField();
        pName.setTranslateX(0);
        pName.setTranslateY(-50);
        pName.setPromptText("Enter Your Name Here");

        Button btnStart = new Button();
        btnStart.setText("Start Game");

        Button btnQuit = new Button();
        btnQuit.setText("Quit Game");
        btnQuit.setTranslateY(60);

        recP1 = new Circle();
        recP1.setRadius(15);

        recP2 = new Circle();
        recP2.setRadius(15);

        StackPane root = new StackPane();
        root.getChildren().add(btnStart);

        root.getChildren().add(btnQuit);
        root.getChildren().add(svrWaitAlert);
        root.getChildren().add(pName);
        root.getChildren().add(labName);
        Scene scene = new Scene(root, 350, 300);
        primaryStage.setTitle("Game Start Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        btnStart.setOnAction((ActionEvent event) -> {
            //connects to the server and receives a player number
            runn = new RunConn();
            new Thread(runn).start();
            while (true) {
                if (runn.getStatusCode() == 0) {
                    try {
                        svrWaitAlert.setText("Connecting to Server!");
                        Thread.sleep(500);
                        System.out.println("not: " + runn.getStatusCode());
                    } catch (InterruptedException ex) {
                        System.out.println(ex);
                    }
                } else {
                    //loads the new level window
                    newPlayer(runn, recP1, recP2, pName.getText());
                    break;
                }
            }
        });
        btnQuit.setOnAction((ActionEvent event) -> {
            System.exit(1);
        });
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public void updateMovesP1(Circle recP1, double x, double y) {
        recP1.setTranslateX(x);
        recP1.setTranslateY(y);
    }

    public void updateMovesP2(Circle recP2, double x, double y) {
        recP2.setTranslateX(x);
        recP2.setTranslateY(y);
    }

    public void newPlayer(RunConn rCon, Circle recP1, Circle recP2, String pName) {
        Label pNameTxt = new Label();
        pNameTxt.setTranslateX(500);
        pNameTxt.setTranslateY(-340);
        pNameTxt.setText(pName);
        pNameTxt.setScaleX(5);
        pNameTxt.setScaleY(2.5);

        labelStartTime.setTranslateX(-200);
        labelStartTime.setTranslateY(-100);
        labelStartTime.setText("Please Wait");
        labelStartTime.setScaleX(5);
        labelStartTime.setScaleY(2.5);

        Label timeFin = new Label("Waiting for all players");
        timeFin.setTranslateX(0);
        timeFin.setTranslateY(00);
        timeFin.setScaleX(5);
        timeFin.setScaleY(2.5);

        finishMsg.setTranslateX(-100);
        finishMsg.setTranslateY(-200);
        finishMsg.setText("");
        finishMsg.setScaleX(5);
        finishMsg.setScaleY(2.5);

        recP1.setTranslateX(-580);
        recP1.setTranslateY(320);
        recP1.setRadius(15);
        recP1.setFill(Color.LIME);

        recP2.setTranslateX(-580);
        recP2.setTranslateY(320);
        recP2.setRadius(15);
        recP2.setStroke(Color.CORAL);
        recP2.setFill(Color.rgb(10, 200, 10, 0.5));

        Rectangle finish = new Rectangle();
        finish.setTranslateX(-630);
        finish.setTranslateY(-200);
        finish.setHeight(100);
        finish.setWidth(10);
        finish.setFill(Color.GREEN);

        roots.getChildren().add(recP1);
        roots.getChildren().add(recP2);
        roots.getChildren().add(finish);
        roots.getChildren().add(finishMsg);
        roots.getChildren().add(timeFin);
        roots.getChildren().add(labelStartTime);
        roots.getChildren().add(pNameTxt);

        Scene scenes = new Scene(roots, 1280, 720);
        Stage stages = new Stage();
        stages.setTitle("K.I.B.: Player-" + runn.getStatusCode());
        stages.setScene(scenes);
        stages.setResizable(false);
        stages.show();

        KeyFrame frame2 = new KeyFrame(Duration.seconds(1),
                new KeyValue(finish.heightProperty(), 0));
        KeyFrame frame3 = new KeyFrame(Duration.seconds(1),
                new KeyValue(finish.heightProperty(), 200));
        Timeline ani = new Timeline(frame3, frame2);
        ani.setCycleCount(1000);
        ani.setAutoReverse(true);
        ani.play();

        pRun = new PlayerRun(new PlayerMove(runn.getStatusCode(), pName, recP1.getTranslateX(), recP1.getTranslateY(), "", recP2.getTranslateX(), recP2.getTranslateY()), recP1, recP2, roots, finishMsg);
        pRun.start();

        countdownTimer(timeFin, labelStartTime);
        if (runn.getStatusCode() == 1) {
            pRun.setPName1(pName);
        } else if (runn.getStatusCode() == 2) {
            pRun.setPName2(pName);
        }
        loadlevel(roots, "level1");
        musicStarter();
        try {
            //event keypress listener for controlling player characters
            scenes.setOnKeyPressed((KeyEvent event) -> {
                switch (event.getCode()) {
                    case W:
                        if (timeFin.getText().equals("0") && runn.getStatusCode() == 1) {
                            forward(recP1, recP2, roots.getChildren());
                            pRun.setpMove(runn.getStatusCode(), recP1.getTranslateX(), recP1.getTranslateY(), pName, 0, 0);
                        } else if (timeFin.getText().equals("0") && runn.getStatusCode() == 2) {
                            forward(recP2, recP1, roots.getChildren());
                            pRun.setpMove(runn.getStatusCode(), 0, 0, pName, recP2.getTranslateX(), recP2.getTranslateY());
                        }
                        break;
                    case S:
                        if (timeFin.getText().equals("0") && runn.getStatusCode() == 1) {
                            backward(recP1, recP2, roots.getChildren());
                            pRun.setpMove(runn.getStatusCode(), recP1.getTranslateX(), recP1.getTranslateY(), pName, 0, 0);
                        } else if (timeFin.getText().equals("0") && runn.getStatusCode() == 2) {
                            backward(recP2, recP1, roots.getChildren());
                            pRun.setpMove(runn.getStatusCode(), 0, 0, pName, recP2.getTranslateX(), recP2.getTranslateY());
                        }
                        break;
                    case A:
                        if (timeFin.getText().equals("0") && runn.getStatusCode() == 1) {
                            left(recP1, recP2, roots.getChildren(), finish, finishMsg);
                            pRun.setpMove(runn.getStatusCode(), recP1.getTranslateX(), recP1.getTranslateY(), pName, 0, 0);
                        } else if (timeFin.getText().equals("0") && runn.getStatusCode() == 2) {
                            left(recP2, recP1, roots.getChildren(), finish, finishMsg);
                            pRun.setpMove(runn.getStatusCode(), 0, 0, pName, recP2.getTranslateX(), recP2.getTranslateY());
                        }
                        break;
                    case D:
                        if (timeFin.getText().equals("0") && runn.getStatusCode() == 1) {
                            right(recP1, recP2, roots.getChildren());
                            pRun.setpMove(runn.getStatusCode(), recP1.getTranslateX(), recP1.getTranslateY(), pName, 0, 0);
                        } else if (timeFin.getText().equals("0") && runn.getStatusCode() == 2) {
                            right(recP2, recP1, roots.getChildren());
                            pRun.setpMove(runn.getStatusCode(), 0, 0, pName, recP2.getTranslateX(), recP2.getTranslateY());
                        }
                        break;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //checks if player has collided with the finish line
    public boolean detectorFin(Circle rec, Rectangle wall, double m) {
        double x2 = wall.getTranslateX();
        double y2 = wall.getTranslateY();
        double recSize = rec.getRadius() / 2;
        double width2 = wall.getWidth() / 2;
        double height2 = wall.getHeight() / 2;

        double widthUp = y2 + height2 + recSize + m;
        double widthDown = y2 - height2 - recSize - m;
        double heightRight = x2 + width2 + recSize + m;
        double heightLeft = x2 - width2 - recSize - m;
        return (rec.getTranslateY() < widthUp) && (rec.getTranslateY() > widthDown)
                && (rec.getTranslateX() < heightRight) && (rec.getTranslateX() > heightLeft);
    }

    //go forward - decreases Y axis
    private void forward(Circle recP1, Circle recP2, ObservableList<Node> e) {
        if (shapeLoop(recP1, recP2, e)) {
            reset(recP1);
        } else {
            recP1.setTranslateY(recP1.getTranslateY() - m);
        }
    }

    //go backward - increase Y axis
    private void backward(Circle recP1, Circle recP2, ObservableList<Node> e) {
        if (shapeLoop(recP1, recP2, e)) {
            reset(recP1);
        } else {
            recP1.setTranslateY(recP1.getTranslateY() + m);
        }
    }

    //go left - decrease X axis
    private void left(Circle recP1, Circle recP2, ObservableList<Node> e, Rectangle finish, Label fin) {
        if (detectorFin(recP1, finish, m)) {
            fin.setText("FinishED the game !!!");
            if (runn.getStatusCode() == 1) {
                pRun.setFinState1(true);
                pRun.setTimer1(Double.valueOf(labelStartTime.getText()));
                System.out.println("player 1 made it");
            } else if (runn.getStatusCode() == 2) {
                pRun.setFinState2(true);
                pRun.setTimer2(Double.valueOf(labelStartTime.getText()));
                System.out.println("no player 2 made it");
            }

        } else if (shapeLoop(recP1, recP2, e)) {
            reset(recP1);
        } else {
            recP1.setTranslateX(recP1.getTranslateX() - m);
        }
    }

    //go left - increase X axis
    private void right(Circle recP1, Circle recP2, ObservableList<Node> e) {
        if (shapeLoop(recP1, recP2, e)) {
            reset(recP1);
        } else {
            recP1.setTranslateX(recP1.getTranslateX() + m);
        }
    }

    //resets player back to starting position
    private void reset(Circle rec) {
        rec.setTranslateX(-580);
        rec.setTranslateY(320);
    }

    //algorithm for checking if player is colliding with the walls, platforms, or obstacles
    public boolean detectors(Circle rec, double height, double width, double x, double y, double m) {
        double recSize = rec.getRadius() / 2;
        double width2 = width / 2;
        double height2 = height / 2;

        double widthUp = y + height2 + recSize + m;
        double widthDown = y - height2 - recSize - m;
        double heightRight = x + width2 + recSize + m;
        double heightLeft = x - width2 - recSize - m;
        return (rec.getTranslateY() < widthUp) && (rec.getTranslateY() > widthDown)
                && (rec.getTranslateX() < heightRight) && (rec.getTranslateX() > heightLeft);
    }

    //loop through the entire scene to find the rectangles
    private boolean shapeLoop(Circle recP1, Circle recP2, ObservableList<Node> e) {
        boolean state = false;
        for (Node child : e) {
            if (child.getClass().getSimpleName().contains("Rectangle")) {
                double width = child.getBoundsInLocal().getWidth();
                double height = child.getBoundsInLocal().getHeight();
                double x = child.getTranslateX();
                double y = child.getTranslateY();
                if (((width == recP1.getRadius())
                        && (height == recP1.getRadius())
                        && (x == recP1.getTranslateX())
                        && (y == recP1.getTranslateY()))) {
                    System.out.println("wall = " + width + "-" + height + " -" + x + "-" + y);
                } else if (((width == recP2.getRadius())
                        && (height == recP2.getRadius())
                        && (x == recP2.getTranslateX())
                        && (y == recP2.getTranslateY()))) {
                    System.out.println("other player");
                } else {
                    mct.setPort(4447);
                    if (detectors(recP1, height, width, x, y, m)) {
                        System.out.println("detect true");
                        return true;
                    }
                    mct.setPort(4446);
                }
            }
        }
        return state;
    }

    //load level from text file
    public void loadlevel(StackPane root, String lvl) {
        try {
            String fileLoc = System.getProperty("user.dir");
            String fileData = fileLoc + File.separator + "src\\leveldata\\" + lvl + ".txt";
            FileReader fr = new FileReader(fileData);
            BufferedReader br = new BufferedReader(fr);
            String lvlline;
            while ((lvlline = br.readLine()) != null) {
                String[] lvls = lvlline.split(",");
                String[] size = lvls[2].split("/");
                String[] translation = lvls[3].split("/");
                Rectangle recs[] = new Rectangle[Integer.parseInt(lvls[1])];
                switch (lvls[0]) {
                    case "wall":
                        for (int i = 0; i < Integer.parseInt(lvls[1]); i++) {
                            recs[i] = new Rectangle();
                            String[] sizes = size[i].split(":");

                            recs[i].setHeight(Integer.parseInt(sizes[0]));
                            recs[i].setWidth(Integer.parseInt(sizes[1]));

                            String[] translations = translation[i].split(":");
                            recs[i].setTranslateX(Integer.parseInt(translations[0]));
                            recs[i].setTranslateY(Integer.parseInt(translations[1]));
                            recs[i].setFill(Color.BLUE);
                            root.getChildren().add(recs[i]);
                        }
                        break;
                    case "platform":
                        for (int i = 0; i < Integer.parseInt(lvls[1]); i++) {
                            recs[i] = new Rectangle();
                            String[] sizes = size[i].split(":");

                            recs[i].setHeight(Integer.parseInt(sizes[0]));
                            recs[i].setWidth(Integer.parseInt(sizes[1]));

                            String[] translations = translation[i].split(":");
                            recs[i].setTranslateX(Integer.parseInt(translations[0]));
                            recs[i].setTranslateY(Integer.parseInt(translations[1]));
                            recs[i].setFill(Color.RED);
                            root.getChildren().add(recs[i]);
                        }
                        break;
                    case "vertical":
                        for (int i = 0; i < Integer.parseInt(lvls[1]); i++) {
                            recs[i] = new Rectangle();
                            String[] sizes = size[i].split(":");

                            recs[i].setHeight(Integer.parseInt(sizes[0]));
                            recs[i].setWidth(Integer.parseInt(sizes[1]));

                            String[] translations = translation[i].split(":");
                            recs[i].setTranslateX(Integer.parseInt(translations[0]));
                            recs[i].setTranslateY(Integer.parseInt(translations[1]));
                            recs[i].setFill(Color.PURPLE);
                            root.getChildren().add(recs[i]);

                            KeyFrame frame2 = new KeyFrame(Duration.seconds(1),
                                    new KeyValue(recs[i].heightProperty(), 0));
                            KeyFrame frame3 = new KeyFrame(Duration.seconds(1),
                                    new KeyValue(recs[i].heightProperty(), 200));
                            Timeline ani = new Timeline(frame3, frame2);
                            ani.setCycleCount(2000);
                            ani.setAutoReverse(true);
                            ani.play();
                        }
                        break;
                    case "seesaw":
                        for (int i = 0; i < Integer.parseInt(lvls[1]); i++) {
                            recs[i] = new Rectangle();
                            String[] sizes = size[i].split(":");

                            recs[i].setHeight(Integer.parseInt(sizes[0]));
                            recs[i].setWidth(Integer.parseInt(sizes[1]));

                            String[] translations = translation[i].split(":");
                            recs[i].setTranslateX(Integer.parseInt(translations[0]));
                            recs[i].setTranslateY(Integer.parseInt(translations[1]));
                            recs[i].setFill(Color.PURPLE);
                            root.getChildren().add(recs[i]);

                            KeyFrame frame2 = new KeyFrame(Duration.seconds(1),
                                    new KeyValue(recs[i].rotateProperty(), 0));
                            KeyFrame frame3 = new KeyFrame(Duration.seconds(1),
                                    new KeyValue(recs[i].rotateProperty(), 90));
                            Timeline ani = new Timeline(frame3, frame2);
                            ani.setCycleCount(3000);
                            ani.setAutoReverse(true);
                            ani.play();
                        }
                        break;
                    case "horizontal":
                        for (int i = 0; i < Integer.parseInt(lvls[1]); i++) {
                            recs[i] = new Rectangle();
                            String[] sizes = size[i].split(":");

                            recs[i].setHeight(Integer.parseInt(sizes[0]));
                            recs[i].setWidth(Integer.parseInt(sizes[1]));

                            String[] translations = translation[i].split(":");
                            recs[i].setTranslateX(Integer.parseInt(translations[0]));
                            recs[i].setTranslateY(Integer.parseInt(translations[1]));
                            recs[i].setFill(Color.PURPLE);
                            root.getChildren().add(recs[i]);

                            KeyFrame frame2 = new KeyFrame(Duration.seconds(1),
                                    new KeyValue(recs[i].widthProperty(), 10));
                            KeyFrame frame3 = new KeyFrame(Duration.seconds(2),
                                    new KeyValue(recs[i].widthProperty(), 200));
                            Timeline ani = new Timeline(frame3, frame2);
                            ani.setCycleCount(10000);
                            ani.setAutoReverse(true);
                            ani.play();
                        }
                        break;
                    case "lifter":
                        for (int i = 0; i < Integer.parseInt(lvls[1]); i++) {
                            recs[i] = new Rectangle();
                            String[] sizes = size[i].split(":");

                            recs[i].setHeight(Integer.parseInt(sizes[0]));
                            recs[i].setWidth(Integer.parseInt(sizes[1]));

                            String[] translations = translation[i].split(":");
                            recs[i].setTranslateX(Integer.parseInt(translations[0]));
                            recs[i].setTranslateY(Integer.parseInt(translations[1]));
                            recs[i].setFill(Color.PURPLE);
                            root.getChildren().add(recs[i]);

                            KeyFrame frame2 = new KeyFrame(Duration.seconds(5),
                                    new KeyValue(recs[i].translateYProperty(), 160));
                            KeyFrame frame3 = new KeyFrame(Duration.seconds(5),
                                    new KeyValue(recs[i].translateYProperty(), 60));
                            Timeline ani = new Timeline(frame3, frame2);
                            ani.setCycleCount(10000);
                            ani.setAutoReverse(true);
                            ani.play();
                        }
                        break;
                    case "shifter":
                        for (int i = 0; i < Integer.parseInt(lvls[1]); i++) {
                            recs[i] = new Rectangle();
                            String[] sizes = size[i].split(":");

                            recs[i].setHeight(Integer.parseInt(sizes[0]));
                            recs[i].setWidth(Integer.parseInt(sizes[1]));

                            String[] translations = translation[i].split(":");
                            recs[i].setTranslateX(Integer.parseInt(translations[0]));
                            recs[i].setTranslateY(Integer.parseInt(translations[1]));
                            recs[i].setFill(Color.PURPLE);
                            root.getChildren().add(recs[i]);

                            KeyFrame frame2 = new KeyFrame(Duration.seconds(1),
                                    new KeyValue(recs[i].translateXProperty(), 0));
                            KeyFrame frame3 = new KeyFrame(Duration.seconds(1),
                                    new KeyValue(recs[i].translateXProperty(), 360));
                            Timeline ani = new Timeline(frame3, frame2);
                            ani.setCycleCount(10000);
                            ani.setAutoReverse(true);
                            ani.play();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //count down timer so players start the game together
    public void countdownTimer(Label timeFin, Label labelStartTime) {
        while (true) {
            if (pRun.getState1() && pRun.getState2()) {
                long startTime = System.currentTimeMillis();
                final Timeline timeline = new Timeline(
                        new KeyFrame(
                                Duration.millis(500), (ActionEvent event) -> {
                                    final long diff = System.currentTimeMillis() - startTime;
                                    long seconds = (pTimers) - diff / 1000;
                                    if (seconds <= 0) {
                                        timeFin.setText("0");
                                        timeFin.setTextFill(Color.GREEN);
                                        timeFin.setVisible(false);
                                    } else {
                                        String sec = Long.toString(seconds);
                                        timeFin.setText(sec);
                                    }
                                })
                );
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
                countupTimer(timeFin, labelStartTime, timeline);

                break;
            } else {
                try {
                    Thread.sleep(1);
                    timeFin.setScaleX(5);
                    timeFin.setScaleY(2.5);
                    timeFin.setTranslateX(-250);
                    timeFin.setTranslateY(-300);
                    timeFin.setText("Waiting for other player");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientStart.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //counter timer for storing in scoreboard, indicates how long the game has been started for
    public void countupTimer(Label timeFin, Label labelStartTime, Timeline cd) {
        labelStartTime.setTranslateY(-250);
        long startTime = System.currentTimeMillis();
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(500), (ActionEvent event) -> {
                            if (timeFin.getText().equals("0")) {
                                cd.stop();
                                long diff = System.currentTimeMillis() - startTime;
                                long seconds = (diff / 1000) - 5;
                                String sec = Long.toString(seconds);
                                labelStartTime.setText(sec);
                            }

                        })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    //display scoreboard when a player has reached the finish line
    public void finMake(ArrayList<Scoreboard> list) {
        TableView<Scoreboard> scoreTbl = new TableView();
        StackPane root = new StackPane();

        root.getChildren().add(scoreTbl);
        Scene scene = new Scene(root, 500, 360);
        Stage stage = new Stage();
        stage.setTitle("Scoreboard");
        stage.setScene(scene);
        stage.show();

        scoreTbl.setTranslateX(0);
        scoreTbl.setTranslateY(0);

        ObservableList<Scoreboard> data = FXCollections.observableArrayList();

        TableColumn pName = new TableColumn("Player Name");
        pName.setCellValueFactory(new PropertyValueFactory<Scoreboard, String>("playerName"));

        TableColumn pTime = new TableColumn("Time Completed");
        pTime.setSortType(TableColumn.SortType.ASCENDING);
        pTime.setCellValueFactory(new PropertyValueFactory<Scoreboard, String>("playerTime"));

        TableColumn pLevel = new TableColumn("Level");
        pLevel.setCellValueFactory(new PropertyValueFactory<Scoreboard, String>("playerLevel"));
        pLevel.setSortType(TableColumn.SortType.ASCENDING);

        list.stream().forEach((list1) -> {
            data.add(list1);
        });
        scoreTbl.setEditable(true);
        scoreTbl.setItems(data);
        scoreTbl.getColumns().addAll(pName, pTime, pLevel);
    }

    //receive music streaming
    public void musicStarter() {
        mct = new MusicClientThread();
        mct.setPort(4446);
        mct.start();
    }
}
