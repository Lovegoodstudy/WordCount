import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class WordCount {
    public static void main(String[] args) {
		Params params = new Params(args); //Get params
		// Testing String[] exceptWordList = getExceptWordList(params.exceptWordListParam, params.exceptWordListFile);
		String[] countResult = count(params.inputFile); //Count and get result
		output(params.outputFile, countResult, params); //Output result to file
    }
	/*
	public static String[] getExceptWordList(boolean exceptWordListParam, String exceptWordListFile) {
		String[] exceptWordList = {};
		if (exceptWordListParam) {
			try {
				File file = new File(exceptWordListFile);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String s = reader.readLine();
				exceptWordList = s.split(" ");
			} catch(IOException e) {
				System.out.println("Except Word List Filepath Error!");
				System.exit(0);
			}
		}
		return exceptWordList;
	}
	*/
	public static String[] count(String filepath) {
		String[] countResult = {"", "", ""}; // Result init
		Integer charCount = 0, wordCount = 0, lineCount = 0, codeLineCount = 0, blankLineCount = 0, noteLineCount = 0; // Count init
		try {
			File file = new File(filepath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int tempChar = 0;
			char c;
			String word = "", line = ""; //Line and word string init
			while ((tempChar = reader.read()) != -1) {
                c = (char)tempChar;
				if (c == '\r')
					continue;
				charCount = charCount + 1;
				if (c == '\n') {
					lineCount = lineCount + 1;
					line = "";
					if (word.length() > 0) {
						wordCount = wordCount + 1;
						word = "";
					}
				} else {
					line = line + c;
					if ((c == ' ') || (c == ',') || (c == '\t')) {
						if (word.length() > 0) {
							wordCount = wordCount + 1;
							word = "";
						}
					} else {
						word = word + c;
					}
				}
            }
			if (word.length() > 0) {
				wordCount = wordCount + 1;
				word = "";
			}
			if (line.length() > 0) {
				lineCount = lineCount + 1;
				line = "";
			}
		} catch (IOException e) {
			System.out.println("Input Filepath Error!");
			System.exit(0);
		}
		// System.out.printf("%d,%d,%d,%d,%d,%d\n", charCount, wordCount, lineCount, codeLineCount, blankLineCount, noteLineCount);
		// Result
		countResult[0] = filepath + ", 字符数" + charCount;
		countResult[1] = filepath + ", 单词数" + wordCount;
		countResult[2] = filepath + ", 行数" + lineCount;
		return countResult;
	}
	public static void output(String filepath, String[] countResult, Params params) {
		try {
			File file = new File(filepath);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			for (int i = 0; i < countResult.length; i++) {
				switch (i % 3) {
					case 0: if (params.charParam) writer.write(countResult[i] + '\n'); break;
					case 1: if (params.wordParam) writer.write(countResult[i] + '\n'); break;
					case 2: if (params.lineParam) writer.write(countResult[i] + '\n'); break;
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Write File Error!");
			System.exit(0);
		}
	}
}