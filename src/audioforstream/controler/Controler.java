/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.controler;

import audioforstream.classes.CopyFile;
import audioforstream.classes.MP3FileEditor;
import audioforstream.classes.SongTableModel;
import audioforstream.classes.XMLFileEditor;
import audioforstream.model.AudioLauncher;
import audioforstream.view.Fenetre;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.blinkenlights.jid3.ID3Exception;
//import org.farng.mp3.TagException;
import org.xml.sax.SAXException;

/**
 *
 * @author Polack
 */
public class Controler {
    
    private static final String FILE_EXISTS = "This file already exists!!!";
    private static final String FILE_SAVED = "File saved!!!";
    private static final String EMPTY_LIST = "List is empty!!!";
    
    private static final String NODE_NAME = "Music";
    private static final String[] ATTR_NAME = {"music_title", "music_artist", "music_album", "art_path", "music_path"};
    
    
    private JFileChooser fileChooser;
    private Fenetre view;
    private AudioLauncher model;
    
    public Controler(Fenetre view, AudioLauncher model){
        
        fileChooser = new JFileChooser();
        
        this.view = view;
        this.model = model;
        
    }
    
    public void controlVolume(int volume){
        
        if (volume <= 100 && volume >= 0){
            model.getMediaPlayer().audio().setVolume(volume);
        }
        
    }
    
    public void controlPrevious(){
        
        if(!model.isPlayedListEmpty()){
                //play.setText("Pause");
            model.previousSong();
            
        }
        
    }
    
    public void controlPlay(int row, int nbRows) throws IOException{
        
        if(row != -1){
            prepareSong(row);
        }else{
            if(nbRows > 0){
                if(model.isPaused()){

                    model.getMediaPlayer().controls().play();
                    view.getPlay().setText("Pause");

                }else if(model.getMediaPlayer().status().isPlaying()){

                    model.pause();
                    view.getPlay().setText("Play");

                }else{

                    prepareSong(-1);

                }
            }
        }
        
    }
    
