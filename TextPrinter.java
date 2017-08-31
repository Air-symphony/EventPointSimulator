package simulation;

import java.time.LocalDate;

import javafx.scene.control.Label;

/**
 * @author admin
 *@category メッセージをラベルに設定
 */
public class TextPrinter {
	public Label printDay;
	public Label printAction;
	
	private String[] alignText = { "日目 ", "時帯 ", "s" };
	
	/**
	 * @return this.printDay = printDay;
		this.printAction = printAction;
	 */
	TextPrinter(Label printDay, Label printAction){
		this.printDay = printDay;
		this.printAction = printAction;
	}
	/**
	 * @param gametype
	 * @param day //年、月、日、間
	 * @return
	 */
	public void DayPrint(String gametype, int[] day){
		int finish_year = day[0];//終了年
		int finish_month = day[1];//終了月
		int finish_day = day[2] + day[3] - 1;//終了日
		
		LocalDate ld = LocalDate.of(day[0], day[1], 1);
		int dayMax = ld.lengthOfMonth();
		if (finish_day  > dayMax){
			finish_day  -= dayMax;
			finish_month += 1;
			if (finish_month == 13){
				finish_year += 1;
				finish_month = 1;
			}
		}
		printDay.setText("TYPE : " + gametype + "\n" + day[0] + "/" + day[1] + "/" + day[2] + " ~ "
				+ finish_year + "/" + finish_month + "/" + finish_day);
	}
	/**
	 * @return printAction.setText("計算を行いました。");
	 */
	public void Calculate_Success(){
		printAction.setText("計算を行いました。");
	}
	/**
	 * @return printAction.setText("修正しました。");
	 */
	public void Align_Success(){
		printAction.setText("修正しました。");
	}
	/**
	 * @return printAction.setText("入力に不備があります。");
	 */
	public void Input_Error(){
		printAction.setText("入力に不備があります。");
	}
	/**
	 * @return printAction.setText("選択してください。");
	 */
	public void Select_Error(){
		printAction.setText("選択してください。");
	}
	/**
	 * @return printAction.setText("設定を保存しました．");
	 */
	public void SaveConfig_Success(){
		printAction.setText("設定を保存しました．");
	}
	/**
	 * @return printAction.setText("設定を保存しませんでした．");
	 */
	public void SaveConfig_Cancel(){
		printAction.setText("設定を保存しませんでした．");
	}
	/**
	 * @return printAction.setText("設定を保存できませんでした．");
	 */
	public void SaveConfig_Error(){
		printAction.setText("設定を保存できませんでした．");
	}
	/**
	 * @return printAction.setText("設定を更新しました．");
	 */
	public void UpdateConfig_Success(){
		printAction.setText("設定を更新しました．");
	}
	/**
	 * @return printAction.setText("設定を更新しませんでした．");
	 */
	public void UpdateConfig_Cancel(){
		printAction.setText("設定を更新しませんでした．");
	}
	/**
	 * @return printAction.setText("設定を更新できませんでした．");
	 */
	public void UpdateConfig_Error(){
		printAction.setText("設定を更新できませんでした．");
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
	/**
	 * @return alignText = { "日目 ", "時帯 ", "s" };
	 */
	public String[] getAlignText(){
		return alignText;
	}
}
