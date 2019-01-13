package Client.PokemonGame.Controllers;

import Client.PokemonGame.Controllers.GUIControllers.WindowController;

public class MainController
{
    private WindowController windowController;

    //Fill in the first scene I want to show
    public MainController(WindowController windowController) {
        this.windowController = windowController;

        try{
            windowController.showGameScene(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //ADD
    //Player logs in
    //Sign in to server
    //Start
}
