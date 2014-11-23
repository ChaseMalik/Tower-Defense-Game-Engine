package utilities.DragAndDropFilePanes;

public class DragAndDropImagePane extends DragAndDropFilePane {

    public DragAndDropImagePane (double width,
                                 double height,
                                 String fileDestination) {
        super(width, height, new String[] {".jpeg", ".jpg", ".png"}, fileDestination);
    }
}
