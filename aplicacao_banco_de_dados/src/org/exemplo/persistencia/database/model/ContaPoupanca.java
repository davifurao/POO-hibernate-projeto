package org.exemplo.persistencia.database.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "conta_poupanca")
public class ContaPoupanca implements IConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String numero_conta;
    private float saldo;
    private boolean status;

    public ContaPoupanca() {}

    public ContaPoupanca(int id) {
        this.id = id;
    }

    public ContaPoupanca(String numero_conta, float saldo, boolean status) {
        this.numero_conta = numero_conta;
        this.saldo = saldo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero_conta() {
        return numero_conta;
    }

    public void setNumero_conta(String numero_conta) {
        this.numero_conta = numero_conta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero_conta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContaPoupanca other = (ContaPoupanca) obj;
        return Objects.equals(numero_conta, other.numero_conta);
    }

    @Override
    public String toString() {
        return "ContaPoupanca [id=" + id + ", numero_conta=" + numero_conta + ", saldo=" + saldo + ", status=" + status
                + "]";
    }

}
