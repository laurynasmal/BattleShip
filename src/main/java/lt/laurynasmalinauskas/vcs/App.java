//package lt.laurynasmalinauskas.vcs;
//
//import lt.laurynasmalinauskas.vcs.beans.EventTypeBean;
//import lt.laurynasmalinauskas.vcs.beans.GameStatusVariable;
//import lt.laurynasmalinauskas.vcs.beans.User;
//import lt.laurynasmalinauskas.vcs.services.GameServiceImpl;
//import lt.laurynasmalinauskas.vcs.services.GameServiceInterface;
//import lt.laurynasmalinauskas.vcs.services.LocalServices;
//
//public class App {
//
//    public static void main(String[] args) throws InterruptedException {
//
//
//        GameServiceInterface gameService = new GameServiceImpl();
//        LocalServices localServices = new LocalServices();
//        //EventTypeBean[] gameStatus123 = gameService.getEventWhileStatus("5a37c7f25bb660407893a6fc");
//        //System.out.println("aa");
//
//        //String table = localServices.createPlayerBoatTable();
//
//        String table = "K0-K3!S0-S2!T0-T2!O0-O1!S8-S9!O8-O9!T8-T8!K9-K9!O5-O5!R6-R6";
//        String status = "";
//        String gameId = "";
//
//
//        User user = gameService.createUser("Laur", "laurynas@gmail.com");
//        gameId = gameService.joinServer(user.getId()).getGameId();
//        //status = gameService.getGameStatusWhileJoin(user.getId());
//        while (!"READY_FOR_SHIPS".equals(status)) {
//            System.out.flush();
//            System.out.println("Your game not ready yet");
//            GameStatusVariable gameInfo = gameService.getGameStatusWhileGame(gameId);
//            status = gameInfo.getStatus();
//
//            System.out.println("Your game ID = " + gameId);
//            System.out.println(status);
//            localServices.tryEndGame();
//            Thread.sleep(5000);
//        }
//        status = gameService.getGameStatusWhileGame(gameId).getStatus();
//        while (!"READY_TO_PLAY".equals(status)) {
//            System.out.flush();
//            GameStatusVariable gameInfo = gameService.getGameStatusWhileGame(gameId);
//            System.out.println("Your game ID = " + gameId);
//            System.out.println("Whose turn = " + gameInfo.getNextTurnForUser());
//            status = gameInfo.getStatus();
//            //status = gameService.getGameStatusWhileSetup(gameId, user.getId(), table);
//            System.out.println(status);
//            Thread.sleep(5000);
//        }
//        System.out.println("game started");
//        GameStatusVariable gameInfo = gameService.getGameStatusWhileGame(gameId);
//        String whoseTurn = gameInfo.getNextTurnForUser();
//        EventTypeBean[] gameLogs;
//        while (!"FINISHED".equals(status)){
//            System.out.flush();
//            gameInfo = gameService.getGameStatusWhileGame(gameId);
//            gameLogs = gameService.getEventWhileStatus(gameId);
//            status = gameInfo.getStatus();
//            whoseTurn = gameInfo.getNextTurnForUser();
//            System.out.println("Your userId"+user.getId());
//            System.out.println("Whose turn now"+whoseTurn);
//            System.out.println(whoseTurn);
//            localServices.printGameTables(gameLogs, user.getId(), table);
//            if (whoseTurn.equals(user.getId())){
//                String userShot = localServices.getYourShotCoordinate();
//                gameService.sentMyShot(gameId,user.getId(),userShot);
//
//            } else {
//                System.out.println("Priesininkas renkasi, palaukite");
//                Thread.sleep(5000);
//            }
//            System.out.println(status);
//        }
//        gameInfo = gameService.getGameStatusWhileGame(gameId);
//        System.out.println("Nugaletojas - "+gameInfo.getWinner());
//        System.out.println("Game Over");
//
//    }
//}
