package br.com.cit.lab.model;

public class Client {
	private String name;
	private Phone telefone;

	public String getName() {
		return name;
	}

	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	public Client(String name, Phone telefone) {
		super();
		this.name = name;
		this.telefone = telefone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Phone getTelefone() {
		return telefone;
	}

	public void setTelefone(Phone telefone) {
		this.telefone = telefone;
	}
}