    private void prepareSong(int row) throws IOException{
        
        try {
            model.prepareSong(row);
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        CopyFile.copyFileUsingStream(new File("images/fond_musique.png"), new File("images/fond_musique_obs.png"));
        view.getPlay().setText("Pause");
    }
    
    public void controlNext(int nbRows) throws IOException{
        
        if(nbRows > 0){
            model.nextSong();
            view.getPlay().setText("Pause");
        }else{
            controlStop();
        }
    }
    
    public void controlStop() throws IOException{
        
        model.stop();
        CopyFile.copyFileUsingStream(new File("images/empty.png"), new File("images/fond_musique_obs.png"));
        view.getPlay().setText("Play");
        
    }
    
    public void controlAddMusic() throws ParserConfigurationException, SAXException, IOException, ID3Exception{
        
        File[] songFiles = this.getMultipleFiles();
        
        if(songFiles != null){
            for(int file = 0; file < songFiles.length; file++){
                
                String songPath = songFiles[file].getAbsolutePath();
                
                if(songPath.endsWith(".mp3")){
                    ((SongTableModel)this.view.getTab().getModel()).addRow(new MP3FileEditor().getMP3FileInfo(songPath));
                }
            }
        }
        
//        if(songPath.endsWith(".mp3")){
//            ((SongTableModel)this.view.getTab().getModel()).addRow(new MP3FileEditor().getMP3FileInfo(songPath));
//            
////            System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 0));
////            System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 1));
////            System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 2));
////            System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 4));
////            System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 5));
//            //model.addSongToList(songPath);
//        }
        
    }
    
    public void controlRemoveMusic(int row , int nbrows){
        
        if(row >= 0 && row < nbrows){
            ((SongTableModel)this.view.getTab().getModel()).removeRow(row);
        }
        
    }
    
    public void controlOpenFile(int nbrows) throws ParserConfigurationException, SAXException, IOException{
        
        String songPath = openFileChooser();
        if(songPath.endsWith(".xml")){
            System.out.println(songPath);
            if(nbrows > 0){
                //this.model.removeAll();
                stopSongIfNeeded();
            }
            ((SongTableModel)this.view.getTab().getModel()).removeAll();
            // Fill Table with songs
            new XMLFileEditor().openXMLFile(songPath, ((SongTableModel)this.view.getTab().getModel()), NODE_NAME);
            
            // Name of file into JTextField
            File file = new File(songPath);
            String[] output = file.getName().split(".xml");
            System.out.println(output[0]);
            this.view.getJtf().setText(output[0]);
        }
        
    }
    
    public void controlSaveFile(String listName, int nbrows, int nbcols) throws ParserConfigurationException, TransformerException{
        
        if(nbrows > 0 && nbcols > 0){
            File file = new File("xml/" + listName + ".xml");
            
            new XMLFileEditor().writeXMLFile(listName, nbrows, nbcols-1, ((SongTableModel)this.view.getTab().getModel()).getData(), NODE_NAME);
    //                writeXMLFile(String fileName, int nbrows, int nbcols, Object[][] tab, String[] attrName, String nodeName)
            this.view.getJlb().setBackground(Color.green);
            this.view.getJlb().setText(FILE_SAVED);
                
        }else{
            this.view.getJlb().setBackground(Color.red);
            this.view.getJlb().setText(EMPTY_LIST);
        }
        
        
    }
    
    public void controlNew(int nbrows){
        
        if(nbrows > 0){
            stopSongIfNeeded();
            ((SongTableModel)this.view.getTab().getModel()).removeAll();
        }
        
    }
    
    public void controlArt(int row, int column, int nbrow, int nbcol) throws IOException{
        
        if(row >= 0 && row < nbrow && column >= 0 && column < nbcol){
            
            if(column == 3){
                
                String imgPath = openFileChooser();
                
                if(imgPath.endsWith(".png")){
                    Image img = (ImageIO.read(new File(imgPath))).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                    ImageIcon icon = new ImageIcon(img);
                    
                    ((SongTableModel)this.view.getTab().getModel()).setValueAt(icon, row, column);
                    ((SongTableModel)this.view.getTab().getModel()).setValueAt(imgPath, row, column+1);
                }
                
                System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 0));
                System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 1));
                System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 2));
                System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 4));
                System.out.println(((SongTableModel)this.view.getTab().getModel()).getValueAt(0, 5));
                
            }
             
        }
        
    }
    
    
    public void controlUpdate(int nbrow){
        
        if(nbrow == 0){
            model.resetLists();
            model.setTemp(-1);
        }
    }
    
    public void controlInsert(int row, int nbrow){
        
        if(row >= 0 && row < nbrow && model.getMediaPlayer().status().isPlaying()){
            this.model.getWaiting_list().add(row);
        }
//        displayList(this.model.getWaiting_list(), "Waiting List: ");
//        displayList(this.model.getPlayed_list(), "Played List: ");
        
    }
    
    public void controlDelete(int row, int nbrow) throws IOException{
        
        System.out.println("row: " + row);
        System.out.println("nbrow: " + nbrow);
        
        if(row == this.model.getTrack()){
            controlStop();
            //controlNext(nbrow);
        }
            
        if(!this.model.isWaitingListEmpty()){
            this.model.removeWaitingListTrack(row,nbrow);
            //displayList(this.model.getWaiting_list(), "Waiting List: ");

        }

        if(!this.model.isPlayedListEmpty()){
            this.model.removePlayedListTrack(row,nbrow);
            //displayList(this.model.getPlayed_list(), "Played List: ");
        }
              
        
        
    }
    
    
    
    
    private void stopSongIfNeeded(){
        if(model.getMediaPlayer().status().isPlaying()){
            model.stop();
        }
    }
    
    private String openFileChooser(){
        
        fileChooser.setMultiSelectionEnabled(false);
        int returnVal = fileChooser.showOpenDialog(null);
        //System.out.println("returnVal: " + returnVal);
            
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                //fileChooser.getSelectedFiles();
                return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
        
    }
    
    private File[] getMultipleFiles(){
        
        fileChooser.setMultiSelectionEnabled(true);
        int returnVal = fileChooser.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFiles();
        }
        return null;
        
    }

    private void displayList(ArrayList<Integer> list, String text){
        
        System.out.println(text);
        for(int i : list)
            System.out.println("    Track: " + list.get(i));
        
    }
    
    
    
}
