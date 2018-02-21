package lt.laurynasmalinauskas.vcs;

import com.sun.xml.internal.bind.v2.TODO;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.UIEvents;
import com.vaadin.server.*;
import com.vaadin.shared.Registration;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.DesignContext;
import elemental.json.JsonObject;
import lt.laurynasmalinauskas.vcs.beans.*;
import lt.laurynasmalinauskas.vcs.services.GameServiceImpl;
import lt.laurynasmalinauskas.vcs.services.GameServiceInterface;
import lt.laurynasmalinauskas.vcs.services.LocalServices;
import org.jsoup.nodes.Element;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.*;

import static com.vaadin.ui.themes.ValoTheme.LABEL_H2;
import static lt.laurynasmalinauskas.vcs.services.LocalServices.initEmptyBoatTable;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI  {
    volatile  User user;
    volatile String gameId;
    Layout gameMapLayout = new HorizontalLayout();



    final GameServiceInterface gameService = new GameServiceImpl();
    GameStatusVariable gameInformation = null;
    Layout game = new HorizontalLayout(new Label("nonono"));






    @Override
    protected void init(VaadinRequest vaadinRequest)  {



        setPollInterval(1000);
        addPollListener(new UIEvents.PollListener() {
            @Override
            public void poll(UIEvents.PollEvent event) {
                try {
                    if(getGameId() == null) {
                    } else {
                        gameInformation = gameService.getGameStatus(getGameId());
                        //game = makeGameTableLayout(getUser(), getGameId(), getMapString(), gameInformation);
                        game.removeAllComponents();
                        game.addComponent(makeGameTableLayout(getUser(), getGameId(), getMapString(), gameInformation));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        final VerticalLayout layout = new VerticalLayout();
        //String table = getMapString();


        Label label = new Label("Lets Play ships game");
        label.addStyleName(LABEL_H2);


        Button buttonShipsMap = new Button("Place your game ships");
        buttonShipsMap.addClickListener(e -> {
            layout.removeAllComponents();
            //layout.addComponent(createShipTable());
        });


        Button buttonStartGame = new Button("Start Your Game");
        buttonStartGame.addClickListener(e -> {
            Thread gameStart = new GameStart();
            gameStart.start();


            layout.removeAllComponents();
            Label gameIdLable = new Label("Zaidimo Id : " + getGameId());
            //TODO fix to show game ID
            layout.addComponent(gameIdLable);

            layout.addComponent(game);
        });

        Button buttonExitGame = new Button("Exit Game");
        buttonExitGame.addClickListener(e -> {
            System.exit(0);
        });

        layout.addComponent(label);
        layout.addComponent(buttonShipsMap);
        layout.addComponent(buttonStartGame);
        layout.addComponent(buttonExitGame);

        setContent(layout);

    }

    private static boolean canYouAddMoreShips(List<Button> allButtonsForMap) {
        int result = 0;
        for (Button temp : allButtonsForMap) {
            if (String.valueOf(TableStatus.YOURBOAT).equals(temp.getCaption())) {
                result++;
            }
        }
        if (result < 20) {
            return true;
        }
        return false;
    }


    public static Layout makeGameTableLayout(User user, String gameId, String table, GameStatusVariable gameStatus) {
        //TODO simplify this method
        if(gameStatus==null){
            HorizontalLayout errorMessage = new HorizontalLayout();
            errorMessage.addComponent(new Label("Table not ready, game still loading"));
            return errorMessage;
        }
        BoatTable[][] boatTablePlayer = LocalServices.stringToBoatTable(table);
        GameServiceInterface gameService = new GameServiceImpl();
        EventTypeBean[] gameEvents = gameStatus.getGameEvents();
        GridLayout gridOponent = new GridLayout(11, 11);
        List<Button> allButtonsOponent = new ArrayList<>();
        gridOponent.addComponent(new Label(""));
        for (int x = 0; x < BoatTable.columnOptions.length(); x++) {
            gridOponent.addComponent(new Label(String.valueOf(BoatTable.columnOptions.charAt(x))));
        }
        for (int i = 0; i < BoatTable.columnOptions.length(); i++) {
            gridOponent.addComponent(new Label(String.valueOf(BoatTable.lineOptionsString.charAt(i))));
            for (int j = 0; j < BoatTable.lineOptions.length; j++) {
                StringBuilder tempButtonName = new StringBuilder("");
                tempButtonName.append(BoatTable.columnOptions.charAt(i)).append(BoatTable.lineOptions[j]);
                allButtonsOponent.add(new Button());
                allButtonsOponent.get(i * 10 + j).setId(tempButtonName.toString());
                allButtonsOponent.get(i * 10 + j).setCaption(String.valueOf(boatTablePlayer[i][j].getStatus()));
                for (EventTypeBean tempEventBean : gameEvents) {
                    if(!user.getId().equals(tempEventBean.getUserId())){
                        if ((String.valueOf(BoatTable.columnOptions.charAt(j)) + String.valueOf(BoatTable.lineOptionsString.charAt(i))).equals
                                ( tempEventBean.getColumn() + String.valueOf(tempEventBean.getRow()) )) {
                            if (tempEventBean.getShotCorrect()) {
                                allButtonsOponent.get(i * 10 + j).setCaption(String.valueOf(TableStatus.CORRECTSHOT));
                            } else {
                                allButtonsOponent.get(i * 10 + j).setCaption(String.valueOf(TableStatus.FALSESHOT));
                            }
                        } else {

                        }
                    }


                }
                gridOponent.addComponent(allButtonsOponent.get(i * 10 + j));
            }
        }
        GridLayout gridPlayer = new GridLayout(11, 11);
        List<Button> allButtonsPlayer = new ArrayList<>();
        gridPlayer.addComponent(new Label(""));
        for (int x = 0; x < BoatTable.columnOptions.length(); x++) {
            gridPlayer.addComponent(new Label(String.valueOf(BoatTable.columnOptions.charAt(x))));
        }
        for (int i = 0; i < BoatTable.columnOptions.length(); i++) {
            gridPlayer.addComponent(new Label(String.valueOf(BoatTable.lineOptionsString.charAt(i))));
            for (int j = 0; j < BoatTable.lineOptions.length; j++) {
                StringBuilder tempButtonName = new StringBuilder("");
                tempButtonName.append(BoatTable.columnOptions.charAt(j)).append(BoatTable.lineOptions[i]);
                allButtonsPlayer.add(new Button());
                allButtonsPlayer.get(i * 10 + j).setId(tempButtonName.toString());
                allButtonsPlayer.get(i * 10 + j).addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {

                        Button source = (Button) clickEvent.getSource();
                        String userShot = source.getId();
                        if("READY_TO_PLAY".equals(gameStatus.getStatus())){
                            gameService.sentMyShot(gameId,user.getId(),userShot);
                        }
                    }
                });
                for (EventTypeBean tempEventBean : gameEvents) {
                    if(user.getId().equals(tempEventBean.getUserId())){
                        if ((String.valueOf(BoatTable.columnOptions.charAt(j)) + String.valueOf(BoatTable.lineOptionsString.charAt(i))).equals
                                (tempEventBean.getColumn() +  String.valueOf(tempEventBean.getRow()) )) {
                            if (tempEventBean.getShotCorrect()) {
                                allButtonsPlayer.get(i * 10 + j).setCaption(String.valueOf(TableStatus.CORRECTSHOT));
                            } else {
                                allButtonsPlayer.get(i * 10 + j).setCaption(String.valueOf(TableStatus.FALSESHOT));
                            }
                        }
                    }


                }
                gridPlayer.addComponent(allButtonsPlayer.get(i * 10 + j));
            }
        }
        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.addComponent(new Label("Oponent table")  );
        verticalLayout1.addComponent(new Label( ""));
        verticalLayout1.addComponent(gridOponent);

        VerticalLayout verticalLayout2 = new VerticalLayout();
        verticalLayout2.addComponent(new Label("Player table"+user.getId())  );
        if (user.getId().equals(gameStatus.getNextTurnForUser())){
            verticalLayout2.addComponent(new Label("YOUR TURN!"));
        } else {
            verticalLayout2.addComponent(new Label("Oponent turn"));
        }
        verticalLayout2.addComponent(gridPlayer);
        HorizontalLayout hor = new HorizontalLayout();
        hor.addComponent(verticalLayout1);
        hor.addComponent(verticalLayout2);
        return hor;
    }

    public class GameStart extends Thread {


        public void run() {

            GameServiceInterface gameService = new GameServiceImpl();
            String table = "K0-K3!S0-S2!T0-T2!O0-O1!S8-S9!O8-O9!T8-T8!K9-K9!O5-O5!R6-R6";

            User user1 = gameService.createUser("Laur", "laurynas@gmail.com");
            setUser(user1);
            GameStatusVariable temporary = gameService.joinServer(user1.getId());
            setGameId(temporary.getGameId());
            while (!"READY_FOR_SHIPS".equals(temporary.getStatus())) {

                temporary = gameService.getGameStatus(temporary.getGameId());

            }
            gameService.sendGameMap(temporary.getGameId(), user1.getId(), table);

        }



    }

    public static String getMapString(){
        return "K0-K3!S0-S2!T0-T2!O0-O1!S8-S9!O8-O9!T8-T8!K9-K9!O5-O5!R6-R6";
    }

    public Layout getGameMap() {
        return gameMapLayout;
    }

    public void setGameMap(Layout gameMap) {
        this.gameMapLayout = gameMap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}



