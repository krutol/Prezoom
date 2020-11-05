

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

//Class used to display the application's menu bar
public class MenuBar extends JMenuBar
{
    //VARIABLES
    public JMenu menu_file, menu_play, menu_help;
    public JMenuItem quit, newFile, openFile, saveFile;
    public JMenuItem playFromStart, playFromCurrent;
    public JMenuItem about;
    public JFileChooser fileChooser = null;


    public MenuBar()
    {
        MenuOptionsHandler itemHandler = new MenuOptionsHandler();   //create the Menu ActionListener

        menu_file = new JMenu("File");                                    //create the menu tabs and options
        menu_play = new JMenu("Play");
        menu_help = new JMenu("Help");

        //Items for File
        newFile = new JMenuItem("New File");
        openFile = new JMenuItem("Open File");
        saveFile = new JMenuItem("Save File");
        quit = new JMenuItem("Exit");

        newFile.addActionListener(itemHandler);                      //add actionListeners
        openFile.addActionListener(itemHandler);
        saveFile.addActionListener(itemHandler);
        quit.addActionListener(itemHandler);

        menu_file.add(newFile);                                           //add options to the menu
        menu_file.add(openFile);
        menu_file.add(saveFile);
        menu_file.addSeparator();
        menu_file.add(quit);

        //Items for View
        playFromStart = new JMenuItem("Play From Start");
        playFromCurrent = new JMenuItem("Play From Current");

        playFromStart.addActionListener(itemHandler);
        playFromCurrent.addActionListener(itemHandler);

        menu_play.add(playFromStart);
        menu_play.add(playFromCurrent);


        //Items for Help
        about = new JMenuItem("About");
        about.addActionListener(itemHandler);
        menu_help.add(about);


        add(menu_file);
        add(menu_play);
        add(menu_help);
    }

    //FILE METHODS

    public JFileChooser getFileChooser()
    {
        if (fileChooser ==null)
        {
            fileChooser = new JFileChooser();                        //create file chooser
            //fileChooser.setFileFilter(new PNGFileFilter());         //set file extension to .png
        }
        return fileChooser;
    }

    public static BufferedImage getScreenShot(Component component)    //used to get the current image drawn on the screen
    {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());   // paints into image's Graphics
        return image;
    }

    //ACTION EVENTS
    private class MenuOptionsHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == quit)          //if Exit application
            {
                Main.app.dispose();
//                System.exit(0);
            }
            if (event.getSource() == newFile)       //if New File
            {

//                BufferedImage bi = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);   //create new BufferedImage
//                Main.paint.drawingPanel.clearImage(bi);                                         //clear current image
//               Main.paint.drawingPanel.setImage(bi);                                           //set image to new blank image
            }
            if (event.getSource() == saveFile)      //if Save file
            {
//                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
//                int result = jFileChooser.showSaveDialog(Main.paint.drawingPanel);
//                if (result== JFileChooser.APPROVE_OPTION )
//                {
//                    try
//                    {
//                        File selectedFile = jFileChooser.getSelectedFile();
//                        selectedFile = new File(selectedFile.getAbsolutePath() + ".png");      //get isSelected file
//                        BufferedImage img = getScreenShot(Main.paint.drawingPanel);            //get current image screenshot
//                        ImageIO.write(img, "png", selectedFile);                               //write the image to the isSelected file
//                    } catch (IOException ioe)
//                    {
//                        JOptionPane.showMessageDialog(null, "Could not save the file");
//                    }
//                }
            }
            if (event.getSource() == openFile)       //if Open file
            {
//                JFileChooser ch = getFileChooser();                                            //open file chooser
//                int result = ch.showOpenDialog(Main.paint.drawingPanel);
//                if (result== JFileChooser.APPROVE_OPTION )                                      //if OK
//                {
//                    try
//                    {
//                        Main.paint.drawingPanel.setOSImage(ImageIO.read(ch.getSelectedFile())); //set current image to isSelected image
//                    } catch (IOException ex)
//                    {
//                        JOptionPane.showMessageDialog(null, "Could not open file");
//                    }
//                }
            }
//            if (event.getSource() == howToPaint)       //if Help
//            {
//                JOptionPane.showMessageDialog(null, "Use the tool buttons on the left to paint components on the screen.\n" +
//                        "Change the color of the components by selecting a color from the palette.\n" +
//                        "Change the stroke of the components by moving the slider on the left.");
//            }
            if (event.getSource() == about)         //if About
            {
                JOptionPane.showMessageDialog(null, "This application was made for the purpose of the ENGI-9874 Project.\n\n" +
                        "Created by: Team Charlie\nTeam Members: Abhishek Sharma, P.Ajanthan, Tianxing Li, Zhijie Lan, Ziyang Li\nCreated date: 01 November 2020");
            }
        }
    }

    //FILE EXTENSION CLASS

//    private static class PNGFileFilter extends FileFilter
//    {
//        public boolean accept(File file)             //filer files to display
//        {
//            return file.getName().toLowerCase().endsWith(".png") || file.isDirectory();
//        }
//
//        public String getDescription()
//        {
//            return "PNG image  (*.png) ";
//        }
//    }

}