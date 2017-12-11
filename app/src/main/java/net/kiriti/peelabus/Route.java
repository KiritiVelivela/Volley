package net.kiriti.peelabus;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by kiriti_sai on 09/12/17.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
