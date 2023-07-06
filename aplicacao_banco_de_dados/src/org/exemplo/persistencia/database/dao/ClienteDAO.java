package org.exemplo.persistencia.database.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Cliente;
import org.hibernate.Session;

public class ClienteDAO implements IEntityDAO<Cliente> {

	private IConnection conn;
	
	public ClienteDAO(IConnection conn) {
		this.conn = conn;
	}
	
	@Override
	public void save(Cliente t) {
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.persist(t);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Cliente findById(Integer id) {
		Session session = conn.getSessionFactory().openSession();
		return session.find(Cliente.class, id);
	}

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        Root<Cliente> root = query.from(Cliente.class);
        query.select(root);
        return session.createQuery(query).getResultList();
	}

	@Override
	public void update(Cliente t) {
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.merge(t);
		session.getTransaction().commit();
		session.close();
		
	}

	@Override
	public void delete(Cliente t) {
		// TODO Auto-generated method stub
		Session session = conn.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(t);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Cliente findByNumeroConta(String numero) {
	    Session session = conn.getSessionFactory().openSession();
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
	    Root<Cliente> root = query.from(Cliente.class);
	    query.select(root).where(builder.or(
	            builder.equal(root.get("conta_corrente_numero"), numero),
	            builder.equal(root.get("conta_poupanca_numero"), numero)
	    ));
	    Cliente cliente = session.createQuery(query).uniqueResult();

	    if (cliente != null) {
	        if (cliente.getConta_corrente_numero() != null && cliente.getConta_poupanca_numero() != null) {
	            // Encontrou nas duas tabelas, retorna a mensagem de ambiguidade
	            System.out.println("O número da conta está associado tanto a uma conta corrente quanto a uma conta poupança.");
	        } else if (cliente.getConta_corrente_numero() != null) {
	            // Encontrou apenas na tabela de ContaCorrente
	            System.out.println("O número da conta está associado a uma conta corrente.");
	        } else if (cliente.getConta_poupanca_numero() != null) {
	            // Encontrou apenas na tabela de ContaPoupanca
	            System.out.println("O número da conta está associado a uma conta poupança.");
	        }
	    } else {
	        // Não encontrou o número da conta em nenhuma tabela
	        System.out.println("A conta com o número especificado não existe.");
	    }

	    return cliente;
	}

	@Override
	public Cliente findByCPF(String cpf) {
	    Session session = conn.getSessionFactory().openSession();
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
	    Root<Cliente> root = query.from(Cliente.class);
	    query.select(root).where(builder.equal(root.get("cpf"), cpf));
	    return session.createQuery(query).uniqueResult();
	}


}
