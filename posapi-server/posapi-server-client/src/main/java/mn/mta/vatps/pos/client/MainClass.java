package mn.mta.vatps.pos.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by nasanjargal on 1/10/16.
 */
public class MainClass {
    public static String posApiPath;
    public static void main(String[] args) {
        try {
            posApiPath = args[0];
            PosClient.loadLibrary(posApiPath);
            int port = Integer.parseInt(args[1]);
            Socket socket = new Socket("localhost", port);

            PosSocket ps = new PosSocket(socket);
            ps.sendReady();
            ps.start();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
