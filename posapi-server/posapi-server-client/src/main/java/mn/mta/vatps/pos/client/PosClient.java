package mn.mta.vatps.pos.client;

/**
 * Created by nasanjargal on 1/10/16.
 */
public class PosClient {

    static {
//        System.loadLibrary("PosClient");
        System.loadLibrary("PosClient");
    }

    public static native boolean loadLibrary(String param);

    public static native String checkApi();

    public static native String getInformation();

    public static native String callFunction(String funcName, String param);

    public static native String put(String param);

    public static native String returnBill(String param);

    public static native String sendData();

}
