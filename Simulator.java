package simulation;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class Simulator {
	public static Label printDay;
	public static ListView<String> timeList;
	public static ListView<String> detailList;
	public static ListView<String> alignList;
	public static String[] alignText = { "日目 ", "時帯 ", "s" };
	public static TextField[] alignField = new TextField[3];
	public static Button alignButton[] = new Button[3];
	public static Button mainButton[] = new Button[2];
	public static Label printAction;
	
	// private String tab[] = { "Name", "ID", "Gender", "Entrance Year", "Hobby"
	// };
	//private Stage dayStage;
	private ComboBox<String>[] daydata = new ComboBox[4];
	private TextField[] statusField = new TextField[3];
}
