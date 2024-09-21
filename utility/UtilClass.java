package utility;

import java.io.IOException;
//import java.net.http.HttpResponse;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

public class UtilClass {

	public static List<String> getRandomElements(List<String> list, int n) {

		Random random = new Random();
		n = Math.min(n, list.size());

		return random.ints(0, list.size()).distinct().limit(n).mapToObj(list::get).collect(Collectors.toList());
	}

	public static Boolean checkBrokenLinks(List<String> links) {

		List<String> brokenLinks = links.stream().filter(href -> href != null && !href.isEmpty())
				.filter(href -> isBrokenLink(href)).collect(Collectors.toList());

		return brokenLinks.size() == 0 ? true : false;

	}

	public static boolean isBrokenLink(String url) {

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			return statusCode < 200 || statusCode >= 300;
		} catch (IOException e) {
			return true;
		}
	}

	public static Boolean emptyLinkCheck(List<String> list) {

		boolean status = true;
		long size = list.stream().filter(el -> el.isBlank() || el.isEmpty()).count();

		if (size != 0) {
			status = false;
		}
		;

		return status;
	}

	public static String normalizeTxt(String input) {

		return input == null ? null
				: Normalizer.normalize(input, Normalizer.Form.NFKD).replaceAll("\\p{M}", "").toLowerCase();
	}

	public static int pricetoInt(String price) {

		String price1 = price.replaceAll("[^0-9.]", "").replace(".", "");
		return Integer.valueOf(price1.substring(0, price1.length() - 2));

	}

	public static boolean isSortedReverse(List<Integer> list) {
		if (list == null || list.size() < 2) {
			return true;
		}
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i) < list.get(i + 1)) {
				return false;
			}
		}

		return true;
	}

	public static List<String> removeBlank(List<String> list) {

		return list.stream().filter(el -> !el.isBlank() || !el.isEmpty()).collect(Collectors.toList());

	}

}
