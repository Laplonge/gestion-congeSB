package fr.formation.inti.services.interfaces;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.formation.inti.entities.Conge;

public interface ICongeService extends IGenericService<Conge>{

    List<Conge> getCongeStartDate(Date date);
    List<Conge> getUnavailableDate(Date date);
    List<Conge> getHistoriqueByIdEmploye(Integer id);
    List<Conge> getPropositionByIdEmploye(Integer id);

    public Integer calculDureeVacances(Conge conge);
    public Integer calculDureeVacances(Date debut, Date fin);
    public String validationDeLaPeriode(Conge conge);
    
    public void TestDeLaValiditeDeLaRequete(String debut, String fin, HttpServletRequest request) throws ParseException;
    public void AlgoDePropoEmployee(Date debut, Date fin, HttpServletRequest request) throws ParseException;
  
    public void AlgoDePropoBoss(Conge conge) throws ParseException;
    public String TestDeConflitDemandeEnCours();
    public void ValidationDeLaDemandeEmployee(Conge conge);
    
    public void ValidationDeLaDemandeBoss(Conge conge);
    public List<Conge> getAllAcceptee();
    public void TestDeLaValiditeDeLaRequete(Conge conge) throws ParseException;
     
}