# Getting Started with the Hypervisor project

Welcome to a Hypervisor java project built by George Karaviotis. Basic functions of this specific hypervisor is creation and management of Virtual Machines, with allocation of resources provided by a computer cluster, as well as of programs running on those VM's. The project has a CLI version and a GUI one too. You can download the project and run it considered that you have the latest version of JDK downloaded on your computer. 

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `log`: the folder that contains the rejected by the Hypervisor programs (programs that cannot run on a current Vm)
- `cfg`: the folder that may contain the `vms.config` and/or the `programs.config` files that make the program load VM's and/or programs automatically.

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Src Folder

Inside the Src folder there are two folders:

### computerClusterAdmin
 the main functioning of the hypervisor that contains three folders:
- `computerClusterBackEnd`: the folder where all the back end functioning is being done
- `compiterClusterFrontEnd`: the folder where the two main method files exist. One file contains the CLI version of the project and the other one the GUI version.
- `clusterGUI`: the back end functioning for the GUI version

### utils
the folder that contains useful methods (e.g. bubble sort algorithm) and classes that are being used many times across different classes and packages in the computerClusterAdmin folder 

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Basic Functioning of the Hypervisor
This hypervisor, as said above, gives the ability to create and manage different type of VM's. When a VM is being created, resources from the computer cluster are being allocated(The available resources provided by the cluster are: 128 CPU cores, 256 GB of RAM memory, 2048 GB of SSD storage, 8 GPU's and 320 GB/sec of bandwidth). A VM can also be updated, deleted or taken report of.

After creating all the desirable VM's, a user can create programs that, at first, are queued based on their priority and then assigned at a VM based on their load and available resources for the programs to allocate.

This proccess can also be done automatically by loading in the `cfg` folder either or both of the files `vms.config` and `programs.config` that contain the VM's and the programs that a user wants to create.

An example of the format for the two files are given below:

### vms.config 
os:fedora,cores:32,ram:64,ssd:64

os:ubuntu,cores:4,ram:24,ssd:128,bandwidth:1

os:windows,cores:6,ram:32,ssd:256,bandwidth:2,gpu:6

### programs.config
cores:32,ram:4,ssd:4,time:6

cores:4,ram:6,ssd:2,bandwidth:1,time:20

cores:1,ram:4,ssd:1,bandwidth:1,gpu:4,time:12



## Feel free to experiment with the program and let me know your impressions!!

