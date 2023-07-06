package org.exemplo.persistencia.database.application;

import java.util.List;

import org.exemplo.persistencia.database.dao.ContaCorrenteDAO;
import org.exemplo.persistencia.database.dao.ContaPoupancaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.ContaCorrente;
import org.exemplo.persistencia.database.model.ContaPoupanca;

;

public class OperacoesBancarias {

    private IEntityDAO<ContaCorrente> contaCorrenteDAO;
    private IEntityDAO<ContaPoupanca> contaPoupancaDAO;

    public OperacoesBancarias() {
        contaCorrenteDAO = new ContaCorrenteDAO(new ConexaoBancoHibernate());
        contaPoupancaDAO = new ContaPoupancaDAO(new ConexaoBancoHibernate());
    }

    public void saqueContaCorrente(String numero_conta, float valor) {
        ContaCorrente contaCorrente = contaCorrenteDAO.findByNumeroConta(numero_conta);
        if (contaCorrente != null) {
            float saldoAtual = contaCorrente.getSaldo();
            float saldoFinal = saldoAtual - valor - calcularTaxaSaque(valor);
            if (saldoFinal >= 0) {
                contaCorrente.setSaldo(saldoFinal);
                contaCorrenteDAO.update(contaCorrente);
                System.out.println("Saque realizado com sucesso.");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("A conta corrente com o n�mero especificado n�o existe.");
        }
    }

    public void saqueContaPoupanca(String numero_conta, float valor) {
        ContaPoupanca contaPoupanca = contaPoupancaDAO.findByNumeroConta(numero_conta);
        if (contaPoupanca != null) {
            float saldoAtual = contaPoupanca.getSaldo();
            float saldoFinal = saldoAtual - valor - calcularTaxaSaque(valor);
            if (saldoFinal >= 0) {
                contaPoupanca.setSaldo(saldoFinal);
                contaPoupancaDAO.update(contaPoupanca);
                System.out.println("Saque realizado com sucesso.");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("A conta poupan�a com o n�mero especificado n�o existe.");
        }
    }

    public void transferencia(String origem, String destino, float valor) {
        ContaCorrente origemCorrente = contaCorrenteDAO.findByNumeroConta(origem);
        ContaCorrente destinoCorrente = contaCorrenteDAO.findByNumeroConta(destino);
        ContaPoupanca origemPoupanca = contaPoupancaDAO.findByNumeroConta(origem);
        ContaPoupanca destinoPoupanca = contaPoupancaDAO.findByNumeroConta(destino);

        if (origemCorrente != null && destinoCorrente != null) {
            // Transfer�ncia entre contas correntes
            if (origemCorrente.getSaldo() >= valor) {
                origemCorrente.setSaldo(origemCorrente.getSaldo() - valor - calcularTaxaTransferencia());
                destinoCorrente.setSaldo(destinoCorrente.getSaldo() + valor);
                contaCorrenteDAO.update(origemCorrente);
                contaCorrenteDAO.update(destinoCorrente);
                System.out.println("Transfer�ncia realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else if (origemPoupanca != null && destinoPoupanca != null) {
            // Transfer�ncia entre contas poupan�a
            if (origemPoupanca.getSaldo() >= valor) {
                origemPoupanca.setSaldo(origemPoupanca.getSaldo() - valor);
                destinoPoupanca.setSaldo(destinoPoupanca.getSaldo() + valor);
                contaPoupancaDAO.update(origemPoupanca);
                contaPoupancaDAO.update(destinoPoupanca);
                System.out.println("Transfer�ncia realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else if (origemCorrente != null && destinoPoupanca != null) {
            // Transfer�ncia entre conta corrente e conta poupan�a
            if (origemCorrente.getSaldo() >= valor) {
                origemCorrente.setSaldo(origemCorrente.getSaldo() - valor);
                destinoPoupanca.setSaldo(destinoPoupanca.getSaldo() + valor);
                contaCorrenteDAO.update(origemCorrente);
                contaPoupancaDAO.update(destinoPoupanca);
                System.out.println("Transfer�ncia realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else if (origemPoupanca != null && destinoCorrente != null) {
            // Transfer�ncia entre conta poupan�a e conta corrente
            if (origemPoupanca.getSaldo() >= valor) {
                origemPoupanca.setSaldo(origemPoupanca.getSaldo() - valor);
                destinoCorrente.setSaldo(destinoCorrente.getSaldo() + valor);
                contaPoupancaDAO.update(origemPoupanca);
                contaCorrenteDAO.update(destinoCorrente);
                System.out.println("Transfer�ncia realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else {
            System.out.println("As contas informadas n�o existem ou n�o s�o do mesmo tipo.");
        }
    }

    public void deposito(String numero_conta, float valor) {
        ContaCorrente contaCorrente = contaCorrenteDAO.findByNumeroConta(numero_conta);
        ContaPoupanca contaPoupanca = contaPoupancaDAO.findByNumeroConta(numero_conta);

        if (contaCorrente != null) {
            contaCorrente.setSaldo(contaCorrente.getSaldo() + valor);
            contaCorrenteDAO.update(contaCorrente);
            System.out.println("Dep�sito realizado com sucesso.");
        } else if (contaPoupanca != null) {
            contaPoupanca.setSaldo(contaPoupanca.getSaldo() + valor);
            contaPoupancaDAO.update(contaPoupanca);
            System.out.println("Dep�sito realizado com sucesso.");
        } else {
            System.out.println("A conta com o n�mero especificado n�o existe.");
        }
    }

    public float balanco() {
        float total = 0;

        List<ContaCorrente> contasCorrente = contaCorrenteDAO.findAll();
        for (ContaCorrente contaCorrente : contasCorrente) {
            total += contaCorrente.getSaldo();
        }

        List<ContaPoupanca> contasPoupanca = contaPoupancaDAO.findAll();
        for (ContaPoupanca contaPoupanca : contasPoupanca) {
            total += contaPoupanca.getSaldo();
        }

        return total;
    }

    private float calcularTaxaSaque(float valor) {
        // Implemente o c�lculo da taxa de saque aqui
        return valor * 0.01f; // Exemplo: 1% do valor de saque
    }

    private float calcularTaxaTransferencia() {
        // Implemente o c�lculo da taxa de transfer�ncia aqui
        return 10.0f; // Exemplo: taxa fixa de R$10,00
    }
}