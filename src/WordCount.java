import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.ArrayList;

public class WordCount {
    public static void main(String[] args) {
		Params params = new Params(args); //Get params
		String[] exceptWordList = getExceptWordList(params.exceptWordListParam, params.exceptWordListFile);
		ArrayList<String> countResult = count(params.inputFile, exceptWordList); //Count and get result
		output(params.outputFile, countResult, params); //Output result to file
    }
	public static String[] getExceptWordList(boolean exceptWordListParam, String exceptWordListFile) {
		String[] exceptWordList = {};
		if (exceptWordListParam) {
			try {
				File file = new File(exceptWordListFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String s = reader.readLine();
				exceptWordList = s.split(" ");
			} catch(IOException e) {
				System.out.println("Except Word List Filepath Error!");
				System.exit(0);
			}
		}
		return exceptWordList;
	}
	public static ArrayList<String> count(ArrayList<String> filepath, String[] exceptWordList) {
		ArrayList<String> countResult = new ArrayList<String>(); // Result init
		for (int i = 0; i < filepath.size(); i++) {
			String filepath0 = filepath.get(i);
			Integer charCount = 0, wordCount = 0, lineCount = 0, codeLineCount = 0, blankLineCount = 0, noteLineCount = 0; // Count init
			try {
				File file = new File(filepath0);
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
						switch (getLineType(line)) {
							case 0: blankLineCount = blankLineCount + 1;break;
							case 1: codeLineCount = codeLineCount + 1;break;
							case 2: noteLineCount = noteLineCount + 1;break;
						}
						line = "";
						if (word.length() > 0) {
							if (isNotExceptWord(word, exceptWordList))
								wordCount = wordCount + 1;
							word = "";
						}
					} else {
						line = line + c;
						if ((c == ' ') || (c == ',') || (c == '\t')) {
							if (word.length() > 0) {
								if (isNotExceptWord(word, exceptWordList))
									wordCount = wordCount + 1;
								word = "";
							}
						} else {
							word = word + c;
						}
					}
				}
				if (word.length() > 0) {
					boolean flag = true;
					if (isNotExceptWord(word, exceptWordList))
						wordCount = wordCount + 1;
					word = "";
				}
				if (line.length() > 0) {
					lineCount = lineCount + 1;
					switch (getLineType(line)) {
						case 0: blankLineCount = blankLineCount + 1;break;
						case 1: codeLineCount = codeLineCount + 1;break;
						case 2: noteLineCount = noteLineCount + 1;break;
					}
					line = "";
				}
			} catch (IOException e) {
				System.out.println("Input Filepath Error!");
				System.exit(0);
			}
			countResult.add(filepath0 + ", 字符数" + charCount);
			countResult.add(filepath0 + ", 单词数" + wordCount);
			countResult.add(filepath0 + ", 行数" + lineCount);
			countResult.add(filepath0 + ", 代码行/空行/注释行: " + codeLineCount + '/' + blankLineCount + '/' + noteLineCount);
		}
		// System.out.printf("%d,%d,%d,%d,%d,%d\n", charCount, wordCount, lineCount, codeLineCount, blankLineCount, noteLineCount);
		// Result
		return countResult;
	}
	public static boolean isNotExceptWord(String word, String[] exceptWordList) {
		for (int i = 0; i < exceptWordList.length; i++) {
			if (exceptWordList[i].equals(word))
				return false;
		}
		return true;
	}
	public static int getLineType(String line) {
		if ((line.indexOf("//")) > -1) {
			String s = line.substring(0, line.indexOf("//")).replaceAll(" ", "").replaceAll("\t", "");
			if (s.length() > 1)
				return 1;
			else
				return 2;
		} else {
			String s = line.replaceAll(" ","").replaceAll("\t","");
			if (s.length() > 1)
				return 1;
			else
				return 0;
		}
	}
	public static void output(String filepath, ArrayList<String> countResult, Params params) {
		try {
			File file = new File(filepath);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			for (int i = 0; i < countResult.size(); i++) {
				switch (i % 4) {
					case 0: if (params.charParam) writer.write(countResult.get(i) + '\n'); break;
					case 1: if (params.wordParam) writer.write(countResult.get(i) + '\n'); break;
					case 2: if (params.lineParam) writer.write(countResult.get(i) + '\n'); break;
					case 3: if (params.detailParam) writer.write(countResult.get(i) + '\n'); break;
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