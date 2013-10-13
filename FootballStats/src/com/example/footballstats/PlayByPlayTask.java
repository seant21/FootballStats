package com.example.footballstats;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.widget.TextView;

public class PlayByPlayTask extends AsyncTask<String, String, String> {
	
	private MainActivity gui;
	private String allHTML;
	
	public PlayByPlayTask(MainActivity theact)
	{
		this.gui = theact;
	}
	
	@Override
	public String doInBackground(String... url)
	{

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url[0]);
		try {
			HttpResponse response = client.execute(get);
			allHTML = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Error");
			return "error";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Error");
			return "error";
		}

		return allHTML;
	}
	
	@Override
	public void onPostExecute(String result)
	{
		GameStatTask process = new GameStatTask(this.gui);
		process.execute(this.allHTML);
	}
}
