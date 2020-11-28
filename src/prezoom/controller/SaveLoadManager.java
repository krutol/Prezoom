package prezoom.controller;

import prezoom.model.CameraInfoI;
import prezoom.model.GObject;

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
    ArrayList<CameraInfoI> state_CamInfo_list;
    ArrayList<JTextArea> jTextAreaList;
    ArrayList<GObject> gObjectList;
    int  total_State_Number;

    public SaveLoadManager(boolean isSave)
    {
        if (isSave)
        {
            total_State_Number = StateManager.getTotal_State_Number();
            state_CamInfo_list = CameraManager.state_CamInfo_list;
            jTextAreaList = GObjectManager.jTextAreaList;
            gObjectList = GObjectManager.gObjectList;
        }
    }

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
        }
    }

    public void load(File selectedFile)
    {
        SaveLoadManager loadData = null;
        try
        {
            ObjectInput ins = new ObjectInputStream(new FileInputStream(selectedFile));
            loadData = (SaveLoadManager) ins.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot open this file");
        }

        if (loadData != null)
        {
            StateManager.clearAllStateData();

            GObjectManager.gObjectList = loadData.gObjectList;
            GObjectManager.jTextAreaList = loadData.jTextAreaList;
            CameraManager.state_CamInfo_list = loadData.state_CamInfo_list;
            StateManager.setTotal_State_Number(loadData.total_State_Number);

            StateManager.reloadSavedData();
        }

    }
}
