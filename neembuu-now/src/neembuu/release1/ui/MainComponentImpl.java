/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;

/**
 *
 * @author Shashank Tulsyan
 */
public class MainComponentImpl implements MainComponent{
    private final JFrame jf;

    public MainComponentImpl(JFrame jf) {
        this.jf = jf;
    }
    
    @Override public JFrame getJFrame() { return jf; }

    @Override public Message newMessage() {
        return new MessageImpl();
    }
    
    private class MessageImpl implements Message {
        private String message,title;
        private int type = JOptionPane.INFORMATION_MESSAGE; 
        private Emotion e; private int timeout = 0;
        private JTextArea jta = null; private volatile JDialog dialog = null;
        
        @Override public Message error() {
            type = JOptionPane.ERROR_MESSAGE;return this;
        }

        @Override public Message info() {
            type = JOptionPane.INFORMATION_MESSAGE;return this;
        }

        @Override public Message setTitle(String title) {
            this.title = title;return this;
        }

        @Override public Message setMessage(String message) {
            this.message = message; if(message.length() > 1024 && jta==null)editable();
            if(jta!=null){
                jta.setText(message);jta.repaint();
            }
            return this;
        }

        @Override public Message setEmotion(Emotion e) {
            this.e=e; return this;
        }
        
        @Override public boolean ask(){
            Icon i = getIconForEmotion(e);
            type = JOptionPane.QUESTION_MESSAGE;
            int x = JOptionPane.showConfirmDialog(jf,message,title,JOptionPane.YES_NO_OPTION,type,i);
            
            return x==JOptionPane.YES_OPTION;
        }

        @Override public Message editable() {
            jta = new JTextArea(); return this;
        }
        
        @Override public String askPassword(){
            Icon i = getIconForEmotion(e);
            JPasswordField pf = new JPasswordField();
            JPanel jp = new JPanel(new GridLayout(0, 1, 5, 5));
            String[]messages=message.split("\n");
            for (String message_i : messages) {
                JLabel messageLB = new JLabel(message_i);
                jp.add(messageLB);
            }
            jp.add(pf);
            int okCxl = JOptionPane.showConfirmDialog(jf, jp, title, JOptionPane.OK_CANCEL_OPTION, type,i);
            if (okCxl == JOptionPane.OK_OPTION) {
              String password = new String(pf.getPassword());
              return password;
            }return null;
        }
        
        @Override public Message setTimeout(int timeout){
            this.timeout = timeout; return this;
        }

        
        @Override public void close() {
            dialog.setVisible(false);
        }
        
        @Override public void show() { showImpl(false); }
        @Override public Message showNonBlocking() { showImpl(true); return this; }
        
        private void showImpl(boolean notBlock){
            final Icon i = getIconForEmotion(e);
            
            Object m = message;
            
            if(message!=null){
                if(jta!=null){
                    jta.setText(message);
                    JScrollPane jsp = new JScrollPane(jta);
                    Dimension sz = new Dimension(150,150);
                    jsp.setMinimumSize(sz);jsp.setPreferredSize(sz);
                    jsp.setMaximumSize(sz);
                    m = jsp;
                }
            }
            
            final JOptionPane pane = new JOptionPane(m,type,JOptionPane.DEFAULT_OPTION,i);
            dialog = pane.createDialog(jf, title);
            
            if(notBlock){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override public void run() {
                        showC(dialog);
                    }});
            }else {
                showC(dialog);
            }
        }
        
        private void showC(final JDialog dialog){
            Timer t = new Timer(100,new ActionListener() {
                private int totalDelay = 0;
                @Override public void actionPerformed(ActionEvent e) {
                    totalDelay+=((Timer)e.getSource()).getDelay();
                    if(totalDelay>timeout){
                        ((Timer)e.getSource()).stop();
                        dialog.setVisible(false);
                    }else{
                        dialog.setTitle(title+" ..."+((timeout - totalDelay)/1000)
                                + " sec(s)");
                    }
                }
            }); if(timeout > 0) t.start();
            dialog.setVisible(true); dialog.dispose();
        }
        
        private Icon getIconForEmotion(Emotion e){
            if(e==Emotion.I_AM_DEAD){
                return neembuu.config.GlobalTestSettings.ONION_EMOTIONS.getQuestionImageIcon("Onion16.gif");
            }
            return null;
        }
        
    }
    
}
