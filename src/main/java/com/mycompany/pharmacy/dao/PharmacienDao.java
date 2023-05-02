package com.mycompany.pharmacy.dao;
import com.mycompany.pharmacy.entities.Pharmacien;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PharmacienDao {
    private final SessionFactory factory;

    public PharmacienDao(SessionFactory factory) {
        this.factory = factory;
    }
    
    //enregistrer pharmacien
    public boolean savePharmacien(Pharmacien pharmacien) {
        boolean f = false;
        try {
            Session session = this.factory.openSession();
            Transaction tx = session.beginTransaction();

            session.save(pharmacien);

            tx.commit();
            session.close();
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
            f = false;
        }
        return f;
    }
    
    //modifier pharmacien
    public void updatePharmacien(Pharmacien pharmacien) {
        Session session = factory.getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.update(pharmacien);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    //supprimer pharmacien 
    public boolean deleteMedicament(int idPharmacien) {
        boolean f = false;
        try {
            Session session = this.factory.openSession();
            Transaction tx = session.beginTransaction();
            
            Pharmacien pharmacien = session.get(Pharmacien.class, idPharmacien);
            
            if(pharmacien != null) {
                session.delete(pharmacien);
                f = true;
            }
            tx.commit();
            session.close();
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
            f = false;
        }
        return f;
    }
    
    //trouver un pharmacien par son id
    public Pharmacien getPharmacien(int idPharmacien) {
        Pharmacien pharmacien = null;
        Transaction tx = null;
        
        try {
            Session session = this.factory.openSession();
            tx = session.beginTransaction();
            
            pharmacien = session.get(Pharmacien.class, idPharmacien);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return pharmacien;
    }
    
    //trouver un pharmacien par son nom
    public List<Pharmacien> searchByName(String nom) {
        Session session = this.factory.openSession();
        String hql = "from Pharmacien where nom like :n";
        Query<Pharmacien> query = session.createQuery(hql, Pharmacien.class);
        query.setParameter("n", "%" + nom + "%");
        List<Pharmacien> pharmacien = query.getResultList();
        session.close();
        return pharmacien;
    }
    
    //trouver toutes les pharmaciens
    public List<Pharmacien> getAllPharmacien() {
        Session s = this.factory.openSession();
        Query query = s.createQuery("from Pharmacien");
        List<Pharmacien> pharmaciens = query.list();

        return pharmaciens;
    }
}