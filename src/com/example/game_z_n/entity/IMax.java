package com.example.game_z_n.entity;

public class IMax {
	private int _id;
	private int n;
	private int sum;
	private long tm;

	public long getTm() {
		return tm;
	}

	public void setTm(long tm) {
		this.tm = tm;
	}

	public IMax(int _id, int n, int sum,long tm) {
		this._id = _id;
		this.tm = tm;
		this.n = n;
		this.sum = sum;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
}
