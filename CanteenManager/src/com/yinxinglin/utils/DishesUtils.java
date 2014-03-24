package com.yinxinglin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yinxinglin.object.Dishes;

import android.graphics.BitmapFactory;

public class DishesUtils {
	private static DoSomeThing justdoit = null;
	private static Boolean result = false;

	// 用户传入已获取菜品数量numOfDishes、学校school
	// 还有一个实现了的DoSomeThing
	// 通过已获取菜品数量numOfDishes和学校school来获取6个菜品
	// 并且将菜品对象保存在dishes里面等待前台操作
	public static void getDishes(int numOfDishes,ArrayList<Dishes> dishes, String school,
			DoSomeThing dosomething) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("order", Integer
				.toString(numOfDishes)));
		params.add(new BasicNameValuePair("school", school));
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(HttpUtils.URL+ "loadmenu?"
				+ URLEncodedUtils.format(params, HTTP.UTF_8));
		DishesUtils.justdoit = dosomething;
		try {
			HttpResponse hr = httpClient.execute(httpGet);
			if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				JSONArray array = new JSONArray(EntityUtils.toString(hr
						.getEntity()));
				for (int i = 0; i < array.length(); i++) {
					HttpClient hc = new DefaultHttpClient();
					JSONObject json2 = array.getJSONObject(i);
					Dishes dish = new Dishes();
					HttpGet get = new HttpGet(HttpUtils.URL + "img?" + "file="
							+ json2.getString("photo"));
					HttpResponse httpResponse = hc.execute(get);
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity he = httpResponse.getEntity();
						InputStream is = he.getContent();
						dish.setPhoto(BitmapFactory.decodeStream(is));
						dish.setName(json2.getString("name"));
						dish.setCanteen(json2.getString("canteen"));
						dish.setPrice((float) json2.getDouble("price"));
						dish.setBad(json2.getInt("bad"));
						dish.setId(json2.getString("id"));
						dish.setGood(json2.getInt("good"));
						dish.setType(json2.getString("type"));
						dish.setCanteen(json2.getString("canteen"));
						dishes.add(dish);
					}
					HttpUtils.handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							justdoit.doit(true, "未知错误", null);
						}

					});
				}
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 前台传入一个dish对象，还有将实现的DoSomeThing传入
	// 将传入的dish对象打包为一个JSONObject传到服务器
	// 服务器传回一个JSONObject
	// 取出JSONObject中的info值
	public static void signIn(Dishes dish, DoSomeThing doit) {
		DishesUtils.justdoit = doit;
		JSONObject json = new JSONObject();
		try {
			json.put("name", dish.getName());
			json.put("username", dish.getUserName());
			json.put("school", dish.getSchool());
			json.put("price", dish.getPrice());
			json.put("canteen", dish.getCanteen());
			json.put("type", dish.getType());
			JSONObject obj = HttpUtils.getInfo(HttpUtils.URL + "", json);
			DishesUtils.result = obj.getBoolean("info");
			HttpUtils.handler.post(new Runnable() {

				@Override
				public void run() {
					DishesUtils.justdoit.doit(DishesUtils.result, "未知错误", null);

				}

			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void delete(Dishes dish, DoSomeThing doit) {
		DishesUtils.justdoit = doit;
		JSONObject json = new JSONObject();
		try {
			json.put("id", dish.getId());

			HttpPost post = new HttpPost(HttpUtils.URL + "");
			post.setEntity(new StringEntity(json.toString()));
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse hr = httpClient.execute(post);
			if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				JSONObject json2 = new JSONObject(hr.getEntity().toString());
				DishesUtils.result = json2.getBoolean("info");

				HttpUtils.handler.post(new Runnable() {

					@Override
					public void run() {
						DishesUtils.justdoit.doit(DishesUtils.result, "未知错误", null);

					}

				});
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void update(Dishes dish, DoSomeThing doit) {
		DishesUtils.justdoit = doit;
		JSONObject json = new JSONObject();
		try {
			json.put("id", dish.getId());
			json.put("name", dish.getName());
			json.put("username", dish.getUserName());
			json.put("school", dish.getSchool());
			json.put("price", dish.getPrice());
			json.put("canteen", dish.getCanteen());
			json.put("type", dish.getType());

			HttpPost post = new HttpPost(HttpUtils.URL+ "");
			HttpClient hc = new DefaultHttpClient();
			HttpResponse hr = hc.execute(post);
			if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				JSONObject json2 = new JSONObject(hr.getEntity().toString());
				DishesUtils.result = json2.getBoolean("info");
				HttpUtils.handler.post(new Runnable() {

					@Override
					public void run() {
						DishesUtils.justdoit.doit(DishesUtils.result, "未知错误", null);

					}

				});
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
