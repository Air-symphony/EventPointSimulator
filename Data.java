package simulation;
 
public class Data {
	public int id;
	public int level, stamina, exp;
	public int point, item;
	public int normalcount, eventcount;

	Data() {
		this.id = -1;
		this.level = this.stamina = this.exp = 0;
		this.point = this.item = 
				this.normalcount = this.eventcount = 0;
	}

	Data(int id, Data d) {
		this.id = id;
		this.level = d.level;
		this.stamina = d.stamina;
		this.exp = d.exp;
		this.point = d.point;
		this.item = d.item;
		this.normalcount = d.normalcount;
		this.eventcount = d.eventcount;
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
	
	/**
	 * 
	 * @return
	 */
	public String[] getDetailText(){
		if (this.id == -1){
			String[] str = {"Print a detail of schedule."};
			return str;
		}
		else{
			String[] str = {getpointToString(), getlevelToString(), getstaminaToString(),
					getexpToString(), getitemToString(), getnormalcountToString(),
					geteventcountToString()};
			return str;
		}
	}
}
