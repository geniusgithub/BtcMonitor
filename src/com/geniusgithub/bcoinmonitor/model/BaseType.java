package com.geniusgithub.bcoinmonitor.model;


public class BaseType {


	public static class ConinInst implements IToPrintString{

		public double mPrice = 0;
		public double mCount = 0;
		public double mRate = 0.01;
		
		public ConinInst(double val1, double val2){
			mPrice = val1;
			mCount = val2;
		}

		@Override
		public String getShowString() {
			return "[" + mPrice + ", " + mCount + "]";
		}
	}
	
}
