package Client.PokemonGame.Controllers.GUIControllers;

import Client.REST.RESTCommunicator;
import Client.WebSocket.Communicator.Communicator;
import Client.WebSocket.Communicator.CommunicatorClientWebSocket;
import Client.WebSocket.Messages.CommunicatorMessage;
import Client.WebSocket.Messages.CommunicatorWebSocketType;
import Client.WebSocket.Messages.MessageContentCreator;
import Client.PokemonGame.Controllers.GameController;
import Client.PokemonGame.Messages.MessageProcessor;
import Client.PokemonGame.Models.Attack;
import Client.PokemonGame.Models.Pokemon;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

//------------------------------//
//-------Update the GUI---------//
//------------------------------//
public class GUIController implements Observer
{
    private MessageProcessor messageProcessor;
    private GameController gameController;
    private static RESTCommunicator restCommunicator;

    @FXML
    private Label lblpName1, lblHealth1;
    @FXML
    private Text lblHealth2, lblpName2;
    @FXML
    private ListView listPokemon, listAttacks;
    @FXML
    private Button btnSwitchPokemon, btnAttack;

    // Communicate with other white boards
    public Communicator communicator = null;

    // Current property to publish
    private String currentProperty;

    private final String[] properties = {"player1", "player2"};

    // Serialize draw events
    private Gson gson = new Gson();

    public GUIController() {
        currentProperty = properties[0];
        messageProcessor = new MessageProcessor(this);

        restCommunicator = new RESTCommunicator();
    }

    @Override
    public void update(Observable o, Object arg) {
        CommunicatorMessage message = (CommunicatorMessage) arg;
        String property = message.getProperty();
        MessageContentCreator content = gson.fromJson(message.getContent(), MessageContentCreator.class);
        messageProcessor.processMessage(content);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void updateLabels(Pokemon pokemon)
    {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                lblpName2.setText(pokemon.getName());
                lblHealth2.setText(String.valueOf(pokemon.getHealth()));
                lblpName1.setText(gameController.getThisPlayer().getSummonedPokemon().getName());
                lblHealth1.setText(String.valueOf(gameController.getThisPlayer().getSummonedPokemon().getHealth()));
            }
        });
    }

    public void attackOpponent()
    {
        broadcastDamage((Attack)listAttacks.getSelectionModel().getSelectedItem());
        gameController.setPlayerTurn(false);
        //switchButtonVisible();
    }

    public void showPokemon()
    {
        broadcastPokemon(gameController.getThisPlayer().getSummonedPokemon());
        if(gameController.getThisPlayer().getName() == properties[0])
        {
            communicator.unsubscribe(properties[0]);
            communicator.subscribe(properties[1]);
        }
        else
        {
            communicator.unsubscribe(properties[1]);
            communicator.subscribe(properties[0]);
        }
    }

    public void gotAttacked(int dmg)
    {
        gameController.gotAttacked((Attack)listAttacks.getSelectionModel().getSelectedItem());
        gameController.setPlayerTurn(true);
        //switchButtonVisible();
        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                fillAttackList();
                fillPokemonList();
                summonPokemonLbl();
                refreshHealthLabel();
                broadcastPokemon(gameController.getThisPlayer().getSummonedPokemon());

            }
        });
    }

    // Send event to other players that are registered
