package org.example.repository;

import org.example.entity.InventaireItem;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
public class InventaireITemRepository extends GeneralRepository<InventaireItem>{
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();



}
