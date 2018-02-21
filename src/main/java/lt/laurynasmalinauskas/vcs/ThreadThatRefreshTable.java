//package lt.laurynasmalinauskas.vcs;
//
//import lt.laurynasmalinauskas.vcs.services.GameServiceImpl;
//import lt.laurynasmalinauskas.vcs.services.GameServiceInterface;
//
//public class ThreadThatRefreshTable extends Thread {
//
//    public void run(){
//        GameServiceInterface gameService = new GameServiceImpl();
//
//        while(!("FINISHED").equals(gameService.getGameStatusWhileGame(MyUI.getGameId()).getStatus())){
//            MyUI.setGameMap(MyUI.showGameTables(MyUI.getUser(), MyUI.getGameId(), MyUI.getMapString(),gameService.getGameStatusWhileGame(MyUI.getGameId())));
//
//        }
//
//
//
//    }
//
//
//}
