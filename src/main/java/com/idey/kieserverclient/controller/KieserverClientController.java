package com.idey.kieserverclient.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.idey.kieserverclient.domain.DeriveSeason;
import com.idey.kieserverclient.service.KieserverClientService;

@RestController
public class KieserverClientController {

	@Autowired
	KieserverClientService service;

	@GetMapping("/hello")
	public String hello() {
		return "Hello !";
	}

	@PostMapping("/deriveSeason")
	public List<DeriveSeason> evaluateSeason(@RequestBody DeriveSeason deriveSeason)
			throws ParseException, JSONException {

		List deriveSeasonList = null;
		System.out.println(deriveSeason.getSeason());
		if (deriveSeason.getSeason() != null && deriveSeason.getMonth() != null
				&& deriveSeason.getHarvestYear() != null) {
			deriveSeason.setIsValid(true);
		}
		try {
			deriveSeasonList = service.evaluateSeason(deriveSeason);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deriveSeasonList;
	}
	
	@PostMapping("/deriveSeasonList")
	public List<DeriveSeason> evaluateSeasonList(@RequestBody List<DeriveSeason> deriveSeasonList)
			throws ParseException, JSONException {

		List evaluatedDeriveSeasonList = null;
		
		try {
			evaluatedDeriveSeasonList = service.evaluateSeasonList(deriveSeasonList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return evaluatedDeriveSeasonList;
	}

}