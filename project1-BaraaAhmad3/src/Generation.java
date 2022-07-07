import java.util.Arrays;
/**
 * 	Generation defines a Generation with the given states (1, 0) at a fixed time
 *  Each Generation encapsulates a boolean array that represents the cell states
 *  The cells are indexed from left to right starting at zero
 *  Maria Doan helped me fix getStates (String, char) 
 * @author Baraa Ahmad
 *
 */
public class Generation {
	private final boolean[] cellStates;



	/**
	 * A Generation is a row in the ECA with a collection of "cells", each a unique value of true or false.
	 * The state of each cell is specified by the value of the corresponding element.
	 * If the array is empty or the method is given a null reference, create a Generation with one cell in the false state.
	 * To ensure there is no IndexOutOfBoundsException, set cellStates equal to the length of states after it passes the null check.
	 * @param states
	 */
	public Generation(boolean... states) {
		boolean[] falseArray = {false};
		if(states == null || states.length == 0) {
			cellStates = falseArray; 

		}
		else {
			cellStates = new boolean[states.length]; 
      
			for(int i =0; i < states.length; ++i) {
				cellStates[i] = states[i];
			}

		}
	}


	/**
	 * Create a Generation with one cell for each character in the given String ('1','0', etc).
	 * If the char value in the specific position of the string equal to an index in cellStates is '0' or '1', its boolean value will be assigned accordingly. 
	 * If the String is empty ("") or the method is given a null reference, a Generation with one cell in the false state is created.
	 * @param states collection of 1s and 0s that will be evaluated to determine the state of each cell
	 * @param trueSymbolthe '1' or 'T' that indicates the cell has a true state.
	 */
	public Generation(String states, char trueSymbol) {
		boolean[] bool = {false};

		if(states == null || states == "") {
			cellStates = bool;
		}
		else {

			cellStates = new boolean[states.length()];

			for(int i =0; i < cellStates.length; ++i) {

				if(states.charAt(i) == trueSymbol) {
					cellStates[i] = true;
				}

				else {
					cellStates[i] = false;
				}
			}
		}
		//		cellStates = new boolean[trueSymbol];
	}


	/**
	 * Return the state of the cell with the given index.
	 * @param idx the specific index of the cellStates array that it wants its state value (1 or 0).
	 * @return	return the state of that element.
	 */
	public boolean getState(int idx) {
		return cellStates[idx];
	}


	/**
	 * Return an array with all of the cell states and ensures its immutable so that its values remain unique for the generation 
	 * This is essential when defining a Generation and evolving, so that the previous Generation (row) does not change as well.
	 * @return return an immutable copy of the current Generation 
	 */
	public boolean[] getStates() {
		return Arrays.copyOf(cellStates, cellStates.length);
	}

	/**
	 * this returns the String format of a true or false value of each state.
	 * It evaluates every index of cellStates that exists, and assigns that index to true or false, depending on its value ( 1 or 0 ).
	 * It then returns that String representation of the symbols ('F' or 'T').
	 * @param falseSymbol the value of a cell in the false state.
	 * @param trueSymbol  the value of a cell in the true state.
	 * @return
	 */

	public String getStates(char falseSymbol, char trueSymbol) {
		String symbolString = "";
		for(int i =0; i < cellStates.length; ++i) {
			if(cellStates[i] == true) {
				symbolString  = symbolString + trueSymbol;
			}

			else if(cellStates[i] == false) {
				symbolString = symbolString + falseSymbol;
			}

		}
		return symbolString;

	}

	/**
	 * This method returns the current size of the cellStates array.
	 * @return
	 */
	public int size() {
		return cellStates.length;
	}


}
