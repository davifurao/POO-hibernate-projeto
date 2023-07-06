package org.exemplo.persistencia.database.application;

import org.exemplo.persistencia.database.dao.ClienteDAO;
import org.exemplo.persistencia.database.dao.ContaCorrenteDAO;
import org.exemplo.persistencia.database.dao.ContaPoupancaDAO;
import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.ConexaoBancoHibernate;
import org.exemplo.persistencia.database.facade.ClienteFacade;
import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.ContaCorrente;
import org.exemplo.persistencia.database.model.ContaPoupanca;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializar DAOs
        IEntityDAO<Cliente> clienteDAO = new ClienteDAO(new ConexaoBancoHibernate());
        IEntityDAO<ContaCorrente> contaCorrenteDAO = new ContaCorrenteDAO(new ConexaoBancoHibernate());
        IEntityDAO<ContaPoupanca> contaPoupancaDAO = new ContaPoupancaDAO(new ConexaoBancoHibernate());

        
        Cliente cliente = null; // Declarar a vari�vel cliente antes do bloco if-else

        
        // Criar um cliente
        System.out.println("Deseja criar um novo cliente (S/N)?");
        String opcaoCriarCliente = scanner.nextLine();

        if (opcaoCriarCliente.equalsIgnoreCase("S")) {
            // Criar um cliente
            System.out.println("Criar novo cliente");
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            ClienteFacade clienteFacade = ClienteFacade.getInstance();
            clienteFacade.save(cpf, nome,"","");
            cliente = clienteDAO.findByCPF(cpf);
        } else {
            System.out.print("CPF do cliente existente: ");
            String cpfExistente = scanner.nextLine();

             cliente = clienteDAO.findByCPF(cpfExistente);

            if (cliente == null) {
                System.out.println("Cliente n�o encontrado. Saindo do programa.");
                scanner.close();
                return;
            }
        }
        
        System.out.println("Deseja criar uma conta corrente (S/N)?");
        String opcaoCriarContaCorrente = scanner.nextLine();

        if (opcaoCriarContaCorrente.equalsIgnoreCase("S")) {
            System.out.print("N�mero da conta corrente: ");
            String numeroContaCorrente = scanner.nextLine();
            System.out.print("Saldo inicial: ");
            float saldoCorrente = scanner.nextFloat();
            scanner.nextLine(); // Limpar o buffer do scanner

            ContaCorrente contaCorrente = new ContaCorrente(numeroContaCorrente, saldoCorrente, true);
            contaCorrenteDAO.save(contaCorrente);

            cliente.setConta_corrente_numero(contaCorrente.getNumero_conta());
            clienteDAO.update(cliente);
        }
        System.out.println("Deseja criar uma conta poupanca (S/N)?");
        String opcaoCriarContaPoupanca = scanner.nextLine();
        
        if(opcaoCriarContaPoupanca.equalsIgnoreCase("S")) {
        	System.out.println("Numero da conta poupanca");
        	String numeroContaPoupanca = scanner.nextLine();
        	System.out.println("Saldo inicial: ");
        	float saldoPoupanca = scanner.nextFloat();
        	scanner.nextLine();
        	
        	ContaPoupanca contaPoupanca = new ContaPoupanca(numeroContaPoupanca,saldoPoupanca,true);
        	contaPoupancaDAO.save(contaPoupanca);
        	
        	cliente.setConta_poupanca_numero(contaPoupanca.getNumero_conta());
        	clienteDAO.update(cliente);
        }
        




        // Realizar opera��es banc�rias
        OperacoesBancarias operacoesBancarias = new OperacoesBancarias();

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.print("N�mero da conta corrente: ");
                    String numeroContaCorrenteSaque = scanner.nextLine();
                    System.out.print("Valor do saque: ");
                    float valorSaque = scanner.nextFloat();
                    scanner.nextLine(); // Limpar o buffer do scanner

                    operacoesBancarias.saqueContaCorrente(numeroContaCorrenteSaque, valorSaque);
                    break;
                case 2:
                    System.out.print("N�mero da conta poupan�a: ");
                    String numeroContaPoupancaSaque = scanner.nextLine();
                    System.out.print("Valor do saque: ");
                    float valorPoupancaSaque = scanner.nextFloat();
                    scanner.nextLine(); // Limpar o buffer do scanner

                    operacoesBancarias.saqueContaPoupanca(numeroContaPoupancaSaque, valorPoupancaSaque);
                    break;
                case 3:
                    System.out.print("N�mero da conta de origem: ");
                    String numeroContaOrigem = scanner.nextLine();
                    System.out.print("N�mero da conta de destino: ");
                    String numeroContaDestino = scanner.nextLine();
                    System.out.print("Valor da transfer�ncia: ");
                    float valorTransferencia = scanner.nextFloat();
                    scanner.nextLine(); // Limpar o buffer do scanner

                    operacoesBancarias.transferencia(numeroContaOrigem, numeroContaDestino, valorTransferencia);
                    break;
                case 4:
                    System.out.print("N�mero da conta: ");
                    String numeroContaDeposito = scanner.nextLine();
                    System.out.print("Valor do dep�sito: ");
                    float valorDeposito = scanner.nextFloat();
                    scanner.nextLine(); // Limpar o buffer do scanner

                    operacoesBancarias.deposito(numeroContaDeposito, valorDeposito);
                    break;
                case 5:
                    float balanco = operacoesBancarias.balanco();
                    System.out.println("Balan�o total: " + balanco);
                    break;
                case 0:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Op��o inv�lida.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("----- Menu -----");
        System.out.println("1. Saque (Conta Corrente)");
        System.out.println("2. Saque (Conta Poupan�a)");
        System.out.println("3. Transfer�ncia");
        System.out.println("4. Dep�sito");
        System.out.println("5. Balan�o");
        System.out.println("0. Sair");
        System.out.print("Escolha uma op��o: ");
    }
}
