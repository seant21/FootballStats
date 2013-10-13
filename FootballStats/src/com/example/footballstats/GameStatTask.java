package com.example.footballstats;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.widget.TextView;

public class GameStatTask extends AsyncTask<String, String, String> {

	private MainActivity gui;
	private String allHTML = "";
	private String[] pbpdd = new String[200];
	private String[] pbpinfo = new String[200];
	private int[] runs = {0,0,0,0};
	private int[] passes = {0,0,0,0};
	private int[] runYards = {0,0,0,0};
	private int[] passYards = {0,0,0,0};
	
	public GameStatTask(MainActivity act)
	{
		this.gui = act;
	}

@Override
public String doInBackground(String...html)
{
			allHTML = html[0];
			int span = -1;
			String pbpinfohtml = allHTML;
			String pbpddhtml = allHTML;
			String subhtml;
			int i = 0;
			while((span = pbpddhtml.indexOf("<span class=\"pbp-dd-info\">")) != -1)
			{
				subhtml = pbpddhtml.substring(span + "<span class=\"pbp-dd-info\">".length());
				pbpdd[i] = subhtml.substring(0,subhtml.indexOf("</span>"));
				pbpdd[i] = pbpdd[i].replace("\n", "");
				i++;
				pbpddhtml = subhtml;
			}
			i = 0;
			while((span = pbpinfohtml.indexOf("<span class=\"pbp-dd-text")) != -1)
			{
				subhtml = pbpinfohtml.substring(span + "<span class=\"pbp-dd-text\">".length());
				pbpinfo[i] = subhtml.substring(0,subhtml.indexOf("</span>"));
				if (!pbpinfo[i].contains("HIGHLIGHT"))
				{
				i++;
				}
				pbpinfohtml = subhtml;
			}
			int valid = i;
			for (i = 0; i < valid; i++)
			{
				int index = 0;
				if(!pbpinfo[i].contains("PENALTY") && !pbpdd[i].contains("0-0"))
					index = Integer.parseInt(pbpdd[i].substring(pbpdd[i].indexOf("-")-1,pbpdd[i].indexOf("-")))-1;
				if(pbpinfo[i].contains("PENALTY") || pbpinfo[i].contains("punts") 
						|| pbpinfo[i].contains("HIGHLIGHT") || pbpinfo[i].contains("Timeout")
						|| pbpinfo[i].contains("field goal") || pbpinfo[i].contains("punts")
						|| pbpinfo[i].contains("FUMBLES") || pbpinfo[i].contains("spiked")
						|| pbpinfo[i].contains("kneels") || pbpinfo[i].contains("INTERCEPTED")
						|| pbpinfo[i].contains("extra point") || pbpinfo[i].contains("TWO-POINT")
						|| pbpinfo[i].contains("SAFETY") || pbpinfo[i].contains("BLOCKED"))
				{}
				else if(pbpinfo[i].contains("pass") || pbpinfo[i].contains("sacked"))
				{ 
					passes[index]++;
					if (!pbpinfo[i].contains("incomplete") && !pbpinfo[i].contains("no gain"))
					{
						passYards[index] += Integer.parseInt(pbpinfo[i].substring(pbpinfo[i].indexOf(" for ")+5,pbpinfo[i].indexOf("yard")-1));
					}
				}
				else
				{
					runs[index]++;
					if (!pbpinfo[i].contains("no gain"))
					{
						runYards[index] += Integer.parseInt(pbpinfo[i].substring(pbpinfo[i].indexOf(" for ")+5,pbpinfo[i].indexOf("yard")-1));
					}
				}		
			}
		return allHTML;
}

@Override
public void onPostExecute(String result)
{
	TextView disp = (TextView)gui.findViewById(R.id.statView);
	String stats = "1st down Runs/Yards: " + runs[0] + "/" + runYards[0] + "\n\n" +
					"1st down Passes/Yards: " + passes[0] + "/" + passYards[0] + "\n\n" +
					"2nd down Runs/Yards: " + runs[1] + "/" + runYards[1] + "\n\n" +
					"2nd down Passes/Yards: " + passes[1] + "/" + passYards[1] + "\n\n" +
					"3rd down Runs/Yards: " + runs[2] + "/" + runYards[2] + "\n\n" +
					"3rd down Passes/Yards: " + passes[2] + "/" + passYards[2] + "\n\n" +
					"4th down Runs/Yards: " + runs[3] + "/" + runYards[3] + "\n\n" +
					"4th down Passes/Yards: " + passes[3] + "/" + passYards[3] + "\n\n";
	int sum = runs[0] + passes[0];
	if (sum == 0)
		stats = "Invalid game ID";
	disp.setText(stats);
}

}
