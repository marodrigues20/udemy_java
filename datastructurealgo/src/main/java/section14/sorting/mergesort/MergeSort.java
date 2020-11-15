package section14.sorting.mergesort;

public class MergeSort {

	public static void mergeSort(int[] Array, int left, int right) {
		if (right > left) {
			int m = (left + right) / 2;
			//System.out.println(" ------------------- LEFT SIDE BREAK DOWN IT -------------------------- ");
			mergeSort(Array, left, m);
			System.out.println("Lets see recursion left -> " + left + " --- right side but is the m -> " + right + "int m / 2 --> " + m);
			mergeSort(Array, m + 1, right);

			System.out.println("Lets see recursion left -> " + left + " --- right side but is the m -> " + right + " int m / 2 --> " + m);
			//merge(Array, left, m, right);
		}
	}//end of method

	
	static void merge(int[] A, int left, int middle, int right) {
		int [] leftTmpArray = new int[middle-left+2];  //Create tmp arrays
		int [] rightTmpArray = new int[right-middle+1];
		
		for(int i=0;i<=middle-left;i++) //Copy values from Array 'A' to these tmp arrays
			leftTmpArray[i]= A[left+i];
		for(int i=0;i<right-middle;i++)
			rightTmpArray[i]= A[middle+1+i];
		
		leftTmpArray[middle-left+1]= Integer.MAX_VALUE; //Merge values and insert into Array 'A'
		rightTmpArray[right-middle] = Integer.MAX_VALUE;
		int i = 0, j = 0;
		for (int k = left; k <= right; k++) {
			if (leftTmpArray[i] < rightTmpArray[j]) {
				A[k] = leftTmpArray[i];
				i++;
			} else {
				A[k] = rightTmpArray[j];
				j++;
			}
		}
	}//end of method
	
	
	public static void printArray(int []array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]+"  ");
		}
		System.out.println("--------------------------------------------------------");
	}//end of method

}//end of class
