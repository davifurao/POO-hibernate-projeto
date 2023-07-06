package org.exemplo.persistencia.database.model;

public interface IConta {

	int getId();
    void setId(int id);
    String getNumero_conta();
    void setNumero_conta(String numero);
    float getSaldo();
    void setSaldo(float saldo);
	
}
