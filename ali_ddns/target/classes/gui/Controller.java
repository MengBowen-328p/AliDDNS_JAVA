package gui;

import ddns.DDNS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ChoiceBox<String>typeChoice;


    @FXML
    private TextField RR_Text;

    @FXML
    private Label Type;

    @FXML
    private TextField ID_Text;

    @FXML
    private TextField Passwd_Text;

    @FXML
    private TextField Domain_Text;

    @FXML
    private Button Resolve;
    @FXML
    void pressResolve(ActionEvent event) throws Exception {
        String accessKey = ID_Text.getText();
        String accessScrect = Passwd_Text.getText();
        String domain = Domain_Text.getText();
        String resourceRecord=RR_Text.getText();
        String Type = typeChoice.getValue();
        ddns.DDNS.resolve(accessKey,accessScrect,domain,resourceRecord,Type);

//        System.out.println(accessKey+accessScrect+domain+resourceRecord);
//        System.out.println("Hello World");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeChoice.getItems().add("A");
        typeChoice.getItems().add("AAAA");
        typeChoice.getItems().add("CNAME");
    }

//    public void init()
//    {
//        typeChoice.getItems().add("python");
//        typeChoice.getItems().add("Java");
//    }
}