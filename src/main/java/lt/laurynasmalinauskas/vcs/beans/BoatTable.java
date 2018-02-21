package lt.laurynasmalinauskas.vcs.beans;

public class BoatTable {
    char status;

    public static final String columnOptions = "KILOMETRAS";//didziosios raidoes
    public static final int[]  lineOptions = {0,1,2,3,4,5,6,7,8,9};
    public static final String lineOptionsString = "0123456789";

    public BoatTable(char status) {
        this.status = status;
    }


    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status +"";
    }
}
