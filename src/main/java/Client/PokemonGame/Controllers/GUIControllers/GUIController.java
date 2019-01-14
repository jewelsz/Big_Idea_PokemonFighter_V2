package Client.PokemonGame.Controllers.GUIControllers;

import Client.Communicator.Communicator;
import Client.Communicator.CommunicatorClientWebSocket;
import Client.Messages.CommunicatorMessage;
import Client.Messages.CommunicatorWebSocketType;
import Client.Messages.MessageContentCreator;
import Client.PokemonGame.Controllers.GameController;
import Client.PokemonGame.Controllers.MainController;
import Client.PokemonGame.Messages.MessageProcessor;
import Client.PokemonGame.Models.Attack;
import Client.PokemonGame.Models.Pokemon;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Observable;
import java.util.Observer;

//------------------------------//
//-------Update the GUI---------//
//------------------------------//
public class GUIController implements Observer
{
    public Pokemon myPokemon;
    private MessageProcessor messageProcessor;
    private MainController mainController;
    private GameController gameController = new GameController();

    @FXML
    private Label lblpName1, lblHealth1;
    @FXML
    private TextField tbMsg;
    @FXML
    private Text txtArea, lblHealth2, lblpName2;
    @FXML
    private ListView listPokemon, listAttacks;

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

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void update(Observable o, Object arg) {
        CommunicatorMessage message = (CommunicatorMessage) arg;
        String property = message.getProperty();
        MessageContentCreator content = gson.fromJson(message.getContent(), MessageContentCreator.class);
        messageProcessor.processMessage(content);
    }



    public void updateLabels(Pokemon pokemon)
    {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                lblpName2.setText(pokemon.getName());
                lblHealth2.setText(String.valueOf(pokemon.getHealth()));
                lblpName1.setText(myPokemon.getName());
                lblHealth1.setText(String.valueOf(myPokemon.getHealth()));
            }
        });
        System.out.println("In update Label methode");
        System.out.println(String.valueOf(myPokemon.getHealth()));
    }

    public void sendMessage()
    {
        broadcastText(tbMsg.getText());
    }

    public void attackOpponent()
    {
        broadcastDamage((Attack)listAttacks.getSelectionModel().getSelectedItem());
    }

    public void showPokemon()
    {
        broadcastPokemon(myPokemon);
    }

    public void gotAttacked(int dmg)
    {
        gameController.gotAttacked((Attack)listAttacks.getSelectionModel().getSelectedItem());

        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                fillAttackList();
                fillPokemonList();
                summonPokemonLbl();
                refreshHealthLabel();
                broadcastPokemon(myPokemon);
            }
        });
    }

    // Send event to other players that are registered
    private void broadcastText(String msg) {
        if (communicator != null)
        {
            String content = gson.toJson(msg);
            CommunicatorMessage message = new CommunicatorMessage();
            message.setProperty(currentProperty);
            //Content is the class I want to send
            message.setContent(content);
            communicator.update(message);
        }
    }

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

        currentProperty = "player1";
        myPokemon = gameController.createPlayer(currentProperty);
        communicator.register(currentProperty);

        communicator.unsubscribe(properties[0]);
        communicator.subscribe(properties[1]);

            lblpName1.setText(myPokemon.getName());
            lblHealth1.setText(String.valueOf(myPokemon.getHealth()));
            fillPokemonList();
            fillAttackList();
    }

    public void p2Clicked ()
    {
        currentProperty = "player2";
        myPokemon = gameController.createPlayer(currentProperty);
        communicator.register(currentProperty);

        communicator.unsubscribe(properties[1]);
        communicator.subscribe(properties[0]);

        lblpName1.setText(myPokemon.getName());
        lblHealth1.setText(String.valueOf(myPokemon.getHealth()));

        fillPokemonList();
        fillAttackList();
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

    }

