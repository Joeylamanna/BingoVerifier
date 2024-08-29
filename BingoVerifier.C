//Author: Joey Lamanna
#include <stdio.h>
#include <string.h>

static char result[100];
static int notValidCount = 0;
static int indicator = 1;
static int patternCounter = 0;
static int validBingo = 0;
static int counter = 0;
static int lastRow = -1;
static int lastCol = -1;
static int sequenceCounter = 0;

   // prints the result
   void showResult()
   {
       printf("%s\n", result);
   }
   
   //determines if your bingo is valid or not
    void isValidBingo(int arr[5][5] , int pattern[5][5]) 
    {
        counter = 0;
        for (int row = 0; row < 5; row++) 
        {
            for (int col = 0; col < 5; col++) 
            {
                if (arr[row][col] == pattern[row][col]) 
                {
                   counter++;
                }
            }
        }
        
        if(counter == patternCounter && validBingo == 1 && patternCounter !=0 && pattern[lastRow][lastCol] == 1)
        {
            strcpy(result, "VALID BINGO");
        }
        else
        {
            strcpy(result, "NO BINGO");

        }
        
    }
 void markBingo(int arr[5][5], int pattern[5][5], int sequence[75])
 {

    for (int i = 0; i < sequenceCounter; i++) 
    {
        int target = sequence[i];
        int lastNumberCalled = sequence[sequenceCounter - 1];
        
        for (int row = 0; row < 5; row++) 
        {
            for (int col = 0; col < 5; col++) 
            {
                if (arr[row][col] == target && lastNumberCalled == target && i == sequenceCounter -1) 
                {
                    arr[row][col] = 1;
                    validBingo = 1;
                    lastRow = row;
                    lastCol = col;
                } 
                else if (arr[row][col] == target || arr[row][col] == 0) 
                {
                    arr[row][col] = 1;
                }
            }
        }
    }
}

// source: https://www.geeksforgeeks.org/complete-guide-on-2d-array-matrix-rotations/
void rotate2dArray(int arr[5][5]) {
    int numRows = 5;
    int numCols = 5;

    int temp;

    for (int i = 0; i < numRows / 2; i++) {
        for (int j = i; j < numCols - i - 1; j++) {
            temp = arr[i][j];
            arr[i][j] = arr[numRows - 1 - j][i];
            arr[numRows - 1 - j][i] = arr[numRows - 1 - i][numCols - 1 - j];
            arr[numRows - 1 - i][numCols - 1 - j] = arr[j][numCols - 1 - i];
            arr[j][numCols - 1 - i] = temp;
        }
    }
}

        
int main() 
{
    int numRows = 5;
    int numCols = 5;
    int pattern[5][5];
    int bingoBoard[5][5];
    
    while (1) 
    {
        sequenceCounter = 0;
        for (int i = 0; i < numRows; i++) 
        {
            for (int j = 0; j < numCols; j++) 
            {
                scanf("%d", &pattern[i][j]);
                
                if(pattern[i][j] == 1)
                        {
                            patternCounter++;
                            indicator = 1;
                        }
                        else if(pattern[i][j] == 4)
                        {
                            patternCounter++;
                            pattern[i][j] = 1;
                            indicator = 4;
                        }
            }
        }
        
        int sequence[75];
        while(sequenceCounter < 76 )
        {
            scanf("%d", &sequence[sequenceCounter]);
            sequenceCounter++;
            if(getchar() == '\n')
            {
                break;
            }
        }
        
        for (int i = 0; i < numRows; i++) 
        {
            for (int j = 0; j < numCols; j++) 
            {
                scanf("%d", &bingoBoard[i][j]);
            }
        }
        
        if(feof(stdin) != 0)
        {
            break;
        }
        
        //process for handling 2D array of ones 
        if(indicator == 1)
        {
            markBingo(bingoBoard,pattern,sequence);
            isValidBingo(bingoBoard,pattern);
            showResult();
            validBingo = 0;
        }
            
        //process for handling 2D array of fours to do rotations
        else if(indicator == 4)
        {
            while(notValidCount < 4)
            {
                markBingo(bingoBoard, pattern, sequence);
                isValidBingo(bingoBoard, pattern);
                rotate2dArray(pattern);

                if(strcmp(result, "VALID BINGO") == 0)
                {
                    showResult();
                    notValidCount = 4;
                }
                notValidCount++;
            }

            if(strcmp(result, "NO BINGO") == 0)
            {
                showResult();
            }
        }
        
            indicator = 1;
            validBingo = 0;
            notValidCount = 0;
            strcpy(result, "");
            patternCounter = 0;
            counter = 0;
            lastCol = 0;
            lastRow = 0;
            sequenceCounter = 0;
            memset(sequence, 0, 75 * sizeof(int));

    } //end while

    return 0;
}
