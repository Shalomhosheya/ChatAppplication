package lk.Ijse.ServerConnectivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Handler {
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;

    private String username;
    private String sendername;

    private static ArrayList<Handler>clients = new ArrayList<>();


    public Handler(Socket socket) {
        try {
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            username = dataInputStream.readUTF();
            System.out.println(username);
            clients.add(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void listenMesage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String message = dataInputStream.readUTF();
                        String[] spilt = message.split(" : ");
                        sendername = spilt[0];

                        broadcastMessage(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
    }

    private void broadcastMessage(String message) throws IOException {
        for (Handler client:clients){
            if (!client.username.equals(sendername)){
                client.dataOutputStream.writeUTF(message);
                client.dataOutputStream.flush();
            }
        }
    }
 }

