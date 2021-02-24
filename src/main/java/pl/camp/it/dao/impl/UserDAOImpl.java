package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IUserDAO;
import pl.camp.it.model.User;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDAOImpl implements IUserDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persist(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    /*@Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.model.User WHERE id = :id");
        query.setParameter("id", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {

        } finally {
            session.close();
        }

        return user;
    }*/

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot);
        criteriaQuery.where(builder.equal(userRoot.get("id"), id));
        Query<User> query = session.createQuery(criteriaQuery);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {

        } finally {
            session.close();
        }

        return user;
    }


}
