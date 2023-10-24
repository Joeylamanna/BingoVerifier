#Author Joey Lamanna
import sys
result = ''
notValidCount = 0
validBingo = False
lastRow = -1
lastCol = -1
indicator = '0'
patternCounter = 0
counter = 0

#prints out the valid bingo or not valid bingo message
def showResult(res):
    global result
    print(res)

#determines if bingo or not
def isvalidBingo(arr, pattern):
    global result, validBingo
    counter = 0
    for row in range(len(arr)):
            for col in range(len(arr[row])):
                if arr[row][col] == pattern[row][col]:
                    counter += 1

    if counter == patternCounter and validBingo == True and patternCounter != 0 and pattern[lastRow][lastCol] == '1': 
        result = 'VALID BINGO'
    else:
        result = 'NO BINGO'
    return result

#mark board with '1'
def markBingo(arr,sequence):
    global result, validBingo, lastRow, lastCol
    for i in range(len(sequence)):
        target = sequence[i]
        lastNumberCalled = sequence[len(sequence) - 1]
        
        for row in range(len(arr)):
            for col in range(len(arr[row])):
                if arr[row][col] == target and lastNumberCalled == target and i == len(sequence) - 1:
                    arr[row][col] = '1'
                    validBingo = True
                    lastRow = row
                    lastCol = col
                elif arr[row][col] == target or arr[row][col] == '00':
                    arr[row][col] = '1'
    return arr

#rotates the list of lists 90 degrees
def rotate(matrix):
    return list(zip(*matrix[::-1])) #https://medium.com/@SantalTech/a-clever-one-liner-to-rotate-2d-arrays-in-python-f67608ec77f9
    

def main():
    global result, patternCounter, notValidCount, validBingo, lastRow, lastCol ,indicator ,counter
    while True:
        try:
            data = []
            pattern = []
            bingoboard = []
            sequence = []
            index = 0
            for i in range(14):
                data.append(input().strip().split(" "))
                index = index + 1
                if index == 13:
                    index = 0
                    break

            #pattern maker
            for i in range(5):
                pattern.append(data[i])

            for row in range(len(pattern)):
                for col in range(len(pattern[row])):
                    if pattern[row][col] == '1':
                        patternCounter += 1
                        indicator = '1'
                    elif pattern[row][col] == '4':
                        patternCounter += 1
                        pattern[row][col] = '1'
                        indicator = '4'
      
            #sequence maker
            for i in range(6,7):
                sequence.append(data[i])
            sequence = [item for sublist in sequence for item in sublist]

            #bingoboard maker
            for i in range(8,13):
                bingoboard.append(data[i])
           
            #pattern with 1s
            if indicator == "1":
                markBingo(bingoboard,sequence)
                isvalidBingo(bingoboard,pattern)
                showResult(result)
                validBingo = False
            elif indicator == "4":
                while(notValidCount < 4):
                    markBingo(bingoboard,sequence)
                    isvalidBingo(bingoboard,pattern)
                    pattern = rotate(pattern)
                    
                    if result == "VALID BINGO":
                        showResult(result)
                        notValidCount = 4
                    notValidCount += 1
                if result == "NO BINGO":
                    showResult(result)

            indicator = '1'
            validBingo = False
            notValidCount = 0
            result = ''
            patternCounter = 0
            counter = 0
            lastCol = 0
            lastRow = 0
        except EOFError:
            break


main()
