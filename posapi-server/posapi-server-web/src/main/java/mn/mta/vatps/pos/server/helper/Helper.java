package mn.mta.vatps.pos.server.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

/**
 * Created by nasanjargal on 1/7/16.
 */
public interface Helper {
    public static final String HIBERNATE_SESSION_FACTORY = "SessionFactory";
    public static final String POS_CLIENT_PATH = "POS_CLIENT_PATH";
    public static final String FILE_PARENT_PATH = System.getProperty("user.home") + File.separator + "client" + File.separator;
    public static final Gson GSON = new GsonBuilder().create();
    public static final String POS_CLIENT_NAME = "PosClient";
    public static final int SERVER_PORT = 9876;
    public static final String POS_SOCKET_SERVER = "POS_SOCKET_SERVER";
    public static final String DEFAULT_ERROR = "{\"success\":false, errorCode:0, errorMessage:\"Pos Server Error\"}";
}
