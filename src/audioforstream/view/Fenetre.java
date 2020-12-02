/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.view;

import audioforstream.classes.AudioLauncherPanel;
import audioforstream.classes.CopyFile;
import audioforstream.classes.SongTableModel;
import audioforstream.classes.StreamingFilesEditor;
import audioforstream.controler.Controler;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.blinkenlights.jid3.ID3Exception;
//import org.farng.mp3.TagException;
import org.xml.sax.SAXException;


/**
 *
 * @author Polack
 */

public class Fenetre extends JFrame{
    
    //JFileChooser fc = new JFileChooser();
    private JMenuBar menuBar = new JMenuBar();
    private Controler controler;
    
    ////// PopUp Menu //////
    
//    private JPopupMenu popup = new JPopupMenu();
//    
//    private JMenuItem playMenuItem = new JMenuItem("Play");
//    private JMenuItem editMenuItem = new JMenuItem(" ");
//    private JMenuItem deleteMenuItem = new JMenuItem("Delete");
    
    
    ////// File Menu //////
    
    private JMenu fileMenu = new JMenu("File");
    
    private JMenuItem newMenuItem = new JMenuItem("New");
    private JMenuItem openMenuItem = new JMenuItem("Open File");
    private JMenuItem saveMenuItem = new JMenuItem("Save File");
    private JMenuItem saveAsMenuItem = new JMenuItem("Save As");
    
    ////// List Menu //////
    
    private JMenu listMenu = new JMenu("List");
    
    private JMenuItem addMusicMenuItem = new JMenuItem("Add Music");
    //private JMenuItem newMenuItem = new JMenuItem("New");
    
    private JTextField jtf;
    
    ////// List //////
    
    private JTable tab;
    
    ////// Buttons //////
    
    private JPanel button_pan = new JPanel();
    private AudioLauncherPanel audioLauncherPane = new AudioLauncherPanel();
    private JButton previous; /*= new JButton("Previous");*/
    private JButton play; /*= new JButton("Play");*/
    private JButton next; /*= new JButton("Next");*/
    private JButton stop; /*= new JButton("Stop");*/
    
    ////// Volume //////
    
    private JSlider slide = new JSlider();
    private JLabel jlb, albumArt, title, artist, album;
    //private AudioLauncher audio;
    
