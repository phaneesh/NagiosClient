package com.sungard.nagios;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class NagiosClientActivity extends Activity {

	public static final String NAGIOS_CLIENT_PREFS = "NagiosClientPrefs";
	
	private SharedPreferences preferences;
	
	private String statusServer;
	
	private String nagiosHost;
	
	public void readPreferences() {
		preferences = getSharedPreferences(NAGIOS_CLIENT_PREFS, MODE_PRIVATE);
		statusServer = preferences.getString("StatusServer", "None");
		nagiosHost = preferences.getString("NagiosHost", "None");
	}
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


	/**
	 * @return the statusSever
	 */
	public String getStatusSever() {
		return statusServer;
	}


	/**
	 * @param statusSever the statusSever to set
	 */
	public void setStatusSever(String statusSever) {
		this.statusServer = statusSever;
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("StatusServer", statusServer);
		editor.commit();
	}


	/**
	 * @return the nagiosHost
	 */
	public String getNagiosHost() {
		return nagiosHost;
	}


	/**
	 * @param nagiosHost the nagiosHost to set
	 */
	public void setNagiosHost(String nagiosHost) {
		this.nagiosHost = nagiosHost;
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("NagiosHost", nagiosHost);
		editor.commit();
	}
 
}
