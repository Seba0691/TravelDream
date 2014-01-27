package store.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import traveldream.dtos.ListaDesideriDTO;
import traveldream.dtos.PacchettoDTO;
import traveldream.dtos.UtenteDTO;
import traveldream.dtos.VoloDTO;
import traveldream.manager.ListaDesideriMng;
import traveldream.manager.UtenteMrg;

@ManagedBean(name = "listaDesideriBean")
@SessionScoped
public class ListaDesisderiBean implements Serializable{
	
	
	private static final long serialVersionUID = -4029975305697609232L;
	
	@EJB
	private UtenteMrg userMgr;
	
	@EJB
	private ListaDesideriMng listaDesideriMng;
	
	private List<ListaDesideriDTO> listaDesideriUtente;
	
	private String utenteProprietario = "";
	
	private String nomePagante = "";
	
	private ListaDesideriDTO listaDaPagare;
	
	private List<VoloDTO> listaVoliAndata;
	
	private List<VoloDTO> listaVoliRitorno;
	
	private boolean listePiene = false;
	
	public ListaDesisderiBean(){
		
		this.listaDesideriUtente = new ArrayList<ListaDesideriDTO>();
		this.listaVoliAndata = new ArrayList<VoloDTO>();
		this.listaVoliRitorno = new ArrayList<VoloDTO>();
		
	}
	
	public void addAListaDesideri(PacchettoDTO pacchetto){
		
		ListaDesideriDTO lista = new ListaDesideriDTO();
		lista.setUtente(this.userMgr.getUserDTO());
		lista.setPacchetto(pacchetto);
		this.listaDesideriMng.addAListaDesideri(lista);
		System.out.println(pacchetto.getNome());
		
	}
	
	
	public void getListaDesideriUtenteProprietario(){
		this.listaDesideriUtente.clear();
		UtenteDTO utente = new UtenteDTO();
		utente.setEmail(this.utenteProprietario);
		List<ListaDesideriDTO> listaDaScremare = new ArrayList<ListaDesideriDTO>(); 
		listaDaScremare = this.listaDesideriMng.getListaDesisderiUtente(utente);
		
		for (ListaDesideriDTO listaDesideriDTO : listaDaScremare) {
			if (listaDesideriDTO.getPagatoDa() == null ){
				this.listaDesideriUtente.add(listaDesideriDTO);
			}
		}
		
		for (Iterator<ListaDesideriDTO> s = this.listaDesideriUtente.iterator(); s.hasNext(); ) {
			
			ListaDesideriDTO listaDaControllare = s.next();
			if (listaDaControllare.getPacchetto().getOk().equals("X") || listaDaControllare.getPacchetto().getEliminato() == 1) {
				s.remove();
			}
		}
		
		this.listaDesideriUtente.size();
		
	}

	

	public List<ListaDesideriDTO> getListaDesideriUtente() {
		
			this.getListaDesideriUtenteProprietario();
		
		return listaDesideriUtente;
	}

	public void setListaDesideriUtente(List<ListaDesideriDTO> listaDesideriUtente) {
		this.listaDesideriUtente = listaDesideriUtente;
	}

	public String getUtenteProprietario() {
		return utenteProprietario;
	}

	public void setUtenteProprietario(String utenteProprietario) {
		this.utenteProprietario = utenteProprietario;
	}
	
	public void pagaPacchetto(){
		RequestContext.getCurrentInstance().execute("ciaoDialog.hide()");
		System.out.println("pagato");
		System.out.println(nomePagante);
		//this.listaDesideriMng.pagaPacchttoInListaDesideri(lista, "bruno");
		
	}
	
	public void goToScegliVoli(ListaDesideriDTO lista){
		System.out.println("premuto");
		this.listePiene = false;
		this.listaDaPagare = lista;		
		this.listaVoliAndata.clear();
		this.listaVoliRitorno.clear();
		if (!this.listaVoliAndata.isEmpty() && !this.listaVoliRitorno.isEmpty()) {
			this.listePiene = true;
		}
		System.out.println(listePiene);
	}

	public String getNomePagante() {
		return nomePagante;
	}

	public void setNomePagante(String nomePagante) {
		this.nomePagante = nomePagante;
	}

	public ListaDesideriDTO getListaDaPagare() {
		return listaDaPagare;
	}

	public void setListaDaPagare(ListaDesideriDTO listaDaPagare) {
		this.listaDaPagare = listaDaPagare;
	}

	public List<VoloDTO> getListaVoliAndata() {
		return listaVoliAndata;
	}

	public void setListaVoliAndata(List<VoloDTO> listaVoliAndata) {
		this.listaVoliAndata = listaVoliAndata;
	}

	public List<VoloDTO> getListaVoliRitorno() {
		return listaVoliRitorno;
	}

	public void setListaVoliRitorno(List<VoloDTO> listaVoliRitorno) {
		this.listaVoliRitorno = listaVoliRitorno;
	}

	public boolean isListePiene() {
		return listePiene;
	}

	public void setListePiene(boolean listePiene) {
		this.listePiene = listePiene;
	}

}
