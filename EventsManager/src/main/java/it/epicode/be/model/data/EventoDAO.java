package it.epicode.be.model.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import it.epicode.be.model.Concerto;
import it.epicode.be.model.Concerto.Genere;
import it.epicode.be.model.Evento;
import it.epicode.be.model.GaraDiAtletica;
import it.epicode.be.model.PartitaDiCalcio;
import it.epicode.be.model.Persona;
import it.epicode.be.utils.JpaUtil;

public class EventoDAO {
	private EntityManagerFactory factory;
	// private EntityManager em;

	public EventoDAO(JpaUtil ju) {
		factory = JpaUtil.getEntityManagerFactory();
		// em = JpaUtil.getEntityManager();
		// em.getTransaction().begin();
	}

	public void closeFA() {
		// em.getTransaction().commit();
		factory.close();
		// em.close();
	}

	public void save(Evento ev) {
		EntityManager em = JpaUtil.getEntityManager();

		// System.out.println(em.isOpen());
		em.getTransaction().begin();
		em.persist(ev);
		em.getTransaction().commit();

	}

	public Evento getById(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		Evento found = em.find(Evento.class, id);

		return found;
	}

	public void delete(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			Evento del = em.find(Evento.class, id);
			em.getTransaction().begin();
			em.remove(del);

		} catch (IllegalArgumentException e) {
			System.out.println("Evento già eliminato");
		} finally {
			em.getTransaction().commit();
		}
	}

	public void refresh(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		Evento ref = em.find(Evento.class, id);
		em.refresh(ref);

	}

	public void refresh(Evento ev) {
		EntityManager em = JpaUtil.getEntityManager();
		em.refresh(ev);
	}

	public void update(Evento ev) {
		EntityManager em = JpaUtil.getEntityManager();

		Evento updated = em.find(Evento.class, ev.getId());
		updated.setTitolo(ev.getTitolo());
		updated.setDataEvento(ev.getDataEvento());
		updated.setDescrizione(ev.getDescrizione());
		updated.setTipoEvento(ev.getTipoEvento());
		updated.setNumeroMassimoPartecipanti(ev.getNumeroMassimoPartecipanti());

		em.getTransaction().begin();
		em.persist(updated);
		em.getTransaction().commit();

	}

	public void update(Long id, Evento ev) {
		EntityManager em = JpaUtil.getEntityManager();

		Evento updated = em.find(Evento.class, id);
		updated.setTitolo(ev.getTitolo());
		updated.setDataEvento(ev.getDataEvento());
		updated.setDescrizione(ev.getDescrizione());
		updated.setTipoEvento(ev.getTipoEvento());
		updated.setNumeroMassimoPartecipanti(ev.getNumeroMassimoPartecipanti());
		updated.setLocation(ev.getLocation());

		em.getTransaction().begin();
		em.persist(updated);
		em.getTransaction().commit();

	}

	public List<Concerto> getConcertiInStreaming(boolean stream) {
		EntityManager em = JpaUtil.getEntityManager();

		Query query = em.createNamedQuery("concertoPerStreaming");
		query.setParameter("value", stream);
		@SuppressWarnings("unchecked")
		List<Concerto> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Concerto> getConcertiPerGenere(List<Genere> generi) {
		EntityManager em = JpaUtil.getEntityManager();

		Query query = em.createNamedQuery("concertoPerGenere");
		List<Concerto> result = new ArrayList<>();
		for (Genere gen : generi) {
			query.setParameter("values", gen);
			List<Concerto> partResult = query.getResultList();
			result.addAll(partResult);
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	public List<Concerto> getConcertiPerGenere(Genere... listaGeneri) {
		EntityManager em = JpaUtil.getEntityManager();
		String quer = "SELECT a FROM Concerto a WHERE a.genere IN (";
		for (Genere gen : listaGeneri) {
			quer = quer + "'" + gen + "',";
		}
		quer = quer.substring(0, quer.length() - 1);
		quer = quer + ")";

		Query query = em.createQuery(quer);

		List<Concerto> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PartitaDiCalcio> getPartiteVinteInCasa() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("partiteVinteInCasa");
		List<PartitaDiCalcio> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PartitaDiCalcio> getPartiteVinteInTrasferta() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("partiteVinteInTrasferta");
		List<PartitaDiCalcio> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PartitaDiCalcio> getPartitePareggiate() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("PartitePareggiate");
		List<PartitaDiCalcio> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GaraDiAtletica> getGareDiAtleticaPerVincitore(Persona vincitore) {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("gareDiAtleticaPerVincitore");
		query.setParameter("vincitore", vincitore);
		List<GaraDiAtletica> result = query.getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<GaraDiAtletica> getGareDiAtleticaPerPartecipante(Persona partecipante){
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("gareDiAtleticaPerPartecipante");
		query.setParameter("partecipante", partecipante);
		List<GaraDiAtletica> result = query.getResultList();
		return result;
	}
	//EXTRA
	@SuppressWarnings("unchecked")
	public List<GaraDiAtletica> getGareDiAtleticaPerSpettatore(Persona spettatore) {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("gareDiAtleticaPerSpettatore");
		query.setParameter("spettatore", spettatore);
		List<GaraDiAtletica> result = query.getResultList();
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public List<Evento> getEventiSoldOut() {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("eventiSoldOut");
		
		List<Evento> result = query.getResultList();
		return result;
	} // [in cui il numero di partecipanti è = numeromassimopartecipanti]

	@SuppressWarnings("unchecked")
	public List<Evento> getEventiPerInvitato(Persona invitato) {
		EntityManager em = JpaUtil.getEntityManager();
		Query query = em.createNamedQuery("eventiPerSpettatore");
		query.setParameter("invitato", invitato);
		List<Evento> result = query.getResultList();
		return result;	
		}

}