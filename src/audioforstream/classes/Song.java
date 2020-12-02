/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.classes;

/**
 *
 * @author Polack
 */
public class Song {
    
    private String songAbsolutePath, albumArtAbsolutePath;
    
    public Song(String songAbsolutePath, String albumArtAbsolutePath){
        
        this.songAbsolutePath = songAbsolutePath;
        this.albumArtAbsolutePath = albumArtAbsolutePath;
        
    }

    public String getSongAbsolutePath() {
        return songAbsolutePath;
    }

    public void setSongAbsolutePath(String songAbsolutePath) {
        this.songAbsolutePath = songAbsolutePath;
    }

    public String getAlbumArtAbsolutePath() {
        return albumArtAbsolutePath;
    }

    public void setAlbumArtAbsolutePath(String albumArtAbsolutePath) {
        this.albumArtAbsolutePath = albumArtAbsolutePath;
    }
    
//    public String[] getSongInfo(){
//        
//        String s[] = {this.title, this.artist, this.album};
//        
//        return s;
//    }
    
}
