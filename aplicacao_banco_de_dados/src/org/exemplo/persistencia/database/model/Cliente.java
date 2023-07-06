package org.exemplo.persistencia.database.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//pois o banco é auto_incremment
	private int id;
	
	private String cpf;
	private String nome;
	private String conta_corrente_numero;
	private String conta_poupanca_numero;
	
	public Cliente() {};
	public Cliente(int id) {this.id = id;};
	public Cliente(String cpf,String nome, String conta_corrente_numero, String conta_poupanca_numero) {
		this.conta_corrente_numero = conta_corrente_numero;
		this.conta_poupanca_numero = conta_poupanca_numero;
		this.nome = nome;
		this.cpf = cpf;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cpf, id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf) && id == other.id;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", conta_corrente_numero="
				+ conta_corrente_numero + ", conta_poupanca_numero=" + conta_poupanca_numero + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getConta_corrente_numero() {
		return conta_corrente_numero;
	}
	public void setConta_corrente_numero(String conta_corrente_numero) {
		this.conta_corrente_numero = conta_corrente_numero;
	}
	public String getConta_poupanca_numero() {
		return conta_poupanca_numero;
	}
	public void setConta_poupanca_numero(String conta_poupanca_numero) {
		this.conta_poupanca_numero = conta_poupanca_numero;
	};
	
	
}
