import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.time.ZonedDateTime;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;

public class AnalogClock extends JFrame {

    private JTextField hourField, minuteField;
    private Clock clock;
    private JButton drawButton;
    private JLabel hourLabel, minuteLabel;
    private int hours, minutes;

    public AnalogClock(){
        JPanel contPanel=new JPanel();
        contPanel.setLayout(new BorderLayout());
        setContentPane(contPanel);
        Logger.getGlobal().info("" + contPanel.getLayout());
        clock=new Clock();
        /*component.addMouseMotionListener(new MouseAdapter(){

            public void mouseMoved(MouseEvent event){
                Logger.getGlobal().info("" + String.format("(%d,%d", event.getX(), event.getY()));
            }
        });*/
        contPanel.add(clock, BorderLayout.CENTER);
        pack();


        int secondsHandDelay=1000;
        Timer secondsHandTimer=new Timer(secondsHandDelay, new ActionListener(){

            public void actionPerformed(ActionEvent event){
                if(clock.secondAngle==360){
                    clock.secondAngle=0;
                }
                if(clock.secondAngle==270){
                    clock.minuteAngle+=6;
                    clock.hourAngle+=.5;
                }
                /*if(clock.minuteAngle==270 && clock.secondAngle==270){
                    clock.hourAngle+=30;
                }*/
                clock.secondAngle+=6;

                clock.repaint();
            }
        });
        secondsHandTimer.start();
    }

    public class Clock extends JComponent {


        private double hourAngle, minuteAngle, secondAngle;


        private Clock() {
            ZonedDateTime now = ZonedDateTime.now();
            int hour=5;
            int minute=59;
            int second=30;
            /*int hour = now.getHour()>12? now.getHour()-12: now.getHour();
            int minute = now.getMinute();

            int second = now.getSecond();
            Logger.getGlobal().info("Minute: " + minute);
            Logger.getGlobal().info("Second: " + second);*/

            hourAngle=hour>=3?(hour - 3) * 30 + (minute * .5):hour*30+(minute*.5)+270;
            minuteAngle=minute>15?(minute-15)*6:minute*6+270;
            secondAngle=second>15?(second-15)*6:second*6+270;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int radius = 100;
            double hourHandLength = radius * .6;
            double minuteHandLength = radius * .8;
            double secondsHandLength = radius * .8;
            Point center = new Point(200, 200);



            Logger.getGlobal().info("Seconds angle: " + secondAngle);
            Logger.getGlobal().info("Minutes angle: " + minuteAngle);
            Logger.getGlobal().info("Hour angle: " + hourAngle);

            g2d.drawOval((int) center.getX() - radius, (int) center.getY() - radius, radius * 2, radius * 2);
            // draw hour hand
            g2d.drawLine((int) center.getX(), (int) center.getY(), (int) center.getX() + (int) (hourHandLength * Math.cos(Math.toRadians(hourAngle))), (int) center.getY() + (int) (hourHandLength * Math.sin(Math.toRadians(hourAngle))));
            // draw minute hand
            g2d.drawLine((int) center.getX(), (int) center.getY(), (int) center.getX() + (int) (minuteHandLength * Math.cos(Math.toRadians(minuteAngle))), (int) center.getY() + (int) (minuteHandLength * Math.sin(Math.toRadians(minuteAngle))));
            // draw seconds hand
            g2d.drawLine((int) center.getX(), (int) center.getY(), (int) center.getX() + (int) (secondsHandLength * Math.cos(Math.toRadians(secondAngle))), (int) center.getY() + (int) (secondsHandLength * Math.sin(Math.toRadians(secondAngle))));
        }

        @Override
        public Dimension getPreferredSize() {
            int COMPONENT_WIDTH = 400;
            int COMPONENT_HEIGHT = 400;
            return new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT);

        }
    }

    private static void createAndShowGUI(){

        AnalogClock frame=new AnalogClock();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                AnalogClock.createAndShowGUI();
            }
        });
    }
}





