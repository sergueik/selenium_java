package filetreeviewsample;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import javafx.concurrent.Task;

/**
 * This refers to this site http://docs.oracle.com/javase/tutorial/essential/io/notification.html
 * I just wanted to bind this result to JavaFX UI control(TextArea)
 * 
 * @author tomo
 */
public class WatchTask extends Task<Void>{
    private Path path;
    private StringBuilder message = new StringBuilder();

    public WatchTask(Path path) {
        this.path = path;
    }
    
    @Override
    protected Void call() throws Exception {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                break;
            }
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == OVERFLOW) {
                    continue;
                }
                Path context = (Path) event.context();
                message.append(context.toAbsolutePath());
                message.append(getKindToMessage(event.kind()));
                message.append(System.getProperty("line.separator"));
                updateMessage(message.toString()); // to bind to the TextArea
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
        return null;
    }

    @Override
    protected void cancelled() {
        updateMessage("Watch task was cancelled"); // to bind to the TextArea
    }

    private String getKindToMessage(WatchEvent.Kind<?> kind) {
        if (kind == ENTRY_CREATE) {
            return " is created";
        } else if (kind == ENTRY_DELETE) {
            return " is deleted";
        }
        return " is updated";
    }    
}
