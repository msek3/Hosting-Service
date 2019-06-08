package siwz.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Consumer;

public abstract class BasicDao {

    protected SessionFactory factory;

    protected void doWithSession(SessionFactory factory, Consumer<Session> command){
        try (Session session = factory.openSession()){
            Transaction tx = session.beginTransaction();

            command.accept(session);
            if(tx.isActive() && !tx.getRollbackOnly()){
                tx.commit();
            }
            else{
                tx.rollback();
            }
        }
    }
}
