package com.javathlon.section7;

public class TwoDimensionalArraySample {

	public static void main(String args[]) {
		int goals[][] = {{2,3,2}, {0,2,0},{1,0,0},{1,1,2}};
		
		String[] footballers = new String[]{"Messi", "Ronaldo", "Robben"};
		
		TwoDimensionalArraySample sample = new TwoDimensionalArraySample();
		
		sample.findMostScorerPlayer(goals, footballers);
		
		sample.findMostScorerPlayer(goals, footballers, 3);
		
	}
	
	private void findMostScorerPlayer(int goals[][], String[] players) {
		
		int footballerCount = goals[0].length;
		
		System.out.println(footballerCount);
		
		int goalCount = 0;
		
		int maxGoalCount = 0;
		int mostScorerPlayerIndex = 0;
		
		for(int footballerIndex = 0; footballerIndex < footballerCount; footballerIndex++) {
			for(int matchIndex = 0; matchIndex < goals.length; matchIndex++) {
				goalCount += goals[matchIndex][footballerIndex];
			}
			System.out.println("player" +footballerIndex+"'s goal count:" + goalCount);
			
			if (goalCount > maxGoalCount) {
				maxGoalCount = goalCount;
				mostScorerPlayerIndex = footballerIndex;
			}
			
			goalCount = 0;
		}
		
		System.out.println("Most scorer player:" + players[mostScorerPlayerIndex] + " goal count:" + maxGoalCount);
	
	}
	
	private void findMostScorerPlayer(int goals[][], String[] players, int matchIndex) {
		
		int footballerCount = goals[0].length;
		
		int maxGoalCount = 0;
		int mostScorerPlayerIndex = 0;
		
		for(int footballerIndex = 0; footballerIndex < footballerCount; footballerIndex++) {
			
			if(goals[matchIndex][footballerIndex] > maxGoalCount) {
				maxGoalCount = goals[matchIndex][footballerIndex];
				mostScorerPlayerIndex = footballerIndex;
			}
			
		}
		
		System.out.println("Most scorer player in match " + (matchIndex + 1) + " is " + players[mostScorerPlayerIndex] + " goal count:" + maxGoalCount);
		
	}
	
}
