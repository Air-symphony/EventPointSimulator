package simulation;

import javafx.scene.control.Label;

/**
 * @author admin
 *@category メッセージをラベルに設定
 */
public class TextPrinter {
	public Label printDay;
	public Label printAction;
	
	/**
	 * @return this.printDay = printDay;
		this.printAction = printAction;
	 */
	TextPrinter(Label printDay, Label printAction){
		this.printDay = printDay;
		this.printAction = printAction;
	}
	
	public void SetText(){
		
	}
	/**
	 * @return printAction.setText("ファイルに保存しました．");
	 */
	public void SaveFile_Success(){
		printAction.setText("ファイルに保存しました．");
	}
	/**
	 * @return printAction.setText("ファイルに保存しませんでした．");
	 */
	public void SaveFile_Cancel(){
		printAction.setText("ファイルに保存しませんでした．");
	}
	/**
	 * @return printAction.setText("ファイルに保存できませんでした．");
	 */
	public void SaveFile_Error(){
		printAction.setText("ファイルに保存できませんでした．");
	}
	/**
	 * @return printAction.setText("ファイルを読み込みました．");
	 */
	public void ReadFile_Success(){
		printAction.setText("ファイルを読み込みました．");
	}
	/**
	 * @return printAction.setText("ファイルを読み込みませんでした．");
	 */
	public void ReadFile_Cancel(){
		printAction.setText("ファイルを読み込みませんでした．");
	}
	/**
	 * @return printAction.setText("ファイルを読み込めませんでした．");
	 */
	public void ReadFile_Error(){
		printAction.setText("ファイルを読み込めませんでした．");
	}
	/**
	 * @param text
	 * @return printAction.setText(text);
	 */
	public void Set_printAction(String text){
		printAction.setText(text);
	}
	/**
	 * @param text
	 * @return printDay.setText(text);
	 */
	public void Set_printDay(String text){
		printDay.setText(text);
	}
}
