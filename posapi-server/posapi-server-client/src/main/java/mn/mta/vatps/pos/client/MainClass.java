package mn.mta.vatps.pos.client;

import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by nasanjargal on 1/10/16.
 */
public class MainClass {

    public static Logger logger = Logger.getLogger("PoAPI");

    public static String posApiDirPath;

    public static void main(String[] args) {
        try {
            posApiDirPath = args[0];
            initLogger(posApiDirPath);

            String name = "PosAPI";
            if (SystemUtils.IS_OS_LINUX) {
                name = "lib" + name + ".so";
            } else {
                name = name + ".dll";
            }

            PosClient.loadLibrary(posApiDirPath + File.separator + name);
            int port = Integer.parseInt(args[1]);
            Socket socket = new Socket("localhost", port);

            PosSocket ps = new PosSocket(socket);
            ps.sendReady();
            ps.start();

            socket.close();

        } catch (Exception e) {
            logger.log(Level.FINEST, e.getMessage(), e);
        }
    }

    private static void initLogger(String posApiDirPath) throws IOException {
        FileHandler fh = new FileHandler(posApiDirPath + File.separator + "posapi.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }
}
