package wrappers.coinlore;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import com.google.gson.*;

import wrappers.Wrapper;

import java.util.HashMap;
import java.util.Iterator;

import javax.naming.NameNotFoundException;

public class CoinLoreWrapper extends Wrapper {

	public static HashMap<String, Integer> nameID = new HashMap<>();

	// caching some coins data
	{
		nameID.put("bitcoin", 90);
		nameID.put("ethereum", 80);
		nameID.put("binance-coin", 2710);
		nameID.put("binance coin", 2710);
		nameID.put("ripple", 58);
		nameID.put("dogecoin", 2);
		nameID.put("cardano", 257);
		nameID.put("polkadot", 45219);
		nameID.put("litecoin", 1);
		nameID.put("bitcoin-cash", 2321);
		nameID.put("bitcoin cash", 2321);
		nameID.put("chainlink", 2751);
		nameID.put("vechain", 2741);
		nameID.put("stellar", 89);
		nameID.put("uniswap", 47305);
	}

	public CoinLoreWrapper() {

	}

	public String getTop10Coins() {
		return getTopCoins(0, 10);
	}

	public String getTopCoins(int start, int limit) {
		final String uri = String.format("https://api.coinlore.net/api/tickers/?start=%d&limit=%d", start, limit);
		JsonElement elem = makeAPICall(uri);

		String resp = elem.toString();

		return resp;
	}

	public int getCoinIDbyName(String coinName) throws NameNotFoundException {
		final String uri = "https://api.coinlore.net/api/tickers/";
		int coinID = -1;
		coinName = coinName.toLowerCase();

		if (nameID.containsKey(coinName)) {
			coinID = nameID.get(coinName);
		} else {
			JsonElement elem = makeAPICall(uri);
			JsonArray data = elem.getAsJsonObject().get("data").getAsJsonArray();
			Iterator<JsonElement> it = data.iterator();
			while (it.hasNext()) {
				JsonObject obj = (JsonObject) it.next();
				String symbol = obj.get("symbol").getAsString().toLowerCase();
				String name = obj.get("name").getAsString().toLowerCase();
				String nameid = obj.get("nameid").getAsString().toLowerCase();
				if (name.contains(coinName) || nameid.contains(coinName) || symbol.contains(coinName)) {
					int id = obj.get("id").getAsInt();
					coinID = id;
					nameID.put(name, id);
					nameID.put(nameid, id);
					nameID.put(symbol, id);
					break;
				}

			}

		}

		if (coinID == -1) {
			throw new NameNotFoundException("can't find the name in the list of actual ctrypto-currencies");
		}

		return coinID;
	}

	public Coin getCoinByName(String coinName) throws NameNotFoundException {
		return getCoinByID(getCoinIDbyName(coinName));
	}

	public Coin getCoinByID(final int coinID) {
		final String uri = String.format("https://api.coinlore.net/api/ticker/?id=%d", coinID);
		Coin coin = null;

		try {

			JsonObject obj = (JsonObject) makeAPICall(uri).getAsJsonArray().get(0);
			coin = new Coin(obj);

		} catch (java.lang.IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return coin;
	}

	public String getCoinPriceByID(int coinID) {
		final String uri = String.format("https://api.coinlore.net/api/ticker/?id=%d", coinID);
		final String priceKey = "price_usd";

		String resp = "";
		String price = "";

		try {
			URIBuilder query = new URIBuilder(uri);

			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet(query.build());

			request.setHeader(HttpHeaders.ACCEPT, "application/json");

			CloseableHttpResponse response = client.execute(request);

			System.out.printf("status code: %d\n", response.getCode());

			HttpEntity entity = response.getEntity();

			resp = EntityUtils.toString(entity);
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(resp);

			price = je.getAsJsonArray().get(0).getAsJsonObject().get(priceKey).getAsString();

			response.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return price;
	}

	public JsonElement getGlobal() {
		String uri = "https://api.coinlore.net/api/global/";
		String response_content = "";
		JsonElement elem = makeAPICall(uri);

		return elem;

	}

	@Override
	public JsonElement makeAPICall(final String uri) {
		String resp = "";
		JsonElement elem = null;
		try {
			URIBuilder query = new URIBuilder(uri);

			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet(query.build());

			request.setHeader(HttpHeaders.ACCEPT, "application/json");

			CloseableHttpResponse response = client.execute(request);

			System.out.printf("status code: %d\n", response.getCode());

			HttpEntity entity = response.getEntity();

			resp = EntityUtils.toString(entity);
			JsonParser jp = new JsonParser();
			elem = jp.parse(resp);

			response.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return elem;
	}

}