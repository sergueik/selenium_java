package filetreeviewsample;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * This refers to this site http://docs.oracle.com/javafx/2/api/index.html
 * I just wanted to use Path insted of File
 * 
 * @author tomo
 */
public class PathTreeItem extends TreeItem<PathItem> {
    private boolean isLeaf = false;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeft = true;

    private PathTreeItem(PathItem pathItem) {
        super(pathItem);
    }

    public static TreeItem<PathItem> createNode(PathItem pathItem) {
        return new PathTreeItem(pathItem);
    }

    @Override
    public ObservableList<TreeItem<PathItem>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeft) {
            isFirstTimeLeft = false;
            Path path = getValue().getPath();
            isLeaf = !Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
        }
        return isLeaf;
    }

    private ObservableList<TreeItem<PathItem>> buildChildren(TreeItem<PathItem> treeItem) {
        Path path = treeItem.getValue().getPath();
        if (path != null && Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            ObservableList<TreeItem<PathItem>> children = FXCollections.observableArrayList();
            try (DirectoryStream<Path> dirs = Files.newDirectoryStream(path)) {
                for (Path dir : dirs) {
                    PathItem pathItem = new PathItem(dir);
                    children.add(createNode(pathItem));
                }
            } catch (IOException ex) {
                Logger.getLogger(FileTreeViewSample.class.getName()).log(Level.SEVERE, null, ex);
            }
            return children;
        }
        return FXCollections.emptyObservableList();
    }
}