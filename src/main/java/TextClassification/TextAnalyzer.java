package TextClassification;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

/**
 * A class for analyzing a text (e.g. counting keywords and to find the lines,
 * where they're)
 *
 * @author Huber
 */

class TextAnalyzer implements Analyzer {

	private JsonArray mJsonArray;

	public int getEmptyParagraphs(String[] keywords) {
		String line;
		int emptyLine = 0;

		try {
			// Create a reader which reads json-files
			InputStream fileInputStream = new FileInputStream(JSON_FILE);
			JsonReader jsonReader = Json.createReader(fileInputStream);
			this.mJsonArray = jsonReader.readArray();

			int emptyParagraphCounter = 0;
			int lineNumber = 0;
			while ((line = ((BufferedReader) this.mJsonArray).readLine()) != null) {
				if (line.equals("")) {
					lineNumber++;
					emptyParagraphCounter++;
					System.out.println("The" + emptyParagraphCounter + "empty paragraph is in line" + lineNumber);
					emptyLine = lineNumber;
				} else {
					lineNumber++;
				}
			}

			// Close jsonReader and fileInputStream
			jsonReader.close();
			fileInputStream.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return emptyLine;
	}

	public int searchKeywordsAndLines(String[] keywords) {
		String fileLine = "";
		String searchedWord = keywords[0];

		int lineNumber = 0;
		int countWord = 0;

		if (keywords.length > 0) {

			try {
				// Create a reader which reads json-files
				InputStream fileInputStream = new FileInputStream(JSON_FILE);
				JsonReader jsonReader = Json.createReader(fileInputStream);
				this.mJsonArray = jsonReader.readArray();

				// Close jsonReader and fileInputStream
				jsonReader.close();
				fileInputStream.close();

				// Search position and how often this words appear in this text
				while ((fileLine = ((BufferedReader) this.mJsonArray).readLine()) != null) {
					lineNumber++;
					int position = fileLine.indexOf(searchedWord);

					if (position > -1) {
						countWord++;
						System.out.println("The word is at " + position + ", line " + lineNumber);
					}
				}

				// Close BufferedReader
				((BufferedReader) this.mJsonArray).close();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		} else {
			System.out.println("Please enter a word.");
		}

		return countWord;
	}

}
