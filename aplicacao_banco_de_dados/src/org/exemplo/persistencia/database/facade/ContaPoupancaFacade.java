package org.exemplo.persistencia.database.facade;

import org.exemplo.persistencia.database.dao.ContaPoupancaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.ContaPoupanca;

public class ContaPoupancaFacade {

    private IEntityDAO<ContaPoupanca> contaPoupancaDAO;
    private static ContaPoupancaFacade instance;

    private ContaPoupancaFacade() {
        contaPoupancaDAO = new ContaPoupancaDAO(new ConexaoBancoHibernate());
    }

    public static ContaPoupancaFacade getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new ContaPoupancaFacade();
            return instance;
        }
    }

    public void save(String numero_conta, float saldo, boolean status) {
        contaPoupancaDAO.save(new ContaPoupanca(numero_conta, saldo, status));
    }

    public void delete(String numero_conta) {
        ContaPoupanca c = contaPoupancaDAO.findByNumeroConta(numero_conta);
        contaPoupancaDAO.delete(c);
    }

    public void update(String numero_conta, float saldo, boolean status) {
        contaPoupancaDAO.update(new ContaPoupanca(numero_conta, saldo, status));
    }

    public ContaPoupanca findByNumeroConta(String numero) {
        return contaPoupancaDAO.findByNumeroConta(numero);
    }
}
