package com.example.Atelier01.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Atelier01.entities.Voiture;
import com.example.Atelier01.service.VoitureService;

@Controller
public class VoitureController {
	@Autowired
	VoitureService voitureService;
	@RequestMapping("/showCreate")
	public String showCreate()
	{
	return "createVoiture";
	}
	@RequestMapping("/saveVoiture")
	public String saveVoiture(@ModelAttribute("voiture") Voiture voiture,
	 @RequestParam("date") String date,
	 ModelMap modelMap) throws
	ParseException
	{
	//conversion de la date
	 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	 Date dateCreation = dateformat.parse(String.valueOf(date));
	 voiture.setDateCreation(dateCreation);
    
	 Voiture saveVoiture = voitureService.saveVoiture(voiture);
	String msg ="voiture enregistré avec Id "+saveVoiture.getIdvoiture();
	modelMap.addAttribute("msg", msg);
	return "createVoiture";
	}
	@RequestMapping("/ListeVoitures")
	public String listeVoitures(ModelMap modelMap,
			@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "2") int size)
	{
		Page<Voiture> voits = voitureService.getAllVoituresParPage(page, size);
		modelMap.addAttribute("voitures", voits);
		 modelMap.addAttribute("pages", new int[voits.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeVoitures";
	}
	
	@RequestMapping("/supprimerVoiture")
	public String supprimerVoiture(@RequestParam("id") Long id,
	ModelMap modelMap,
	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "2") int size)
	{
	voitureService.deleteVoitureById(id);
	Page<Voiture> voits = voitureService.getAllVoituresParPage(page,
	size);
	modelMap.addAttribute("voitures", voits);
	modelMap.addAttribute("pages", new int[voits.getTotalPages()]);
	modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("size", size);
	return "listeVoitures";
	}


	
	@RequestMapping("/modifierVoiture")
	public String editerVoiture(@RequestParam("id") Long id,ModelMap modelMap)
	{
		Voiture v= voitureService.getVoiture(id);
	modelMap.addAttribute("voiture", v);
	return "editerVoiture";
	}
	@RequestMapping("/updateVoiture")
	public String updateVoiture(@ModelAttribute("voiture") Voiture voiture,
	@RequestParam("date") String date,ModelMap modelMap) throws ParseException 
	{
		//conversion de la date
		 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		 Date dateCreation = dateformat.parse(String.valueOf(date));
		 voiture.setDateCreation(dateCreation);

		 voitureService.updateVoiture(voiture);
		 List<Voiture> voits = voitureService.getAllVoitures();
		 modelMap.addAttribute("voitures", voits);
		return "listeVoitures";
		}
	
	

	
	

}
