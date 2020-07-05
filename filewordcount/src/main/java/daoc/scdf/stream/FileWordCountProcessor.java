package daoc.scdf.stream;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(Processor.class)
public class FileWordCountProcessor {

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Map<String, Integer> wordCountInFile(String filePath) {
		Map<String, Integer> wordCount = new HashMap<>();
		wordCount.put("__" + filePath, null);
		try {
			Scanner scan = new Scanner(new FileReader(filePath));
			while(scan.hasNextLine()) {
				String[] words = scan.nextLine().split("\\W+");
				for(String w : words) {
					Integer num;
					if( (num = wordCount.get(w)) != null ) {
						wordCount.put(w, num + 1);
					} else {
						wordCount.put(w, 1);
					}
				}
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
		return wordCount;
	}
	
}
