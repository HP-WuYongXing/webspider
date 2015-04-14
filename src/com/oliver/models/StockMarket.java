package com.oliver.models;

public class StockMarket {
	private int id;
	private float jkp;/*开盘价*/
	private float zgj;//最高价
	private float ztj;//涨停价
	private float hsl;//换手率
	private float syl;//市盈率
	private double cjl;//成交量
	private double zsz;//总市值
	private float zsp;//昨收盘
	private float zdj;//最低价
	private float dtj;//跌停价
	private float zf;//振幅
	private float sjl;//市净率
	private double cje;//成交额
	private double ltsz;//流通市值
	private float mc1;//卖出1
	private float mc2;//...
	private float mc3;
	private float mc4;
	private float mc5;
	private float mr1;//买入1
	private float mr2;
	private float mr3;
	private float mr4;
	private float mr5;
	private int mc1Num;//卖出申报量1
	private int mc2Num;//
	private int mc3Num;
	private int mc4Num;
	private int mc5Num;
	
	private int mr1Num;//买入申请1
	private int mr2Num;
	private int mr3Num;
	private int mr4Num;
	private int mr5Num;
	private int stockId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getJkp() {
		return jkp;
	}
	public void setJkp(float jkp) {
		this.jkp = jkp;
	}
	public float getZgj() {
		return zgj;
	}
	public void setZgj(float zgj) {
		this.zgj = zgj;
	}
	public float getZtj() {
		return ztj;
	}
	public void setZtj(float ztj) {
		this.ztj = ztj;
	}
	public float getHsl() {
		return hsl;
	}
	public void setHsl(float hsl) {
		this.hsl = hsl;
	}
	public float getSyl() {
		return syl;
	}
	public void setSyl(float syl) {
		this.syl = syl;
	}
	public double getCjl() {
		return cjl;
	}
	public void setCjl(double cjl) {
		this.cjl = cjl;
	}
	public double getZsz() {
		return zsz;
	}
	public void setZsz(double zsz) {
		this.zsz = zsz;
	}
	public float getZsp() {
		return zsp;
	}
	public void setZsp(float zsp) {
		this.zsp = zsp;
	}
	public float getZdj() {
		return zdj;
	}
	public void setZdj(float zdj) {
		this.zdj = zdj;
	}
	public float getDtj() {
		return dtj;
	}
	public void setDtj(float dtj) {
		this.dtj = dtj;
	}
	public float getZf() {
		return zf;
	}
	public void setZf(float zf) {
		this.zf = zf;
	}
	public float getSjl() {
		return sjl;
	}
	public void setSjl(float sjl) {
		this.sjl = sjl;
	}
	public double getCje() {
		return cje;
	}
	public void setCje(double cje) {
		this.cje = cje;
	}
	public double getLtsz() {
		return ltsz;
	}
	public void setLtsz(double ltsz) {
		this.ltsz = ltsz;
	}
	public float getMc1() {
		return mc1;
	}
	public void setMc1(float mc1) {
		this.mc1 = mc1;
	}
	public float getMc2() {
		return mc2;
	}
	public void setMc2(float mc2) {
		this.mc2 = mc2;
	}
	public float getMc3() {
		return mc3;
	}
	public void setMc3(float mc3) {
		this.mc3 = mc3;
	}
	public float getMc4() {
		return mc4;
	}
	public void setMc4(float mc4) {
		this.mc4 = mc4;
	}
	public float getMc5() {
		return mc5;
	}
	public void setMc5(float mc5) {
		this.mc5 = mc5;
	}
	public float getMr1() {
		return mr1;
	}
	public void setMr1(float mr1) {
		this.mr1 = mr1;
	}
	public float getMr2() {
		return mr2;
	}
	public void setMr2(float mr2) {
		this.mr2 = mr2;
	}
	public float getMr3() {
		return mr3;
	}
	public void setMr3(float mr3) {
		this.mr3 = mr3;
	}
	public float getMr4() {
		return mr4;
	}
	public void setMr4(float mr4) {
		this.mr4 = mr4;
	}
	public float getMr5() {
		return mr5;
	}
	public void setMr5(float mr5) {
		this.mr5 = mr5;
	}
	public int getMc1Num() {
		return mc1Num;
	}
	public void setMc1Num(int mc1Num) {
		this.mc1Num = mc1Num;
	}
	public int getMc2Num() {
		return mc2Num;
	}
	public void setMc2Num(int mc2Num) {
		this.mc2Num = mc2Num;
	}
	public int getMc3Num() {
		return mc3Num;
	}
	public void setMc3Num(int mc3Num) {
		this.mc3Num = mc3Num;
	}
	public int getMc4Num() {
		return mc4Num;
	}
	public void setMc4Num(int mc4Num) {
		this.mc4Num = mc4Num;
	}
	public int getMc5Num() {
		return mc5Num;
	}
	public void setMc5Num(int mc5Num) {
		this.mc5Num = mc5Num;
	}
	public int getMr1Num() {
		return mr1Num;
	}
	public void setMr1Num(int mr1Num) {
		this.mr1Num = mr1Num;
	}
	public int getMr2Num() {
		return mr2Num;
	}
	public void setMr2Num(int mr2Num) {
		this.mr2Num = mr2Num;
	}
	public int getMr3Num() {
		return mr3Num;
	}
	public void setMr3Num(int mr3Num) {
		this.mr3Num = mr3Num;
	}
	public int getMr4Num() {
		return mr4Num;
	}
	public void setMr4Num(int mr4Num) {
		this.mr4Num = mr4Num;
	}
	public int getMr5Num() {
		return mr5Num;
	}
	public void setMr5Num(int mr5Num) {
		this.mr5Num = mr5Num;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	
	@Override
	public String toString() {
		return "StockMarket [id=" + id + ", jkp=" + jkp + ", zgj=" + zgj
				+ ", ztj=" + ztj + ", hsl=" + hsl + ", syl=" + syl + ", cjl="
				+ cjl + ", zsz=" + zsz + ", zsp=" + zsp + ", zdj=" + zdj
				+ ", dtj=" + dtj + ", zf=" + zf + ", sjl=" + sjl + ", cje="
				+ cje + ", ltsz=" + ltsz + ", mc1=" + mc1 + ", mc2=" + mc2
				+ ", mc3=" + mc3 + ", mc4=" + mc4 + ", mc5=" + mc5 + ", mr1="
				+ mr1 + ", mr2=" + mr2 + ", mr3=" + mr3 + ", mr4=" + mr4
				+ ", mr5=" + mr5 + ", mc1Num=" + mc1Num + ", mc2Num=" + mc2Num
				+ ", mc3Num=" + mc3Num + ", mc4Num=" + mc4Num + ", mc5Num="
				+ mc5Num + ", mr1Num=" + mr1Num + ", mr2Num=" + mr2Num
				+ ", mr3Num=" + mr3Num + ", mr4Num=" + mr4Num + ", mr5Num="
				+ mr5Num + ", stockId=" + stockId + "]";
	}
	
	
	
}
