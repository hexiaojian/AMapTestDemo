package com.ruihai.amaptest;

import com.amap.api.maps.model.LatLng;
import com.amp.apis.lib.ClusterItem;

public class RegionItem implements ClusterItem {
	private LatLng mLatLng;
	//private String mTitle;
	public RegionItem(LatLng latLng) {
     mLatLng=latLng;
     //mTitle=title;
	}

	@Override
	public LatLng getPosition() {
		// TODO Auto-generated method stub
		return mLatLng;
	}
	/*public String getTitle(){
		return mTitle;
	}*/

}
