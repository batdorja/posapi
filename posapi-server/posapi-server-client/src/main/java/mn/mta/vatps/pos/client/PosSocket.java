package mn.mta.vatps.pos.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import mn.mta.vatps.pos.client.model.PosCommand;
import mn.mta.vatps.pos.client.model.PosInfo;
import mn.mta.vatps.pos.client.model.PosResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nasanjargal on 1/11/16.
 */
public class PosSocket {
    Gson gson = new GsonBuilder().create();
    SimpleDateFormat dateFormat;

    Socket socket;
    ObjectOutputStream outStream;
    ObjectInputStream inputStream;

    String posId;

    boolean sending = false;

    public PosSocket(Socket socket) throws IOException {
        this.socket = socket;

        outStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }


    public void sendReady() throws IOException {
        PosInfo info = getInfo();
        this.posId = info.getPosId();
        outStream.writeObject(info);
        outStream.flush();
    }

    public void start() throws IOException, ClassNotFoundException {
        while (true) {
            final PosCommand command = (PosCommand) inputStream.readObject();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result;
                        PosResult posResult = null;
                        switch (command.getFunction()) {
                            case CHECK_API:
                                result = PosClient.checkApi();
                                posResult = new PosResult(true, posId, result);
                                break;
                            case GET_INFORMATION:
                                result = PosClient.getInformation();
                                posResult = new PosResult(true, posId, result);
                                break;
                            case CALL_FUNCTION:
                                result = PosClient.callFunction(command.getFunctionName(), command.getParameter());
                                posResult = new PosResult(true, posId, result);
                                break;
                            case PUT:
                                result = PosClient.put(command.getParameter());
                                posResult = new PosResult(true, posId, result);
                                break;
                            case RETURN_BILL:
                                result = PosClient.returnBill(command.getParameter());
                                posResult = new PosResult(true, posId, result);
                                break;
                            case SEND_DATA:
                                sending = true;
                                result = PosClient.sendData();
                                posResult = new PosResult(true, posId, result);
                                sending = false;
                                break;
                            case POS_INFO:
                                PosInfo info = getInfo();
                                posResult = new PosResult(true);
                                posResult.setPosId(posId);
                                posResult.setPosInfo(info);
                                break;
                        }
                        if (posResult == null) {
                            posResult = new PosResult(false);
                        }
                        outStream.writeObject(posResult);
                        outStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public PosInfo getInfo() {
        PosInfo posInfo = new PosInfo(false);

        try {
            String info = PosClient.getInformation();
            JsonReader reader = new JsonReader(new StringReader(info));
            Map result = gson.fromJson(reader, HashMap.class);

            posInfo.setPosId((String) result.get("posId"));
            posInfo.setBranchNo((String) result.get("branchNo"));
            posInfo.setRegisterNo((String) result.get("registerNo"));
            posInfo.setPosApiPath(MainClass.posApiDirPath);
            posInfo.setSending(this.sending);

            Map extraInfo = (Map) result.get("extraInfo");

            System.out.println("extraInfo = " + extraInfo);

            if (extraInfo != null) {
                posInfo.setCountBill("" + extraInfo.get("countBill"));
                posInfo.setCountLottery("" + extraInfo.get("countLottery"));
                String lastSentDate = (String) extraInfo.get("lastSentDate");
                if (lastSentDate != null) {
                    lastSentDate = lastSentDate.replace('T', ' ');
                    posInfo.setLastSentDate(dateFormat.parse(lastSentDate));
                }
            }

            posInfo.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posInfo;
    }
}
