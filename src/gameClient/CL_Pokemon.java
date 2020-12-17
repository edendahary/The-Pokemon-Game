package gameClient;

import api.edge_data;
import api.geo_location;
import gameClient.util.Point3D;

public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private api.geo_location _pos;
	private double min_dist;
	private int min_ro;
	private String Trace = "NotInTrace";
	public CL_Pokemon(double value,int type , geo_location g){
		this._value=value;
		this._type=type;
		this._pos=g;
	}
	
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
	//	_speed = s;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
	}

	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public geo_location getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}
	public String getTrace() {
		return Trace;
	}

	public void setTrace(String trace) {
		Trace = trace;
	}
}
