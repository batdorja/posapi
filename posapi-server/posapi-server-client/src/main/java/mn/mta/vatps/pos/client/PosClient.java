package mn.mta.vatps.pos.client;

import org.apache.commons.lang.SystemUtils;

/**
 * Created by nasanjargal on 1/10/16.
 */
public class PosClient {

    static {
        if (SystemUtils.IS_OS_WINDOWS) {
            System.loadLibrary("icudt53");
            System.loadLibrary("icuuc53");
            System.loadLibrary("icuin53");
            System.loadLibrary("Qt5Core");
            System.loadLibrary("Qt5SQL");
            System.loadLibrary("Qt5Network");
            System.loadLibrary("Qt5Script");
        }

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
