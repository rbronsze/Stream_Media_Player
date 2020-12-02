/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.classes;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
//import org.farng.mp3.MP3File;
//import org.farng.mp3.TagException;
//import org.farng.mp3.id3.AbstractID3v2;

/**
 *
 * @author Polack
 */
public class MP3FileEditor{
    
    public Object[] getMP3FileInfo(String musicPath) throws IOException, ID3Exception{
        
        String title, artist, album;
        MP3File mp3file = new MP3File(new File(musicPath));
        try{
            
            title = mp3file.getID3V2Tag().getTitle();
        }catch(NullPointerException e){
            title = "";
        }
        
        try{
            artist = mp3file.getID3V2Tag().getArtist();
        }catch(NullPointerException e){
            artist = "";
        }
        
        try{
            album = mp3file.getID3V2Tag().getAlbum();
        }catch(NullPointerException e){
            album = "";
        }
        
        
        Image img;
        
        String s = getAlbumArt(musicPath);
        
        img = ImageIO.read(new File(s));
        
        Object[] out = new Object[]{title,
                                    artist,
                                    album,
                                    new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_DEFAULT)),
                                    s,
                                    musicPath};
        
        return out;
        
    }
    
//    public boolean setMP3MusicTitle(String musicPath, String value) throws IOException{
//        
//        MP3File mp3file = new MP3File(new File(musicPath));
//        
//        AbstractID3v2 tag = mp3file.getID3v2Tag();
//        tag.setSongTitle(value);
//        
//        mp3file.setID3v2Tag(tag);
//        
//        //mp3file.save(musicPath);
//        
//        mp3file.save();
//        
//        return true;
//        
//    }
//    
//    public boolean setMP3Artist(String musicPath, String value) throws IOException, TagException{
//        
//        MP3File mp3file = new MP3File(musicPath);
//        System.out.println(musicPath);
//        System.out.println(value);
//        
//        AbstractID3v2 tag = mp3file.getID3v2Tag();
//        tag.setLeadArtist("Prout");
//        
//        mp3file.setID3v2Tag(tag);
//        
//        //mp3file.save(musicPath);
//        
//        mp3file.save();
//        
//        return true;
//        
//    }
//    
//    public boolean setMP3AlbumTitle(String musicPath, String value) throws IOException, TagException{
//        
//        MP3File mp3file = new MP3File(musicPath);
//        
//        AbstractID3v2 tag = mp3file.getID3v2Tag();
//        tag.setLeadArtist(value);
//        
//        mp3file.setID3v2Tag(tag);
//        
//        //mp3file.save(musicPath);
//        
//        mp3file.save();
//        
//        return true;
//        
//    }
    
    private String getAlbumArt(String path){
        String[] parts;
        parts = path.split("\\\\");
        String out = "";
        
        for(int i = 0; i < parts.length-1; i++){
            out = out + parts[i] + "/";
        }
        out = out + "album_art.png";
        File f = new File(out);
        if(!f.exists()){
            out = "images/image.png";
        }
        
        return out;
        
    }
    
    
}
