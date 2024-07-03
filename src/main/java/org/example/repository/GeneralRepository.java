package org.example.repository;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class GeneralRepository <T> {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public GeneralRepository() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public boolean save(T entity) {

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }

    }

    public boolean delete(T entity) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(T entity) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error updating: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    public T findById(Class<T> entityClass, long id) {
        try {
            session = sessionFactory.openSession();
            return session.get(entityClass, id);
        } catch (Exception e) {
            System.out.println("Error finding by ID: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    public List<T> findAll(Class<T> classe){
        String query = "FROM " + classe.getSimpleName();
        session = sessionFactory.openSession();
        Query typedQuery = session.createQuery(query, classe);
        List<T> list = typedQuery.getResultList();
        session.close();
        return list;
    }
}