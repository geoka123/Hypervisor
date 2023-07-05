package computerClusterAdmin.computerClusterBackEnd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.*;

import utils.BoundedQueue;
import utils.DuplicateEntryException;
import utils.Globals;
import utils.MyScanner;

import computerClusterAdmin.storageManager.StorableProgram;
import computerClusterAdmin.storageManager.StorableVm;
import computerClusterAdmin.storageManager.UnMarshalingException;

public class Cluster implements StorableVm, StorableProgram {
    Vector<Vm> vmArray;
    ArrayList<Program> progArray;
    BoundedQueue<Program> progQueue;
    private int totalCores = 128;
    private int totalRam = 256;
    private int totalSsd = 2048;
    private int totalGPU = 8;
    private int totalBandwidth = 320;

    private int allocatedCores = 0;
    private int allocatedRam = 0;
    private int allocatedSsd = 0;
    private int allocatedGPU = 0;
    private int allocatedBandwidth = 0;

    private int programCounter=0;

    public Cluster() {
        vmArray = new Vector<Vm>();
        progArray = new ArrayList<>(50);
    }

    @Override
    public void unMarshalVm(String Data) throws UnMarshalingException {
        String[] tokens = null;
        String[] vms = Data.split("\n");

        boolean availableSpace;
        Vm vm = null;

        System.out.println("Attempting to create VMs automatically...");
        System.out.println("");

        for (String vmStr : vms) {
            boolean createVm = true;
            HashSet<String> vmtype = new HashSet<>();
            tokens = vmStr.split(",");
            for (String token : tokens) {
                String[] keyVal = token.split(":");
                try {
                    boolean added = vmtype.add(keyVal[0]);
                    if (!added)
                        throw new DuplicateEntryException("Vm cannot have duplicate characteristics!");
                } catch (DuplicateEntryException e) {
                    System.out.println("Vm cannot have duplicate characteristics!");
                    createVm = false;
                }
            }

            if (createVm) {
                try {
                    if (vmtype.contains("os") && vmtype.contains("cores") && vmtype.contains("ram")
                            && vmtype.contains("ssd")) {
                        if (vmtype.contains("bandwidth") && vmtype.contains("gpu")) {
                            vm = new VmNetworkedGPU();
                        } else if (vmtype.contains("bandwidth")) {
                            vm = new VmNetworked();
                        } else if (vmtype.contains("gpu")) {
                            vm = new VmGPU();
                        } else {
                            vm = new PlainVm();
                        }
                    } else
                        System.out.println("This is not a valid VM type");
                    if (vm != null) {
                        try {
                            vm.unMarshalVm(vmStr);
                            availableSpace = vm.checkAvailableResources(totalCores, totalRam, totalSsd, totalGPU,
                            totalBandwidth);
                            if (availableSpace) {
                                this.vmArray.add(vm);
                                allocateClusterResources(vmStr);
                            }
                        }catch(Exception e) {
                                System.out.println(e.getMessage());
                        }
                    } else
                        System.out.println("Not enough space");
                    } catch (Exception e) {
                    System.out.println("An error occured in Cluster.unMarshal()...");
                    }
            }
        }
        System.out.println("");
        if (!this.vmArray.isEmpty()) {
            System.out.println("Vm's created:");
            for (Vm v : this.vmArray)
                System.out.println("\u2219 " + v);
        }
        System.out.println("\n");
    }


