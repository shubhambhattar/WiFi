import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
/**
 *
 * @author shubhambhattar
 */
public class WiFiHotspot extends JFrame 
{
    public JLabel hotspot=new JLabel("Hotspot Name");
    public JLabel password=new JLabel("Password");
    public JTextField ssid=new JTextField();
    public JPasswordField key=new JPasswordField();
    public JToggleButton create = new JToggleButton("Start");
    public JRadioButton button=new JRadioButton("Show Password", false);
    public WiFiHotspot()
    {
        setLayout(new GridLayout(1, 1, 10, 10));
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.add(hotspot);
        p1.add(ssid);
        p1.add(password);
        p1.add(key);
        p1.setBorder(new TitledBorder("Enter credentials"));
        p1.add(create);
        p1.add(button);
        
        create.setToolTipText("Click to start hotspot");
        key.setToolTipText("Password must contain at least 8 characters");
        
        //p2.setLayout(new GridLayout(1, 2, 10, 100));
        //p2.add(create);
        //p2.add(button);
        
        add(p1);        
        //add(p2);
        
        CreateListenerClass run=new CreateListenerClass();
        ShowListenerClass pass=new ShowListenerClass();
        create.addItemListener(run);
        button.addItemListener(pass);
    }
    
    class CreateListenerClass implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(e.getStateChange()==ItemEvent.SELECTED)
            {
                String s=ssid.getText();
                String a=new String(key.getPassword());
                if(a.length()<8)
                {
                    System.out.println("Password must contain atleast 8 characters");
                }
                try{
                    PrintWriter target=new PrintWriter("Hotspot2.bat");
                    target.println("netsh wlan set hostednetwork mode=allow ssid="+s+" key="+a);
                    target.println("netsh wlan start hostednetwork");
                    target.println("pause");
                    target.close();
                    Runtime.getRuntime().exec(" cmd /c start Hotspot2.bat");
                    create.setText("Stop");
                }
                catch(Exception e1)
                {
                    System.out.println(e1);
                    System.exit(0);
                }
            }
            else
            {
                try{
                    PrintWriter target=new PrintWriter("Hotspot2.bat");
                    target.println("netsh wlan stop hostednetwork");
                    target.close();
                    Runtime.getRuntime().exec("cmd /c start Hotspot2.bat");
                    create.setText("Start");
                }
                catch(Exception e1)
                {
                    System.out.println(e1);
                    System.exit(0);
                }
            }
        }   
    }
    
    class ShowListenerClass implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if(e.getStateChange()==ItemEvent.SELECTED)
                key.setEchoChar((char)0);
            else
                key.setEchoChar('*');
        }        
    }

    public static void main(String[] args)
    {
        WiFiHotspot frame=new WiFiHotspot();
        frame.setTitle("WiFi Hotspot");
        frame.setSize(350, 160);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }    
}

