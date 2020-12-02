/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.model;

import audioforstream.classes.CopyFile;
import audioforstream.classes.SongTableModel;
import audioforstream.classes.StreamingFilesEditor;
import audioforstream.view.Fenetre;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.farng.mp3.MP3File;
//import org.farng.mp3.TagException;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

/**
 *
 * @author Polack
 */
public class AudioLauncher {
    
    private Fenetre view;
    
    private AudioPlayerComponent mediaPlayerComponent;
    private ArrayList<Integer> waiting_list, played_list;
    
    private boolean isUp = true, isPaused = false;
    
    private int temp = 0, track = 0;
    
//    private String path = "music/";
//    File repertoire = new File(path);
    
    public AudioLauncher() throws IOException{
        
        
        
        
        
        
        waiting_list = new ArrayList<Integer>();
        played_list = new ArrayList<Integer>();
        new StreamingFilesEditor().setFiles("","","","images/empty.png");
        mediaPlayerComponent  = new AudioPlayerComponent();
        mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                
                mediaPlayer.submit(new Runnable() {
                    @Override
                    public void run() {
                        if(temp > -1){
                            waiting_list.remove(temp);
                            played_list.add(track);
                        }
//                        else{
//                            stop();
//                        }
                        try {
                            prepareSong(-1);
                        } catch (IOException ex) {
                            Logger.getLogger(AudioLauncher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
            }
            
            public void playing(MediaPlayer mediaPlayer) {
                isPaused = false;
            }
            
            @Override
            public void paused(MediaPlayer mediaPlayer) {
                isPaused = true;
                System.out.println(mediaPlayerComponent.mediaPlayer().status().isPlaying());
                
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                exit(1);
            }
        });
        
        
        
    }
    
    public MediaPlayer getMediaPlayer(){
        return mediaPlayerComponent.mediaPlayer();
    }
    
    public void setView(Fenetre view) {
        this.view = view;
    }
    
    public boolean isWaitingListEmpty(){
        return this.waiting_list.isEmpty();
    }
    
    public boolean isPlayedListEmpty(){
        return this.played_list.isEmpty();
    }
    
    public boolean isUp(){
        return this.isUp;
    }
    
    public void setIsUp(){
        this.isUp = !(this.isUp);
    }
    
    public boolean isPaused(){
        return this.isPaused;
    }
    
    public void setIsPaused(){
        this.isPaused = !(this.isPaused);
    }

    public ArrayList<Integer> getWaiting_list() {
        return waiting_list;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getTrack() {
        return track;
    }
    
    
    
    public ArrayList<Integer> getPlayed_list() {
        return played_list;
    }
    
    public void resetLists(){
        waiting_list = new ArrayList<Integer>();
        played_list = new ArrayList<Integer>();
    }
    
    
    public void removeWaitingListTrack(int row, int nbrow){
        
        int index = -1;//index
        if(track == row){
            temp = -1;
            track = -1;
        }
        if(track > row){
            track--;
        }
        for(int i = 0; i < this.waiting_list.size(); i++){
            
            if(this.waiting_list.get(i) == row){
                index = i;
            }
            if(this.waiting_list.get(i) > row){
                int t = this.waiting_list.get(i)-1;
                this.waiting_list.set(i, t);
                if(track == t){
                    temp = i;
                }
            }
        }
        if(index > -1)
            this.waiting_list.remove(index);
        
    }
    
    public void removePlayedListTrack(int row, int nbrow){
        
        int nb = -1;
        for(int i = 0; i < this.played_list.size(); i++){
            if(this.played_list.get(i) > row){
                int t = this.played_list.get(i)-1;
                this.played_list.set(i, t);
                
            }
            if(this.played_list.get(i) == row){
                nb = i;
            }
        }
        
        if(nb > -1)
            this.played_list.remove(nb);
        
    }
    
    public void stop(){
        
        mediaPlayerComponent.mediaPlayer().controls().stop();
        new StreamingFilesEditor().setFiles("","","","images/empty.png");
        view.setMusicTitle("");
        view.setArtist("");
        view.setAlbum("");
        try {
            view.setAlbumArt("image.png");
        } catch (IOException ex) {
            Logger.getLogger(AudioLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        isUp = true;
    }
      
    public void play(){
        mediaPlayerComponent.mediaPlayer().controls().play();
    }            
                
    
    public void pause(){
        
        mediaPlayerComponent.mediaPlayer().controls().pause();
    }
    
    
    
    public void nextSong() throws IOException{
        
        if(!isUp){
            waiting_list.remove(temp);
            played_list.add(track);
        }

        prepareSong(-1);
    }
    
    public void previousSong(){
        
        isUp = true;
        if(!played_list.isEmpty()){
                
            track = played_list.get(played_list.size()-1);
            played_list.remove(played_list.size()-1);
            waiting_list.add(track);
            temp = waiting_list.size()-1;
            launchSong();
            
        }
    }
    
    public void prepareSong(int row) throws IOException{
        
        if(waiting_list.isEmpty())
            setLists();
        
        if(row == -1){
            temp = ThreadLocalRandom.current().nextInt(0, waiting_list.size());
            track = waiting_list.get(temp);
        }else{
            temp = row;
            track = waiting_list.get(temp);
        }
        
        launchSong();
  
    }
    
    
    
//    public void setSongList(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException{
//        
//        songList = openXMLFile(xmlFilePath);
//        
//    }
    
//    public void addSongToList(String songPath) throws IOException{
//        
////        MP3File mp3file = new MP3File(new File(songPath));
//
//        //this.songList.add(songPath);
//        MP3File mp3file = new MP3File(songPath);
//        Object[] donnee = new Object[]{ mp3file.getID3v2Tag().getSongTitle(),
//                                        mp3file.getID3v2Tag().getLeadArtist(),
//                                        mp3file.getID3v2Tag().getAlbumTitle(),
//                                        null,
//                                        songPath};
//        
//        
//        ((SongTableModel)this.view.getTab().getModel()).addRow(donnee);
//        
//        
//        
//        //view.getTab().
////        FrameBodyAPIC f;
////        
////                
////        AbstractID3v2 tag = mp3file.getID3v2Tag();
////        
////        tag.setSongTitle("Prout");
////        tag.setLeadArtist("LeProut");
////        tag.setAlbumTitle("Reprout");
////        tag.set
//        
////        System.out.println(mp3file.getID3v2Tag().getSongTitle());
////        System.out.println(mp3file.getID3v2Tag().getLeadArtist());
////        System.out.println(mp3file.getID3v2Tag().getAlbumTitle());
//        
////        mp3file.setID3v2Tag(tag);
////        
////        System.out.println(mp3file.getID3v2Tag().getSongTitle());
////        System.out.println(mp3file.getID3v2Tag().getLeadArtist());
////        System.out.println(mp3file.getID3v2Tag().getAlbumTitle());
////        
////        mp3file.save();
//        
////        mp3_list.get(track).getID3v2Tag().getSongTitle(),
////                 mp3_list.get(track).getID3v2Tag().getLeadArtist(), 
////                 mp3_list.get(track).getID3v2Tag().getAlbumTitle(),
//        
//        
//    }
    
    private void start(String mrl) {
        mediaPlayerComponent.mediaPlayer().media().play(mrl);
    }

    private void exit(int result) {
        // It is not allowed to call back into LibVLC from an event handling thread, so submit() is used
        mediaPlayerComponent.mediaPlayer().submit(new Runnable() {
            @Override
            public void run() {
                mediaPlayerComponent.mediaPlayer().release();
                System.exit(result);
            }
        });
    }
    
    private ArrayList<Integer> getList(int nb) throws IOException{
        
        ArrayList<Integer> mp3_list = new ArrayList<Integer>();
        
        for(int i = 0; i < nb; i++){
                mp3_list.add(i);
        }
        return mp3_list;
        
    }
    
    private void setLists() throws IOException{
        
        waiting_list = getList((((SongTableModel)this.view.getTab().getModel()).getRowCount()));
        played_list = new ArrayList<Integer>();
    }
    
    private void launchSong(){
        
        String title = (((SongTableModel)this.view.getTab().getModel()).getValueAt(track, 0)).toString();
        String artist = (((SongTableModel)this.view.getTab().getModel()).getValueAt(track, 1)).toString();
        String album = (((SongTableModel)this.view.getTab().getModel()).getValueAt(track, 2)).toString();
        String art = (((SongTableModel)this.view.getTab().getModel()).getValueAt(track, 4)).toString();
        
        view.setMusicTitle(title);
        view.setArtist(artist);
        view.setAlbum(album);
        try {
            view.setAlbumArt(art);
        } catch (IOException ex) {
            Logger.getLogger(AudioLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new StreamingFilesEditor().setFiles(title, artist, album, art);

        String str = (((SongTableModel)this.view.getTab().getModel()).getValueAt(track, 5)).toString();
        start(str);
        
        isUp = false;
        
        view.repaint();
        view.revalidate();
        
        
    }
    
    
    

    
    
    
}
