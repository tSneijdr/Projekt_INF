# Author: Tobias Meisel, June 2017
# Tested only for Python3.5
# This document is an example of how the various output formats could be parsed in a format readable by the
# Programm submitted for our project inf 2017. For more informations please look at out doc (only german
# version).
# This script however can (and should) be altered to fitt differen formats
import sys

# A class representring a dataset
class Dataset:
    def __init__(self, filepath, experimentee, timepoint, x, y, fixationDuration):
        self.filepath = str(filepath)
        self.experimentee = str(experimentee)
        self.timepoint = int(timepoint)
        self.x = int(x)
        self.y = int(y)
        self.fixationDuration = str(fixationDuration)

    def __str__(self):
        res = "[\"" + self.filepath + "\", \"" + self.experimentee 
        res += "\", \"" + str(self.timepoint) + "\", \""
        res += str(self.x) + "\", \"" + str(self.y) + "\", \"" + str(self.fixationDuration) + "\"]"
        return res

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

    # Read the input string
    inputString = source.read()

    print("\n--------------------------------------------------------------\n")
    # Transforms the inputstring in raw data
    inputString = inputString.replace("\"", "")
    inputString = inputString[2:(len(inputString)-3)]
    datasetsRaw = inputString.split("], [")
    data = []

    for d in datasetsRaw:
        r = d.split(", ")
        x = Dataset(r[0], r[1], r[2], r[3], r[4], r[5])
        print(x)
        data.append(x)
    print(str(len(data)) + " datasets were read.")

    print("\n--------------------------------------------------------------\n")
    # Filter data here
    

    # End Filter here
    print("\n--------------------------------------------------------------\n")
    # Transforms the raw data in an outputstring
    print(str(len(data)) + " datasets will be writen.")
    res = "["

    for d in data:
        res += str(d)
        res += ", "

    res = res[:len(res)-2] # Cut the last two letters (", ")
    res += "]"
    print(res)

    print("\n--------------------------------------------------------------\n")
    # Write output string in file

    target.write(res)
    target.flush()
    
    # Wrap up
    source.close()
    target.close()

    print("Finished.\n")
main()
