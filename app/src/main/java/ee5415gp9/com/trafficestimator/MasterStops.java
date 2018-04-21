package ee5415gp9.com.trafficestimator;

public class MasterStops{

    int ID;
    String COMPANY;
    String ROUTE;
    String BOUND;
    String SERVICE_TYPE;
    String rdv;
    String stopid;
    String stopseq;
    String chi_name;
    String eng_name;
    String eta_para;

    String stopseqs;

    //ETA
    String first_time;
    String first_min;
    String first_dest_chi;
    String first_dest_eng;


    String second_time;
    String second_min;
    String second_dest_chi;
    String second_dest_eng;


    String third_time;
    String third_min;
    String third_dest_chi;
    String third_dest_eng;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

    public String getROUTE() {
        return ROUTE;
    }

    public void setROUTE(String ROUTE) {
        this.ROUTE = ROUTE;
    }

    public String getBOUND() {
        return BOUND;
    }

    public void setBOUND(String BOUND) {
        this.BOUND = BOUND;
    }

    public String getSERVICE_TYPE() {
        return SERVICE_TYPE;
    }

    public void setSERVICE_TYPE(String SERVICE_TYPE) {
        this.SERVICE_TYPE = SERVICE_TYPE;
    }

    public String getRdv() {
        return rdv;
    }

    public void setRdv(String rdv) {
        this.rdv = rdv;
    }

    public String getStopid() {
        return stopid;
    }

    public void setStopid(String stopid) {
        this.stopid = stopid;
    }

    public String getStopseq() {
        return stopseq;
    }

    public void setStopseq(String stopseq) {
        this.stopseq = stopseq;
    }

    public String getChi_name() {
        return chi_name;
    }

    public void setChi_name(String chi_name) {
        this.chi_name = chi_name;
    }

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }

    public String getEta_para() {
        return eta_para;
    }

    public void setEta_para(String eta_para) {
        this.eta_para = eta_para;
    }


    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }

    public String getFirst_min() {
        return first_min;
    }

    public void setFirst_min(String first_min) {
        this.first_min = first_min;
    }

    public String getSecond_time() {
        return second_time;
    }

    public void setSecond_time(String second_time) {
        this.second_time = second_time;
    }

    public String getSecond_min() {
        return second_min;
    }

    public void setSecond_min(String second_min) {
        this.second_min = second_min;
    }

    public String getThird_time() {
        return third_time;
    }

    public void setThird_time(String third_time) {
        this.third_time = third_time;
    }

    public String getThird_min() {
        return third_min;
    }

    public void setThird_min(String third_min) {
        this.third_min = third_min;
    }

    public String getFirst_dest_chi() {
        return first_dest_chi;
    }

    public void setFirst_dest_chi(String first_dest_chi) {
        this.first_dest_chi = first_dest_chi;
    }

    public String getFirst_dest_eng() {
        return first_dest_eng;
    }

    public void setFirst_dest_eng(String first_dest_eng) {
        this.first_dest_eng = first_dest_eng;
    }

    public String getSecond_dest_chi() {
        return second_dest_chi;
    }

    public void setSecond_dest_chi(String second_dest_chi) {
        this.second_dest_chi = second_dest_chi;
    }

    public String getSecond_dest_eng() {
        return second_dest_eng;
    }

    public void setSecond_dest_eng(String second_dest_eng) {
        this.second_dest_eng = second_dest_eng;
    }

    public String getThird_dest_chi() {
        return third_dest_chi;
    }

    public void setThird_dest_chi(String third_dest_chi) {
        this.third_dest_chi = third_dest_chi;
    }

    public String getThird_dest_eng() {
        return third_dest_eng;
    }

    public void setThird_dest_eng(String third_dest_eng) {
        this.third_dest_eng = third_dest_eng;
    }

    public String getStopseqs() {
        return stopseqs;
    }

    public void setStopseqs(String stopseqs) {
        this.stopseqs = stopseqs;
    }
}