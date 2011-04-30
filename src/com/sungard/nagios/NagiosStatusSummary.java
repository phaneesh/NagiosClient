package com.sungard.nagios;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sungard.nagios.rest.client.RestClient;

public class NagiosStatusSummary extends NagiosClientActivity {

	private final static String STATUS_APPLICATION_NAME = "nagios-json";
	
	private final static String STATUS_SUMMARY_REQUEST_ENDPOINT = "nagiosStatusSummary/getSummary";
	
	ProgressDialog dialog;
	
	static final int PROGRESS_DIALOG = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        readPreferences();
        Button btnBack =(Button) findViewById(R.id.btnSummaryBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
       	 public void onClick(View v) {
       		 startActivity(new Intent(NagiosStatusSummary.this, NagiosClientHome.class));
       	 }
        });

        Button btnRefresh =(Button) findViewById(R.id.btnSummaryRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
       	 public void onClick(View v) {
       		getDataFromServer();
       	 }
        });
        
        getDataFromServer();
    }
    
    private void getDataFromServer() {
    	dialog = ProgressDialog.show(NagiosStatusSummary.this, "", "Loading. Please wait...", true);
        getStatusSummary();
        dialog.dismiss();
    }
    
    private void getStatusSummary() {
    	String requestUrl = "http://" +getStatusSever() +"/" +STATUS_APPLICATION_NAME + "/" +STATUS_SUMMARY_REQUEST_ENDPOINT;
    	RestClient rs = new RestClient(requestUrl);
    	try {
    		rs.addParam("n_host", getNagiosHost());
    		rs.executeRequest(RestClient.GET);
    		JSONObject response = rs.getResponseJSON();
    		processSummaryResponse(response);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    		dialog.dismiss();
    		Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    }
    
    private void processSummaryResponse(JSONObject response) throws JSONException {
    	JSONObject result = response.getJSONObject("result");
    	String responseCode = result.getString("status");
    	if(!responseCode.matches("200")) {
    		Toast.makeText(getBaseContext(), result.getString("data"), Toast.LENGTH_SHORT).show();
    		return;
    	}
    	JSONObject data = result.getJSONObject("data");
    	JSONObject hosts = data.getJSONObject("hosts");
    	JSONObject services = data.getJSONObject("service");
    	
    	//Set the values to the screen to the hosts section
    	((TextView)findViewById(R.id.totalHostsUp)).setText(hosts.getString("total_hosts_up"));
    	((TextView)findViewById(R.id.totalHostsProblems)).setText(hosts.getString("total_host_problems"));
    	((TextView)findViewById(R.id.totalHostsDown)).setText(hosts.getString("total_hosts_down"));
    	
    	//Set the values to the screen to the services section
    	((TextView)findViewById(R.id.servicesOk)).setText(services.getString("service_ok"));
    	((TextView)findViewById(R.id.servicesWarnings)).setText(services.getString("service_warning"));
    	((TextView)findViewById(R.id.servicesUnknown)).setText(services.getString("service_unknown"));
    	((TextView)findViewById(R.id.servicesPending)).setText(services.getString("service_pending"));
    	((TextView)findViewById(R.id.servicesCritical)).setText(services.getString("service_critical"));
    	((TextView)findViewById(R.id.servicesProblems)).setText(services.getString("service_problems"));
    }
}
