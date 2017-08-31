package simulation;
 
public class Data {
	public int id;
	public int level, stamina, exp;
	public int point, item;
	public int normalcount, eventcount;

	Data() {
		id = level = stamina = exp = 0;
		point = item = normalcount = eventcount = 0;
	}

	Data(int id, Data d) {
		this.id = id;
		level = d.level;
		stamina = d.stamina;
		exp = d.exp;
		point = d.point;
		item = d.item;
		normalcount = d.normalcount;
		eventcount = d.eventcount;
	}

	public String getlevel() {
		return level + "";
	}
	
	public String getlevelToString() {
		return "Level : " + level;
	}

	public String getstamina() {
		return stamina + "";
	}
	
	public String getstaminaToString() {
		return "stamina : " + stamina;
	}

	public String getexp() {
		return exp + "";
	}
	
	public String getexpToString() {
		return "Exp : " + exp;
	}

	public String getpoint() {
		return point + "";
	}
	
	public String getpointToString() {
		return "Point : " + point;
	}

	public String getitem() {
		return item + "";
	}
	
	public String getitemToString() {
		return "Item : " + item;
	}

	public String getnormalcount() {
		return normalcount + "";
	}
	
	public String getnormalcountToString() {
		return "Normal : " + normalcount;
	}

	public String geteventcount() {
		return eventcount + "";
	}
	
	public String geteventcountToString() {
		return "Event : " + eventcount;
	}
	
	public String[] getDetailText(){
		String[] str = {getpointToString(), getlevelToString(), getstaminaToString(),
				getexpToString(), getitemToString(), getnormalcountToString(),
				geteventcountToString()};
		return str;
	}
}
