package mn.mta.vatps.pos.server.listener;

import mn.mta.vatps.pos.server.data.dao.PosApiDAO;
import mn.mta.vatps.pos.server.data.entity.PosApiEntity;
import mn.mta.vatps.pos.server.helper.Helper;
import mn.mta.vatps.pos.server.socket.PosClientSocket;
import mn.mta.vatps.pos.server.socket.PosServerSocket;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebListener
public class PosClientListener implements ServletContextListener {

    public final Logger logger = Logger.getLogger(PosClientListener.class.getName());

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ServletContext ctx = servletContextEvent.getServletContext();

            HashMap<String, PosClientSocket> posApiList = new HashMap<String, PosClientSocket>();
            PosServerSocket pss = new PosServerSocket(posApiList, ctx);
            pss.start();

            ctx.setAttribute(Helper.POS_SOCKET_SERVER, pss);

            PosApiDAO posApiDAO = PosApiDAO.build(ctx);
            List<PosApiEntity> posApiEntities = posApiDAO.findAll();

            for (PosApiEntity posApiEntity : posApiEntities) {
                pss.execClient(ctx, posApiEntity.getPath());
            }


        } catch (IOException e) {
            logger.throwing(PosClientListener.class.getName(), "contextInitialized", e);
        }
    }

}