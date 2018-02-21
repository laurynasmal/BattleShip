package lt.laurynasmalinauskas.vcs.services;

import lt.laurynasmalinauskas.vcs.beans.EventTypeBean;
import lt.laurynasmalinauskas.vcs.beans.GameStatusVariable;
import lt.laurynasmalinauskas.vcs.beans.User;

public interface GameServiceInterface {

    User createUser(String name, String email);
    GameStatusVariable joinServer(String id);
    void sendGameMap(String gameId, String userId, String shipMapinString);
    GameStatusVariable getGameStatus(String gameId);
    //EventTypeBean[] getEventWhileStatus(String gameId);
    void sentMyShot(String gameId, String playerId, String shotCoordinate);

}


