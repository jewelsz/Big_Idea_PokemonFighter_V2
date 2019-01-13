package Client.PokemonGame.Controllers.GUIControllers;

import Client.PokemonGame.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.PrimitiveIterator;

//------------------------------//
//----Which scene is visible----//
//------------------------------//
public class WindowController
{
    private Stage primaryStage;
    private FXMLLoader loader;
    public GUIController guiController;

    public WindowController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showGameScene(MainController mainController) throws Exception{
        loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Pokemon Fighter - Jowelle Klomp");
        primaryStage.setScene(new Scene(root, 600,750));

        guiController = loader.getController();
        guiController.setMainController(mainController);

        primaryStage.show();
    }
}
