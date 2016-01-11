import mn.mta.vatps.pos.client.model.Function;
import mn.mta.vatps.pos.client.model.PosCommand;
import mn.mta.vatps.pos.client.model.PosInfo;
import mn.mta.vatps.pos.client.model.PosResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nasanjargal on 1/11/16.
 */
public class TestServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket ss = new ServerSocket(9876);
        System.out.println("SERVER WAIT");

        while (true) {
            Socket accept = ss.accept();

            ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
//            PosInfo posInfo = (PosInfo) ois.readObject();
//            System.out.println("posId = " + posInfo.getPosId());

            PosCommand posCommand = null;
            PosResult result = null;
            System.out.println("================CHECK API==================");

            posCommand = new PosCommand(Function.CHECK_API);
            oos.writeObject(posCommand);
            result = (PosResult) ois.readObject();
            System.out.println("result = " + result.getResult());

            System.out.println("================GET INFO==================");
            posCommand = new PosCommand(Function.GET_INFORMATION);
            oos.writeObject(posCommand);
            result = (PosResult) ois.readObject();
            System.out.println("result = " + result.getResult());

            System.out.println("================POS INFO==================");
            posCommand = new PosCommand(Function.POS_INFO);
            oos.writeObject(posCommand);
            result = (PosResult) ois.readObject();
            System.out.println("result = " + result.getPosInfo().getCountLottery());
            System.out.println("result = " + result.getPosInfo().getLastSentDate());
        }
    }

}
