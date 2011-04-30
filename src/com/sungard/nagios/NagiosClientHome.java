package com.sungard.nagios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NagiosClientHome extends NagiosClientActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Load preferences
        readPreferences();
        
        //Set the stored preference from shared preference store
        EditText txtStatusServer = (EditText)findViewById(R.id.statusServer);
        txtStatusServer.setText(getStatusSever());
        
        EditText txtNagiosServer = (EditText)findViewById(R.id.nagiosServer);
        txtNagiosServer.setText(getNagiosHost());
        
        Button btnGetStatus = (Button)findViewById(R.id.btnGetStatus);
        // anonymous implementation
        btnGetStatus.setOnClickListener(new View.OnClickListener() {
        	 public void onClick(View v) {
        		 setStatusSever(((EditText)findViewById(R.id.statusServer)).getText().toString());
        		 setNagiosHost(((EditText)findViewById(R.id.nagiosServer)).getText().toString());
        		 startActivity(new Intent(NagiosClientHome.this, NagiosStatusSummary.class));
        	 }
         });
    }
    
}