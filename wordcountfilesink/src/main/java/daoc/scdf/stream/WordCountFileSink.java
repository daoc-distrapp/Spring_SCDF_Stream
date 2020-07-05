package daoc.scdf.stream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class WordCountFileSink {
	public static final String filePath = "/root/scdf/shared/WordCountFileSink.log";

	@StreamListener(Sink.INPUT)
	public void toSink(Map<String, Integer> wordCount) {
		try {
			FileWriter fileWriter = new FileWriter(filePath, true);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.println(wordCount.toString());
		    printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}
