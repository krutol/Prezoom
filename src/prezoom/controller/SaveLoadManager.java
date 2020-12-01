package prezoom.controller;

import prezoom.model.CameraInfoI;
import prezoom.model.GObject.GObject;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The class to mange saving and loading
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/28<p>
 **/
public class SaveLoadManager implements Serializable
{
    /**
     * camera info for all states
     */
    ArrayList<CameraInfoI> state_CamInfo_list;
    /**
     * all GObjects
     */
    ArrayList<GObject> gObjectList;
    /**
     * the total number of states
     */
    int  total_State_Number;

    /**
     * constructor of the save load manager
     * @param isSave true to fetch the latest {@link CameraManager#state_CamInfo_list},
     *               {@link GObjectManager#gObjectList}, and {@link StateManager#getTotal_State_Number()}
     *               to be ready to save data.
     */
    public SaveLoadManager(boolean isSave)
    {
        if (isSave)
        {
            total_State_Number = StateManager.getTotal_State_Number();
            state_CamInfo_list = CameraManager.state_CamInfo_list;
            gObjectList = GObjectManager.gObjectList;
        }
    }

    /**
     * serialize the data to local file
     * @param selectedFile the output file information
     */
    public void save(File selectedFile)
    {
        try
        {
            ObjectOutput output = new ObjectOutputStream(new FileOutputStream(selectedFile));
            output.writeObject(this);
            output.close();
        } catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Cannot save this file",
                    "Save failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * deserialize the local file to data,
     * if success, clear all current data, and reload this data
     * @param selectedFile the input file information
     */
    public void load(File selectedFile)
    {
        SaveLoadManager loadData = null;
        try
        {
            ObjectInput ins = new ObjectInputStream(new FileInputStream(selectedFile));
            loadData = (SaveLoadManager) ins.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Cannot open this file",
                    "Open failed", JOptionPane.ERROR_MESSAGE);
        }

        if (loadData != null)
        {
            StateManager.clearAllStateData();

            GObjectManager.gObjectList = loadData.gObjectList;
            CameraManager.state_CamInfo_list = loadData.state_CamInfo_list;
            StateManager.setTotal_State_Number(loadData.total_State_Number);

            StateManager.reloadSavedData();
        }

    }
}
