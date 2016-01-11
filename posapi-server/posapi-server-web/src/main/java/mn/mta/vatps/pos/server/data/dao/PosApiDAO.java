package mn.mta.vatps.pos.server.data.dao;

import mn.mta.vatps.pos.server.data.entity.PosApiEntity;
import mn.mta.vatps.pos.server.listener.HibernateContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Created by nasanjargal on 1/6/16.
 */
public class PosApiDAO {

    private SessionFactory sessionFactory;

    private PosApiDAO(ServletContext ctx) {
        this.sessionFactory = HibernateContext.getSessionFactory(ctx);
    }

    public static PosApiDAO build(ServletContext ctx) {
        return new PosApiDAO(ctx);
    }

    public List<PosApiEntity> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(PosApiEntity.class);
        criteria.addOrder(Order.asc("registerNo"));
        List<PosApiEntity> posApiEntities = criteria.list();
        tx.commit();
        session.close();
        return posApiEntities;
    }


    public PosApiEntity merge(PosApiEntity posApiEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(posApiEntity);
        session.flush();
        tx.commit();
        session.close();
        return posApiEntity;
    }

    public PosApiEntity findById(String posId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(PosApiEntity.class);
        criteria.add(Restrictions.eq("id", posId));
        PosApiEntity posApiEntity = (PosApiEntity) criteria.uniqueResult();
        tx.commit();
        session.close();
        return posApiEntity;
    }
}
