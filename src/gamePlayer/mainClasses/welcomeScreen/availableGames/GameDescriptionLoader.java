package gamePlayer.mainClasses.welcomeScreen.availableGames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import utilities.JavaFXutilities.imageView.StringToImageViewConverter;

public class GameDescriptionLoader {

    public List<GameDescription> getDescriptions(String directory) {
        File[] games = new File(directory).listFiles();
        List<GameDescription> list = new ArrayList<GameDescription>();

        //step through each game and create game description
        for (File file:games) {
            File[] images = new File(file.getPath()+"/background/").listFiles();
            if(images != null){
            ImageView gameImage = StringToImageViewConverter.getImageView
                    (GameDescription.WIDTH, GameDescription.GAME_IMAGE_HEIGHT, images[0].getPath());
                list.add(new GameDescription(gameImage,file));
            }
        }

        return list;
    }
}
