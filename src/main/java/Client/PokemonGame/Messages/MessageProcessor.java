package Client.PokemonGame.Messages;

import Client.WebSocket.Messages.MessageContentCreator;
import Client.PokemonGame.Controllers.GUIControllers.GUIController;
import Client.PokemonGame.Controllers.GameController;
import Client.PokemonGame.Models.Pokemon;

public class MessageProcessor
{
    GUIController guiController;
    GameController gameController;

    public MessageProcessor(GUIController guiController){ //}, GameController gameController) {
        this.guiController = guiController;
        this.gameController = gameController;
    }

    public void processMessage(MessageContentCreator content)
    {
        //Process the content to send to the right controller
        switch (content.getMessageType())
        {
            case ATTACK:
                attackContent(content);
                break;
            case UPDATEPOKEMON:
                updateContent(content);
                break;
            case PLAYERDEAD:
                break;
        }
    }

    private void attackContent(MessageContentCreator content)
    {
        int dmg = content.getDmg();
        guiController.gotAttacked(dmg);
    }

    private void updateContent(MessageContentCreator content)
    {
        Pokemon pokemon = content.getPokemon();
        guiController.updateLabels(pokemon);
    }
}
