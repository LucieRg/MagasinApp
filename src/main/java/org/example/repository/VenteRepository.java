package org.example.repository;

import org.example.entity.Client;
import org.example.entity.InventaireItem;
import org.example.entity.Vente;
import org.example.util.HibernateUtil;
import org.example.util.StatusType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class VenteRepository extends GeneralRepository<Vente> {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public boolean saveVente(Vente vente, Client client, StatusType statusType, List<InventaireItem> items) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(client);
            vente.setClient(client);
            vente.setStatusType(statusType);
            vente.setInventaireItem(items);
            session.save(vente);
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }


    }


    public List<Vente> findByStatus(StatusType statusType) {
        Session session = sessionFactory.openSession();
        List<Vente> ventes = null;
        try {
            ventes = session.createQuery("from Vente where statusType = :status", Vente.class)
                    .setParameter("status", statusType)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ventes;
    }


}