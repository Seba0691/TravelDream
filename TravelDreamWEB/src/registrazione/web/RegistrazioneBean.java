package registrazione.web;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import registrazione.client.UtenteDTO;
import registrazione.client.UtenteMrg;

@ManagedBean(name="registrazioneBean")
@RequestScoped
public class RegistrazioneBean {
	
	private UtenteDTO user;
	
	@EJB
	private UtenteMrg userMgr;
	
	public RegistrazioneBean() {
		user = new UtenteDTO();
	}

	public void setUser(UtenteDTO user) {
		this.user = user;
	}
	

	public UtenteDTO getUser() {
		return user;
	}
	

	public void validateUsername(FacesContext context,UIComponent component,Object value) throws ValidatorException{
		if (userMgr.existEmail((String)value)){
			throw new ValidatorException(new FacesMessage("Username already used.Choose another one."));
		}
	}
	
	public String register(){
		System.out.println("bottone premuto");
		userMgr.salvaUtente(user, "cliente");
		return "home?faces-redirect=true";
	}
	
	public String aggiungiDipendente(){
		System.out.println("bottone premuto");
		userMgr.salvaUtente(user, "dipendnte");
		return "home?faces-redirect=true";
	}
	
	

}
