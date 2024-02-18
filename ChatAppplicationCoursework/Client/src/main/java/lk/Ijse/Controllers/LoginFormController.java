package lk.Ijse.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.Ijse.ServerConnectivity.Handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginFormController {
    @FXML
    private TextField name;
    

     static String name1;

    private ServerSocket serverSocket;

    public void initialize(){

        try {
            serverSocket = new ServerSocket(50000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
             while (!serverSocket.isClosed()){

                 try {
                     Socket socket = serverSocket.accept();
                     System.out.println("Connected");
                     Handler serverConnectivity = new Handler(socket);
                     serverConnectivity.listenMesage();

                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }

             }

            }
        }).start();
    }


    public void JoinInAction(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        name1 = name.getText();
        name.clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/View/ChatForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(name1);
        stage.show();
    }
}
