/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream;

import audioforstream.controler.Controler;
import audioforstream.model.AudioLauncher;
import audioforstream.view.Fenetre;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import org.farng.mp3.TagException;




/**
 *
 * @author Polack
 */
public class AudioForStream {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        
        
        AudioLauncher m = new AudioLauncher();
        Fenetre f = new Fenetre(m.getMediaPlayer().audio().volume());
        Controler c = new Controler(f, m);
        m.setView(f);
        f.setControler(c);
        //f.start("sfx_ryu.mp3");
        try {
            Thread.currentThread().join();
        }
        catch(InterruptedException e) {
        }
       
    }
    
}
