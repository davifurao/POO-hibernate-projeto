package org.exemplo.persistencia.database.facade;

import org.exemplo.persistencia.database.dao.ClienteDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.Cliente;

public class ClienteFacade {

    private IEntityDAO<Cliente> clienteDAO;
    private static ClienteFacade instance;

    private ClienteFacade() {
        clienteDAO = new ClienteDAO(new ConexaoBancoHibernate());
    }

    public static ClienteFacade getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new ClienteFacade();
            return instance;
        }
    }

    public void save(String cpf, String nome, String conta_corrente_numero, String conta_poupanca_numero) {
        clienteDAO.save(new Cliente(cpf, nome, conta_corrente_numero, conta_poupanca_numero));
    }

    public void delete(int id) {
        Cliente c = clienteDAO.findById(id);
        clienteDAO.delete(c);
    }

    public void update(int id, String cpf, String nome, String conta_corrente_numero, String conta_poupanca_numero) {
        Cliente c = clienteDAO.findById(id);
        if (c != null) {
            c.setCpf(cpf);
            c.setNome(nome);
            c.setConta_corrente_numero(conta_corrente_numero);
            c.setConta_poupanca_numero(conta_poupanca_numero);
            clienteDAO.update(c);
        }
    }

    public Cliente findById(int id) {
        return clienteDAO.findById(id);
    }

    public Cliente findByNumeroConta(String numero) {
        return clienteDAO.findByNumeroConta(numero);
    }
    
    public Cliente findByCPF(String cpf) {
        return clienteDAO.findByCPF(cpf);
    }

}
