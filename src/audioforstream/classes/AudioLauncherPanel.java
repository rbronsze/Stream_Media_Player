/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.classes;

import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author Polack
 */
public class AudioLauncherPanel extends JPanel{
    
    private JButton previous = new JButton("Previous");
    private JButton play = new JButton("Play");
    private JButton next = new JButton("Next");
    private JButton stop = new JButton("Stop");
    private JSlider slide = new JSlider();
    
    private JLabel jlb = new JLabel();
    
    private JLabel albumImg = new JLabel();
    
    private JLabel title = new JLabel();
    private JLabel artist = new JLabel();
    private JLabel album = new JLabel();

    public AudioLauncherPanel() {
        
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()    
                        .addComponent(previous)
                        .addComponent(play)
                        .addComponent(next)
                        .addComponent(stop)
                        .addComponent(slide))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(albumImg)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(title)
                            .addComponent(artist)
                            .addComponent(album)))
                    .addComponent(jlb)));
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                    .addComponent(previous)
                    .addComponent(play)
                    .addComponent(next)
                    .addComponent(stop)
                    .addComponent(slide))
            .addGroup(layout.createParallelGroup(LEADING)
                    .addComponent(albumImg)
                    .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(title))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(artist))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(album))))
            .addComponent(jlb)
        );
                    
    }

    public JButton getPrevious() {
        return previous;
    }

    public JButton getPlay() {
        return play;
    }

    public JButton getNext() {
        return next;
    }

    public JButton getStop() {
        return stop;
    }

    public JSlider getSlide() {
        return slide;
    }

    public JLabel getAlbumImg() {
        return albumImg;
    }

    public JLabel getTitle() {
        return title;
    }

    public JLabel getArtist() {
        return artist;
    }

    public JLabel getAlbum() {
        return album;
    }
    
    

    public JLabel getJlb() {
        return jlb;
    }
    
    
    
}
