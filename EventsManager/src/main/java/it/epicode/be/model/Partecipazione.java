package it.epicode.be.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name = "getPartecipazioniDaConfermarePerEvento", 
query = "SELECT a FROM Partecipazione a JOIN a.evento e  WHERE a.stato = :stato AND a.evento=:evento")




public class Partecipazione {

	public enum Stato {CONFERMATA, DA_CONFERMARE};
	@Id
	@SequenceGenerator(name="chiavePartecipazione", sequenceName = "partecipazione_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="chiavePartecipazione")
	private Long id;
	@ManyToOne
	private Persona persona;
	@ManyToOne(fetch = FetchType.LAZY)
	private Evento evento;
	@Enumerated(EnumType.STRING)
	private Stato stato;
	
	
	
	public Partecipazione() {
		
	}
	
	public Partecipazione(Persona persona, Evento evento, Stato stato) {
		this.persona = persona;
		this.evento = evento;		
		this.stato = stato;
		evento.addPartecipante();
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	
	public LocalDate getDataEvento() {
		return getEvento().getDataEvento();
	}
	
}
