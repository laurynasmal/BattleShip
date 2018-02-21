//package lt.laurynasmalinauskas.vcs;
//
//import lt.laurynasmalinauskas.vcs.beans.GameStatusVariable;
//import lt.laurynasmalinauskas.vcs.beans.User;
//import lt.laurynasmalinauskas.vcs.services.GameServiceImpl;
//import lt.laurynasmalinauskas.vcs.services.GameServiceInterface;
//
//public class GameStart extends Thread {
//
//
//        public void run() {
//
//            GameServiceInterface gameService = new GameServiceImpl();
//            MyUI myUi = new MyUI();
//            String table = "K0-K3!S0-S2!T0-T2!O0-O1!S8-S9!O8-O9!T8-T8!K9-K9!O5-O5!R6-R6";
//
//            User user1 = gameService.createUser("Laur", "laurynas@gmail.com");
//            myUi.setUser(user1);
//            GameStatusVariable temporary = gameService.joinServer(user1.getId());
//            myUi.setGameId(temporary.getGameId());
//            String status = "";
//            while (!"READY_FOR_SHIPS".equals(temporary.getStatus())) {
//
//                temporary = gameService.getGameStatus(temporary.getGameId());
//
//            }
//            gameService.sendGameMap(temporary.getGameId(), user1.getId(), table);
//
//        }
//
//
//
//}
