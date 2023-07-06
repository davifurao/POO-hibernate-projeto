package org.exemplo.persistencia.database.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaCorrente;

import org.hibernate.Session;

import org.hibernate.query.Query;

public class ContaCorrenteDAO implements IEntityDAO<ContaCorrente> {

	private IConnection conn;
	
	public ContaCorrenteDAO(IConnection conn) {
		this.conn = conn;
	}

	@Override
	public void save(ContaCorrente t) {
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.persist(t);
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public ContaCorrente findById(Integer id) {
		Session session = conn.getSessionFactory().openSession();
		return session.find(ContaCorrente.class, id);
	}

	@Override
	public List<ContaCorrente> findAll() {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ContaCorrente> query = builder.createQuery(ContaCorrente.class);
        Root<ContaCorrente> root = query.from(ContaCorrente.class);
        query.select(root);
        return session.createQuery(query).getResultList();
	}
	public ContaCorrente findByNumeroConta(String numero) {
	    Session session = conn.getSessionFactory().openSession();
	    Query<ContaCorrente> query = session.createQuery("FROM ContaCorrente c WHERE c.numero_conta = :numero", ContaCorrente.class);
	    query.setParameter("numero", numero);
	    return query.uniqueResult();
	}


	@Override
	public void update(ContaCorrente t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.merge(t);
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public void delete(ContaCorrente t) {
		// TODO Auto-generated method stub
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
