package mn.mta.vatps.pos.server.socket;

import mn.mta.vatps.pos.client.model.Function;
import mn.mta.vatps.pos.client.model.PosCommand;
import mn.mta.vatps.pos.client.model.PosInfo;
import mn.mta.vatps.pos.client.model.PosResult;
import mn.mta.vatps.pos.server.helper.Helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by nasanjargal on 1/11/16.
 */
public class PosClientSocket {
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    private PosInfo posInfo;

    public PosClientSocket(Socket socket) throws IOException {
        this.socket = socket;

        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    private synchronized PosResult callMethod(PosCommand command) throws IOException, ClassNotFoundException {
        this.output.writeObject(command);
        this.output.flush();

        PosResult result = (PosResult) this.input.readObject();

        return result;
    }

    public PosInfo getReady() throws IOException, ClassNotFoundException {
        this.posInfo = (PosInfo) this.input.readObject();
        return posInfo;
    }

    public PosInfo getPosInfo() {
        return posInfo;
    }

    public void close() throws IOException {
        this.socket.close();
    }

    public void refreshPosInfo() throws IOException, ClassNotFoundException {
        PosResult result = callMethod(new PosCommand(Function.POS_INFO));
        if (result.isSuccess()) {
            this.posInfo = result.getPosInfo();
        }
    }

    public String checkApi() throws IOException, ClassNotFoundException {
        PosResult result = callMethod(new PosCommand(Function.CHECK_API));
        if (result.isSuccess()) {
            return result.getResult();
        }

        return Helper.DEFAULT_ERROR;
    }

    public String getInformation() throws IOException, ClassNotFoundException {
        PosResult result = callMethod(new PosCommand(Function.GET_INFORMATION));
        if (result.isSuccess()) {
            return result.getResult();
        }

        return Helper.DEFAULT_ERROR;
    }

    public String callFunction(String funcName, String param) throws IOException, ClassNotFoundException {
        PosResult result = callMethod(new PosCommand(Function.CALL_FUNCTION, param, funcName));
        if (result.isSuccess()) {
            return result.getResult();
        }

        return Helper.DEFAULT_ERROR;
    }

    public String put(String param) throws IOException, ClassNotFoundException {
        PosResult result = callMethod(new PosCommand(Function.PUT, param));
        if (result.isSuccess()) {
            return result.getResult();
        }

        return Helper.DEFAULT_ERROR;
    }

    public String returnBill(String param) throws IOException, ClassNotFoundException {
        PosResult result = callMethod(new PosCommand(Function.RETURN_BILL, param));
        if (result.isSuccess()) {
            return result.getResult();
        }

        return Helper.DEFAULT_ERROR;
    }

    public String sendData() throws IOException, ClassNotFoundException {
        PosResult result = callMethod(new PosCommand(Function.SEND_DATA));
        if (result.isSuccess()) {
            return result.getResult();
        }

        return Helper.DEFAULT_ERROR;
    }

}
