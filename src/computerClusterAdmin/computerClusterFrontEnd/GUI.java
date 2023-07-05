package computerClusterAdmin.computerClusterFrontEnd;

import java.io.File;

import computerClusterAdmin.clusterGUI.Control.Controller;
import computerClusterAdmin.storageManager.StorageManagerProgram;
import computerClusterAdmin.storageManager.StorageManagerVm;

public class GUI {
    public static void main(String[] args) throws Exception {
      File f1 = new File("./cfg/vms.config");
      File f2 = new File("./cfg/programs.config");
      String vmFilePath = "./cfg/vms.config"; 
      String programFilePath = "./cfg/programs.config";
      StorageManagerVm smgr = new StorageManagerVm(vmFilePath);
      StorageManagerProgram smgrp = new StorageManagerProgram(programFilePath);
      Controller gc = new Controller();

      if (f1.exists())
        smgr.restoreFromFile(gc.getCluster());
      if (f2.exists())
          smgrp.restoreFromFile(gc.getCluster());


      gc.start();
    
  }
}
