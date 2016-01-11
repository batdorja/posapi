package mn.mta.vatps.pos.server.socket;

import mn.mta.vatps.pos.client.model.PosInfo;
import mn.mta.vatps.pos.server.data.dao.PosApiDAO;
import mn.mta.vatps.pos.server.data.entity.PosApiEntity;
import mn.mta.vatps.pos.server.helper.Helper;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nasanjargal on 1/11/16.
 */
public class PosServerSocket extends Thread {

    private static Map<String, PosClientSocket> POS_SOCKET_LIST;

    private final ServerSocket serverSocket;
    private final ServletContext ctx;

    public PosServerSocket(Map<String, PosClientSocket> posApiList, ServletContext ctx) throws IOException {
        POS_SOCKET_LIST = posApiList;

        serverSocket = new ServerSocket(Helper.SERVER_PORT);
        this.ctx = ctx;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Socket socket = serverSocket.accept();

                PosApiDAO posApiDAO = PosApiDAO.build(ctx);

                PosClientSocket clientSocket = new PosClientSocket(socket);
                PosInfo info = clientSocket.getReady();

                PosApiEntity posApiEntity = posApiDAO.findById(info.getPosId());

                if (posApiEntity == null) {
                    posApiEntity = new PosApiEntity();
                }

                posApiEntity.setId(info.getPosId());
                posApiEntity.setPath(info.getPosApiPath());
                posApiEntity.setRegisterNo(info.getRegisterNo());
                posApiEntity.setBranchNo(info.getBranchNo());

                posApiDAO.merge(posApiEntity);

                if (POS_SOCKET_LIST.containsKey(info.getPosId())) {
                    PosClientSocket pcs = POS_SOCKET_LIST.get(info.getPosId());
                    pcs.close();
                }

                POS_SOCKET_LIST.put(info.getPosId(), clientSocket);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void execClient(ServletContext ctx, String path) throws IOException {
        String posApiPath = ctx.getRealPath("WEB-INF/posClient/posapi-server-client.jar");

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("java", "-jar", posApiPath, path, Helper.SERVER_PORT + "");
        Process process = processBuilder.start();
    }

    public static PosClientSocket getPosClientSocket(String posId) {
        return POS_SOCKET_LIST.get(posId);
    }

    public static Map<String, PosClientSocket> getPosClientSocketMap() {
        return POS_SOCKET_LIST;
    }
}
