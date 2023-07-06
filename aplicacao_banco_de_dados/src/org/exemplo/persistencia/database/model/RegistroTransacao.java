package org.exemplo.persistencia.database.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registro_transacao")
public class RegistroTransacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String numero_conta;
	private float valor;
	private String tipo_conta;
	private String tipo_transacao;
	
	public RegistroTransacao() {};
	public RegistroTransacao(int id) {this.id = id;};
	public RegistroTransacao(String numero_conta,float valor,String tipo_conta,String tipo_transacao) {
		this.numero_conta = numero_conta;
		this.valor = valor;
		this.tipo_conta = tipo_conta;
		this.tipo_transacao = tipo_transacao;
		
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
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getTipo_conta() {
		return tipo_conta;
	}
	public void setTipo_conta(String tipo_conta) {
		this.tipo_conta = tipo_conta;
	}
	public String getTipo_transacao() {
		return tipo_transacao;
	}
	public void setTipo_transacao(String tipo_transacao) {
		this.tipo_transacao = tipo_transacao;
	}
	@Override
	public String toString() {
		return "RegistroTransacao [id=" + id + ", numero_conta=" + numero_conta + ", valor=" + valor + ", tipo_conta="
				+ tipo_conta + ", tipo_transacao=" + tipo_transacao + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, numero_conta);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroTransacao other = (RegistroTransacao) obj;
		return id == other.id && Objects.equals(numero_conta, other.numero_conta);
	}
	
	
}
