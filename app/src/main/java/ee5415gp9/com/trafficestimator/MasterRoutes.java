package ee5415gp9.com.trafficestimator;

public class MasterRoutes {
    int ID;
    String COMPANY;
    String ROUTE;
    String BOUND;
    String SERVICE_TYPE;
    String rdv;
    String ETA_ID;
    String source_chi;
    String source_eng;
    String dest_chi;
    String dest_eng;

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

    public String getETA_ID() {
        return ETA_ID;
    }

    public void setETA_ID(String ETA_ID) {
        this.ETA_ID = ETA_ID;
    }

    public String getSource_chi() {
        return source_chi;
    }

    public void setSource_chi(String source_chi) {
        this.source_chi = source_chi;
    }

    public String getSource_eng() {
        return source_eng;
    }

    public void setSource_eng(String source_eng) {
        this.source_eng = source_eng;
    }

    public String getDest_chi() {
        return dest_chi;
    }

    public void setDest_chi(String dest_chi) {
        this.dest_chi = dest_chi;
    }

    public String getDest_eng() {
        return dest_eng;
    }

    public void setDest_eng(String dest_eng) {
        this.dest_eng = dest_eng;
    }
}