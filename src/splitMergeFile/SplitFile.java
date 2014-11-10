package splitMergeFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
	
public class SplitFile {
	 	    private static byte PART_SIZE = 5;
	 	    
	 	    public SplitFile(File file) {
	 	        FileInputStream inputStream;
	 	        String newFileName;
	 	        FileOutputStream filePart;
	 	        int fileSize = (int) file.length();
	 	        int nChunks = 0, read = 0, readLength = PART_SIZE;
	 	        byte[] byteChunkPart;
	 	        try {
	 	            inputStream = new FileInputStream(file);
	 	            while (fileSize > 0) {
	 	                if (fileSize <= 5) {
	 	                    readLength = fileSize;
	 	                }
	 	                byteChunkPart = new byte[readLength];
	 	                read = inputStream.read(byteChunkPart, 0, readLength);
	 	                fileSize -= read;
	 	                nChunks++;
	 	                newFileName = file.getName() + ".part"
	 	                        + Integer.toString(nChunks - 1);
	 	                filePart = new FileOutputStream(new File(newFileName));
	 	                filePart.write(byteChunkPart);
	 	                filePart.flush();
	 	                filePart.close();
	                 byteChunkPart = null;
	 	                filePart = null;
	 	            }
	 	            inputStream.close();
	 	        } catch (IOException exception) {
	 	            exception.printStackTrace();
	 	        }
	 	    }
	 	    
	 	   public static void main(String[] args) {
	 		  File inputFile = new File("TextFile.txt");
	 		  new SplitFile(inputFile);
	 	   }
}
