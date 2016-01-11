package mn.mta.vatps.pos.server.listener;

import mn.mta.vatps.pos.server.helper.Helper;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;

/**
 * Created by nasanjargal on 1/7/16.
 */
public final class HibernateContext {
    private HibernateContext(){

    }

    public static SessionFactory getSessionFactory(ServletContext ctx){
        return (SessionFactory) ctx.getAttribute(Helper.HIBERNATE_SESSION_FACTORY);
    }

}
