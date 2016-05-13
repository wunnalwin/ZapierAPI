package com.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.beans.SimpleBeans;

@RestController
// @RequestMapping("/simpleWS")
public class SimpleController {
	private static String token = "5cc86941c278c78bfc7cf44936393c42";

	@RequestMapping(value = "/countries", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<SimpleBeans> getCountries() {
		List<SimpleBeans> listOfCountries = new ArrayList<SimpleBeans>();
		listOfCountries = createCountryList();
		return listOfCountries;
	}

	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public SimpleBeans getCountryById(@PathVariable int id) {
		List<SimpleBeans> beanlist = new ArrayList<SimpleBeans>();
		beanlist = createCountryList();

		for (SimpleBeans country : beanlist) {
			if (country.getId() == id)
				return country;
		}

		return null;
	}

	@RequestMapping(value = "/countries/{diffbotjson}", method = RequestMethod.GET)
	public String getArticle(@PathVariable String diffbotjson) {
		// JsonObject jsonObject = new JsonObject();
		System.out.println(diffbotjson);
		return diffbotjson;
	}

	@RequestMapping(value = "/api/v1", method = RequestMethod.GET)
	@ResponseBody
	public String getRawDatafromZapeir() throws IOException {

		String diffbotUrl = "http://api.diffbot.com/v3/crawl/data?token=" + token + "&name=testWunna&format=json";
		JsonNode json = readJsonFromUrl(diffbotUrl);

		System.out.println(json.toString());
		return json.toString();
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JsonNode readJsonFromUrl(String url) throws IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(jsonText);
			return jsonNode;
		} finally {
			is.close();
		}
	}

	// Utiliy method to create country list.
	public List<SimpleBeans> createCountryList() {
		SimpleBeans indiaCountry = new SimpleBeans(1, "India");
		SimpleBeans chinaCountry = new SimpleBeans(4, "China");
		SimpleBeans nepalCountry = new SimpleBeans(3, "Nepal");
		SimpleBeans bhutanCountry = new SimpleBeans(2, "Bhutan");

		List<SimpleBeans> listOfCountries = new ArrayList<SimpleBeans>();
		listOfCountries.add(indiaCountry);
		listOfCountries.add(chinaCountry);
		listOfCountries.add(nepalCountry);
		listOfCountries.add(bhutanCountry);
		return listOfCountries;
	}

}
