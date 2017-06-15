# Author: Tobias Meisel, June 2017
# Tested only for Python3.5
# This document is an example of how the various output formats could be parsed in a format readable by the
# Programm submitted for our project inf 2017. For more informations please look at out doc.
# This script however can (and should) be altered to fitt differen formats
import sys


def main():

    # Build key-value pairs
    keyValPairs = {}    
    args = sys.argv
    del(args[0])
    for argument in args:
        l = argument.replace("--", "").split("=")
        if len(l) != 2:
            raise Exception("Illegal commandoline parameters")
        else:
            keyValPairs[l[0]]=l[1]
    print("Arguments provided: " + str(keyValPairs))

    # Check for the various necessary fields
    if (not ("target" in keyValPairs.keys())):
        print("An argument 'target=<some value>' must be provided")
        return
    if (not ("source" in keyValPairs.keys())):
        print("An argument 'source=<some value>' must be provided")
        return
    
    # Open the needed files
    source = None
    target = None

    source = open(keyValPairs["source"], mode="r")
    print(keyValPairs["source"] + " has been opened...")
    target = open(keyValPairs["target"], mode="w+")
    print(keyValPairs["target"] + " has been opened...")
    
    if (source == None or target == None):
        print("Source or target could not be opened.")
        return 

    inputString = source.read()

    print("\n--------------------------------------------------------------\n")

    # Get the placement of fields in the dataset
    print("Now please enter the position of the fields in the source data.")
    print("As a reminder, here is the beginning of the sourcefile:")
    print(inputString[0:200])

    posFilepath = int(input("Position of the filepath in the dataset\n"))
    posExperimentee = int(input("Position of the experimentee in the dataset\n"))
    posTime = int(input("Position of the timepoint in the dataset\n"))
    posX = int(input("Position of the x position in the dataset\n"))
    posY = int(input("Position of the y position in the dataset\n"))
    posFixDuration = int(input("Position of the fixation duration in the dataset\n"))

    maxLen = max(posFilepath, posFixDuration, posExperimentee, posTime, posX, posY)

    print("\n--------------------------------------------------------------\n")

    choice = input("Are the datasets seperatet by the same signs as its field? (y/n)")
    if choice == "y" or choice == "Y":
        choice = True
    elif choice == "n" or choice == "N":
        choice = False
    else:
        print("Illegal argument.")
        return

    print("\n--------------------------------------------------------------\n")
    
    # Convert input in output
    res = "["
    if choice == False: # dataset limiter available
        fieldLimiter = input("Please enter the field limiter (enter nl instead of \\n and tab instead of \\t).\n")
        datasetLimiter = input("Please enter the dataset limiter (enter nl instead of \\n and tab instead of \\t).\n")
        # This has to be done for correct interpretation of \n and \t
        if fieldLimiter == "nl":
            fieldLimiter = "\n"
        if fieldLimiter == "tab":
            fieldLimiter = "\t"
        if datasetLimiter == "nl":
            datasetLimiter = "\n"
        if datasetLimiter == "tab":
            datasetLimiter = "\t"

        dataset = inputString.split(datasetLimiter)
        for data in dataset: # Transform dataset in readable string
            d = data.split(fieldLimiter)
            
            if (len(d) > maxLen):
                res += "[\"" + d[posFilepath] + "\", \"" + d[posExperimentee] + "\", \"" + d[posTime]
                res += "\", \"" + d[posX] + "\", \"" + d[posY] + "\", \"" + d[posFixDuration] + "\"]"
                res += ", "

        res = res[:(len(res) - 2)]

    else: # dataset size given
        limiter = input("Please enter a limiter (enter nl instead of \\n and tab instead of \\t).\n")
        datasetSize = int(input("Please enter the number of fields in a dataset."))
        # This has to be done for correct interpretation of \n and \t
        if limiter == "nl":
            limiter = "\n"
        if limiter == "tab":
            limiter = "\t"

        data = inputString.split(limiter)

        if (len(data) % datasetSize):
            raise Exception("Not dividable. data size: " + str(len(data)) + " dataset size: " + str(datasetSize))

        for i in range(0, int(len(data)/datasetSize)):
            n = i*datasetSize
            res += "[\"" + data[n + posFilepath] + "\", \"" + data[n + posExperimentee] + "\", \"" + data[n + posTime]
            res += "\", \"" + data[n + posX] + "\", \"" + data[n + posY] + "\", \"" + data[n + posFixDuration] + "\"]"
            res += ", "
        
        res = res[:(len(res) - 2)]
    res += "]"

    print("\n--------------------------------------------------------------\n")

    target.write(res)
    target.flush()
    
    # Wrap up
    source.close()
    target.close()

    print("Finished.\n")
main()
