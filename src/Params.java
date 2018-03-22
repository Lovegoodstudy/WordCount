import java.util.ArrayList;

import java.awt.Frame;
import java.awt.FileDialog;

public class Params {
	public static boolean charParam = false, wordParam = false, lineParam = false, detailParam = false, exceptWordListParam = false, recursionParam = false;
	public static String outputFile = "outputFile.txt", exceptWordListFile = "";
	public static ArrayList<String> inputFile = new ArrayList<String>();;
	Params(String[] args) {
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
				case "-c":
					this.charParam = true;
					break;
				case "-w":
					this.wordParam = true;
					break;
				case "-l":
					this.lineParam = true;
					break;
				case "-a":
					this.detailParam = true;
					break;
				case "-s":
					this.recursionParam = true;
					break;
				case "-x":
					this.charParam = true;
					this.wordParam = true;
					this.lineParam = true;
					this.detailParam = true;
					Frame frame = new Frame();
					FileDialog openFile = new FileDialog(frame, "打开文件", FileDialog.LOAD);
					openFile.setVisible(true);
					String dirName = openFile.getDirectory();
					String fileName = openFile.getFile();
					System.out.println(dirName + fileName);
					this.inputFile.add(dirName + fileName);
					frame.dispose();
					break;
				case "-o":
					this.outputFile = args[i + 1];
					i = i + 1;
					break;
				case "-e":
					this.exceptWordListParam = true;
					this.exceptWordListFile = args[i + 1];
					i = i + 1;
					break;
				default:
					this.inputFile.add(args[i]);
					break;
			}
        }
	}
}