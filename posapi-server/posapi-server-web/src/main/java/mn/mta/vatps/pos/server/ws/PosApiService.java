package mn.mta.vatps.pos.server.ws;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import mn.mta.vatps.pos.server.data.dao.PosApiDAO;
import mn.mta.vatps.pos.server.data.entity.PosApiEntity;
import mn.mta.vatps.pos.server.helper.Helper;
import mn.mta.vatps.pos.server.listener.HibernateContext;
import mn.mta.vatps.pos.server.socket.PosClientSocket;
import mn.mta.vatps.pos.server.socket.PosServerSocket;
import org.apache.commons.io.IOUtils;

/**
 * Created by nasanjargal on 1/5/16.
 */
@Path("/posApi")
public class PosApiService {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @FormDataParam("posapi") InputStream posApiInput,
            @FormDataParam("posapi") FormDataContentDisposition fileDetail,
            @Context ServletContext ctx
    ) throws URISyntaxException, IOException {

        File path = new File(Helper.FILE_PARENT_PATH + UUID.randomUUID().toString() + File.separator);
        if (path.mkdirs()) {

            String pathName = path.getPath() + File.separator + fileDetail.getFileName();

            FileOutputStream output = new FileOutputStream(pathName);
            IOUtils.copy(posApiInput, output);
            output.flush();
            output.close();

            PosServerSocket pss = (PosServerSocket) ctx.getAttribute(Helper.POS_SOCKET_SERVER);
            pss.execClient(ctx, pathName);

        } else {
            throw new IOException("Can't make directories : [" + path.toString() + "]");
        }

        return Response.temporaryRedirect(new URI("../index.jsp")).build();
    }

    @POST
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    public Response send(@QueryParam("posId") String posId, @Context ServletContext ctx) throws IOException, URISyntaxException, ClassNotFoundException {
        PosClientSocket posClientSocket = PosServerSocket.getPosClientSocket(posId);
        if (posClientSocket != null) {
            posClientSocket.getPosInfo().setSending(true);
            String result = posClientSocket.sendData();
            posClientSocket.refreshPosInfo();
            return Response.ok().entity(result).build();
        }

        return Response.status(500).entity("{\"success\":false}").build();
    }

    @POST
    @Path("/getInformation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInformation(@QueryParam("posId") String posId, @Context ServletContext ctx) throws IOException, URISyntaxException, ClassNotFoundException {
        PosClientSocket posClientSocket = PosServerSocket.getPosClientSocket(posId);
        if (posClientSocket != null) {
            String result = posClientSocket.getInformation();
            return Response.ok().entity(result).build();
        }

        return Response.status(500).entity("{\"success\":false}").build();
    }

    @POST
    @Path("/checkApi")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkApi(@QueryParam("posId") String posId, @Context ServletContext ctx) throws IOException, ClassNotFoundException {
        PosClientSocket posClientSocket = PosServerSocket.getPosClientSocket(posId);
        if (posClientSocket != null) {
            String result = posClientSocket.checkApi();
            return Response.ok().entity(result).build();
        }

        return Response.status(500).entity("{\"success\":false}").build();
    }

    @POST
    @Path("/callFunction")
    @Produces(MediaType.TEXT_PLAIN)
    public Response callFunction(@QueryParam("posId") String posId, @QueryParam("funcName") String funcName, String body, @Context ServletContext ctx) throws IOException, URISyntaxException, ClassNotFoundException {
        PosClientSocket posClientSocket = PosServerSocket.getPosClientSocket(posId);
        if (posClientSocket != null) {
            String result = posClientSocket.callFunction(funcName, body);
            return Response.ok().entity(result).build();
        }

        return Response.status(500).entity("{\"success\":false}").build();
    }

    @POST
    @Path("/put")
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@QueryParam("posId") String posId, @QueryParam("registerNo") String registerNo, String body, @Context ServletContext ctx) throws IOException, URISyntaxException, ClassNotFoundException {
        PosClientSocket posClientSocket = PosServerSocket.getPosClientSocket(posId);
        if (posClientSocket != null) {
            String result = posClientSocket.put(body);
            return Response.ok().entity(result).build();
        }

        return Response.status(500).entity("{\"success\":false}").build();
    }

    @POST
    @Path("/returnBill")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBill(@QueryParam("posId") String posId, @QueryParam("registerNo") String registerNo, String body, @Context ServletContext ctx) throws IOException, URISyntaxException, ClassNotFoundException {
        PosClientSocket posClientSocket = PosServerSocket.getPosClientSocket(posId);
        if (posClientSocket != null) {
            String result = posClientSocket.returnBill(body);
            return Response.ok().entity(result).build();
        }

        return Response.status(500).entity("{\"success\":false}").build();
    }
}
