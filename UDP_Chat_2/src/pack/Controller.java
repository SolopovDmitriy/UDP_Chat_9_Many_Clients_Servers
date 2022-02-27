package pack;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import udp.Client;
import udp.Server;

import java.awt.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server = new Server(3033);
        client = new Client();
        server_msg.textProperty().bindBidirectional(server.property());
        server.start();
    }

    private Server server;
    private Client client;

    @FXML
    private TextField server_adr;
    @FXML
    private TextArea server_msg;
    @FXML
    private TextArea client_msg;

    @FXML
    public void sendHandler(){

        for (InetSocketAddress inetSocketAddress : getAddresses(server_adr.getText())) {
            client.send(inetSocketAddress, client_msg.getText());
        }
//        client.send(getAddress(server_adr.getText()), client_msg.getText());
    }



    // str = "localhost:3031;localhost:3032"
    private InetSocketAddress[] getAddresses(String str){
        String[] data = str.split(";");  // ["localhost:3031";  "localhost:3032"]

        InetSocketAddress[] inetSocketAddresses = new InetSocketAddress[data.length];
//        for (int i = 0; i < data.length; i++) {
//            String adress = data[i];
//            InetSocketAddress inetSocketAddress = getAddress(adress);
//            inetSocketAddresses[i] = inetSocketAddress;
//        }
        for (int i = 0; i < data.length; i++) {
            inetSocketAddresses[i] = getAddress(data[i]);
        }
        // return Arrays.stream(data).map(this::getAddress).toArray(InetSocketAddress[]::new);
        return inetSocketAddresses;
    }


    // str = "localhost:3031
    private InetSocketAddress getAddress(String str){
        String[] data = str.split(":");
        return new InetSocketAddress(
                data[0],
                Integer.parseInt(data[1])
        );
    }

    @FXML
    public void clear_server_log(){
        System.out.println("Worked");
        server_msg.textProperty().setValue("");
    }

    @FXML
    public void clear_client_log(){
        client_msg.clear();
    }

    @FXML
    public void keyHandler(KeyEvent event){
        if(event.isControlDown() && event.getSource().equals(client_msg)){
            switch(event.getCode()){
                case ENTER:
                    client.send(getAddress(server_adr.getText()), client_msg.getText());
                    break;
            }
        }
    }
}
