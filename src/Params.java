public class Params {
	public static boolean charParam = false, wordParam = false, lineParam = false, detailParam = false, exceptWordListParam = false;
	public static String inputFile = "file.c", outputFile = "outputFile.txt", exceptWordListFile = "";
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
					this.inputFile = args[i];
					i = i + 1;
					break;
			}
        }
	}
}