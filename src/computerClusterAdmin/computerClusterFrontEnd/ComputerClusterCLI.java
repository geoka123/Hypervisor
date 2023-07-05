package computerClusterAdmin.computerClusterFrontEnd;

import java.io.File;

import computerClusterAdmin.computerClusterBackEnd.Cluster;
import computerClusterAdmin.storageManager.StorageManagerProgram;
import computerClusterAdmin.storageManager.StorageManagerVm;

public class ComputerClusterCLI {
    public static void main(String[] args) throws Exception {

        File f1 = new File("./cfg/vms.config");
        File f2 = new File("./cfg/programs.config");
        String vmFilePath = "./cfg/vms.config"; 
        String programFilePath = "./cfg/programs.config";
        StorageManagerVm smgr = new StorageManagerVm(vmFilePath);
        StorageManagerProgram smgrp = new StorageManagerProgram(programFilePath);
        Cluster c1 = new Cluster();

        if (f1.exists()) {
            smgr.restoreFromFile(c1);
            if (f2.exists())
                smgrp.restoreFromFile(c1);
            else
                c1.printProgramMenu();
        } else {
            c1.printVmMenu();
            if (f2.exists())
                smgrp.restoreFromFile(c1);
            else
                c1.printProgramMenu();
        }
    }

}
