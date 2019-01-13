/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.PokemonGame;

import Client.PokemonGame.Controllers.GUIControllers.GUIController;
import Client.PokemonGame.Controllers.GUIControllers.WindowController;
import Client.PokemonGame.Controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * White board application.
 * Observer-Observable pattern is used to receive messages from
 * the Communicator.
 * 
 * @author Nico Kuijpers
 */
public class Main extends Application
{
    private WindowController windowController;
    @Override
    public void start(Stage primaryStage) throws IOException {

        windowController = new WindowController(primaryStage);
        MainController mainController = new MainController(windowController);
    }


    @Override
    public void stop() {
        try {
            super.stop();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (windowController.guiController.communicator != null) {
            windowController.guiController.communicator.stop();
        }
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
