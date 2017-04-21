package controller.points;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.datastructures.Sixtupel;

public class DataReader {

	/**
	 * read in the eye tracking data from textfile which was created by python
	 * script data is returned as an arraylist consisting of a 6-tuple 0:
	 * filepath 1: proband 2: timestamp 3: x 4: y 5: fixation duration
	 * 
	 * @param filepath
	 * @return
	 */
	public static ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>> readInFile(String filepath) {
		ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>> dataList = new ArrayList<Sixtupel<String, String, Integer, Integer, Integer, Integer>>();

		try {
			Scanner scanner = new Scanner(new File(filepath));
			scanner.useDelimiter(Pattern.compile("]"));

			// get next String until "]" appears (end of the array)
			while (scanner.hasNext()) {
				String line = scanner.next();
				line = line.replace("[", "").replace("]", "");

				// all values are stored in between ""
				Pattern p = Pattern.compile("\"(.+?)\"");
				Matcher m = p.matcher(line);

				String imagePath = "";
				String proband = "";
				int timestamp = 0;
				int x = 0;
				int y = 0;
				int fixationDuration = 0;

				int index = 0;

				while (m.find()) {

					// System.out.println(m.group(1));

					switch (index) {
					case 0:
						imagePath = m.group(1);
						break;
					case 1:
						proband = m.group(1);
						break;
					case 2:
						timestamp = Integer.valueOf(m.group(1));
						break;
					case 3:
						x = Integer.valueOf(m.group(1));
						break;
					case 4:
						y = Integer.valueOf(m.group(1));
						break;
					case 5:
						fixationDuration = Integer.valueOf(m.group(1));
						break;
					}

					index++;
				}

				dataList.add(new Sixtupel<String, String, Integer, Integer, Integer, Integer>(imagePath, proband,
						timestamp, x, y, fixationDuration));

			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		return dataList;
	}
}