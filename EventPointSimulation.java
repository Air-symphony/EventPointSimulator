package simulation;

import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventPointSimulation extends Application {
	public Calculator calculator;
	public FileController fileController;
	private ChangeDataStage changeDataStage;
	private TextPrinter textPrinter;
	private Stage stage = new Stage();
	
	private MenuBar mb;
	private Menu[] mn = new Menu[2];
	private MenuItem[] mi = new MenuItem[3];
	public Label printDay = new Label();
	private ListView<String> timeList;
	private ListView<String> detailList;
	private ListView<String> alignList;
	private TextField[] alignField = new TextField[3];
	private Button alignButton[] = new Button[3];
	private Button mainButton[] = new Button[2];
	private ComboBox<String> gametype;
	private ComboBox<String>[] daydata = new ComboBox[4];
	private TextField[] statusField = new TextField[3];
	public Label printAction = new Label();
	private String[] alignText;
	
	private TextField addcalField[] = new TextField[2];
	private Button addcalButton = new Button();
	private Label printAddcal = new Label("------");
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage s) throws Exception {
		BorderPane bp = new BorderPane();
		VBox vb;
		HBox hb;

		mb = new MenuBar();
		mn[0] = new Menu("Config");
		mn[1] = new Menu("Main2");
		mi[0] = new MenuItem("Change Data");
		mi[1] = new MenuItem("Read Data");
		mi[2] = new SeparatorMenuItem();

		for (int i = 0; i < mi.length; i++) {
			mi[i].setOnAction(new MenuEventHandler());
		}
		mn[0].getItems().addAll(mi[0], mi[1]);
		mb.getMenus().addAll(mn[0], mn[1]);

		changeDataStage = new ChangeDataStage();
		daydata = changeDataStage.getDaydata();
		statusField = changeDataStage.getstatusField();
		//CreateMenuStage();
		
		textPrinter = new TextPrinter(printDay, printAction);
		alignText = textPrinter.getAlignText();
		
		HBox main = new HBox(10);
		//main.setAlignment(Pos.CENTER);
		hb = new HBox(5);
		vb = new VBox(5);
		vb.setAlignment(Pos.CENTER);

		timeList = new ListView<String>();
		ObservableList<String> oll = FXCollections.observableArrayList();
		oll.add("time schedule");
		timeList.setItems(oll);
		timeList.getSelectionModel().selectedItemProperty()
				.addListener(new TimeListListener());
		vb.getChildren().addAll(printDay, timeList);
		hb.getChildren().add(vb);

		vb = new VBox(5);
		oll = FXCollections.observableArrayList();
		detailList = new ListView<String>();
		oll = FXCollections.observableArrayList("Time:", "Point", "Item",
				"stamina", "Normal", "Event");
		detailList.setItems(oll);

		alignList = new ListView<String>();
		oll = FXCollections.observableArrayList("day" + alignText[0] + "Time"
				+ alignText[1] + "00" + alignText[2]);
		alignList.setItems(oll);

		vb.getChildren().addAll(detailList, alignList);
		hb.getChildren().add(vb);
		main.getChildren().add(hb);
		// --------------
		//------------------------------
		vb = new VBox(10);
		hb = new HBox(5);
		hb.setAlignment(Pos.CENTER);
		addcalField[0] = new TextField("0");
		addcalField[0].setMaxWidth(90);
		addcalField[1] = new TextField("0");
		addcalField[1].setMaxWidth(90);
		addcalButton = new Button("test");
		addcalButton.setOnAction(new addcalButtonHandler());
		HBox[] hbs = new HBox[2];
		hbs[0] = new HBox(5);
		hbs[0].setAlignment(Pos.CENTER_LEFT);
		hbs[0].getChildren().addAll(addcalField[0],new Label("pt"));
		hbs[1] = new HBox(5);
		hbs[1].setAlignment(Pos.CENTER_LEFT);
		hbs[1].getChildren().addAll(addcalField[1],new Label("回"));
		vb.getChildren().addAll(hbs[0], hbs[1]);
		hb.getChildren().addAll(new Label("NowPoints:"),vb ,addcalButton);
		vb = new VBox(10);
		vb.getChildren().addAll(hb);
		hb = new HBox(5);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(printAddcal);
		vb.getChildren().addAll(hb);
		hb = new HBox(5);
		hb.setAlignment(Pos.CENTER);
		// String[] text2 = { "日目", "時帯", "" };
		for (int i = 0; i < 3; i++) {
			HBox hbox = new HBox(0);
			hbox.setAlignment(Pos.CENTER);
			alignField[i] = new TextField();
			alignField[i].setMaxWidth(70);
			hbox.getChildren().addAll(alignField[i],new Label(alignText[i]));
			hb.getChildren().add(hbox);
		}
		vb.getChildren().addAll(hb);
		hb = new HBox(5);
		hb.setAlignment(Pos.CENTER);
		String[] text3 = { "Update", "Clear" , "Delete"};
		for (int i = 0; i < alignButton.length; i++) {
			alignButton[i] = new Button(text3[i]);
			hb.getChildren().add(alignButton[i]);
			alignButton[i].setOnAction(new AlignButtonHandler());
		}
		vb.getChildren().addAll(hb);
		hb = new HBox(5);
		hb.setAlignment(Pos.CENTER);
		String[] text4 = { "Calculate", "Finish" };
		for (int i = 0; i < mainButton.length; i++) {
			mainButton[i] = new Button(text4[i]);
			hb.getChildren().add(mainButton[i]);
			mainButton[i].setOnAction(new mainHandler());
		}
		vb.getChildren().addAll(hb, printAction);
		main.getChildren().add(vb);

		bp.setTop(mb);
		bp.setCenter(main);

		Scene sc = new Scene(bp, 900, 500);
		stage.setScene(sc);
		stage.setTitle("アタポン型イベントポイント計算機");
		stage.show();
		
		calculator = new Calculator(timeList);
		fileController = new FileController(printDay, alignList, 
				printAction, gametype, daydata, statusField);
	}

	class TimeListListener implements ChangeListener<String> {
		public void changed(ObservableValue ob, String bs, String as) {
			int id = timeList.getSelectionModel().getSelectedIndex();
			
			textPrinter.Set_printAction("time_id : " + id);
			
			if (id >= 0 && calculator.Getmemory() != null){
				Data data = calculator.Getmemory().get(id);
				ObservableList<String> ol = FXCollections.observableArrayList(
						data.getDetailText());
				detailList.setItems(ol);
			}
		}
	}

	class MenuEventHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			MenuItem tmp = (MenuItem) e.getSource();
			if (tmp.equals(mi[0])) {// 日付変更
				changeDataStage.Show();
			} else if (tmp.equals(mi[1])) {// File読み込み
				if(fileController.OpenFile())
					calculator.Start(gametype.getValue(), daydata, statusField);
			}
		}
	}

	class AlignButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Button tmp = (Button) e.getSource();
			if (tmp.equals(alignButton[0])) {// OK
				Pattern pass = Pattern.compile("[0-9]*|-[0-9]*");
				boolean update = true;
				for (int i = 0; i < alignField.length; i++) {
					String str = alignField[i].getText();
					if (str.equals("")) {
						update = false;
						continue;
					} 
					int[] limit = { 0, 24 };
					if (i == 0) {
						limit[1] = Integer.parseInt(daydata[3].getValue());
					} else if (i == 1) {
						if(alignField[0].getText().equals("1")){
							limit[0] = 15;
						}
						else if(alignField[0].getText().equals(daydata[3].getValue())){
							limit[1] = 21;
						}
					} else if (i == 2) {
						limit[0] = -255;
						limit[1] = 255;
					}
					if (!pass.matcher(str).matches()) {
						alignField[i].setText("");
						update = false;
					} else if (limit[0] > Integer.parseInt(str)
							|| Integer.parseInt(str) > limit[1] 
							|| 0 == Integer.parseInt(str) ) {
						alignField[i].setText("");
						update = false;
					}
				}
				if (!update){
					textPrinter.Input_Error();
					return;
				}
				//既存の修正内容の順番の数値化
				String[] day = { alignField[0].getText(),
						alignField[1].getText(), alignField[2].getText() };
				ObservableList<String> items = alignList.getItems();
				int id = items.size();
				int[] sort = new int[id];
				for (int i = 0; i < id; i++){
					String item = items.get(i);
					for (int j = 0; j < alignText.length; j++) {
						if (j < alignText.length - 1) {
							item = item.replace(alignText[j], ",");
						} else {
							item = item.replace(alignText[j], "");
						}
					}
					String[] str = item.split(",", 0);
					sort[i] = ( (Integer.parseInt(str[0]) - 1) * 24 + Integer.parseInt(str[1]));
				}
				//修正内容の追加
				boolean finish_add = false;
				int insert = (Integer.parseInt(day[0]) - 1) * 24 + Integer.parseInt(day[1]);
				for(int i = 0; i < sort.length;i++){
					if (insert < sort[i]){
						items.add(i, day[0] + alignText[0] + day[1] + alignText[1] + day[2] + alignText[2]);
						finish_add = true;
					}
				}
				//最後に追加
				if (!finish_add)
					items.add(day[0] + alignText[0] + day[1] + alignText[1] + day[2] + alignText[2]);
				
				fileController.UpdateFile();
				textPrinter.Align_Success();
				for (int i = 0; i < alignField.length; i++) {
					alignField[i].setText("");
				}
			}
			else if (tmp.equals(alignButton[1])) {//clear
				for (int i = 0; i < alignField.length; i++) {
					alignField[i].setText("");
				}
			}
			else if (tmp.equals(alignButton[2])) {// Delete
				int id = alignList.getSelectionModel().getSelectedIndex();
				textPrinter.Set_printAction("align_id : " + id);
				if (id < 0){
					textPrinter.Select_Error();
				}
				else{
					ObservableList<String> items = alignList.getItems();
					items.remove(id);
					fileController.UpdateFile();
					textPrinter.Align_Success();
				}
			}
			calculator.SetAlign(fileController.getAlign());
		}
	}

	class mainHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Button tmp = (Button) e.getSource();
			if (tmp.equals(mainButton[0])) { // Calculator
				calculator.SetAlign(fileController.getAlign());
				calculator.Start(gametype.getValue(), daydata, statusField);
				textPrinter.Calculate_Success();
			} else if (tmp.equals(mainButton[1])) {// Finish
				fileController.UpdateConfig();
				changeDataStage.Close();
				stage.close();
			}
		}
	}
	
	class addcalButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Button tmp = (Button) e.getSource();
			if (tmp.equals(addcalButton)) {// OK
				Pattern pass = Pattern.compile("[0-9]*|-[0-9]*");
				boolean check = true;
				for(int i = 0; i< addcalField.length; i ++){
					String str = addcalField[i].getText();
					if (!pass.matcher(str).matches()) {
						addcalField[i].setText("");
						check = false;
					}
				}
				if(check){
					int[] pt = {Integer.parseInt(addcalField[0].getText()),
							Integer.parseInt(addcalField[1].getText())};
					String count = calculator.addcalSimulater(gametype.getValue(), pt);
					printAddcal.setText(count);
					textPrinter.Calculate_Success();
				}else {
					textPrinter.Input_Error();
				}
			}
		}
	}
	
	public class ChangeDataStage{
		private Stage dayStage;
		private Button bt[] = new Button[3];
		
		public ChangeDataStage() {
			VBox vb;
			HBox hb;
			
			dayStage = new Stage();
			dayStage.setTitle("Config");
			dayStage.initModality(Modality.APPLICATION_MODAL);
			vb = new VBox(20);
			vb.setAlignment(Pos.CENTER);
			hb = new HBox(10);
			hb.setAlignment(Pos.CENTER);
			ObservableList<String> type = FXCollections.observableArrayList();
			type.addAll(new GameType().getList());
			gametype =  new ComboBox<String>();
			gametype.setItems(type);
			gametype.setValue(type.get(0));
			hb.getChildren().addAll(new Label("GameType:"), gametype);
			vb.getChildren().add(hb);
			hb = new HBox(10);
			hb.setAlignment(Pos.CENTER);
			String[] name = { "年", "月", "日", "日間" };
			for (int i = 0; i < daydata.length; i++) {
				int[] item = { 2015, 2025 };
				daydata[i] = new ComboBox<String>();
				// daydata[i].setStyle("-fx-font-size: " + fontsize + "px;");
				if (i == 1) {
					item[0] = 1;
					item[1] = 12;
				} else if (i == 2) {
					item[0] = 1;
					item[1] = 31;
				} else if (i == 3) {
					item[0] = 7;
					item[1] = 11;
				}
				ObservableList<String> ol = FXCollections.observableArrayList();
				for (int j = item[0]; j <= item[1]; j++)
					ol.add(j + "");
				daydata[i].setItems(ol);
				daydata[i % 2].setOnAction(new ComboHandler());
				daydata[i].setValue(item[0] + "");
				HBox hbox = new HBox(0);
				hbox.setAlignment(Pos.CENTER);
				hbox.getChildren().addAll(daydata[i], new Label(name[i]));
				hb.getChildren().add(hbox);
			}
			vb.getChildren().add(hb);

			hb = new HBox(20);
			hb.setAlignment(Pos.CENTER);
			String[] text = { "Rank:", "stamina:", "exp:" };
			for (int i = 0; i < statusField.length; i++) {
				HBox hbox = new HBox(0);
				hbox.setAlignment(Pos.CENTER);
				statusField[i] = new TextField();
				statusField[i].setMaxWidth(100);
				hbox.getChildren().addAll(new Label(text[i]), statusField[i]);
				hb.getChildren().add(hbox);
			}
			vb.getChildren().add(hb);

			hb = new HBox(40);
			hb.setAlignment(Pos.CENTER);
			String[] OKtext = { "Remaking", "New making", "Cancel" };
			for (int i = 0; i < bt.length; i++) {
				bt[i] = new Button(OKtext[i]);
				hb.getChildren().add(bt[i]);
				bt[i].setOnAction(new DecideHandler());
			}
			vb.getChildren().add(hb);
			dayStage.setScene(new Scene(vb));// ,400, 150));
		}
		
		public void Show(){
			dayStage.show();
		}
		public void Close(){
			dayStage.close();
		}
		public String[] getDaydataToString(){
			String[] day = { daydata[0].getValue(), daydata[1].getValue(),
					daydata[2].getValue(), daydata[3].getValue() };
			return day;
		}
		public ComboBox<String>[] getDaydata(){
			return daydata;
		}		
		public TextField[] getstatusField(){
			return statusField;
		}
		
		class ComboHandler implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
				String[] day = { daydata[0].getValue(), daydata[1].getValue(),
						daydata[2].getValue() };
				int d = 31;
				if (day[1].equals("4") || day[1].equals("6") || day[1].equals("9")
						|| day[1].equals("11")) {
					d = 30;
				} else if (day[1].equals("2")) {
					d = 28;
					int year = Integer.parseInt(day[0]);
					if ((year % 4) == 0) {// && (year % 400) == 0){
						d = 29;
					}
				}
				ObservableList<String> ol = FXCollections.observableArrayList();
				for (int i = 1; i <= d; i++)
					ol.add("" + i);
				daydata[2].setItems(ol);
				if (Integer.parseInt(day[2]) > d) {
					daydata[2].setValue("1");
				}
			}
		}
		
		class DecideHandler implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {
				//年、月、日、間
				int[] day = { Integer.parseInt(daydata[0].getValue()),
						Integer.parseInt(daydata[1].getValue()),
						Integer.parseInt(daydata[2].getValue()),
						Integer.parseInt(daydata[3].getValue()) };
				Button tmp = (Button) e.getSource();
				if (tmp.equals(bt[0])) {// Remaking
					fileController.UpdateFile();
					textPrinter.DayPrint(gametype.getValue(), day);
					calculator.Start(gametype.getValue(), daydata, statusField);
					textPrinter.UpdateConfig_Success();
				} else if (tmp.equals(bt[1])) {// new file
					if(fileController.MakeFile()){
						textPrinter.DayPrint(gametype.getValue(), day);
						calculator.Start(gametype.getValue(), daydata, statusField);
						textPrinter.SaveConfig_Success();
					}
					else{
						textPrinter.SaveConfig_Cancel();
					}
				} else if (tmp.equals(bt[2])) {// Cancel
					textPrinter.SaveConfig_Cancel();
				}
				dayStage.close();
			}
		}
	}
}