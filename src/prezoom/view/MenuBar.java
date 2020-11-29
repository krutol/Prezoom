package prezoom.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import prezoom.controller.PresentManager;
import prezoom.controller.SaveLoadManager;
import prezoom.controller.StateManager;

/**
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
    public JMenuItem about, instructions;
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
        instructions = new JMenuItem("Instructions");
        instructions.addActionListener(itemHandler);
        menu_help.add(instructions);

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
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit PreZoom?",
                        "Confirm Exit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION)
                    System.exit(0);
            }
            else if (event.getSource() == newFile)       //if New File
            {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to create a new project?",
                        "Confirm New", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION)
                    StateManager.clearAllStateData();

            }
            else if (event.getSource() == saveFile)      //if Save file
            {
                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showSaveDialog(null);
                if (result== JFileChooser.APPROVE_OPTION )
                {
                    File selectedFile = jFileChooser.getSelectedFile();
                    if (!selectedFile.getAbsolutePath().contains(".pzm"))
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".pzm");
                    SaveLoadManager saveLoadManager = new SaveLoadManager(true);
                    saveLoadManager.save(selectedFile);
                }
            }
            else if (event.getSource() == openFile)       //if Open file
            {
                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showOpenDialog(null);
                if (result== JFileChooser.APPROVE_OPTION )                                      //if OK
                {
                    SaveLoadManager saveLoadManager = new SaveLoadManager(false);
                    saveLoadManager.load(jFileChooser.getSelectedFile());
                }
            }
            else if (event.getSource() == instructions)       //if Help
            {
                JOptionPane.showMessageDialog(null,
                        "Controls:\n" +
                                "******************************\n"+
                                "Editing Mode:\n" +
                                "Left Click:   select objects\n" +
                                "        Drag:   drawing, dragging, or resizing objects\n"+
                                "Right Click:   deleting objects \n"+
                                "         Drag:   dragging the canvas\n"+
                                "Wheel:   zooming in/out the canvas\n"+
                                "******************************\n"+
                                "Presentation Mode:\n" +
                                "Left Click:   next state \n" +
                                "Right Click:   previous state \n"+
                                "         Drag:    dragging the canvas\n"+
                                "Wheel:   zooming in/out the canvas\n"+
                                "Left Key:   previous state\n"+
                                "Right Key:   next state\n"+
                                "Space:   resetting current state\n",
                        "Instruction",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else if (event.getSource() == about)         //if About
            {
                JOptionPane.showMessageDialog(null, "This application was made for the purpose of the ENGI-9874 Project.\n\n" +
                        "Created by: Team Charlie\nTeam Members: Abhishek Sharma, P.Ajanthan, Tianxing Li, Zhijie Lan, Ziyang Li\nCreated date: 01 November 2020",
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (event.getSource() == playFromStart)
                PresentManager.startPresent(true);
            else if (event.getSource() == playFromCurrent)
                PresentManager.startPresent(false);
        }
    }

}