package com.example.footballstats;

import java.util.Timer;
import java.util.TimerTask;

import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class buttonListener implements OnClickListener {

	private MainActivity gui;
	private Timer time = new Timer();
	
	public buttonListener(MainActivity act)
	{
		gui = act;
		Button b = (Button)gui.findViewById(R.id.button1);
		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View b) {
		EditText txt = (EditText)gui.findViewById(R.id.editText1);
		time.cancel();
		String gameID = txt.getText().toString();
		String url = "http://www.nfl.com/widget/gc/2011/tabs/cat-post-playbyplay?gameId=" + gameID;
		time = new Timer();
		RefreshTask rt = new RefreshTask(url, this.gui);
		time.schedule(rt, 0, 40000);
	}

}

class RefreshTask extends TimerTask {

	private MainActivity gui;
	private String url = "";
	public RefreshTask(String s, MainActivity act)
	{
		this.url = s;
		this.gui = act;
	}
    public void run() {
    	System.out.println("Refreshing");
    	PlayByPlayTask task = new PlayByPlayTask(this.gui);
    	task.execute(url);
        
    }
}