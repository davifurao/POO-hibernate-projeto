package org.exemplo.persistencia.database.application;

import org.exemplo.persistencia.database.facade.ClienteFacade;
import org.exemplo.persistencia.database.model.Cliente;

public class Application {

    public static void main(String[] args) {

        ClienteFacade clienteFacade = ClienteFacade.getInstance();

        // Salvar um cliente
       Cliente c = clienteFacade.findByCPF("987654321");
        // Deletar um cliente
        int idDeletar = 1;
        clienteFacade.delete(c.getId());
    }
}