    public void allocateClusterResources(String data) {
        try {
            String[] vmResources = data.split(",");
            for (String resource : vmResources) {
                String keyVal[] = resource.split(":");
                switch (keyVal[0].toLowerCase()) {
                    case "cores": {
                        this.totalCores -= Integer.parseInt(keyVal[1]);
                        this.allocatedCores += Integer.parseInt(keyVal[1]);
                        break;
                    }
                    case "ram": {
                        this.totalRam -= Integer.parseInt(keyVal[1]);
                        this.allocatedRam += Integer.parseInt(keyVal[1]);
                        break;
                    }
                    case "ssd": {
                        this.totalSsd -= Integer.parseInt(keyVal[1]);
                        this.allocatedSsd += Integer.parseInt(keyVal[1]);
                        break;
                    }
                    case "bandwidth": {
                        this.totalBandwidth -= Integer.parseInt(keyVal[1]);
                        this.allocatedBandwidth += Integer.parseInt(keyVal[1]);
                        break;
                    }
                    case "gpu": {
                        this.totalGPU -= Integer.parseInt(keyVal[1]);
                        this.allocatedGPU += Integer.parseInt(keyVal[1]);
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in Cluster.allocateClusterResources() ...");
        }
    }

    public String marshalVm() { // fix it ..............................
        String str = "";
        return str;
    }

    @Override
    public String marshalProgram() { // fix it ..............................
        String str = "";
        return str;
    }

    @Override
    public void unMarshalProgram(String Data) throws UnMarshalingException {
        String[] tokens = null;
        String[] programs = Data.split("\n");


        Program program = null;

        System.out.println("Attempting to create Programs automatically...");
        System.out.println("");

        for (String prog : programs) {
            boolean createProgram = true;
            HashSet<String> progCharacteristics = new HashSet<>();
            tokens = prog.split(",");

            for (String token : tokens) {
                String[] keyVal = token.split(":");
                if (keyVal.length == 1) {
                    System.out.println("Program characteristics cannot be null");
                    createProgram = false;
                }

                try {
                    boolean added = progCharacteristics.add(keyVal[0]);
                    if (!added)
                        throw new DuplicateEntryException("Vm cannot have duplicate characteristics!");
                } catch (DuplicateEntryException e) {
                    System.out.println("Program cannot have duplicate characteristics!");
                    createProgram = false;
                }
            }

            if (createProgram) {
                try {
                    if (progCharacteristics.contains("cores") && progCharacteristics.contains("ram")
                            && progCharacteristics.contains("ssd") && progCharacteristics.contains("time")) {
                        program = new Program();
                    } else
                        System.out.println("This is not a valid program type...");
                    if (program != null) {
                        program.unMarshalProgram(prog);
                        program.calculatePriority(allocatedCores, allocatedRam, allocatedSsd, allocatedGPU,
                                allocatedBandwidth);
                        this.progArray.add(program);
                        System.out.println("\u2219 " + program );
                        Globals.sort(this.progArray);
                    }
                } catch (Exception e) {
                    System.out.println("Error while unMarshaling programs...");
                }
            }
        }
        addToQueue();
        System.out.println("");
        runPrograms();
    }

    public void printVmMenu() {
        int choice = 0;
        MyScanner msc = new MyScanner();

        while (choice != 5) {
            System.out.println(".....VM MENU.....");
            System.out.println("1)Create Vm");
            System.out.println("2)Update Vm");
            System.out.println("3)Delete Vm");
            System.out.println("4)Take Report");
            System.out.println("5)Continue");
            choice = msc.readInt("Your choice");

            switch (choice) {
                case 1: {
                    createVm();
                    break;
                }
                case 2: {
                    updateVm();
                    break;
                }
                case 3: {
                    deleteVm();
                    break;
                }
                case 4: {
                    vmReport();
                    break;
                }
                case 5: {
                    if (this.vmArray.isEmpty()) {
                        System.out.println("You need to create at least one VM before trying to create a Program");
                        choice = 0;
                    } else
                        break;
                }
                default: {
                    System.out.println("Invalid input");
                }
            }
        }
    }

    public void printProgramMenu() {
        int choice = 0;
        MyScanner ms = new MyScanner();

        while (choice != 2) {
            System.out.println(".....PROGRAM MENU.....");
            System.out.println("1) Create Program");
            System.out.println("2) Continue");
            choice = ms.readInt("Your choice");

            switch (choice) {
                case 1: {
                    createProgram();
                    break;
                }
                case 2: {
                    runPrograms();
                    break;
                }
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    public int findVmById(int vId) {
        int vmIdx = -1;

        for (int i = 0; i < this.vmArray.size(); i++) {
            if (this.vmArray.get(i).getVmId() == vId) {
                vmIdx = i;
                break;
            }
        }
        return vmIdx;
    }

    private void createVm() {
        Vm vm = null;
        int vmType = 0;

        String vmOs = null;
        int vmCores = 0;
        int vmRam = 0;
        int vmSsd = 0;
        int vmGpu = 0;
        int vmBandwidth = 0;

        MyScanner ms = new MyScanner();

        System.out.println("Select the type of the VM that you wish to create");
        System.out.println("1. Plain Vm (OS, CPU, RAM, SSD)");
        System.out.println("2. Vm GPU (OS, CPU, RAM, SSD, GPU)");
        System.out.println("3. Vm Networked (OS, CPU, RAM, SSD, Bandwidth)");
        System.out.println("4. Vm Networked GPU (OS, CPU, RAM, SSD, Bandwidth, GPU)");
        vmType = ms.readInt("Your choice");

        switch (vmType) {
            case 1: {
                boolean availableSpace = true;

                vmOs = ms.readStr("Select the desirable OS between WINDOWS, FEDORA, UBUNTU");
                if (!Globals.checkOs(vmOs))
                    break;
                vmCores = ms.readInt("Select the vm's number of CPU cores");
                if (vmCores < 1) {
                    System.out.println("Invalid number of cores.");
                    break;
                }
                vmRam = ms.readInt("Select the vm's amount of RAM memory");
                if (vmRam < 1) {
                    System.out.println("Invalid number of RAM.");
                    break;
                }
                vmSsd = ms.readInt("Select the vm's amount of SSD storage");
                if (vmSsd < 1) {
                    System.out.println("Invalid number of SSD.");
                    break;
                }
                vm = new PlainVm(vmCores, vmRam, vmOs, vmSsd);
                availableSpace = vm.checkAvailableResources(totalCores, totalRam, totalSsd, totalGPU, totalBandwidth);
                if (availableSpace) {
                    this.vmArray.add(vm);
                    allocateClusterResources(vm.marshalVm());
                } else
                    vm.resetCounter();

                break;
            }
            case 2: {
                boolean availableSpace = true;
                vmOs = ms.readStr("Select the desirable OS between WINDOWS, FEDORA, UBUNTU");
                if (!Globals.checkOs(vmOs))
                    break;
                vmCores = ms.readInt("Select the vm's number of CPU cores");
                if (vmCores < 1) {
                    System.out.println("Invalid number of cores.");
                    break;
                }
                vmRam = ms.readInt("Select the vm's amount of RAM memory");
                if (vmRam < 1) {
                    System.out.println("Invalid number of RAM.");
                    break;
                }
                vmSsd = ms.readInt("Select the vm's amount of SSD storage");
                if (vmSsd < 1) {
                    System.out.println("Invalid number of SSD.");
                    break;
                }
                vmGpu = ms.readInt("Select the vm's number of GPU's");
                if (vmGpu < 1) {
                    System.out.println("Invalid number of GPU.");
                    break;
                }
                vm = new VmGPU(vmCores, vmRam, vmOs, vmSsd, vmGpu);
                availableSpace = vm.checkAvailableResources(totalCores, totalRam, totalSsd, totalGPU, totalBandwidth);
                if (availableSpace) {
                    this.vmArray.add(vm);
                    allocateClusterResources(vm.marshalVm());
                } else
                    vm.resetCounter();

                break;
            }
            case 3: {
                boolean availableSpace = true;

                vmOs = ms.readStr("Select the desirable OS between WINDOWS, FEDORA, UBUNTU");
                if (!Globals.checkOs(vmOs))
                    break;
                vmCores = ms.readInt("Select the vm's number of CPU cores");
                if (vmCores < 1) {
                    System.out.println("Invalid number of cores.");
                    break;
                }
                vmRam = ms.readInt("Select the vm's amount of RAM memory");
                if (vmRam < 1) {
                    System.out.println("Invalid number of RAM.");
                    break;
                }
                vmSsd = ms.readInt("Select the vm's amount of SSD storage");
                if (vmSsd < 1) {
                    System.out.println("Invalid number of SSD.");
                    break;
                }
                vmBandwidth = ms.readInt("Select the vm's amount of Bandwidth(Must be greater or equal to 4)");
                if (vmBandwidth < 4) {
                    System.out.println("Invalid number of Bandwidth.");
                    break;
                }
                vm = new VmNetworked(vmCores, vmRam, vmOs, vmSsd, vmBandwidth);
                availableSpace = vm.checkAvailableResources(totalCores, totalRam, totalSsd, totalGPU, totalBandwidth);
                if (availableSpace) {
                    this.vmArray.add(vm);
                    allocateClusterResources(vm.marshalVm());
                } else
                    vm.resetCounter();

                break;
            }
            case 4: {
                boolean availableSpace = true;

                vmOs = ms.readStr("Select the desirable OS between WINDOWS, FEDORA, UBUNTU");
                if (!Globals.checkOs(vmOs))
                    break;
                vmCores = ms.readInt("Select the vm's number of CPU cores");
                if (vmCores < 1) {
                    System.out.println("Invalid number of cores.");
                    break;
                }
                vmRam = ms.readInt("Select the vm's amount of RAM memory");
                if (vmRam < 1) {
                    System.out.println("Invalid number of RAM.");
                    break;
                }
                vmSsd = ms.readInt("Select the vm's amount of SSD storage");
                if (vmSsd < 1) {
                    System.out.println("Invalid number of SSD.");
                    break;
                }
                vmBandwidth = ms.readInt("Select the vm's amount of Bandwidth(Must be greater or equal to 4)");
                if (vmBandwidth < 4) {
                    System.out.println("Invalid number of Bandwidth.");
                    break;
                }
                vmGpu = ms.readInt("Select the vm's number of GPU's");
                if (vmGpu < 1) {
                    System.out.println("Invalid number of GPU.");
                    break;
                }
                vm = new VmNetworkedGPU(vmCores, vmRam, vmOs, vmSsd, vmBandwidth, vmGpu);
                availableSpace = vm.checkAvailableResources(totalCores, totalRam, totalSsd, totalGPU, totalBandwidth);
                if (availableSpace) {
                    this.vmArray.add(vm);
                    allocateClusterResources(vm.marshalVm());
                } else
                    vm.resetCounter();

                break;
            }
            default: {
                System.out.println("Invalid vm type...");
                break;
            }
        }
    }

    private void updateVm() {
        String vmType;
        String vmResources;
        String newVmOs;
        int newVmCores;
        int newVmRam;
        int newVmSsd;
        int newVmGpu;
        int newVmBandwidth;

        if (!this.vmArray.isEmpty()) {
            System.out.println("Current VM's");
            for (int i = 0; i < this.vmArray.size(); i++) {
                System.out.println(i + 1 + ") " + this.vmArray.get(i));
            }

            MyScanner ms = new MyScanner();
            int vId = ms.readInt("Enter the id of the VM that you wish to update");
            int vIdx = findVmById(vId);

            while (vIdx == -1) {
                vId = ms.readInt("Invalid VM id. Please enter again");
                vIdx = findVmById(vId);
            }

            vmType = this.vmArray.get(vIdx).returnType();
            vmResources = this.vmArray.get(vIdx).marshalVm();
            String[] resources = vmResources.split(",");

            switch (vmType) {
                case "PlainVm": {
                    int updateResourceChoice = 0;

                    PlainVm tempVm = new PlainVm(Integer.parseInt(resources[1].split(":")[1]),
                            Integer.parseInt(resources[2].split(":")[1]), resources[0].split(":")[1],
                            Integer.parseInt(resources[3].split(":")[1]));

                    System.out.println("Current vm's resources:");
                    System.out.println(this.vmArray.get(vIdx));

                    while (updateResourceChoice != 5) {
                        System.out.println("Choose the resource you wish to update");
                        System.out.println("1. Vm Os");
                        System.out.println("2. Vm CPU cores");
                        System.out.println("3. Vm RAM");
                        System.out.println("4. Vm SSD");
                        System.out.println("5. I changed my mind");
                        updateResourceChoice = ms.readInt("Your choice");

                        switch (updateResourceChoice) {
                            case 1: {
                                newVmOs = ms.readStr("Enter the new VM OS");
                                if (Globals.checkOs(newVmOs))
                                    tempVm.setVmOs(newVmOs.toUpperCase());
                                break;
                            }
                            case 2: {
                                newVmCores = ms.readInt("Enter the new number of the VM's CPU cores");
                                if (newVmCores < tempVm.getAvVmCores()) {
                                    totalCores += (tempVm.getAvVmCores() - newVmCores);
                                    tempVm.setAvVmCores(newVmCores);
                                } else {
                                    if (totalCores < (newVmCores - tempVm.getAvVmCores()))
                                        System.out.println("Not enough cores for VM update");
                                    else {
                                        totalCores -= newVmCores - tempVm.getAvVmCores();
                                        tempVm.setAvVmCores(newVmCores);
                                    }
                                }
                                break;
                            }
                            case 3: {
                                newVmRam = ms.readInt("Enter the new amount of the VM's RAM memory");
                                if (newVmRam < tempVm.getAvVmRam()) {
                                    totalRam += (tempVm.getAvVmRam() - newVmRam);
                                    tempVm.setAvVmRam(newVmRam);
                                } else {
                                    if (totalRam < (newVmRam - tempVm.getAvVmRam()))
                                        System.out.println("Not enough RAM memory for VM update");
                                    else {
                                        totalRam -= newVmRam - tempVm.getAvVmRam();
                                        tempVm.setAvVmRam(newVmRam);
                                    }
                                }
                                break;
                            }
                            case 4: {
                                newVmSsd = ms.readInt("Enter the new amount of the VM's SSD storage");
                                if (newVmSsd < tempVm.getAvVmSsd()) {
                                    totalSsd += (tempVm.getAvVmSsd() - newVmSsd);
                                    tempVm.setAvVmSsd(newVmSsd);
                                } else {
                                    if (totalSsd < (newVmSsd - tempVm.getAvVmSsd()))
                                        System.out.println("Not enough SSD storage for VM update");
                                    else {
                                        totalSsd -= newVmSsd - tempVm.getAvVmSsd();
                                        tempVm.setAvVmSsd(newVmSsd);
                                    }
                                }
                                break;
                            }
                            case 5:
                                break;
                            default: {
                                System.out.println("Invalid choice...");
                                break;
                            }
                        }
                    }
                    tempVm.setVmId(tempVm.getVmId() - this.vmArray.size());
                    tempVm.resetCounter();
                    this.vmArray.set(vIdx, tempVm);

                    break;
                }
                case "VmGPU": {
                    int updateResourceChoice = 0;

                    VmGPU tempVm = new VmGPU(Integer.parseInt(resources[1].split(":")[1]),
                            Integer.parseInt(resources[2].split(":")[1]), resources[0].split(":")[1],
                            Integer.parseInt(resources[3].split(":")[1]), Integer.parseInt(resources[4].split(":")[1]));

                    System.out.println("Current vm's resources:");
                    System.out.println(this.vmArray.get(vIdx));

                    while (updateResourceChoice != 6) {
                        System.out.println("Choose the resource you wish to update");
                        System.out.println("1. Vm Os");
                        System.out.println("2. Vm CPU cores");
                        System.out.println("3. Vm RAM");
                        System.out.println("4. Vm SSD");
                        System.out.println("5. Vm GPU");
                        System.out.println("6. I changed my mind");
                        updateResourceChoice = ms.readInt("Your choice");

                        switch (updateResourceChoice) {
                            case 1: {
                                newVmOs = ms.readStr("Enter the new VM OS");
                                if (Globals.checkOs(newVmOs))
                                    tempVm.setVmOs(newVmOs.toUpperCase());
                                break;
                            }
                            case 2: {
                                newVmCores = ms.readInt("Enter the new number of the VM's CPU cores");
                                if (newVmCores < tempVm.getAvVmCores()) {
                                    totalCores += (tempVm.getAvVmCores() - newVmCores);
                                    tempVm.setAvVmCores(newVmCores);
                                } else {
                                    if (totalCores < (newVmCores - tempVm.getAvVmCores()))
                                        System.out.println("Not enough cores for VM update");
                                    else {
                                        totalCores -= newVmCores - tempVm.getAvVmCores();
                                        tempVm.setAvVmCores(newVmCores);
                                    }
                                }
                                break;
                            }
                            case 3: {
                                newVmRam = ms.readInt("Enter the new amount of the VM's RAM memory");
                                if (newVmRam < tempVm.getAvVmRam()) {
                                    totalRam += (tempVm.getAvVmRam() - newVmRam);
                                    tempVm.setAvVmRam(newVmRam);
                                } else {
                                    if (totalRam < (newVmRam - tempVm.getAvVmRam()))
                                        System.out.println("Not enough RAM memory for VM update");
                                    else {
                                        totalRam -= newVmRam - tempVm.getAvVmRam();
                                        tempVm.setAvVmRam(newVmRam);
                                    }
                                }
                                break;
                            }
                            case 4: {
                                newVmSsd = ms.readInt("Enter the new amount of the VM's RAM memory");
                                if (newVmSsd < tempVm.getAvVmSsd()) {
                                    totalSsd += (tempVm.getAvVmSsd() - newVmSsd);
                                    tempVm.setAvVmSsd(newVmSsd);
                                } else {
                                    if (totalSsd < (newVmSsd - tempVm.getAvVmSsd()))
                                        System.out.println("Not enough SSD storage for VM update");
                                    else {
                                        totalSsd -= newVmSsd - tempVm.getAvVmSsd();
                                        tempVm.setAvVmSsd(newVmSsd);
                                    }
                                }
                                break;
                            }
                            case 5: {
                                newVmGpu = ms.readInt("Enter the new number of VM's GPU.");
                                if (newVmGpu < tempVm.getAvVmGPU()) {
                                    totalSsd += (tempVm.getAvVmGPU() - newVmGpu);
                                    tempVm.setAvVmGPU(newVmGpu);
                                } else {
                                    if (totalSsd < (newVmGpu - tempVm.getAvVmGPU()))
                                        System.out.println("Not enough Gpu storage for VM update");
                                    else {
                                        totalSsd -= newVmGpu - tempVm.getAvVmGPU();
                                        tempVm.setAvVmGPU(newVmGpu);
                                    }
                                }
                                break;
                            }
                            case 6:
                                break;
                            default: {
                                System.out.println("Invalid choice...");
                                break;
                            }
                        }
                    }
                    tempVm.setVmId(tempVm.getVmId() - this.vmArray.size());
                    tempVm.resetCounter();
                    this.vmArray.set(vIdx, tempVm);
                    System.out.println(this.vmArray.get(vIdx));

                    break;
                }
                case "VmNetworked": {
                    int updateResourceChoice = 0;

                    VmNetworked tempVm = new VmNetworked(Integer.parseInt(resources[1].split(":")[1]),
                            Integer.parseInt(resources[2].split(":")[1]), resources[0].split(":")[1],
                            Integer.parseInt(resources[3].split(":")[1]), Integer.parseInt(resources[4].split(":")[1]));

                    System.out.println("Current vm's resources:");
                    System.out.println(this.vmArray.get(vIdx));

                    while (updateResourceChoice != 6) {
                        System.out.println("Choose the resource you wish to update");
                        System.out.println("1. Vm Os");
                        System.out.println("2. Vm CPU cores");
                        System.out.println("3. Vm RAM");
                        System.out.println("4. Vm SSD");
                        System.out.println("5. Vm Bandwidth");
                        System.out.println("6. I changed my mind");
                        updateResourceChoice = ms.readInt("Your choice");

                        switch (updateResourceChoice) {
                            case 1: {
                                newVmOs = ms.readStr("Enter the new VM OS");
                                if (Globals.checkOs(newVmOs))
                                    tempVm.setVmOs(newVmOs.toUpperCase());
                                break;
                            }
                            case 2: {
                                newVmCores = ms.readInt("Enter the new number of the VM's CPU cores");
                                if (newVmCores < tempVm.getAvVmCores()) {
                                    totalCores += (tempVm.getAvVmCores() - newVmCores);
                                    tempVm.setAvVmCores(newVmCores);
                                } else {
                                    if (totalCores < (newVmCores - tempVm.getAvVmCores()))
                                        System.out.println("Not enough cores for VM update");
                                    else {
                                        totalCores -= newVmCores - tempVm.getAvVmCores();
                                        tempVm.setAvVmCores(newVmCores);
                                    }
                                }
                                break;
                            }
                            case 3: {
                                newVmRam = ms.readInt("Enter the new amount of the VM's RAM memory");
                                if (newVmRam < tempVm.getAvVmRam()) {
                                    totalRam += (tempVm.getAvVmRam() - newVmRam);
                                    tempVm.setAvVmRam(newVmRam);
                                } else {
                                    if (totalRam < (newVmRam - tempVm.getAvVmRam()))
                                        System.out.println("Not enough RAM memory for VM update");
                                    else {
                                        totalRam -= newVmRam - tempVm.getAvVmRam();
                                        tempVm.setAvVmRam(newVmRam);
                                    }
                                }
                                break;
                            }
                            case 4: {
                                newVmSsd = ms.readInt("Enter the new amount of the VM's RAM memory");
                                if (newVmSsd < tempVm.getAvVmSsd()) {
                                    totalSsd += (tempVm.getAvVmSsd() - newVmSsd);
                                    tempVm.setAvVmSsd(newVmSsd);
                                } else {
                                    if (totalSsd < (newVmSsd - tempVm.getAvVmSsd()))
                                        System.out.println("Not enough SSD storage for VM update");
                                    else {
                                        totalSsd -= newVmSsd - tempVm.getAvVmSsd();
                                        tempVm.setAvVmSsd(newVmSsd);
                                    }
                                }
                                break;
                            }
                            case 5: {
                                newVmBandwidth = ms.readInt("Enter the new number of VM's Bandwidth.");
                                if (newVmBandwidth < tempVm.getAvVmBandwidth()) {
                                    totalSsd += (tempVm.getAvVmBandwidth() - newVmBandwidth);
                                    tempVm.setAvVmBandwidth(newVmBandwidth);
                                } else {
                                    if (totalSsd < (newVmBandwidth - tempVm.getAvVmBandwidth()))
                                        System.out.println("Not enough Bandwidth storage for VM update");
                                    else {
                                        totalSsd -= newVmBandwidth - tempVm.getAvVmBandwidth();
                                        tempVm.setAvVmBandwidth(newVmBandwidth);
                                    }
                                }
                                break;
                            }
                            case 6:
                                break;
                            default: {
                                System.out.println("Invalid choice...");
                                break;
                            }
                        }
                    }
                    tempVm.setVmId(tempVm.getVmId() - 1);
                    tempVm.resetCounter();
                    this.vmArray.set(vIdx, tempVm);

                    break;
                }
                case "VmNetworkedGPU": {
                    int updateResourceChoice = 0;

                    VmNetworkedGPU tempVm = new VmNetworkedGPU(Integer.parseInt(resources[1].split(":")[1]),
                            Integer.parseInt(resources[2].split(":")[1]), resources[0].split(":")[1],
                            Integer.parseInt(resources[3].split(":")[1]), Integer.parseInt(resources[4].split(":")[1]),
                            Integer.parseInt(resources[5].split(":")[1]));

                    System.out.println("Current vm's resources:");
                    System.out.println(this.vmArray.get(vIdx));

                    while (updateResourceChoice != 7) {
                        System.out.println("Choose the resource you wish to update");
                        System.out.println("1. Vm Os");
                        System.out.println("2. Vm CPU cores");
                        System.out.println("3. Vm RAM");
                        System.out.println("4. Vm SSD");
                        System.out.println("5. Vm Bandwidth");
                        System.out.println("6. Vm GPU");
                        System.out.println("7. I changed my mind");
                        updateResourceChoice = ms.readInt("Your choice");

                        switch (updateResourceChoice) {
                            case 1: {
                                newVmOs = ms.readStr("Enter the new VM OS");
                                if (Globals.checkOs(newVmOs))
                                    tempVm.setVmOs(newVmOs.toUpperCase());
                                break;
                            }
                            case 2: {
                                newVmCores = ms.readInt("Enter the new number of the VM's CPU cores");
                                if (newVmCores < tempVm.getAvVmCores()) {
                                    totalCores += (tempVm.getAvVmCores() - newVmCores);
                                    tempVm.setAvVmCores(newVmCores);
                                } else {
                                    if (totalCores < (newVmCores - tempVm.getAvVmCores()))
                                        System.out.println("Not enough cores for VM update");
                                    else {
                                        totalCores -= newVmCores - tempVm.getAvVmCores();
                                        tempVm.setAvVmCores(newVmCores);
                                    }
                                }
                                break;
                            }
                            case 3: {
                                newVmRam = ms.readInt("Enter the new amount of the VM's RAM memory");
                                if (newVmRam < tempVm.getAvVmRam()) {
                                    totalRam += (tempVm.getAvVmRam() - newVmRam);
                                    tempVm.setAvVmRam(newVmRam);
                                } else {
                                    if (totalRam < (newVmRam - tempVm.getAvVmRam()))
                                        System.out.println("Not enough RAM memory for VM update");
                                    else {
                                        totalRam -= newVmRam - tempVm.getAvVmRam();
                                        tempVm.setAvVmRam(newVmRam);
                                    }
                                }
                                break;
                            }
                            case 4: {
                                newVmSsd = ms.readInt("Enter the new amount of the VM's RAM memory");
                                if (newVmSsd < tempVm.getAvVmSsd()) {
                                    totalSsd += (tempVm.getAvVmSsd() - newVmSsd);
                                    tempVm.setAvVmSsd(newVmSsd);
                                } else {
                                    if (totalSsd < (newVmSsd - tempVm.getAvVmSsd()))
                                        System.out.println("Not enough SSD storage for VM update");
                                    else {
                                        totalSsd -= newVmSsd - tempVm.getAvVmSsd();
                                        tempVm.setAvVmSsd(newVmSsd);
                                    }
                                }
                                break;
                            }
                            case 5: {
                                newVmBandwidth = ms.readInt("Enter the new number of VM's Bandwidth.");
                                if (newVmBandwidth < tempVm.getAvVmBandwidth()) {
                                    totalSsd += (tempVm.getAvVmBandwidth() - newVmBandwidth);
                                    tempVm.setAvVmBandwidth(newVmBandwidth);
                                } else {
                                    if (totalSsd < (newVmBandwidth - tempVm.getAvVmBandwidth()))
                                        System.out.println("Not enough Gpu storage for VM update");
                                    else {
                                        totalSsd -= newVmBandwidth - tempVm.getAvVmBandwidth();
                                        tempVm.setAvVmBandwidth(newVmBandwidth);
                                    }
                                }
                                break;
                            }
                            case 6: {
                                newVmGpu = ms.readInt("Enter the new number of VM's GPU.");
                                if (newVmGpu < tempVm.getAvVmGPU()) {
                                    totalSsd += (tempVm.getAvVmGPU() - newVmGpu);
                                    tempVm.setAvVmGPU(newVmGpu);
                                } else {
                                    if (totalSsd < (newVmGpu - tempVm.getAvVmGPU()))
                                        System.out.println("Not enough Gpu storage for VM update");
                                    else {
                                        totalSsd -= newVmGpu - tempVm.getAvVmGPU();
                                        tempVm.setAvVmGPU(newVmGpu);
                                    }
                                }
                                break;
                            }
                            case 7:
                                break;
                            default: {
                                System.out.println("Invalid choice...");
                                break;
                            }
                        }
                    }
                    tempVm.setVmId(tempVm.getVmId() - this.vmArray.size());
                    tempVm.resetCounter();
                    this.vmArray.set(vIdx, tempVm);

                    break;
                }
                default:
                    break;
            }
        } else
            System.out.println("You need to create at least one VM before trying to update it...");

    }

    private void deleteVm() {
        if (!this.vmArray.isEmpty()) {
            System.out.println("Current VM's");
            for (int i = 0; i < this.vmArray.size(); i++) {
                System.out.println(i + 1 + ") " + this.vmArray.get(i));
            }

            MyScanner ms = new MyScanner();
            int vId = ms.readInt("Enter the id of the VM that you wish to delete");
            int vIdx = findVmById(vId);

            while (vIdx == -1) {
                vId = ms.readInt("Invalid VM id. Please enter again");
                vIdx = findVmById(vId);
            }

            this.vmArray.get(vIdx).resetCounter();
            this.vmArray.remove(vIdx);
            for (int k = vIdx; k < this.vmArray.size(); k++) {
                this.vmArray.get(k).setVmId(this.vmArray.get(k).getVmId() - 1);
            }

        } else
            System.out.println("You need to create at least one VM before trying to update it...");
    }

    private void vmReport() {
        if (!this.vmArray.isEmpty()) {
            MyScanner ms = new MyScanner();

            String choice = ms
                    .readStr(
                            "Would you like a report for all of the VM's or for a specific one?(A/O) (A: all, O: one)")
                    .toUpperCase();

            switch (choice) {
                case "A": {
                    for (int i = 0; i < this.vmArray.size(); i++) {
                        System.out.println(i + 1 + ") " + this.vmArray.get(i));
                    }

                    break;
                }
                case "O": {
                    int vId = ms.readInt("Enter the id of the VM that you wish to delete");
                    int vIdx = findVmById(vId);

                    while (vIdx == -1) {
                        vId = ms.readInt("Invalid VM id. Please enter again");
                        vIdx = findVmById(vId);
                    }

                    System.out.println(this.vmArray.get(vIdx));

                    break;
                }
                default: {
                    System.out.println("Invalid choice...");
                    break;
                }
            }
        } else
            System.out.println("You need to create at least one VM before trying to take a report for it...");
    }

    private void createProgram() {
        if (!this.vmArray.isEmpty()) {
            try {
                Program p = null;
                int programCores;
                int programRam;
                int programSsd;
                int programGpu = 0;
                int programBandwidth = 0;
                int programTime;
                MyScanner ms = new MyScanner();

                programCores = ms.readInt("Enter the number of the Program's cores");
                programRam = ms.readInt("Enter the amount of the Program's RAM memory");
                programSsd = ms.readInt("Enter the amount of the Program's SSD storage");
                if (allocatedGPU != 0)
                    programGpu = ms.readInt(
                            "Enter the number of the Program's GPU's(Enter 0 if you do not want the Program to have any)");
                if (allocatedBandwidth != 0)
                    programBandwidth = ms.readInt(
                            "Enter the amount of the Program's Bandwidth(Enter 0 if you do not want the Program to have any)");
                programTime = ms.readInt("Enter the Program's expected time");

                if (programCores > 0 && programRam > 0 && programSsd > 0 && programTime > 0) {
                    p = new Program(programCores, programRam, programSsd, programGpu, programBandwidth, programTime);
                    p.calculatePriority(allocatedCores, allocatedRam, allocatedSsd, allocatedGPU, allocatedBandwidth);
                    this.progArray.add(p);
                    if (this.progArray.size() > this.progArray.size() - 5)
                        this.progArray.ensureCapacity(this.progArray.size() + 10);
                } else
                    System.out.println("Cores, RAM, SSD and time are mandatory for the creation of a Program");
            } catch (Exception e) {
                System.out.println("Exception while creating program ...");
                System.out.println(e.getMessage());
            }
        } else
            System.out.println("You need to create at least one VM before trying to create a Program.");
    }

    public void findBestVm(Program p) {
        try {
            ArrayList<Vm> appropriateVms = new ArrayList<>();
            ArrayList<String> progCharacteristics = new ArrayList<>();
            long timeToSleep = 2L;
            TimeUnit time = TimeUnit.SECONDS;

            String[] progResources = p.marshalProgram().split(",");
            for (String s : progResources) {
                String[] keyVal = s.split(":");
                progCharacteristics.add(keyVal[0]);
            }

            if (progCharacteristics.contains("gpu") && progCharacteristics.contains("bandwidth")) {
                for (Vm v : this.vmArray) {
                    if (v.returnType() == "VmNetworkedGPU")
                        appropriateVms.add(v);
                }
            } else if (progCharacteristics.contains("gpu")) {
                for (Vm v : this.vmArray) {
                    if (v.returnType() == "VmNetworkedGPU")
                        appropriateVms.add(v);
                    if (v.returnType() == "VmGPU")
                        appropriateVms.add(v);
                }
            } else if (progCharacteristics.contains("bandwidth")) {
                for (Vm v : this.vmArray) {
                    if (v.returnType() == "VmNetworkedGPU")
                        appropriateVms.add(v);
                    if (v.returnType() == "VmNetworked")
                        appropriateVms.add(v);
                }
            } else {
                for (Vm v : this.vmArray)
                    appropriateVms.add(v);
            }

            Iterator<Vm> iterator = appropriateVms.iterator();
            while (iterator.hasNext()) {
                Vm v = iterator.next();
                    if (!v.checkProgramResources(p.getProgramDemandedCores(),
                            p.getProgramDemandedRAM(),
                            p.getProgramDemandedSSD(), p.getProgramDemandedBandwidth(),
                            p.getProgramDemandedGPU())) {
                        iterator.remove();
                }
            }

            for (Vm vm : appropriateVms) {
                vm.allocateVmResources(p.marshalProgram());
                vm.calculateVmLoad();
            }
            Globals.sort(appropriateVms);

            if (!appropriateVms.isEmpty()) {
                for (int j = 1; j < appropriateVms.size(); j++) {
                    appropriateVms.get(j).freeVmResources(p.marshalProgram());
                }
                appropriateVms.get(0).addToRunningPrograms(p);
                p.setProgramOS(appropriateVms.get(0).getVmOs());
                System.out.println(p);
                System.out.println("");
                p.setInitTime(new Date());
            } else {
                p.setRejected(p.getRejected() + 1);
                System.out.println("Program with id: " + p.getProgramId() + " was rejected...");
                try {
                    System.out.println("Be quiet... the Program  with id: " + p.getProgramId() + " is sleeping");
                    System.out.println("\n");
                    time.sleep(timeToSleep);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if (p.getRejected() != 3)
                    this.progQueue.push(p);
                else {
                    System.out.println("Program  with id: " + p.getProgramId() + " was rejected 3 times");
                    System.out.println("\n");
                    writeToOutput(p);
                    programCounter++;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Exception while trying to find the best vm");
        }
    }

    private void writeToOutput(Program p) {
        try (FileOutputStream fileOut = new FileOutputStream("./log/rejected.out");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

            // Serialize the object
            objOut.writeObject(p);
            System.out.println("Object serialized and written to file.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runPrograms() {
        String filePath = "./log/rejected.out";
        File f2 = new File("./cfg/programs.config");

        File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File rejected.out created succesfully");
                    System.out.println("\n");
                }
                else {
                    System.out.println("Failed to create file rejected.out");
                    System.out.println("\n");
                }
            }
            catch (IOException e) {
                System.out.println("An error occured while trying to create file rejected.out . Error message: ");
                System.out.println(e.getMessage());
                System.out.println("\n");
            }
        }
        else {
            try (FileWriter fWriter = new FileWriter(filePath, false)) {
                fWriter.write("");
            }
            catch (IOException e) {
                System.out.println("Something went wrong while trying to recreate rejected.out file. Error message: ");
                System.out.println(e.getMessage());
                System.out.println("\n");
            }
        }

        if (!f2.exists())
            this.addToQueue();

        while (true) {
            for (Vm v : this.vmArray) {
                Iterator<Program> iterator = v.getRunningPrograms().iterator(); 
                if (!v.getRunningPrograms().isEmpty()) {
                    while(iterator.hasNext()) {
                        Program p = iterator.next();
                        Date currentTime = new Date();
                        p.calculateExecutionTime(currentTime, p.getInitTime());
                        if ( p.getExecutionTime() >= p.getProgramExpectedTime()) {
                            iterator.remove();
                            v.freeVmResources(p.marshalProgram());
                            System.out.println("Program  with id: " + p.getProgramId() + " was removed from VM with VM ID: " + v.getVmId());
                            System.out.println("\n");
                            programCounter++;
                        }
                    }
                }
            }

            if (this.progQueue.isEmpty() && programCounter == this.progArray.size()) {
                System.out.println("Process terminated");
                break;
            }
            
            if (this.progQueue.peek() != null)
                findBestVm(this.progQueue.pop());
            
        }
    }

    public void addToQueue() {
        this.progQueue = new BoundedQueue<>(this.progArray.size());
        for (Program p : this.progArray) {

            this.progQueue.push(p);
        }
    }

    public Vector<Vm> getVmArray() {
        return vmArray;
    }

    public void setVmArray(Vector<Vm> vmArray) {
        this.vmArray = vmArray;
    }

    public int getTotalCores() {
        return totalCores;
    }

    public void setTotalCores(int totalCores) {
        this.totalCores = totalCores;
    }

    public int getTotalRam() {
        return totalRam;
    }

    public void setTotalRam(int totalRam) {
        this.totalRam = totalRam;
    }

    public int getTotalSsd() {
        return totalSsd;
    }

    public void setTotalSsd(int totalSsd) {
        this.totalSsd = totalSsd;
    }

    public int getTotalGPU() {
        return totalGPU;
    }

    public void setTotalGPU(int totalGPU) {
        this.totalGPU = totalGPU;
    }

    public int getTotalBandwidth() {
        return totalBandwidth;
    }

    public void setTotalBandwidth(int totalBandwidth) {
        this.totalBandwidth = totalBandwidth;
    }

    public BoundedQueue<Program> getProgQueue() {
        return progQueue;
    }

    public void setProgQueue(BoundedQueue<Program> progQueue) {
        this.progQueue = progQueue;
    }

    public ArrayList<Program> getProgArray() {
        return progArray;
    }

    public void setProgArray(ArrayList<Program> progArray) {
        this.progArray = progArray;
    }
}
