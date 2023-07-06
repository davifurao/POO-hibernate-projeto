package org.exemplo.persistencia.database.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaPoupanca;

import org.hibernate.Session;

import org.hibernate.query.Query;

public class ContaPoupancaDAO implements IEntityDAO<ContaPoupanca> {

    private IConnection conn;

    public ContaPoupancaDAO(IConnection conn) {
        this.conn = conn;
    }

    @Override
    public void save(ContaPoupanca t) {
        Session session = conn.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public ContaPoupanca findById(Integer id) {
        Session session = conn.getSessionFactory().openSession();
        return session.find(ContaPoupanca.class, id);
    }

    @Override
    public List<ContaPoupanca> findAll() {
        Session session = conn.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ContaPoupanca> query = builder.createQuery(ContaPoupanca.class);
        Root<ContaPoupanca> root = query.from(ContaPoupanca.class);
        query.select(root);
        return session.createQuery(query).getResultList();
    }

    public ContaPoupanca findByNumeroConta(String numero) {
        Session session = conn.getSessionFactory().openSession();
        Query<ContaPoupanca> query = session.createQuery("FROM ContaPoupanca c WHERE c.numero_conta = :numero", ContaPoupanca.class);
        query.setParameter("numero", numero);
        return query.uniqueResult();
    }

    @Override
    public void update(ContaPoupanca t) {
        Session session = conn.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(t);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(ContaPoupanca t) {
        Session session = conn.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

	@Override
	public Cliente findByCPF(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}
}
