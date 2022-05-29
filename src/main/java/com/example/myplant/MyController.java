package com.example.myplant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class MyController implements Initializable{

    @FXML
    private AnchorPane ap2;

    @FXML
    private AnchorPane ap1;

    @FXML
    private HBox hbox2;

    @FXML
    private TableColumn<Plant, String> resultCol;

    @FXML
    private TableColumn<Plant, String> firstCol;

    @FXML
    private TableColumn<Plant, String> secondCol;

    @FXML
    private TableColumn<Plant, String> pathCol;

    @FXML
    private Tab tab1;

    @FXML
    private Tab tab2;

    @FXML
    private Tab tab3;

    @FXML
    private TableColumn<Plant, String> thirdCol;

    @FXML
    private TableColumn<Plant, String> opreCol;

    @FXML
    private ImageView image1;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private ListView<String> list1;

    @FXML
    private VBox vbox1;

    @FXML
    private TableView<Plant> table1;

    @FXML
    private HBox hbox4;

    @FXML
    private HBox hbox3;

    @FXML
    private Button btn2;

    @FXML
    private Button btn1;

    @FXML
    private TabPane tabPane;
    @FXML
    private TextField text3;

    @FXML
    private TextField text4;

    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    @FXML
    private Button btn3;

    @FXML
    private ImageView image2;
    @FXML
    private TextField text7;

    @FXML
    private TextField text8;

    @FXML
    private TextField text5;

    @FXML
    private TextField text6;

    @FXML
    private Label label3;

    private static final ObservableList<String> ob = FXCollections.observableArrayList();

    private static final ObservableList<Plant> data = FXCollections.observableArrayList();

    public String imagesPath = "D:\\Java\\MyPlant\\src\\main\\images";
    static String AIPath = "D:\\Java\\MyPlant\\src\\main\\BaiduAI.py";
    int cnt = 0;
    Text text = new Text();

    public void btn1Event(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(imagesPath)); // 设置初始路径，默认为我的电脑
        chooser.setTitle("打开图片"); // 设置窗口标题，默认为“打开”
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        try {
            Stage stage = (Stage) tabPane.getScene().getWindow();
            String path = chooser.showOpenDialog(stage).getAbsolutePath();
            text.setText(path);
            Image image = new Image("file:" + path);
            System.out.println("file:" + path);
            image1.setImage(image);
        } catch (NullPointerException ignored) {

        }
    }

    public void btn2Event(ActionEvent event){
        try {
            ob.clear();
            String[] args1 = new String[] { "python", AIPath, text.getText() };
            Process proc = Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8));
            String line = null;
            try {
                line = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line != null) {
                // 分割返回字符串
                StringTokenizer st = new StringTokenizer(line, ",");
                String[] s = new String[3];
                int i = 0;
                while (st.hasMoreElements()) {
                    s[i++] = st.nextElement().toString();
                    System.out.println(s[i - 1]);
                }
                addToTable(text.getText(), s);

                ob.add("图片路径为：");
                ob.add(text.getText());
                ob.add("识别结果：");
                ob.add(s[0]);
                ob.add(s[1]);
                ob.add(s[2]);


            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

    }

    public void btn3Event(ActionEvent event){
        String[] s = new String[3];
        s[0] = text2.getText();
        s[1] = text3.getText();
        s[2] = text4.getText();
        addToTable(text1.getText(), s);
        // 清空文本
        text1.clear();
        text2.clear();
        text3.clear();
        text4.clear();
    }

    public void addToTable(String path, String[] s){
        Button delete = new Button("删除");
        Button show = new Button("查看");
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(delete, show);
        Plant temp = new Plant(path, s[0], s[1], s[2], hb);
        delete.setOnAction(e->{
            data.remove(temp);
        });
        show.setOnAction(e->{
//            System.out.println(path);
            text5.setText(path);
            text6.setText(s[0]);
            text7.setText(s[1]);
            text8.setText(s[2]);
            image2.setImage(new Image("file:" + path));
            tabPane.getSelectionModel().select(tab3);
            label3.requestFocus();
        });

        data.add(temp);

    }

    private void initTableColumn(){
        setTableColumn( pathCol, "path");
        setTableColumn( firstCol, "firstPlant");
        setTableColumn( secondCol, "secondPlant");
        setTableColumn( thirdCol, "thirdPlant");
        opreCol.setCellValueFactory(new PropertyValueFactory<>("operate"));
        table1.setEditable(true);//表格设置为可编辑
        pathCol.setCellFactory(TextFieldTableCell.forTableColumn());
        pathCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Plant, String> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPath(t.getNewValue());
                });
        firstCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Plant, String> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstPlant(t.getNewValue());
                });
        secondCol.setCellFactory(TextFieldTableCell.forTableColumn());
        secondCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Plant, String> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setSecondPlant(t.getNewValue());
                });
        thirdCol.setCellFactory(TextFieldTableCell.forTableColumn());
        thirdCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Plant, String> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setThirdPlant(t.getNewValue());
                });

    }
    private static void setTableColumn(TableColumn<Plant, String> tableColumn, String string) {
        // 设置宽度
//        tableColumn.setMinWidth(250);
        // 设置分箱的类下面的属性名
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(string));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableColumn();
        list1.setItems(ob);
        table1.setItems(data);
        String[] strings = new String[]{"1: 山丹 准确率： 0.47737324", "2: 卷丹 准确率： 0.47027993", "3: 有斑百合 准确率： 0.2142905"};
        addToTable("D:\\Java\\MyGUI\\MyAi\\src\\images\\3.jpg", strings);

    }
}

class MyButton extends Button{
    int id;
    MyButton(String string, int id){
        super.setText(string);
        this.id = id;
    }
}




