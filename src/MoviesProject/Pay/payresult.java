package MoviesProject.Pay;

public class payresult {

	
	private int usept;	//사용하게될 포인트 
	private int beforept;	//현재소지하고있는 포인트
	private int takept;	//예매후 적립되는 적립금
	private int nowpt;	//최종적립금
	private boolean ptallin; //모두사용 체크박스
	//?��립금 모두?��?�� 체크박스 체크 ?��무에 ?��?�� ?���??��?��?���? ?��?���? ?��?��
	
	private int beforepayment;
	private int dcpayment;
	private int howtopayment;
	private int totalpayment;
	
	public int getUsept() {return usept;}
	public void setUsept(int usept) {this.usept = usept;}
	
	public int getBeforept() {return beforept;}
	public void setBeforept(int beforept) {this.beforept = beforept;}
	
	public int getTakept() {return takept;}
	public void setTakept(int takept) {this.takept = takept;}
	
	public int getNowpt() {return nowpt;}
	public void setNowpt(int nowpt) {this.nowpt = nowpt;}
	
	
	public boolean getPtallin() {return ptallin;}
	public void setPtallin(boolean ptallin) {this.ptallin = ptallin;}
	
	public int getBeforepayment() {return beforepayment;}
	public void beforepayment(int beforepayment) {this.beforepayment = beforepayment;}
	public int getDcpayment() {return dcpayment;}
	public void getDcpayment(int dcpayment) {this.dcpayment = dcpayment;}
	public int getHowtopayment() {return howtopayment;}
	public void setHowtopayment(int howtopayment) {this.howtopayment = howtopayment;}
	public int getTotalpayment() {return totalpayment;}
	public void setTotalpayment(int totalpayment) {this.totalpayment = totalpayment;}
	
	

	
}
