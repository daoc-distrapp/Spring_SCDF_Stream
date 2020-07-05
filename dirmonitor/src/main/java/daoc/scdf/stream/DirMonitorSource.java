package daoc.scdf.stream;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;

@EnableBinding(Source.class)
public class DirMonitorSource {

	public static final String dirPath = "/root/scdf/shared/";
	
	@Autowired
	private Source source;
	
	private void sendEvents(String newFile) {
		source.output().send(MessageBuilder.withPayload(newFile).build());
	}
	
	@PostConstruct
	public void watch() {
		WatchService watchService;
		try {
			watchService = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(dirPath);	
			dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
	
			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					if(event.context().toString().endsWith(".txt")) {
						String file = dirPath + event.context();
						sendEvents(file);
					} 
				}
				key.reset();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
