package simulation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Calculator {
	private String gametype = "";
	private ObservableList<Data> memory;
	private ObservableList<int[]> align = FXCollections.observableArrayList();
	private ListView<String> timeList;
	
	public Calculator(ListView<String> timeList) {
		this.timeList = timeList;
	}
	
	public void Start(String gametype, ComboBox<String>[] daydata, TextField[] statusField){
		this.gametype = gametype;
		memory = FXCollections.observableArrayList();
		ObservableList<String> schedule = FXCollections.observableArrayList();
		Status status = new Status(daydata, statusField);
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d/H");
		String[] day_now_text = sdf.format(c.getTime()).split("/");
		//現在の日付
		int[] day_now = new int[day_now_text.length];
		for(int i = 0; i < day_now_text.length ; i++)
			day_now[i] = Integer.parseInt(day_now_text[i]);
		
		int timeList_id = 0, timeList_count = 0;

		int align_id = 0;
		for (int d = 1; d <= status.term; d++) {// 日単位
			int start = 0;
			int end = 24;
			if (d == 1)
				start = 15;
			else if (d == status.term)
				end = 21;
			memory.add(new Data());
			int[] ticket = status.TicketTime(d);
			if(gametype.equals("cinderella")){
				schedule.add(d + "日目<" + ticket[0] + "," + ticket[1] + ","
						+ ticket[2] + ">");
			}
			else if(gametype.equals("million")){
				schedule.add(d + "日目");
			}
			timeList_count++;
			
			status.item += status.p.dailyitem;//ログインボーナス
			status.stamina += status.p.dailystamina;;//デイリースタドリ
			for (int h = start + 1; h < end + 1; h++) {// 時間単位
				status.stamina += 12;
				if (align_id < align.size()){
					if (align.get(align_id)[0] == d && align.get(align_id)[1] == h){
						status.stamina += align.get(align_id)[2];
						align_id++;
					}
				}
				status.addpoint_Normal();
				if(gametype.equals("cinderella")){
					for (int i = 0; i < ticket.length; i++) {
						if (h == ticket[i]) {
							status.addpoint_Event();
						}
					}
				}
				else if(gametype.equals("million")){
					status.addpoint_Event();
				}
				memory.add(new Data((d * 24 + h), status));
				if (h < 10)
					schedule.add(d + "日目 0" + h + "時  " + status.getpoint() + "pt");
				else
					schedule.add(d + "日目 " + h + "時  " + status.getpoint() + "pt");
				timeList_count++;
				if (day_now[0] == (status.day + d - 1) && day_now[1] == h){
					timeList_id = timeList_count;
				}
			}
		}
		timeList.setItems(schedule);
		timeList.getSelectionModel().select(timeList_id);
	}
	
	public void SetAlign(ObservableList<int[]> align){
		this.align = align;
	}
	
	public ObservableList<Data> Getmemory(){
		return memory;
	}
	
	public String addcalSimulater(String gametype, int[] pt){
		this.gametype = gametype;
		EventPoint p = new EventPoint();
		int n = 0;
		while(pt[0] > n * p.point + pt[1] * p.ev_point){
			n++;
		}
		return n + " * " + p.point + " + " + pt[1] +" * " + p.ev_point + " = " + (n * p.point + pt[1] * p.ev_point);
	}
	
	class EventPoint {
		private int dailyitem, dailystamina;
		private int stamina, exp, ev_exp;
		private int item, point, ev_point;

		EventPoint() {
			if(gametype.equals("cinderella")){
				stamina = 18;
				exp = 63;
				ev_exp = 70;
				item = 150;
				point = 53;
				ev_point = 320;
				dailyitem = 300;
				dailystamina = 20;
			}else if(gametype.equals("million")){
				stamina = 30;
				exp = ev_exp= 306;
				item = 180;
				point = 68;
				ev_point = 429;
				dailyitem = 180;
				dailystamina = 20;
			}
		}
	};

	class Status extends Data {
		private EventPoint p = new EventPoint();
		private int year, month, day, term;

		Status(ComboBox<String>[] daydata, TextField[] statusField) {
			year = Integer.parseInt(daydata[0].getValue());
			month = Integer.parseInt(daydata[1].getValue());
			day = Integer.parseInt(daydata[2].getValue());
			term = Integer.parseInt(daydata[3].getValue());
			id = 0;
			level = Integer.parseInt(statusField[0].getText());
			stamina = Integer.parseInt(statusField[1].getText());
			exp = Integer.parseInt(statusField[2].getText());
			point = item = normalcount = eventcount = 0;
		}

		void addpoint_Normal() {
			int count = stamina / p.stamina;
			for (int i = 0; i < count; i++) {
				point += p.point;
				item += p.point;
				exp += p.exp;
				normalcount += 1;
				stamina -= p.stamina;
				LevelUP();
			}
		}

		void addpoint_Event() {
			int count = item / p.item;
			for (int i = 0; i < count; i++) {
				point += p.ev_point;
				if(gametype.equals("cinderella")){
					if (eventcount == 0 || 30 < eventcount)
						exp += p.exp;
					else
						exp += p.ev_exp;
				}
				else if(gametype.equals("million")){
					exp += p.exp;
				}
				eventcount += 1;
				item -= p.item;
				LevelUP();
			}
		}

		int expMax() {
			int count = 0;
			if(gametype.equals("cinderella")){
				count = 10;
				for (int x = 1; x < level + 1; x++) {
					count += 20;
					if (x == 50) {
						count -= 10;
					}
					if (x <= 150 && x % 50 == 0) {
						count += 500;
					} else if (x > 150 && x % 10 == 0) {
						count += 700;
					}
					if (x == 250) {
						count -= 20;
					}
				}
			}
			else if(gametype.equals("million")){
				count = 50;
				count += (level - 1) * 100;
			}
			return count;
		}

		int staminaMax() {
			float count = 0;
			if(gametype.equals("cinderella")){
				count = 40;
				for (int i = 1; i < level + 1; i++) {
					if (i <= 20) {
						count += 1 / 2;
					} else if (i <= 50) {
						count += 1 / 3;
					} else if (i <= 100) {
						count += 1 / 4;
					} else if (i <= 140) {
						count += 1 / 5;
					} else {
						count += 1 / 10;
					}
				}
			}
			else if(gametype.equals("million")){
				count = 60;
				for (int i = 1; i < level + 1; i++) {
					if (i <= 60) {
						count += 1 / 2;
					} else {
						count += 1 / 3;
					}
				}
			}
			return (int) count;
		}

		void LevelUP() {
			if (exp >= expMax()) {
				exp -= expMax();
				level += 1;
				if(gametype.equals("cinderella")){
					stamina += staminaMax();
				}
				else if(gametype.equals("million")){
					stamina = staminaMax();
				}
			}
		}

		int[] TicketTime(int dt) {
			int year = this.year;
			int month = this.month;
			int day = this.day + (dt - 1);
			int[] days = { 2016, 9, 3 };
			int[] time = { 11, 15, 22 };
			int count = 0;
			if (year - days[0] > 0) {
				count += (365 * (year - days[0]));
			}
			if (Math.abs(month - days[1]) > 0) {
				int s = days[1];
				for (int x = 0; x < month - days[1]; x++) {
					if (s == 9 || s == 11)
						count += 30;
					else
						count += 31;
					s = s % 12 + 1;
				}
				for (int x = 0; x < days[1] - month; x++) {
					s = s % 12 - 1;
					if (s == 4 || s == 6)
						count -= 30;
					else if (s == 2)
						count -= 28;
					else
						count -= 31;
				}
			}
			count += (day - days[2]);
			count %= 4;
			for (int i = 1; i < count + 1; i++) {
				if (i == 1) {
					for (int x = 0; x < time.length; x++) {
						time[x] -= 4;
					}
				}
				for (int x = 0; x < time.length; x++) {
					time[x] += 1;
				}
			}
			return time;
		}
	}
}
