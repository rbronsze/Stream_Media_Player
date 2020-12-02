/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.classes;

import audioforstream.view.Fenetre;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Polack
 */
public class StreamingFilesEditor {
    
    public void setFiles(String song, String artist, String album, String art){
        try {
            writeFile(" " + song + " ", "title.txt");
            writeFile(" " + artist + " ", "artist.txt");
            writeFile(" " + album + " ", "album.txt");

            copyFileUsingStream(new File(art), new File("image.png"));

        } catch (IOException ex) {
            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(String line, String file) throws IOException{
        
        FileWriter fw = new FileWriter(file);
        fw.write(line);
        fw.close();
    }
    
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    
    
    
}
