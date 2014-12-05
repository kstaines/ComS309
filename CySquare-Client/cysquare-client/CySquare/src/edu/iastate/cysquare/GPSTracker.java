package edu.iastate.cysquare;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener{
	
	private final Context mContext;
	
	//flag for GPS status
	boolean isGPSEnabled = false;
	
	//flag for network status
	boolean isNetworkEnabled = false;
	
	//flag for location status
	boolean canGetLocation = false;
	
	Location location; //location
	double latitude; //latitude
	double longitude; //longitude
	
	//the minimum distance to change updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10 meters
	
	//the minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000*60*1; //1 minute
	
	//declaring a Location Manager
	protected LocationManager locationManager;
	
	public GPSTracker(Context context){
		this.mContext = context;
		getLocation();
	}
	
	public Location getLocation(){
		try{
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			
			//getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			//getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(!isGPSEnabled && !isNetworkEnabled){
				//no network provider is enabled
			}
			else{
				this.canGetLocation = true;
				//first get location from network provider
				if(isNetworkEnabled){
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if(locationManager != null){
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if(location != null){
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				//if GPS enabled get lat/long using GPS services
				if(isGPSEnabled){
					if(location == null){
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if(locationManager != null){
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if(location != null){
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return location;
	}
	
//****** STOP USING GPS **********
    //stop using GPS listener
    public void stopUsingGPS(){
    	if(locationManager != null){
    		locationManager.removeUpdates(GPSTracker.this);
    	}
    }
    
//****** GET USER'S CURRENT LOCATION **********
    //function to get latitude
    public double getLatitude(){
    	if(location != null){
    		latitude = location.getLatitude();
    	}
    	return latitude;
    }
    
    //function to get longitude
    public double getLongitude(){
    	if(location != null){
    		longitude = location.getLongitude();
    	}
    	return longitude;
    }
	
//****** ASKING USERS TO CHECK GPS/WIFI ENABLED **********
    //function to check if best network provider
    public boolean canGetLocation(){
    	return this.canGetLocation;
    }
    
//****** SHOW SETTINGS ALERT DIALOG ********************    
    //function to show settings alert dialog
    public void showSettingsAlert(){
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
    	
    	//setting dialog title
    	alertDialog.setTitle("GPS settings");
    	
    	//setting dialog message
    	alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
    	
    	//setting icon to dialog
    	//alertDialog.setIcon(R.drawable.delete);
    	
    	//on pressing settings button
    	alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int which){
    			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    			mContext.startActivity(intent);
    		}
    	});
    	
    	//on pressing cancel button
    	alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
    	
    	//showing alert message
    	alertDialog.show();
    }
    
    
    
    
	@Override
	public void onLocationChanged(Location location){}
	
	@Override
    public void onProviderDisabled(String provider) {}
 
    @Override
    public void onProviderEnabled(String provider) {}
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
}

