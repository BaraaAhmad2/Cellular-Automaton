import java.util.ArrayList;
import java.util.Arrays;


/**
 * Rule Class: takes in the given binary values and converts them into boolean values that correspond to all 256 rules
 * This class also "evolves" Generations and neighborhoods, creating new rows and changing the value of each cell
 * (Cole Hoffman helped me with evolve(Generation) and statesArray(), Christopher White worked with me to do switchHelper and evolve(boolean[])
 * @author Baraa Ahmad
 *
 */

public class Rule {
	private final int ruleNum;
	private final int BINARY = 8;
	private char[] charArray;
	private final boolean[] ruleStates = new boolean[BINARY];

	/**
	 * A number is passed into this constructor that represents the rule number (0-255)
	 * This will determine the form of the automaton and its behavior, given its cell states.
	 * @param ruleNum
	 */
	public Rule(int ruleNum) {
		if(ruleNum > 255)
		{
					
		} 

		else if(ruleNum < 0) 
		{
			ruleNum = 0;
		}

		this.ruleNum = ruleNum;


		padBinary();
		statesArray();

	}


	/**
	 * This method ensures that, if a binary value derived from ruleNum is less than 8 values, it will pad the remaining empty spots with 0s
	 * The first for loop will start at the last element of the padding (where it takes the difference between 8 and the length
	 * of the actual array) and fill it with ruleNum converted to binary
	 * the nested loop ensures that every index up to the padding amount (the empty spots) will be filled with 0s
	 * This method was made to avoid null values.
	 */
	private void padBinary() {

		char[] binaryCharArray = Integer.toBinaryString(ruleNum).toCharArray();

		charArray = new char[BINARY];

		int paddingAmount = BINARY - binaryCharArray.length;


		for(int i = paddingAmount; i < charArray.length; ++i) {
			for(int j = 0; j < paddingAmount; ++j) {
				charArray[j] = '0';
			}
			charArray[i] = (binaryCharArray[i - paddingAmount]);
		}

	}


	/**
	 * This method uses each of the 8 values in ruleStates array and parses the String value (from char) into an int 
	 * if the index has a value of 1, then its boolean value is true. If its index has a value of 0, then it is false
	 * 
	 */

	private void statesArray() {

		for (int i = 0; i < charArray.length; i++) {

			if(Integer.parseInt(String.valueOf(charArray[i])) == 1) 
			{
				ruleStates[i] = true;
			}
			else 
			{
				ruleStates[i] = false;
			}
		}
	}


	/**
	 * This method will create a new Generation (row) using getStates to ensure it is the same length;
	 *
	 * Generation gen is an object used to access getStates from cellStates in the Generation class; 
	 * 
	 * This will ensure that it contains the correct amount of state cells; 
	 * 
	 * After running the for loop, newGen (array for the new Generation) will invoke the evolve(boolean[] neighborhood) method
	 * 
	 * which will send an index (the cell that will be evaluated) along with a Generation object,
	 * 
	 * This object will then be used in evolve(boolean[] neighborhood) to receive the values for each index of the newGen array
	 * 
	 * At the end, this new Generation array will be sent to the first Generation constructor to evaluate it once more, and repeat the process
	 *
	 * @param gen this object will access the Generation class and retrieve getStates(), which is cellStates' length
	 * @return	the array for the new row after calling it in the Generation class.
	 */
	public Generation evolve(Generation gen) {
		boolean[] boolGen = gen.getStates();

		boolean[] newGen = new boolean[boolGen.length];

		for (int i = 0; i < boolGen.length; i++) {
			newGen[i] = evolve(getNeighborhood(i, gen));
		}

		return new Generation(newGen);	 
	}


	/**
	 * Using circular boundary conditions, this method receives the values of the state of the current index, the cell to its left, and the cell to its right;
	 * int index in the loop is the basis code that receives the state of the left and right neighbor, along with the current state
	 * if the current state is at the end of the array, it will access the conditional regarding this situation and "wrap around" to the beginning of the Generation array
	 * to retrieve the value of the index to its right
	 * 
	 * Likewise, if the current state is at the beginning of the array, its left neighbor will wrap around to the end of the array
	 * @param idx	the desired index of the cell in the Generation
	 * @param gen	Uses the Generation class to access getStates() values.
	 * @return
	 */
	public static boolean[] getNeighborhood(int idx, Generation gen) {
		boolean[] booleanGen = gen.getStates();
		boolean[] neighborhood = new boolean[3];

		if(booleanGen.length == 1) {
			Arrays.fill(neighborhood, neighborhood[0]);
		}

		for(int i =0; i < neighborhood.length; ++i) {

			int index = (idx - 1 + i);

			if(index < 0) {
				neighborhood[i] = booleanGen[booleanGen.length - 1];
			}

			else if( index > booleanGen.length - 1) {
				neighborhood[i] = booleanGen[0];
			}

			else {
				neighborhood[i] = booleanGen[index];
			}

		}

		return neighborhood;

	}


	/**
	 * This will return the next state of a cell with the given neighborhood (left, middle and right)
	 * It will pass the string neighborhoodVal (with the state "1" or "0")
	 * it will then send that value to switchHelper, which will determine the next cell states.
	 * @param neighborhood  array that contains the left neighbor and right neighbor of the cell, and the cell itself
	 * @return the state of the next cell.
	 */
	public boolean evolve(boolean[] neighborhood) {
		String neighborhoodVal = "";

		for (int i = 0; i < neighborhood.length; ++i) {

			if(neighborhood[i]) {
				neighborhoodVal += "1";
			}
			else {
				neighborhoodVal += "0";
			}
		}

		return switchHelper(neighborhoodVal);

	}






	/**
	 * designates each possible value of states (8 possibilities) into each index of the ruleStates array
	 * @param parameter the String neighborhoodVal that determined the states of the next cell
	 * @return 	returns each case into the given index of ruleStates; otherwise, it would return false.
	 */
	private boolean switchHelper(String parameter) {

		switch(parameter) 
		{
		case "111":

			return ruleStates[0];

		case "110":

			return ruleStates[1];

		case "101":

			return ruleStates[2];

		case "100":

			return ruleStates[3];

		case "011":

			return ruleStates[4];

		case "010":


			return ruleStates[5];

		case "001":


			return ruleStates[6];

		case "000":

			return ruleStates[7];

		default:
			return false;
		}


	}


	/**
	 * This returns the rule's Wolfram code
	 * @return	return the value of ruleNum as the Wolfram code.
	 */

	public int getRuleNum() {
		return ruleNum;
	}

}
