package com.yinxinglin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yinxinglin.activity.PopupPicture;
import com.yinxinglin.object.Dish;

import android.graphics.BitmapFactory;

public class DishesUtils {
	private static DoSomeThing justdoit = null;
	private static Boolean result = false;
	private static String temps;
	// 用户传入已获取菜品数量numOfDishes、学校school
	// 还有一个实现了的DoSomeThing
	// 并且将菜品对象保存在dishes里面等待前台操作
	public static void getDishes(int numOfDishes, String school,
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
					Dish dish = new Dish();
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
					}
					HttpUtils.handler.post(new PostDish(dish));
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

	//根据学校名字获得所有食堂的名字
	public static void getCanteenName(String school,DoSomeThing doit){

		DishesUtils.justdoit = doit;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("school", school));
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(HttpUtils.URL+ "loadcanteens?"
				+ URLEncodedUtils.format(params, HTTP.UTF_8));
		httpGet.addHeader("Content-Type", "text/html");
		httpGet.addHeader("charset", HTTP.UTF_8);
		try {
			HttpResponse hr=httpClient.execute(httpGet);
			if(hr.getStatusLine().getStatusCode()==200){
				System.out.println();
				JSONArray array=new JSONArray(EntityUtils.toString(hr
				.getEntity()));
				for(int i=0;i<array.length();i++) {
					temps = array.get(i).toString();
					HttpUtils.handler.post(new PostCanteen(temps));
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static class PostDish implements Runnable {

		private Dish dish=null;
		
		public PostDish(Dish dish) {
			this.dish = dish;
		}
		
		@Override
		public void run() {
			justdoit.doit(true, "未知错误", dish);
			
		}
		
	}
	
	private static class PostCanteen implements Runnable {
		private String canteen=null;
		
		public PostCanteen(String canteen) {
			this.canteen = canteen;
		}
		
		@Override
		public void run() {
			justdoit.doit(true, canteen, null);
			
		}
	}
	
	// 前台传入一个dish对象，还有将实现的DoSomeThing传入
		// 将传入的dish对象打包为一个JSONObject传到服务器
		// 服务器传回一个JSONObject
		// 取出JSONObject中的info值
		public static void signIn(Dish dish, DoSomeThing doit) {
			String URL = HttpUtils.URL + "upload";
			DishesUtils.justdoit = doit;
			JSONObject json = new JSONObject();

			try {
				json.put("name", dish.getName());
				json.put("userName", dish.getUserName());
				json.put("school", dish.getSchool());
				json.put("price", dish.getPrice());
				json.put("canteen", dish.getCanteen());
				json.put("type", dish.getType());
				HttpURLConnection conn = (HttpURLConnection) (new URL(URL)
						.openConnection());
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
//				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				OutputStream outStream = conn.getOutputStream();
				byte js[] = json.toString().getBytes();
				int i = js.length;
				int i1 = 0, i2 = 0;
				if (i > 250) {
					i1 = 250;
					i2 = i - 250;
				} else {
					i1 = i;
				}
				outStream.write(i1);
				outStream.write(i2);
				outStream.write(js);
				File file = new File(PopupPicture.TEMP_PATH+PopupPicture.TEMP_FILENAME);
				FileInputStream input = new FileInputStream(file);
				byte buffer[] = new byte[1024];
				while (input.read(buffer) != -1) {
					outStream.write(buffer);

				}
				outStream.flush();
				if(conn.getResponseCode()!=-1){
					InputStream is = conn.getInputStream();
					DishesUtils.result = is.read() == 1?true:false;
				}
				
				HttpUtils.handler.post(new Runnable() {

					@Override
					public void run() {
						DishesUtils.justdoit.doit(DishesUtils.result, "未知错误", null);

					}

				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

//	public static void upload(String s) {
//		System.out.println("ssssss");
//		File f = new File(s);
//		try {
//			System.out.println("sss");
//			FileInputStream fo = new FileInputStream(f);
//			JSONObject js = new JSONObject();
//			js.put("name", "刘嘉鑫");
//			js.put("school", "电子科技大学");
//			String ss = js.toString();
//			byte[] bits = ss.getBytes();
//			byte[] buffer = new byte[1024];
//			int i1=bits.length,i2=0;
//			if(i1>250) {
//				i2=i1-250;
//				i1=250;
//			}
//			System.out.println(i1+""+i2);
//			URL url = new URL(HttpUtils.URL+"upload");
//			HttpURLConnection httpConnect = (HttpURLConnection)url.openConnection();
//			httpConnect.setDoOutput(true);
//			httpConnect.setRequestMethod("GET");
//			httpConnect.setUseCaches(false);
//			
//			OutputStream os = httpConnect.getOutputStream();
//			os.write(i1);
//			os.write(i2);
//			os.write(bits);
//			while(fo.read(buffer)!=-1) {
//				os.write(buffer);
//			}
//			os.flush();
//			os.close();
//			fo.close();
//			int res = httpConnect.getResponseCode();
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void delete(Dish dish, DoSomeThing doit) {
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

	public static void update(Dish dish, DoSomeThing doit) {
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