//    private void broadcastText(String msg) {
//        if (communicator != null)
//        {
//            String content = gson.toJson(msg);
//            CommunicatorMessage message = new CommunicatorMessage();
//            message.setProperty(currentProperty);
//            //Content is the class I want to send
//            message.setContent(content);
//            communicator.update(message);
//        }
//    }

    private void broadcastPokemon(Pokemon pokemon)
    {if (communicator != null)
    {
        MessageContentCreator msg = new MessageContentCreator();
        msg.setPokemon(pokemon);
        msg.setMessageType(CommunicatorWebSocketType.UPDATEPOKEMON);
        String content = gson.toJson(msg);
        CommunicatorMessage message = new CommunicatorMessage();
        message.setProperty(currentProperty);
        message.setContent(content);
        communicator.update(message);
    }
    }

    private void broadcastDamage(Attack attack) {
        if (communicator != null)
        {
            MessageContentCreator msg = new MessageContentCreator();
            msg.setDmg(attack.getDamage());
            msg.setMessageType(CommunicatorWebSocketType.ATTACK);
            String content = gson.toJson(msg);
            CommunicatorMessage message = new CommunicatorMessage();
            message.setProperty(currentProperty);
            message.setContent(content);
            communicator.update(message);
        }
    }

    public void switchToSelectedPokemon()
    {
        gameController.getThisPlayer().setSummonedPokemon((Pokemon)listPokemon.getSelectionModel().getSelectedItem());
        summonPokemonLbl();
        gameController.setPlayerTurn(false);
        //switchButtonVisible();
        broadcastPokemon(gameController.getThisPlayer().getSummonedPokemon());
    }

    private void summonPokemonLbl()
    {
        //Pokemon name
        lblpName1.setText(gameController.getThisPlayer().getSummonedPokemon().getName());
        //Pokemon health
        lblHealth1.setText(String.valueOf(gameController.getThisPlayer().getSummonedPokemon().getHealth()));
    }

    public void p1Clicked ()
    {

        currentProperty = properties[0];
        Pokemon myPokemon = gameController.createPlayer(currentProperty);
        communicator.register(currentProperty);

        gameController.setPlayerTurn(true);
            lblpName1.setText(myPokemon.getName());
            lblHealth1.setText(String.valueOf(myPokemon.getHealth()));
            fillPokemonList();
            fillAttackList();

            //switchButtonVisible();
    }

    public void p2Clicked ()
    {
        currentProperty = properties[1];
        Pokemon myPokemon = gameController.createPlayer(currentProperty);
        communicator.register(currentProperty);

        gameController.setPlayerTurn(false);

        lblpName1.setText(myPokemon.getName());
        lblHealth1.setText(String.valueOf(myPokemon.getHealth()));
        fillPokemonList();
        fillAttackList();

        //switchButtonVisible();
    }

    private void refreshHealthLabel()
    {
        lblHealth1.setText(String.valueOf(gameController.getThisPlayer().getSummonedPokemon().getHealth()));
    }

        //button connecten met server
    public void connectToServer ()
    {
        // Create the client web socket to communicate with other player
        communicator = CommunicatorClientWebSocket.getInstance();
        communicator.addObserver(this);

        // Establish connection with server
        communicator.start();
        }

    private  void fillPokemonList()
    {
        listPokemon.getItems().clear();
        listPokemon.getItems().addAll(gameController.getThisPlayer().getAllPokemon());
    }
    private void fillAttackList()
    {
        listAttacks.getItems().clear();
        listAttacks.getItems().addAll(gameController.getThisPlayer().getSummonedPokemon().getAttacks());
    }

    private void switchButtonVisible()
    {
        if(gameController.isPlayerTurn())
        {
            btnAttack.setVisible(true);
            btnSwitchPokemon.setVisible(true);
        }
        else
        {
            btnSwitchPokemon.setVisible(false);
            btnAttack.setVisible(false);
        }
    }

    public void savePokemon()
    {
        Platform.runLater(new Runnable(){
        @Override
        public void run() {


            restCommunicator.addPokemon((Pokemon)listPokemon.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void removePokemon()
    {
        Platform.runLater(new Runnable(){
        @Override
        public void run() {

            Pokemon pokemon = (Pokemon)listPokemon.getSelectionModel().getSelectedItem();
            restCommunicator.removePet(pokemon.getName());
        }
    });
    }

    public void updateHealth()
    {
        Platform.runLater(new Runnable(){
        @Override
        public void run() {

            restCommunicator.changeHealth((Pokemon)listPokemon.getSelectionModel().getSelectedItem());
        }
    });
    }

    public void printAllSavedPokemon()
    {
        Platform.runLater(new Runnable(){
        @Override
        public void run() {

            List<Pokemon> savedPokemon = restCommunicator.getAllPokemon();
            for(Pokemon p : savedPokemon)
            {
                System.out.println(p);
            }
        }
    });
    }

}

