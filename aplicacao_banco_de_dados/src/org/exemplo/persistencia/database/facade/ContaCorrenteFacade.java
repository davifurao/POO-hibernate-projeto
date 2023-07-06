package org.exemplo.persistencia.database.facade;

import org.exemplo.persistencia.database.dao.ContaCorrenteDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.ContaCorrente;

public class ContaCorrenteFacade {

	private IEntityDAO<ContaCorrente> contaCorrenteDAO;
	private static ContaCorrenteFacade instance;
	
	private ContaCorrenteFacade() {
		contaCorrenteDAO = new ContaCorrenteDAO(new ConexaoBancoHibernate());
	}
	
	public static ContaCorrenteFacade getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new ContaCorrenteFacade();
			return instance;
		}
	}
	
	public void save(String numero_conta,float saldo, boolean status) {
		contaCorrenteDAO.save(new ContaCorrente(numero_conta,saldo,status));
	}
	
	public void delete(String numero_conta) {
		ContaCorrente c =contaCorrenteDAO.findByNumeroConta(numero_conta);
		contaCorrenteDAO.delete(c);
	}
	
	public void update(String numero_conta,float saldo, boolean status) {
		contaCorrenteDAO.update(new ContaCorrente(numero_conta,saldo,status));
	}
	
	public ContaCorrente findByNumeroConta(String numero) {
		return contaCorrenteDAO.findByNumeroConta(numero);
	}
}
