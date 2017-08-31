package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class FileController {
	private TextPrinter textPrinter;
	private String[] alignText = { "日目 ", "時帯 ", "s" };
	private ListView<String> alignList;
	private ComboBox<String> gametype;
	private ComboBox<String>[] daydata;// = new ComboBox[4];
	private TextField[] statusField = new TextField[3];

	private String currentFilename = ""; // 現在のファイル名
	private String config = "D:\\Eclipse\\4.4.x\\Workspace\\j4\\simulator\\Config.txt";
	private String filetype = ".txt";

	public FileController(Label printDay, ListView<String> alignList,
			Label printAction, ComboBox<String> gametype, ComboBox<String>[] daydata,
			TextField[] statusField) {
		this.alignList = alignList;
		this.gametype = gametype;
		this.daydata = daydata;
		this.statusField = statusField;
		this.textPrinter = new TextPrinter(printDay, printAction);
		SetConfig();
	}

	boolean OpenFile() {
		String filename = null;
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Resource File");
		chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Saveファイル", "*" + filetype));
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("すべてのファイル", "*.*"));
		if (currentFilename != null) {
			chooser.setInitialFileName(currentFilename);
			// chooser.setSelectedFile(new File(currentFilename));
		}
		File result = chooser.showOpenDialog(null);
		if (result == null){
			textPrinter.ReadFile_Cancel();
			return false;
		}
		filename = result.getPath().toString();
		if (!filename.endsWith(filetype)) {
			filename += filetype;
		}
		currentFilename = filename;
		if (filename != null) {
			File file = new File(filename);
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
				String[] data = scanner.nextLine().split(",", 0);//cinderella,2016,8,1,8
				gametype.setValue(data[0]);
				for (int i = 1; i < data.length; i++) {
					daydata[i - 1].setValue(data[i]);
				}
				data = scanner.nextLine().split(",", 0);//stamina,level,exp
				for (int i = 0; i < data.length; i++) {
					statusField[i].setText(data[i]);
				}
				ObservableList<String> items = FXCollections
						.observableArrayList();
				while (scanner.hasNextLine()) {
					String[] day = scanner.nextLine().split(",", 0);
					String text = "";
					for (int i = 0; i < day.length; i++) {
						text += day[i] + alignText[i];
					}
					items.add(text);
				}
				alignList.setItems(items);
				String[] day = { daydata[0].getValue(), daydata[1].getValue(),
						daydata[2].getValue(), daydata[3].getValue() };
				textPrinter.DayPrint(gametype.getValue(), day);
				textPrinter.ReadFile_Success();
				//printAction.setText("ファイルを読み込みました．");
			} catch (FileNotFoundException ex) {
				textPrinter.ReadFile_Error();
				//printAction.setText("ファイルを読み込めませんでした．");
			} finally {
				if (scanner != null) {
					scanner.close();
				}
			}
		}
		return true;
	}

	void UpdateFile() {
		if (currentFilename != null) {
			File file = new File(currentFilename);
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(file);
				writer.print(gametype.getValue() + ",");
				for (int i = 0; i < daydata.length; i++) {
					writer.print(daydata[i].getValue());
					if (i < daydata.length - 1) {
						writer.print(",");
					}
				}
				writer.println();
				for (int i = 0; i < statusField.length; i++) {
					writer.print(statusField[i].getText());
					if (i < statusField.length - 1) {
						writer.print(",");
					}
				}
				writer.println();
				ObservableList<String> items = alignList.getItems();
				for(int j = 0; j < items.size();j++){
					String item = items.get(j);
					for (int i = 0; i < alignText.length; i++) {
						if (i < alignText.length - 1) {
							item = item.replace(alignText[i], ",");
						} else {
							item = item.replace(alignText[i], "");
						}
					}
					writer.println(item);
				}
				textPrinter.SaveFile_Success();
			} catch (FileNotFoundException ex) {
				textPrinter.SaveFile_Error();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
		else{
			textPrinter.SaveFile_Error();
		}
	}

	boolean MakeFile() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Resource File");
		chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Saveファイル", "*" + filetype));
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("すべてのファイル", "*.*"));

		if (currentFilename != null) {
			chooser.setInitialFileName(new File(currentFilename).getName());
			// chooser.setSelectedFile(new File(currentFilename));
		}
		File result = chooser.showSaveDialog(null);

		if (result != null) {
			textPrinter.SaveFile_Success();
			
			textPrinter.Set_printAction(result.getPath().toString());
			
			currentFilename = result.getPath().toString();
			if (!currentFilename.endsWith(filetype)) {
				currentFilename += filetype;
			}
		}
		else {
			return false;
		}
		UpdateFile();
		return true;
	}

	void SetConfig() {
		File file = new File(config);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			currentFilename = scanner.nextLine();
			textPrinter.ReadFile_Success();
		} catch (FileNotFoundException ex) {
			textPrinter.ReadFile_Error();
			System.out.println(ex);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		file = new File(currentFilename);
		scanner = null;
		try {
			scanner = new Scanner(file);
			String[] data = scanner.nextLine().split(",", 0);
			gametype.setValue(data[0]);
			String[] day = new String[4];
			for (int i = 1; i < data.length; i++) {
				day[i - 1] = data[i];
				daydata[i - 1].setValue(data[i]);
			}
			textPrinter.DayPrint(data[0], day);
			
			data = scanner.nextLine().split(",", 0);
			for (int i = 0; i < data.length; i++) {
				statusField[i].setText(data[i]);
			}
			ObservableList<String> items = FXCollections.observableArrayList();
			while (scanner.hasNextLine()) {
				data = scanner.nextLine().split(",", 0);
				String text = "";
				for (int i = 0; i < data.length; i++) {
					text += (data[i] + alignText[i]);
				}
				items.add(text);
			}
			alignList.setItems(items);
			textPrinter.ReadFile_Success();
		} catch (FileNotFoundException ex) {
			textPrinter.ReadFile_Error();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	void UpdateConfig() {
		File file = new File(config);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.print(currentFilename);
		} catch (FileNotFoundException ex) {
			textPrinter.SaveFile_Success();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public ObservableList<int[]> getAlign(){
		ObservableList<int[]> items = FXCollections.observableArrayList();
		File file = new File(currentFilename);
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(file);
			scanner.nextLine();scanner.nextLine();//日付など
			while(scanner.hasNext()){
				int[] data = new int[3];
				String[] d = scanner.nextLine().split(",", 0);
				for(int i = 0; i< d.length;i++){
					data[i] = Integer.parseInt(d[i]);
				}
				items.add(data);
			}
		} catch (FileNotFoundException ex) {
			textPrinter.ReadFile_Error();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return items;
	}
}
