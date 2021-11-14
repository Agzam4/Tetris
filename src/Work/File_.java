package Work;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class File_ {
	
	public String ReadFile(/*URI*/String name) throws IOException {
		String string = "";

		byte[] all = Files.readAllBytes(Paths.get(name));
		string = new String(all);
		return string;
	}
	
	public boolean CanReadFile(String name) {
		String string = "";
		try(FileReader reader = new FileReader(name)) {
            int c;
            while((c=reader.read())!=-1){
            	string = string + (char)c;
            }
            return true;
        }
        catch(IOException ex){
        	return false;
        }
		
	}
	
	public boolean WriteFile(String filename, String text, String bytes) throws IOException {
	    FileOutputStream fos = null;
	    boolean tr;
	    try {
	        fos = new FileOutputStream(filename);
	        fos.write(text.getBytes(bytes));//"ASCII"));//UTF-8"));
	        tr = true;
	        close(fos);
	    } catch (IOException e) {
	    	tr = false;
	    	close(fos);
	        throw e;
	    }
	    return tr;
	}
	
	private void close(Closeable closeable) {
	    try {
	        closeable.close();
	    } catch(IOException ignored) {
	    }
	}
	
	public int CountOfFiles() {
		int i = 0;
		boolean can = true;
		while (can) {
			try {
				can = CanReadFile(System.getProperty("user.dir") + "\\files\\names\\" + i + ".name");
			} catch (Exception e) {
				can = false;
			}
			i++;
			
		}
		return i-2;
	}
	
	
}
