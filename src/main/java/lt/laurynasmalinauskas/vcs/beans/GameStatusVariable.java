package lt.laurynasmalinauskas.vcs.beans;

public class GameStatusVariable {

    String gameId;
    String nextTurnForUser;
    String status;
    String winner;
    EventTypeBean[] gameEvents;

    public GameStatusVariable(String gameId, String nextTurnForUser, String status, String winner, EventTypeBean[] gameEvents) {
        this.gameId = gameId;
        this.nextTurnForUser = nextTurnForUser;
        this.status = status;
        this.winner = winner;
        this.gameEvents = gameEvents;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getNextTurnForUser() {
        return nextTurnForUser;
    }

    public void setNextTurnForUser(String nextTurnForUser) {
        this.nextTurnForUser = nextTurnForUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public EventTypeBean[] getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(EventTypeBean[] gameEvents) {
        this.gameEvents = gameEvents;
    }
}
