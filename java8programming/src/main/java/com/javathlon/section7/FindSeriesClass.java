package com.javathlon.section7;

public class FindSeriesClass {
	
	public static void main(String args []) {
		
		boolean matches[] ={false, false, true, true, true, false,false, false, true, false,true, true, true, true};
		
		new FindSeriesClass().findTheLongsetSeries(matches);
		
	}
	
	private void findTheLongsetSeries(boolean[] input) {
		
		if (input == null) {
			return;
		}
		
		int index = 0;
		
		int seriesStart = 0;
		
		int seriesEnd = 0;
		
		int longestSeriesStart = 0;
		int longestSeriesEnd = 0;
		
		for (index = 0; index < input.length; index++) {
			
			if(input[index]) {
				
				// we are starting a win series by breaking a lose series 
				if(index >= 1 && !input[index - 1]) {
					seriesStart = index;
				}
				
				seriesEnd = index + 1;
			}
			
			// we are completing a won series and start to lose series 
			if((input.length - 1 == index) || (!input[index] && index >= 1 && input[index - 1])) {
				
				if((seriesEnd - seriesStart) > (longestSeriesEnd - longestSeriesStart)) {
					longestSeriesStart = seriesStart;
					longestSeriesEnd = seriesEnd;
				}
			
			}
		}
		
		System.out.println("series start:" + longestSeriesStart +", series end:" + longestSeriesEnd );
	}

}
