package com.idey.kieserverclient;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.idey.kieserverclient.controller.KieserverClientController;
import com.idey.kieserverclient.domain.DeriveSeason;
import com.idey.kieserverclient.service.KieserverClientService;



@RunWith(SpringRunner.class)
//@SpringBootTest
public class KieserverClientApplicationTests {

	@Test
	public void contextLoads() throws IOException, ParseException, java.text.ParseException, JSONException {
		
		KieserverClientService service = new KieserverClientService();
		DeriveSeason ds = new DeriveSeason();
		ds.setCountry("India");
		ds.setMonth("January");
		ds.setHarvestYear(2018);
		service.evaluateSeason(ds);
	}

}
