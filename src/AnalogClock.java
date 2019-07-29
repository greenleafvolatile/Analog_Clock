import java.awt.*;
import java.time.ZonedDateTime;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class AnalogClock extends JFrame {

    private final Clock clock;

    private AnalogClock(){
        // set JPanel as content pane so I can add a border to it.
        JPanel contPanel=new JPanel();
        contPanel.setBorder(new LineBorder(Color.RED, 2));
        contPanel.setLayout(new BorderLayout());
        setContentPane(contPanel);

        // Construct clock object and add to content pane.
        clock=new Clock();
        contPanel.add(clock, BorderLayout.CENTER);
        pack();


        // Construct Timer object that moves the hands of the clock.
        int secondsHandDelay=1000;
        Timer secondsHandTimer=new Timer(secondsHandDelay, event -> {
            if(clock.secondAngle==360)
                clock.secondAngle=0;
            if(clock.secondAngle==270){
                clock.minuteAngle+=6;
                clock.hourAngle+=.5;
            }
            clock.secondAngle+=6;
            clock.repaint();
        });
        secondsHandTimer.start();
    }

    class Clock extends JComponent {


        private double hourAngle, minuteAngle, secondAngle;


        private Clock() {
            // Get the actual system time.
            ZonedDateTime now = ZonedDateTime.now();
            int hour = now.getHour()>12? now.getHour()-12: now.getHour();
            int minute = now.getMinute();
            int second = now.getSecond();

            // Because y-axis is flipped, 3 o' clock is 0 degrees, 6 o' clock is 90 degrees, 9 o' clock is 180 degrees and 12 o' clock is 270 degrees.
            hourAngle=hour>=3?(hour - 3) * 30 + (minute * .5):hour*30+(minute*.5)+270;
            minuteAngle=minute>15?(minute-15)*6:minute*6+270;
            secondAngle=second>15?(second-15)*6:second*6+270;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int radius = 100;
            double hourHandLength = radius * .75;
            double minuteHandLength = radius * .9;
            double secondsHandLength = radius * .9;
            Point center = new Point(200, 200);



            // populate clock with numbers
            int angle=300; // one o' clock point on the circle.
            for(int i=1;i<=12;i++){
                String number="" + i;
                double numberWidth=g2d.getFontMetrics().getStringBounds(number, g2d).getWidth();
                double numberHeight=g2d.getFontMetrics().getStringBounds(number, g2d).getHeight();
                g2d.drawString(number, (int) (center.getX() + secondsHandLength * Math.cos(Math.toRadians(angle)) - (numberWidth/2)), (int) (center.getY() + secondsHandLength * Math.sin(Math.toRadians(angle))+(numberHeight/4))); // Why does stringHeight/4 look better than stringHeight/2??
                if(angle==360){
                    angle=0;
                }
                angle+=30;
            }

            // draw clock face
            g2d.drawOval((int) center.getX() - radius, (int) center.getY() - radius, radius * 2, radius * 2);
            g2d.fillOval((int) center.getX()-((radius*2)/20)/2, (int) center.getY()-((radius*2)/20)/2, (radius*2)/20, (radius*2)/20);
            // draw hour hand
            g2d.drawLine((int) center.getX(), (int) center.getY(), (int) center.getX() + (int) (hourHandLength * Math.cos(Math.toRadians(hourAngle))), (int) center.getY() + (int) (hourHandLength * Math.sin(Math.toRadians(hourAngle))));
            // draw minute hand
            g2d.drawLine((int) center.getX(), (int) center.getY(), (int) center.getX() + (int) (minuteHandLength * Math.cos(Math.toRadians(minuteAngle))), (int) center.getY() + (int) (minuteHandLength * Math.sin(Math.toRadians(minuteAngle))));
            // draw seconds hand
            g2d.setColor(Color.RED);
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
        SwingUtilities.invokeLater(() -> AnalogClock.createAndShowGUI());
    }
}





