/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioforstream.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author Polack
 */
public class SouthPanel extends JPanel{
    
    private JButton previous = new JButton("Previous");
    private JButton play = new JButton("Play");
    private JButton next = new JButton("Next");
    private JButton stop = new JButton("Stop");
    
    private JSlider slide = new JSlider();
    
    
    public SouthPanel(){
        
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(previous)
                .addComponent(play)
                .addComponent(next)
                .addComponent(stop)
                .addComponent(slide)
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(previous)
                    .addComponent(play)
                    .addComponent(next)
                    .addComponent(stop))
                .addComponent(slide)
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
    
    
    
}
