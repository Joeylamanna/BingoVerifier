//Author: Joey Lamanna
import java.util.Scanner;

public class BingoVerifier 
{
    //static variables
    static String result = "";
    static int notValidCount = 0;
    static String indicator = "1";
    static int patternCounter = 0;
    static boolean validBingo = false;
    static int counter = 0;
    static int lastRow = -1;
    static int lastCol = -1;

    //method to print out result of bingo
    public static void showResult()
    {
        System.out.println(result);
    }
    //determines if your bingo is valid or not
    public static void validBingo(String[][] arr, String [][] pattern) 
    {
        counter = 0;
        for (int row = 0; row < arr.length; row++) 
        {
            for (int col = 0; col < arr[row].length; col++) 
            {
                if (arr[row][col].equals(pattern[row][col])) 
                {
                   counter++;
                }
            }
        }
        
        if(counter == patternCounter && validBingo == true && patternCounter !=0 && pattern[lastRow][lastCol].equals("1"))
        {
            result = "VALID BINGO";
        }
        else
        {
            result = "NO BINGO";
        }
        
    }
    //marks bingo board with 1s 
    public static void markBingo(String[][] arr, String [][] pattern, String [] sequence) 
    {
        for (int i = 0; i < sequence.length; i++) {
            String target = sequence[i];
            String lastNumberCalled = sequence[sequence.length - 1];
           
                for (int row = 0; row < arr.length; row++) 
                {
                    for (int col = 0; col < arr[row].length; col++) 
                    {
                        if(arr[row][col].equals(target) && lastNumberCalled.equals(target) && i == sequence.length - 1)
                        {
                            arr[row][col] = "1";
                            validBingo = true;
                            lastRow = row;
                            lastCol = col;
                        }
                        else if (arr[row][col].equals(target) || arr[row][col].equals("00")) 
                        {
                            arr[row][col] = "1";
                        }
                    
                    }
               
                 }
            }
            
        }
        //method used to rotate bingo pattern 
        public static String[][] rotate2dArray(String[][] arr) {
            int numRows = 5;
            int numCols = 5;
            
            String rotated[][] = new String[numCols][numRows];
    
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    rotated[j][numRows - i - 1] = arr[i][j];
                }
            }
            return rotated;
        }

    public static void main(String[] args)
    {
     Scanner in = new Scanner(System.in);
     // while loop to allow input stream with endless test cases
     while(in.hasNext())
       {
        int numRows = 5;
        int numCols = 5;
        String data [] = new String[13]; // takes in all input data into an array
        String splitter[] = new String[25]; //for splitting the lines of data from the input
        String pattern[][] = new String[5][5]; //takes in the bingo pattern
        String sequence[] = new String[26]; //takes in the sequence of number
        String bingoBoard[][] = new String[5][5]; //takes in the bingo board 
        int index = 0; //tracks how many times the big while loop goes
        
       
            while(in.hasNextLine() & index < 13)
            {
                data[index] = in.nextLine();
            
                index++;
                if(index == 14)
                    index = 0;
             }
            
             //pattern 2d array maker
             for (int i = 0; i < numRows; i++) 
             {
                splitter = data[i].split(" ");
                if (splitter.length == numCols) 
                {
                    for (int j = 0; j < numCols; j++) 
                    {
                        pattern[i][j] = splitter[j];
                        if(pattern[i][j].equals("1"))
                        {
                            patternCounter++;
                            indicator = "1";
                        }
                        else if(pattern[i][j].equals("4"))
                        {
                            patternCounter++;
                            pattern[i][j] = "1";
                            indicator = "4";
                        }
                    }
                }
             }

             //sequence array maker
                sequence = data[6].split(" ");
              
             //bingoBoard 2d array maker
             int k = 0;
             for (int i = 8; i < 13; i++) 
             {
                splitter = data[i].split(" ");
                if (splitter.length == numCols) 
                {
                    for (int j = 0; j < numCols; j++) 
                    {
                        
                        bingoBoard[k][j] = splitter[j];
                    }
                }
                k++;
             }
             //process for handling 2D array of ones 
             if(indicator.equals("1"))
             {
                markBingo(bingoBoard,pattern,sequence);
                validBingo(bingoBoard,pattern);
                showResult();
                validBingo = false;
             }
             //process for handling 2D array of fours to do rotations
             else if(indicator.equals("4"))
             {
                while(notValidCount < 4)
                {
                    markBingo(bingoBoard, pattern, sequence);
                    validBingo(bingoBoard,pattern);
                    pattern = rotate2dArray(pattern);
                    if(result.equals("VALID BINGO"))
                    {
                        showResult();
                        notValidCount = 4;
                    }
                    notValidCount++;
                }
                if(result.equals("NO BINGO"))
                {
                    showResult();
                }
                   
                    
             }
             indicator = "1";
             validBingo = false;
             notValidCount = 0;
             result ="";
             patternCounter = 0;
             counter = 0;
             lastCol = 0;
             lastRow = 0;
        
        }//end big while loop
            in.close();
        }
    }

