import sys
import os
import fnmatch
import operator
import json

directory = sys.argv[1]
directory = "C:/Users/TS/Uni/5. Semester/Projekt INF/test"
directory = "C:/Users/TS/Uni/5. Semester/Projekt INF/EyeTracking Daten"

if not os.path.isdir(directory):
	raise NotADirectoryError("Directory does not exist on this filesystem")

# all_data has the form [directory+filename / proband / timestamp / x / y / fixation duration] per row
all_data = []

# get all tsv files in directory
for root, dirs, files in os.walk(directory):
	for file in files:
		if fnmatch.fnmatch(file, '*.tsv') and "Proband" in file:
			with open(root+"/"+file, 'r') as f:
				for line in f:
					# remove last element, usually \n
					splitted_line = line.split('\t')[:-1]
					# add directory and file name at the front of the list, second item is proband (file.tsv) and then remove first elem in splitted_line
					#splitted_line = [(root.replace(directory, "")+"/"+splitted_line[0]).replace("\\", "/")] + [file] + splitted_line[1:]

					# absoluter pfad
					splitted_line = [(root+"/"+splitted_line[0]).replace("\\", "/")] + [file] + splitted_line[1:]

					all_data.append(splitted_line)

# sort the array in the following way:
# directory+filename / proband / timestamp
all_data = sorted(all_data, key=lambda x: (x[0], x[1], int(x[2])))

with open('all_data.txt','w') as file:
    json.dump(all_data, file)
