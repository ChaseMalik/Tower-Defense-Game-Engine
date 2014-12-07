package gamePlayer.mainClasses.welcomeScreen.availableGames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.ImageView;
import utilities.JavaFXutilities.imageView.StringToImageViewConverter;

public class GameDescriptionLoader {
    private static String GAMES_DIRECTORY = "./Games/";

    public List<GameDescription> getDescriptions() {
        File[] games = new File(GAMES_DIRECTORY).listFiles();
        List<GameDescription> list = new ArrayList<GameDescription>();

        //step through each game and create game description
        for (File file:games) {
            ImageView gameImage = StringToImageViewConverter.getImageView
                    (GameDescription.WIDTH, GameDescription.GAME_IMAGE_HEIGHT, file.getAbsolutePath()+"/background/Map1.jpg");
            list.add(new GameDescription(gameImage,"Awesome Game",file.getAbsolutePath()));
        }

        return list;
    }
}
