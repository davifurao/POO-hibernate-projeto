package org.exemplo.persistencia.database.application;

import java.util.List;

import org.exemplo.persistencia.database.dao.ContaCorrenteDAO;
import org.exemplo.persistencia.database.dao.ContaPoupancaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.dao.RegistroTransacaoDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.model.ContaCorrente;
import org.exemplo.persistencia.database.model.ContaPoupanca;
import org.exemplo.persistencia.database.model.RegistroTransacao;

;

public class OperacoesBancarias {

    private IEntityDAO<ContaCorrente> contaCorrenteDAO;
    private IEntityDAO<ContaPoupanca> contaPoupancaDAO;
    private RegistroTransacaoDAO registroTransacaoDAO;

    public OperacoesBancarias() {
        contaCorrenteDAO = new ContaCorrenteDAO(new ConexaoBancoHibernate());
        contaPoupancaDAO = new ContaPoupancaDAO(new ConexaoBancoHibernate());
        registroTransacaoDAO = new RegistroTransacaoDAO(new ConexaoBancoHibernate());
    }

    public void saqueContaCorrente(String numero_conta, float valor) {
    	String tipo_conta;
    	String tipo_transacao;
    	
    	tipo_conta = "Conta Corrente";
    	tipo_transacao = "Saque";
    	
        ContaCorrente contaCorrente = contaCorrenteDAO.findByNumeroConta(numero_conta);
        if (contaCorrente != null) {
            float saldoAtual = contaCorrente.getSaldo();
            float saldoFinal = saldoAtual - valor - calcularTaxaSaque(valor);
            if (saldoFinal >= 0) {
                contaCorrente.setSaldo(saldoFinal);
                contaCorrenteDAO.update(contaCorrente);
                System.out.println("Saque realizado com sucesso.");
                registroTransacaoDAO.save(new RegistroTransacao(contaCorrente.getNumero_conta(),valor,tipo_conta,tipo_transacao));
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("A conta corrente com o número especificado não existe.");
        }
    }

    public void saqueContaPoupanca(String numero_conta, float valor) {
    	String tipo_conta;
    	String tipo_transacao;
    	
    	tipo_conta = "Conta Poupanca";
    	tipo_transacao = "Saque";
    	
    	
        ContaPoupanca contaPoupanca = contaPoupancaDAO.findByNumeroConta(numero_conta);
        if (contaPoupanca != null) {
            float saldoAtual = contaPoupanca.getSaldo();
            float saldoFinal = saldoAtual - valor - calcularTaxaSaque(valor);
            if (saldoFinal >= 0) {
                contaPoupanca.setSaldo(saldoFinal);
                contaPoupancaDAO.update(contaPoupanca);
                System.out.println("Saque realizado com sucesso.");
                registroTransacaoDAO.save(new RegistroTransacao(contaPoupanca.getNumero_conta(),valor,tipo_conta,tipo_transacao));
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("A conta poupança com o número especificado não existe.");
        }
    }

    public void transferencia(String origem, String destino, float valor) {
    	String tipo_conta;
    	String tipo_transacao;
    	
    
    	
    	
        ContaCorrente origemCorrente = contaCorrenteDAO.findByNumeroConta(origem);
        ContaCorrente destinoCorrente = contaCorrenteDAO.findByNumeroConta(destino);
        ContaPoupanca origemPoupanca = contaPoupancaDAO.findByNumeroConta(origem);
        ContaPoupanca destinoPoupanca = contaPoupancaDAO.findByNumeroConta(destino);

        if (origemCorrente != null && destinoCorrente != null) {
            // Transferência entre contas correntes
            if (origemCorrente.getSaldo() >= valor) {
                origemCorrente.setSaldo(origemCorrente.getSaldo() - valor - calcularTaxaTransferencia());
                destinoCorrente.setSaldo(destinoCorrente.getSaldo() + valor);
                contaCorrenteDAO.update(origemCorrente);
                contaCorrenteDAO.update(destinoCorrente);
                registroTransacaoDAO.save(new RegistroTransacao(origemCorrente.getNumero_conta(),valor,"Conta Corrente","Transferencia Credito"));
                registroTransacaoDAO.save(new RegistroTransacao(destinoCorrente.getNumero_conta(),valor,"Conta Corrente","Transferencia Credito"));
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else if (origemPoupanca != null && destinoPoupanca != null) {
            // Transferência entre contas poupança
            if (origemPoupanca.getSaldo() >= valor) {
                origemPoupanca.setSaldo(origemPoupanca.getSaldo() - valor);
                destinoPoupanca.setSaldo(destinoPoupanca.getSaldo() + valor);
                contaPoupancaDAO.update(origemPoupanca);
                contaPoupancaDAO.update(destinoPoupanca);
                registroTransacaoDAO.save(new RegistroTransacao(destinoPoupanca.getNumero_conta(),valor,"Conta Poupanca","Transferencia Credito"));
                registroTransacaoDAO.save(new RegistroTransacao(origemPoupanca.getNumero_conta(),valor,"Conta Poupanca","Transferencia Debito"));
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else if (origemCorrente != null && destinoPoupanca != null) {
            // Transferência entre conta corrente e conta poupança
            if (origemCorrente.getSaldo() >= valor) {
                origemCorrente.setSaldo(origemCorrente.getSaldo() - valor);
                destinoPoupanca.setSaldo(destinoPoupanca.getSaldo() + valor);
                contaCorrenteDAO.update(origemCorrente);
                contaPoupancaDAO.update(destinoPoupanca);
                registroTransacaoDAO.save(new RegistroTransacao(origemCorrente.getNumero_conta(),valor,"Conta Corrente","Transferencia Debito"));
                registroTransacaoDAO.save(new RegistroTransacao(destinoPoupanca.getNumero_conta(),valor,"Conta Poupanca","Transferencia Credito"));
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else if (origemPoupanca != null && destinoCorrente != null) {
            // Transferência entre conta poupança e conta corrente
            if (origemPoupanca.getSaldo() >= valor) {
                origemPoupanca.setSaldo(origemPoupanca.getSaldo() - valor);
                destinoCorrente.setSaldo(destinoCorrente.getSaldo() + valor);
                contaPoupancaDAO.update(origemPoupanca);
                contaCorrenteDAO.update(destinoCorrente);
                registroTransacaoDAO.save(new RegistroTransacao(origemPoupanca.getNumero_conta(),valor,"Conta Poupanca","Transferencia Debito"));
                registroTransacaoDAO.save(new RegistroTransacao(destinoCorrente.getNumero_conta(),valor,"Conta Corrente","Transferencia Credito"));
                System.out.println("Transferência realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente na conta de origem.");
            }
        } else {
            System.out.println("As contas informadas não existem ou não são do mesmo tipo.");
        }
    }

    public void deposito(String numero_conta, float valor) {
    	
    	String tipo_conta;
    	String tipo_transacao;
    	
    	
    	tipo_transacao = "deposito";
    	
    	
        ContaCorrente contaCorrente = contaCorrenteDAO.findByNumeroConta(numero_conta);
        ContaPoupanca contaPoupanca = contaPoupancaDAO.findByNumeroConta(numero_conta);

        if (contaCorrente != null) {
        	tipo_conta = "Conta Corrente";
            contaCorrente.setSaldo(contaCorrente.getSaldo() + valor);
            contaCorrenteDAO.update(contaCorrente);
            registroTransacaoDAO.save(new RegistroTransacao(contaCorrente.getNumero_conta(),valor,tipo_conta,tipo_transacao));
            System.out.println("Depósito realizado com sucesso.");
        } else if (contaPoupanca != null) {
        	tipo_conta = "Conta Poupanca";
            contaPoupanca.setSaldo(contaPoupanca.getSaldo() + valor);
            contaPoupancaDAO.update(contaPoupanca);
            registroTransacaoDAO.save(new RegistroTransacao(contaPoupanca.getNumero_conta(),valor,tipo_conta,tipo_transacao));
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("A conta com o número especificado não existe.");
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
        // Implemente o cálculo da taxa de saque aqui
        return valor * 0.01f; // Exemplo: 1% do valor de saque
    }

    private float calcularTaxaTransferencia() {
        // Implemente o cálculo da taxa de transferência aqui
        return 10.0f; // Exemplo: taxa fixa de R$10,00
    }
}