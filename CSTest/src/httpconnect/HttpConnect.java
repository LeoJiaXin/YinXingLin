package httpconnect;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpConnect {

	public JSONObject getInfo(String URL) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("result", "Unknown error");
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		try {
			HttpResponse hr = httpClient.execute(httpGet);
			if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				json = new JSONObject(EntityUtils.toString(hr.getEntity()));
			}
			return json;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return json;
		}
	}

	public JSONObject getInfo(String URL, JSONObject json2)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("result", "Unknown error");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);

		try {
			httpPost.setEntity(new StringEntity(json2.toString()));
			HttpResponse hr = httpClient.execute(httpPost);
			if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				json = new JSONObject(EntityUtils.toString(hr.getEntity()));
			}
			return json;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return json;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return json;
		}
	}
}
