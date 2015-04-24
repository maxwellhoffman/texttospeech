import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SimpleTextToSpeech {

    private static final String TEXT_TO_SPEECH_SERVICE = 
            "http://translate.google.com/translate_tts";
    private static final String USER_AGENT =  
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) " +
            "Gecko/20100101 Firefox/11.0";

    public static void main(String[] args) throws Exception {
	
        if (args.length != 1) {
            System.out.println("Usage: SimpleTextToSpeech <text> ");
            System.out.println();
            System.out.println("- Text : If text is more than one word, " +
                    "then is must be put inside double quote, for example:");
            System.out.println("\tjava SimpleTextToSpeech en Hello");
            System.out.println("\tjava SimpleTextToSpeech en \"Hello World\"");
            System.exit(1);
        }
        
        String text = args[0];
        new SimpleTextToSpeech().callTextToSpeechService(text);
    }

    public void callTextToSpeechService(String text) throws Exception {
	
        // Create url based on input params
		text = URLEncoder.encode(text,"utf-8");
        String strUrl = TEXT_TO_SPEECH_SERVICE + "?" + "tl=en" + "&q=" + text;
        URL url = new URL(strUrl);

        // Establish connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Get method
        connection.setRequestMethod("GET");
        // Set User-Agent to "mimic" the behavior of a web browser. In this 
        // example, I used my browser's info
        connection.addRequestProperty("User-Agent", USER_AGENT);
        connection.connect();

        readAndWriteToFile(connection.getInputStream());
		
        //System.out.println("Done");
    }
	
	public void readAndWriteToFile(InputStream iStream) throws Exception
	{
		// Get content
        BufferedInputStream bufIn = new BufferedInputStream(iStream);
        byte[] buffer = new byte[1024];
        int n;
        ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
        while ((n = bufIn.read(buffer)) > 0) {
            bufOut.write(buffer, 0, n);
        }

        // Done, save data
        File output = new File("../wwwroot/TextToSpeech/output.mp3");
        BufferedOutputStream out = 
                new BufferedOutputStream(new FileOutputStream(output));
        out.write(bufOut.toByteArray());
        out.flush();
        out.close();
	}
}