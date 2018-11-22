package com.idey.kieserverclient.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.idey.kieserverclient.domain.DeriveSeason;

@Service
public class KieserverClientService {
	
	
	
	public List<DeriveSeason> evaluateSeasonList(List<DeriveSeason> deriveSeasonList)
			throws IOException, ParseException, JSONException {

		List<DeriveSeason> evaluatedDeriveSeasonList = new ArrayList();
		
		for ( DeriveSeason deriveSeason : deriveSeasonList) {
			
			//create request string
			String jsonRequestString = createJsonRequestString(deriveSeason);
			
			//send to kieserver
			//receive response
			String response =  seasonRuleEvaluation(jsonRequestString);
			
			//extract season from response
			//create a list of deriveSessionList to send back to clients
	        DeriveSeason evaluatedSeason = extractSeasonFromRuleResponse(response);
	        evaluatedDeriveSeasonList.add(evaluatedSeason);
		}
		return evaluatedDeriveSeasonList;
	}
	
	private String createJsonRequestString(DeriveSeason deriveSeason) {
		
		 
		String jsonRequestString  = "{\r\n" + "   \"lookup\":\"defaultStatelessKieSession\",\r\n"
				+ "   \"commands\":[\r\n" + "      {\r\n" + "         \"insert\":{\r\n" + "            \"object\":{\r\n"
				+ "               \"com.syngenta.dda.facts.DeriveSeason\":{\r\n"
				+ "                  \"country\":\"" + deriveSeason.getCountry() + "\",\r\n" + "                  \"month\":\"" + deriveSeason.getMonth()+ "\",\r\n"
				+ "                  \"harvestYear\":" + deriveSeason.getHarvestYear() + "\r\n" + "               }\r\n" + "            }\r\n"
				+ "         }\r\n" + "      },     \r\n" + "      {\r\n" + "         \"fire-all-rules\":{\r\n"
				+ "         	 \"max\" : -1,\r\n" + "         \"out-identifier\": \"output\"}\r\n" + "      },\r\n"
				+ "      {\r\n" + "       \"get-objects\" : {\r\n" + "       \"out-identifier\" : \"output\" }\r\n"
				+ "     }\r\n" + "    \r\n" + "   ]\r\n" + "}";
		
		
		return jsonRequestString;
	}
	
	private String seasonRuleEvaluation(String jsonRequestString) throws IOException {
		
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("adminUser", "syngenta1!".toCharArray());
			}
		});
		
		URL obj = new URL("kieserver url here";
				
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(jsonRequestString.getBytes());
		os.flush();
		os.close();
		int responseCode = postConnection.getResponseCode();
		
		StringBuffer response = new StringBuffer();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
		}
		
		
		return response.toString();
	
	}
	
	private DeriveSeason extractSeasonFromRuleResponse(String response) throws JSONException {
		
		//System.out.println("'" + response.toString() + "'");
		JSONObject jsonObj1 = new JSONObject(response.toString());
		System.out.println(jsonObj1.get("result"));

		JSONObject jsonObj2 = new JSONObject(jsonObj1.get("result").toString());
		System.out.println(jsonObj2.get("execution-results"));

		JSONObject jsonObj3 = new JSONObject(jsonObj2.get("execution-results").toString());
		System.out.println(jsonObj3.get("results"));

		JSONArray jArray = new JSONArray(jsonObj3.get("results").toString());
		System.out.println("array length" + jArray.length());
		DeriveSeason ds = new DeriveSeason();
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject jb = new JSONObject(jArray.getJSONObject(i).toString());
			
			JSONArray jArray1 = new JSONArray(jb.get("value").toString());
			
			for (int j = 0; j < jArray.length(); j++) {
				JSONObject deriveSeasonNode = new JSONObject(jArray1.getJSONObject(j).toString());

				JSONObject deriveSeasonChildNode = new JSONObject(
				deriveSeasonNode.get("com.idey.DeriveSeason").toString());

				
				ds.setCountry(deriveSeasonChildNode.get("country").toString());
				ds.setMonth(deriveSeasonChildNode.get("month").toString());
				ds.setHarvestYear(new Integer(deriveSeasonChildNode.get("harvestYear").toString()));
				// ds.setIsValidderiveSeasonJSon.get("country").toString()););
				ds.setSeason(deriveSeasonChildNode.get("season").toString());

				
			}
		}
		return ds;
	}
	
}
