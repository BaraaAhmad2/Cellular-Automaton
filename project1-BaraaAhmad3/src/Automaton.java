import java.util.ArrayList;
import java.util.*;
import java.io.*;
/**
 * This class represents an ECA; Each Automaton encapsulates a Rule and an ArrayList of Generations
 * The ArrayList is ordered by time, with the first element being the initial Generation and the last being the current Generation
 * @Override
	Each Automaton also contains two public fields, falseSymbol and trueSymbol, 
 * that store the characters used to represent the cell states in the output of toString
 * (This class was worked on entirely with Christopher White)
 * 
 * @author Baraa Ahmad 
 * 
 * 
 */
public class Automaton {

	private Rule rule;
	private ArrayList<Generation> generations = new ArrayList<>();
	char falseSymbol;
	char trueSymbol;
	Generation gen = new Generation();



	/**
	 * Create an Automaton from a rule number and an initial Generation and initialize falseSymbol and trueSymbol to '0' and '1', respectively
	 * @param ruleNum 	this will take the input argument and pass it to Rule, where it is then evaluated on its bounds between 0 and 255
	 * @param initial	Value that is being sent to the Generation class to be evaluated
	 */
	public Automaton(int ruleNum, Generation initial) {
		falseSymbol = '0';
		trueSymbol = '1';
		rule = new Rule(ruleNum);
		generations.add(initial);
	}



	/**
	 * this constructor reads the filename of the ECA and splits the symbols for both false and true and puts it in a separate array;
	 * the rule value is sent to Rule and its value is parsed;
	 * The Generation object is sent to the String constructor in the Generation class to retrieve the row with the cells and String representation;
	 * that value is added into the generations list
	 * @param filename	This is the text file for the different ECA rules to be evaluated
	 * @throws IOException Signals that an I/O exception of some sort has occurred.
	 */
	public Automaton(String filename) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(filename));
		rule = new Rule(Integer.parseInt(br.readLine()));
		String[] charArray = br.readLine().split(" ");
		falseSymbol = charArray[0].charAt(0);
		trueSymbol = charArray[1].charAt(0);
		Generation generationFileValue = new Generation(br.readLine(), trueSymbol);
		generations.add(generationFileValue);
		br.close();


	}

	/**
	 * This will evolve the ECA a number of steps given by the parameter and add the next generation to the end of the list everytime
	 * 
	 * @param numSteps the desired number of rows that the ECA is to evolve.
	 */
	public void evolve(int numSteps) {

		if(numSteps <= 0 ) {
			return;
		}
		else {
			for(int i =0; i < numSteps; ++i) {
				generations.add(rule.evolve(generations.get(generations.size() - 1)));
			}
		}
	}


	/**
	 * Returns the Generation produced during the given time step;
	 * if the generation has not evolved that far, find the difference between the desired number of steps and the current number of steps 
	 * it has evolved, then call the evolve method to complete the rest of the evolutions
	 * @param stepNum  desired time step for generations to be returned
	 * @return Return the Generation produced during the given time step
	 */
	public Generation getGeneration(int stepNum) {


		evolve(stepNum - getTotalSteps());
		return generations.get(stepNum);
	}



	/**
	 *  Return the total number of steps that the ECA has evolved
	 * @return  Returns the total number of steps that the ECA has evolved
	 */
	public int getTotalSteps() {
		return generations.size() -1;
	}


	/**
	 * Saves the file that was scanned by the BufferedReader and saves the result
	 * @param filename given text file that was scanned by the BufferedReader
	 * @throws IOException  Signals that an I/O exception of some sort has occurred
	 */
	public void saveEvolution(String filename) throws IOException 
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		bw.write(toString());
		bw.close();
	}



	/**
	 * Returns a string representation of the full evolution
	 * The first loop ensures that every line in the list is accessed and does not exceed boundaries;
	 * Second loop ensures that it does not exceed the retrieved values from getStates() so that only the desired ECA lines are collected;
	 * if the retrieved symbol is equal to trueSymbol, it adds a new line (using stringjoiner) and adds it to the new Generation
	 * Otherwise, return false with a new line from the stringjoiner for the new Generation
	 * @return return the full StringJoiner value, which represents the entire Automaton as a string 
	 */

	public String toString() {



		StringJoiner join = new StringJoiner(System.lineSeparator());
		for(int i = 0; i < generations.size(); ++i) {
			for(int j = 1; j < generations.get(i).getStates().length; ++j) {

				if(generations.get(i).getStates(falseSymbol, trueSymbol).charAt(j) == trueSymbol) {

					join.add(generations.get(i).getStates(falseSymbol, trueSymbol).toString());
					break;
				}
				else  {
					join.add(generations.get(i).getStates(falseSymbol, trueSymbol).toString());
					break;

				}
			}




		}
		return join.toString();
	}



	/**
	 * Return the Wolfram code for the rule that governs the ECA
	 * @return returns the value that dictates what the ECA looks like.
	 */
	public int getRuleNum() {
		return this.rule.getRuleNum();
	}



	protected Rule createRule(int ruleNum) throws RuleNumException {
		// TODO Auto-generated method stub
		return null;
	}
}
