package prezoom.view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import prezoom.Main;
import prezoom.controller.GObjectManager;
import prezoom.controller.SaveLoadManager;

/** TODO
 * Class used to display the application's menu bar
 * @author Zhijie Lan<p>
 * create date: 2020/11/3
 **/
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

    private JFileChooser getFileChooser()
    {
        if (fileChooser ==null)
        {
            fileChooser = new JFileChooser();                        //create file chooser
            fileChooser.setFileFilter(
                    new FileNameExtensionFilter("PreZoom Presentation Project",
                            "pzm"));
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //fileChooser.setFileFilter(new PNGFileFilter());         //set file extension to .png
        }
        return fileChooser;
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
//                Color ori = GObjectManager.inspectedObj.getCurrentAttributes().getColor();
//                Color col = JColorChooser.showDialog(Main.app,"choose a color", ori);
//                if (col != null)
//                GObjectManager.inspectedObj.getCurrentAttributes().setColor(col);

//                BufferedImage bi = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);   //create new BufferedImage
//                prezoom.Main.paint.drawingPanel.clearImage(bi);                                         //clear current image
//               prezoom.Main.paint.drawingPanel.setImage(bi);                                           //set image to new blank image
            }
            if (event.getSource() == saveFile)      //if Save file
            {
                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showSaveDialog(null);
                if (result== JFileChooser.APPROVE_OPTION )
                {
                    File selectedFile = jFileChooser.getSelectedFile();
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".pzm");
                    SaveLoadManager saveLoadManager = new SaveLoadManager(true);
                    saveLoadManager.save(selectedFile);
                }
            }
            if (event.getSource() == openFile)       //if Open file
            {
                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showOpenDialog(null);
                if (result== JFileChooser.APPROVE_OPTION )                                      //if OK
                {
                    SaveLoadManager saveLoadManager = new SaveLoadManager(false);
                    saveLoadManager.load(jFileChooser.getSelectedFile());
                }
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