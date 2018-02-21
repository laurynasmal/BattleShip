package lt.laurynasmalinauskas.vcs.beans;

import java.util.Date;

public class EventTypeBean {
    Date date;
    String column;
    int row;
    String userId;
    boolean shotCorrect;

    public EventTypeBean(Date date, String column, int row, String userId, boolean shotCorrect) {
        this.date = date;
        this.column = column;
        this.row = row;
        this.userId = userId;
        this.shotCorrect = shotCorrect;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getShotCorrect() {
        return shotCorrect;
    }

    public void setShotCorrect(boolean shotCorrect) {
        this.shotCorrect = shotCorrect;
    }
}
