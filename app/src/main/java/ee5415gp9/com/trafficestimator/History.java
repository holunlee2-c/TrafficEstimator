package ee5415gp9.com.trafficestimator;

public class History {

    int ID;
    int pk_from_master;

    public History(int ID, int pk_from_master) {
        this.ID = ID;
        this.pk_from_master = pk_from_master;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPk_from_master() {
        return pk_from_master;
    }

    public void setPk_from_master(int pk_from_master) {
        this.pk_from_master = pk_from_master;
    }
}
