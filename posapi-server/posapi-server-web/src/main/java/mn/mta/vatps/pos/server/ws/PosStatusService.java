package mn.mta.vatps.pos.server.ws;

import mn.mta.vatps.pos.client.model.PosInfo;
import mn.mta.vatps.pos.server.data.dao.PosApiDAO;
import mn.mta.vatps.pos.server.data.entity.PosApiEntity;
import mn.mta.vatps.pos.server.helper.Helper;
import mn.mta.vatps.pos.server.socket.PosClientSocket;
import mn.mta.vatps.pos.server.socket.PosServerSocket;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

/**
 * Created by nasanjargal on 1/5/16.
 */
@Path("/status")
public class PosStatusService {

    @GET
    @Path("/api_list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws Exception {
        List<PosInfo> statusList = getStatusList();
        String result = Helper.GSON.toJson(statusList);
        Response response = Response.ok(result).build();
        return response;
    }

    @GET
    @Path("/api_status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response status(@QueryParam("posId") String posId, @Context ServletContext ctx) throws Exception {
        PosInfo posInfo = getStatus(posId);
        String result = Helper.GSON.toJson(posInfo);
        return Response.ok().entity(result).build();
    }

    private List<PosInfo> getStatusList() throws IOException {
        ArrayList<PosInfo> statusList = new ArrayList<PosInfo>();
        Map<String, PosClientSocket> sockets = PosServerSocket.getPosClientSocketMap();
        for (String posId : sockets.keySet()) {
            statusList.add(sockets.get(posId).getPosInfo());
        }
        return statusList;
    }

    private PosInfo getStatus(String posId) throws IOException, ClassNotFoundException {
        PosClientSocket psc = PosServerSocket.getPosClientSocket(posId);
        psc.refreshPosInfo();
        return psc.getPosInfo();
    }

}
