/***************************************************************
* file: KnightsOnABoard.java
* author: Z. Yung
* class: CS 1400 – Programming and Problem Solving
*
* assignment: program 5
* date last modified: 11/10/2024
*
* purpose: This program takes in a file, checks for validation, and then
* prints out the file as a chess board. It then checks to see if any knights
* on the board would be able to capture another.
****************************************************************/
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class KnightsOnABoard {

	/**
	 * This method validates if a file exists or not
	 * 
	 * @param inputFile file name
	 * @return boolean true/false if file can be located
	 */

	public static File validateFile(File inputFile){
		Scanner scnr = new Scanner(System.in);
		if(inputFile.exists()){
			//System.out.println();
			return inputFile;	
		}
		else {
			System.out.println("File does not exist!");
			System.out.print("Please enter the name of a valid file: ");
			File input = new File(scnr.nextLine());
			return validateFile(input);
		}  
	}

	/**
	 * This method checks if the input file contains an 8x8 grid of numbers
	 * 
	 * @param inputFile file name
	 * @return boolean true/false if file contains 8x8 grid of numbers
	 */

	public static boolean validateData(File inputFile) throws IOException{
	    Scanner scnr = new Scanner(inputFile);
		int countColumn = 0;
		int countRow = 0;
		boolean column = true;

		while(scnr.hasNextLine()){
			String line = scnr.nextLine();
			Scanner scan = new Scanner(line);
			while(scan.hasNextInt()){
			    int num = scan.nextInt();
			    countColumn++;
			}
			if(countColumn != 8){
			    column = false;
			}
			countColumn = 0;
			countRow++;
		}
		
		if(countRow == 8 && column){
		    return true;
		}
		else {
		    System.out.println("File has invalid data.");
		    return false;
		}
	}

	/**
	 * This method scans the 8x8 grid and creates a new board consisting of 1s and 0s
	 * 
	 * @param inputFile file name
	 * @return created board 
	 */

	public static int[][] populateBoard(File inputFile) throws IOException{
		Scanner scnr = new Scanner(inputFile);
		int[][] board = new int[8][8];
		//boolean greater = false;
		//boolean less = false;
		boolean check = false;
		for(int i = 0; i < board.length; i++){
			String line = scnr.nextLine();
			Scanner scan = new Scanner(line);
			for(int j = 0; j < board[0].length; j++){
				int num = scan.nextInt();
				if(num == 1 || num == 0){
					board[i][j] = num;
				}
				else if(num > 1){
					check = true;
					board[i][j] = 1;
				}
				else if(num < 0){
					check = true;
					board[i][j] = 0;
				}
			}
		}
		if(check){
			System.out.println("Positive values greater than 1 were detected and will be converted to 1 and/or that negative values smaller than 0 were detected and will be converted to 0");
			System.out.println();
			System.out.println();
		}
		return board;
	}

	/**
	 * This method prints out the board
	 * 
	 * @param chessBoard 
	 * @return board 
	 */

	public static void printBoard(int[][] chessBoard){
		for(int i = 0; i < chessBoard.length; i++){
			for(int j = 0; j < chessBoard[0].length; j++){
				System.out.print(chessBoard[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * This method analyzes where each 1 is and determines if any "knight" can capture another
	 * 
	 * @param chessBoard
	 * @return boolean true/false if a knight can be captured
	 */

	public static boolean cannotCapture(int[][] chessBoard){
		for(int i = 0; i < chessBoard.length; i++){
			for(int j = 0; j < chessBoard[0].length; j++){
				if(chessBoard[i][j] == 1){
					if((i + 2) < 8){
						if(((j + 1) < 8) && (chessBoard[i+2][j+1] == 1)){
							return false;
						}
						else if(((j - 1) >= 0) && (chessBoard[i+2][j-1] == 1)){
							return false;
						}		
					}
					else if((i - 2) >= 0){
						if(((j + 1) < 8) && (chessBoard[i-2][j+1] == 1)){
							return false;
						}
						else if(((j - 1) >= 0) && (chessBoard[i-2][j-1] == 1)){
							return false;
						}		
					}
					else if((j + 2) < 8){
						if(((i + 1) < 8) && (chessBoard[i+1][j+2] == 1)){
							return false;
						}
						else if(((i - 1) >= 0) && (chessBoard[i-1][j+2] == 1)){
							return false;
						}		
					}
					else if((j - 2) >= 0){
						if(((i + 1) < 8) && (chessBoard[i+1][j-2] == 1)){
							return false;
						}
						else if(((i - 1) >= 0) && (chessBoard[i-1][j-2] == 1)){
							return false;
						}		
					}

				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException{  
		Scanner scnr = new Scanner(System.in);
		System.out.print("Please enter the name of a valid file: ");
		File inputFile = new File(scnr.nextLine());
		inputFile = validateFile(inputFile);
		boolean check = validateData(inputFile);
		while(check == false){
			System.out.print("Please enter the name of a valid file: ");
			inputFile = new File(scnr.nextLine());
			inputFile = validateFile(inputFile);
			check = validateData(inputFile);
		}
		
		System.out.println();
		int[][] chessBoard;
		chessBoard = populateBoard(inputFile);
		System.out.println("The board looks as follows:");
		System.out.println();
		printBoard(chessBoard);
		System.out.println();

		check = cannotCapture(chessBoard);
		if(!check){
			System.out.println("There is at least one knight which can capture another knight.");
		}
		else{
			System.out.println("No knights can capture any other knight.");
		}
	}
}
