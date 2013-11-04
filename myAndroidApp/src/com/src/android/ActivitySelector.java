package com.src.android;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivitySelector extends ListActivity {
	
	public static String globalString = "ActivitySelector";
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 Log.i("leiyong", "ActivitySelector onCreate");
		 
		 
		 Intent intent = getIntent();
		 String extra = intent.getStringExtra("com.src.path");
		 
		 if (extra == null) {
			 extra = "";
	        }
		 		 
		 setListAdapter(new SimpleAdapter(this, getData(extra),
	                android.R.layout.simple_list_item_1, new String[] { "title" },
	                new int[] { android.R.id.text1 }));
	        getListView().setTextFilterEnabled(true);
		 
	 }
	
	 protected List<Map<String, Object>> getData(String prefix){ 
		
		List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();
		 
		PackageManager pmanager = getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("com.src.android.sample_code");
        List<ResolveInfo> lst = pmanager.queryIntentActivities(mainIntent, 0);
        
        String [] prefixPath ;
        String prefixSlash = prefix;
        
        if(prefix.equals("")){
        	prefixPath = null;
        }else{
        	prefixPath = prefix.split("/");
        	prefixSlash = prefix + "/";
        }
        
        HashMap<String, Object> entry = new HashMap<String, Object>();
        
        for(ResolveInfo info : lst){
        	String label = info.loadLabel(pmanager).toString();
        	System.out.println(label);
        	
        	String[] splitPath = label.split("/");
        	//������������µ�'/'��� ��ֹ��������֮ǰ���ظ�����
        	if(prefixSlash.length() == 0 || label.startsWith(prefixSlash)){
        		//��ǰ���������µ���һ��'/'�ַ�
        		String nextLabel = prefixPath==null ? splitPath[0] : splitPath[prefixPath.length]; 
        		
        		if((prefixPath != null? prefixPath.length:0) == splitPath.length-1){	
        			ActivityInfo ainfo = info.activityInfo;
                	String classname = ainfo.name;
                	addItem(myData, nextLabel, activityIntent(
                			info.activityInfo.applicationInfo.packageName ,classname));
        		}else{
            		if(entry.get(nextLabel) == null){
            			addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
            			entry.put(nextLabel, true);
            		}	
        		}
        	}
        }
        
        return myData;
	}
	 
	protected void addItem(List<Map<String, Object>> data, String name, Intent intent){
		 
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 map.put("title", name);
		 map.put("intent", intent);
		 data.add(map);
	 }
	 
	 protected Intent browseIntent(String path) {
		 Intent result = new Intent();
		 result.setClass(this, ActivitySelector.class);
		 result.putExtra("com.src.path", path);
		 return result;
	 }
	 
	 protected Intent activityIntent(String pkg, String componentName) {
	        Intent result = new Intent();
	        result.setClassName(pkg, componentName);
	        return result;
	    }
	
	 @Override
	 @SuppressWarnings("unchecked")
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);

		 Intent intent = (Intent) map.get("intent");
		 startActivity(intent);
	 }	
	
	 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("leiyong", "ActivitySelector onDestroy");
	}
	 
	 
}