    public Fenetre(int volume) throws IOException{
        previous = audioLauncherPane.getPrevious();
        play = audioLauncherPane.getPlay();
        next = audioLauncherPane.getNext();
        stop = audioLauncherPane.getStop();
        
        slide = audioLauncherPane.getSlide();
        
        albumArt = audioLauncherPane.getAlbumImg();
        title = audioLauncherPane.getTitle();
        artist = audioLauncherPane.getArtist();
        album = audioLauncherPane.getAlbum();
        
//        title.setText("Title");
//        artist.setText("Artist");
//        album.setText("Album");
        albumArt.setIcon(new ImageIcon(ImageIO.read(new File("image.png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        
        
        jlb = audioLauncherPane.getJlb();
        
        
//        try {
//            CopyFile.copyFileUsingStream(new File("images/fond_musique.png"), new File("images/fond_musique_obs.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
//        }
        initTableModel();
        
//        this.popup.add(this.playMenuItem);
//        this.popup.add(this.editMenuItem);
//        this.popup.add(this.deleteMenuItem);
        
        this.fileMenu.add(this.newMenuItem);
        this.fileMenu.add(this.openMenuItem);
        this.fileMenu.add(this.saveMenuItem);
        //this.fileMenu.add(this.saveAsMenuItem);
        
        this.menuBar.add(fileMenu);
        
        this.listMenu.add(this.addMusicMenuItem);
        
        this.menuBar.add(this.listMenu);
        
        jtf = new JTextField();
        jtf.setText("Song List");
        //System.out.println("/" + jtf.getText() + "/");
        
        button_pan.add(previous);
        button_pan.add(play);
        button_pan.add(next);
        button_pan.add(stop);
        
        slide.setMaximum(100);
        slide.setMinimum(0);
        
        slide.setValue(volume);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        slide.setMinorTickSpacing(10);
        slide.setMajorTickSpacing(20);
        
        //this.playMenuItem.addActionListener();
        //this.editMenuItem.addActionListener();
        //this.deleteMenuItem.addActionListener();
        
        this.newMenuItem.addActionListener(new NewMenuListener());
        this.openMenuItem.addActionListener(new OpenMenuListener());
        this.saveMenuItem.addActionListener(new SaveMenuListener());
        
        this.addMusicMenuItem.addActionListener(new AddMusicListener());
        
        
        play.addActionListener(new PlayListener()); 
        previous.addActionListener(new PreviousListener()); 
        next.addActionListener(new NextListener()); 
        stop.addActionListener(new StopListener()); 
        
        slide.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                //System.out.println(audio.getMediaPlayer().status().isPlaying());
                 controler.controlVolume(((JSlider)e.getSource()).getValue());//To change body of generated methods, choose Tools | Templates.
            }
        });  
        button_pan.add(slide);
        
        jlb = new JLabel();
        button_pan.add(jlb);
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                new StreamingFilesEditor().setFiles("","","","images/empty.png");
                try {
                    CopyFile.copyFileUsingStream(new File("images/empty.png"), new File("images/fond_musique_obs.png"));
                } catch (IOException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.setJMenuBar(menuBar);
        this.getContentPane().add(jtf, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(tab), BorderLayout.CENTER);
        this.getContentPane().add(/*button_pan*/audioLauncherPane, BorderLayout.SOUTH);
        
        this.setSize(600, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }

    public JButton getPlay() {
        return play;
    }

    public JTable getTab() {
        return tab;
    }

    public JLabel getJlb() {
        return jlb;
    }

    public JLabel getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) throws IOException {
        this.albumArt.setIcon(new ImageIcon(ImageIO.read(new File(albumArt)).getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
    }

    public JLabel getMusicTitle() {
        return title;
    }

    public void setMusicTitle(String title) {
        this.title.setText(title);
    }

    public JLabel getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.setText(artist);
    }

    public JLabel getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album.setText(album);
    }
    
    

    public JTextField getJtf() {
        return jtf;
    }
    
    public void initTableModel(){
        
        Object[][] data = new Object[0][6];
        
        String  title[] = {"Title", "Artist", "Album", "Art"};
        
        SongTableModel out = new SongTableModel(data, title){
            
            @Override
            public Class<?> getColumnClass(int column) {
                switch(column) {
                    case 3: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        };
        
        this.tab = new JTable(out);
//        this.tab.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        this.tab.setRowHeight(50);
        
        this.tab.addMouseListener(new ClickAdapter());
        
        tab.getModel().addTableModelListener(new CellChangedListener());
        
    }
//��
    
    ////// Class //////
    
    private class PopUpMenu extends JPopupMenu{
        
        private JMenuItem playMI, modifyMI, deleteMI;
        //private int row, column, nbrow, nbcol;
        
        public PopUpMenu(int row, int column, int nbrow, int nbcol){
            
//            this.row = row;
//            this.column = column;
//            this.nbrow = nbrow;
//            this.nbcol = nbcol;
            
            playMI = new JMenuItem("Play");
            modifyMI = new JMenuItem(" ");
            deleteMI = new JMenuItem("Delete");
            
            add(playMI);
            add(modifyMI);
            add(deleteMI);
            
            playMI.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        controler.controlPlay(row, nbrow);
                    } catch (IOException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            });
            
            deleteMI.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    controler.controlRemoveMusic(row, nbrow);
                }
                
            });
            
            
        }
        
    }

    private class ClickAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            
            int row = tab.rowAtPoint(e.getPoint());
            int column = tab.columnAtPoint(e.getPoint());
            int nbrow = ((SongTableModel)tab.getModel()).getRowCount();
            int nbcol = ((SongTableModel)tab.getModel()).getColumnCount();
            
            if(SwingUtilities.isRightMouseButton(e)){
                if(row >= 0 && row < nbrow && column >= 0 && column < nbcol){
                    
                    PopUpMenu popup = new PopUpMenu(row, column, nbrow, nbcol);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                    
                }
            }else{
                if(e.getClickCount() == 2){
                    try {
                        controler.controlArt(row, column, nbrow, nbcol);
                    } catch (IOException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
//            if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
//                
//            }
//            if (e.getClickCount() == 2 && SwingUtilities.isRightMouseButton(e)) {
//                controler.controlRemoveMusic(row, nbrow);
//            }
        }
            
    }
    
    private class AddMusicListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            
            try {
                controler.controlAddMusic();
            } catch (IOException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ID3Exception ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private class NewMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            controler.controlNew(((SongTableModel)tab.getModel()).getRowCount());
        }
    }

    private class OpenMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            
            int nbrow = ((SongTableModel)tab.getModel()).getRowCount();
            try {
                controler.controlOpenFile(nbrow);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    private class SaveMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            
            int nbrows = ((SongTableModel)tab.getModel()).getRowCount();
            int nbcols = ((SongTableModel)tab.getModel()).getColumnCount()+2;
            System.out.println("nbrows: " + nbrows);
            System.out.println("nbcols: " + nbcols);
            
            try {
                controler.controlSaveFile(jtf.getText(), nbrows, nbcols);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    class PreviousListener implements ActionListener{
        
        public void actionPerformed(ActionEvent arg0) {
            controler.controlPrevious();
        }
        
    }
    
    
    class PlayListener implements ActionListener{
        
        public void actionPerformed(ActionEvent arg0) {
            try {
                controler.controlPlay(-1, ((SongTableModel)tab.getModel()).getRowCount());
            } catch (IOException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    class NextListener implements ActionListener{
        
        public void actionPerformed(ActionEvent arg0) {
            try {
                controler.controlNext(((SongTableModel)tab.getModel()).getRowCount());
            } catch (IOException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    class StopListener implements ActionListener{
        
        public void actionPerformed(ActionEvent arg0) {
            try {
                controler.controlStop();
            } catch (IOException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    class CellChangedListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            
            int row = e.getFirstRow();
            int column = e.getColumn(); 
            int nbrow = ((SongTableModel)e.getSource()).getRowCount();
            int nbcolumn = ((SongTableModel)e.getSource()).getColumnCount()+2;
            // Cell update || Reset Table
//            if(e.getType() == e.UPDATE){
//                System.out.println("Update");
//                if(column > -1){
//                    Object musicPath = ((SongTableModel)e.getSource()).getValueAt(row, 4);
//                    Object value = ((SongTableModel)e.getSource()).getValueAt(row, column);
//                    try {
//                        controler.controlUpdate(column, musicPath.toString(), value);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (TagException ex) {
//                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
            if(e.getType() == e.UPDATE){
                controler.controlUpdate(nbrow);
            }
            if(e.getType() == e.DELETE){
                try {
                    controler.controlDelete(row, nbrow);
                } catch (IOException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(e.getType() == e.INSERT){
                controler.controlInsert(row, nbrow);
            }
            
            
            
            
            
            
            
            //System.out.println(row + " " + lrow + " " + column);
//            SongTableModel model = (SongTableModel)e.getSource();
//            Object data = model.getValueAt(row, column);
//            System.out.println(row + " " + column + " " + data);
            
        }
    }

}
