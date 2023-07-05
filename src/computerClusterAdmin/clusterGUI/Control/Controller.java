package computerClusterAdmin.clusterGUI.Control;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import computerClusterAdmin.clusterGUI.view.MainWindow;
import computerClusterAdmin.computerClusterBackEnd.Cluster;
import computerClusterAdmin.computerClusterBackEnd.PlainVm;
import computerClusterAdmin.computerClusterBackEnd.Program;
import computerClusterAdmin.computerClusterBackEnd.Vm;
import computerClusterAdmin.computerClusterBackEnd.VmGPU;
import computerClusterAdmin.computerClusterBackEnd.VmNetworked;
import computerClusterAdmin.computerClusterBackEnd.VmNetworkedGPU;
import utils.Globals;

public class Controller extends WindowAdapter{
    MainWindow view;
    Cluster cluster;
    
    public Controller() {
        this.cluster = new Cluster();
    }

    @Override
    public void windowClosing(WindowEvent event) {
        quit();
    }

    public void start() {
        this.view = new MainWindow(this);
        this.view.addWindowListener(this);
        this.view.setVisible(true);
        this.view.getBotPanel().showCard("cMenu");
    }

    public void quit() {
        System.out.println("Exiting...");
        System.exit(1);
    }

    public void vmCreation() {
        this.view.getBotPanel().showCard("createVm");
        this.view.getTopPanel().showCard("cVmHeader");
    }

    public void vmUpdate() {
        this.view.getTopPanel().showCard("uVmHeader");
        this.view.getBotPanel().showCard("updateVm");
    }

    public void vmDeletion() {
        this.view.getTopPanel().showCard("dVmHeader");
        this.view.getBotPanel().showCard("deleteVm");
    }

    public void vmReport() {
        this.view.getBotPanel().showCard("takeReport");
        this.view.getTopPanel().showCard("trVmHeader");
    }

    public void showPlainVmCreationForm() {
        this.view.getBotPanel().showCard("pVmPanel");
    }

    public void showVmGPUCreationForm() {
        this.view.getBotPanel().showCard("pGPUVm");
    }

    public void showVmNetworkedCreationForm() {
        this.view.getBotPanel().showCard("netVmPanel");
    }

    public void showVmNetworkedGPUCreationForm() {
        this.view.getBotPanel().showCard("netGPUVmPanel");
    }

    public void goBackVm() {
        this.view.getTopPanel().showCard("vMenu");
        this.view.getBotPanel().showCard("cMenu");
    }

    public void enableComboBoxReport() {
        this.view.getBotPanel().getTakeReport().getComboBox().setEnabled(true);
    }

    public void takeReportOfOne(Vm v) {
        this.view.getBotPanel().getTakeReport().getTextArea().setText(v.toString());
        this.view.getBotPanel().getTakeReport().getComboBox().setSelectedIndex(-1);
    }

    public void takeReportOfAll() {
        StringBuffer sb = new StringBuffer();
        for (Vm v : this.getVmArray()) {
            sb.append(v.toString()).append("\n");
        }

        this.view.getBotPanel().getTakeReport().getTextArea().setText(sb.toString());
    }

    public void createPlainVm(JTextField cores, JTextField ram, JTextField ssd, JTextField os) {
        if (!cores.getText().equals("") && !ram.getText().equals("") && !ssd.getText().equals("") && !os.getText().equals("")) {
            PlainVm tempVm = new PlainVm(Integer.parseInt(cores.getText()), Integer.parseInt(ram.getText()), os.getText(), Integer.parseInt(ssd.getText()));
            if (tempVm.checkAvailableResources(this.cluster.getTotalCores(), this.cluster.getTotalRam(), this.cluster.getTotalSsd(), 0, 0) && tempVm.getVmOs() != null) {
                this.cluster.getVmArray().add(tempVm);
                this.cluster.allocateClusterResources(tempVm.marshalVm());
                this.updatable();
                this.deletable();
                this.reportable();
                this.enableContinue();
            }
            else {
                System.out.println("A problem was encountered while trying to create a Plain Vm.");
                tempVm.resetCounter();
            }
            if (!this.cluster.getVmArray().isEmpty()) {
                for (Vm v : this.cluster.getVmArray())
                    System.out.println(v);
            }
        }
        else
            System.out.println("Null vm characteristics. No VM was created.");
        this.view.getBotPanel().showCard("cMenu");
        this.view.getTopPanel().showCard("vMenu");
    }

    private void updatable() {
        this.view.getBotPanel().getMenu().getUpdateBtn().setEnabled(true);
    }

    public void deletable() {
        this.view.getBotPanel().getMenu().getDeleteBtn().setEnabled(true);
    }

    public void reportable() {
        this.view.getBotPanel().getMenu().getTakeReportBtn().setEnabled(true);
    }

    public void createVmGPU(JTextField cores, JTextField ram, JTextField ssd, JTextField os,JTextField gpu){
        if (!cores.getText().equals("") && !ram.getText().equals("") && !ssd.getText().equals("") && !os.getText().equals("") && !gpu.getText().equals("")) {
            VmGPU tempVm = new VmGPU(Integer.parseInt(cores.getText()), Integer.parseInt(ram.getText()), os.getText(), Integer.parseInt(ssd.getText()),Integer.parseInt(gpu.getText()));
            if (tempVm.checkAvailableResources(this.cluster.getTotalCores(), this.cluster.getTotalRam(), this.cluster.getTotalSsd(), this.cluster.getTotalGPU(), 0) && tempVm.getVmOs() != null) {
                this.cluster.getVmArray().add(tempVm);
                this.cluster.allocateClusterResources(tempVm.marshalVm());
                this.updatable();
                this.deletable();
                this.reportable();
                this.enableContinue();
            }
            else {
                System.out.println("A problem was encountered while trying to create a Vm GPU.");
                tempVm.resetCounter();
            }
            if (!this.cluster.getVmArray().isEmpty()) {
                for (Vm v : this.cluster.getVmArray())
                    System.out.println(v);
            }
        }
        else
            System.out.println("Null vm characteristics. No VM was created.");
        this.view.getBotPanel().showCard("cMenu");
        this.view.getTopPanel().showCard("vMenu");
    }

    public void createVmNetworked(JTextField cores, JTextField ram, JTextField ssd, JTextField os,JTextField bandwidth){
        if (!cores.getText().equals("") && !ram.getText().equals("") && !ssd.getText().equals("") && !os.getText().equals("") && !bandwidth.getText().equals("")) {
            VmNetworked tempVm = new VmNetworked(Integer.parseInt(cores.getText()), Integer.parseInt(ram.getText()), os.getText(), Integer.parseInt(ssd.getText()),Integer.parseInt(bandwidth.getText()));
            if (tempVm.checkAvailableResources(this.cluster.getTotalCores(), this.cluster.getTotalRam(), this.cluster.getTotalSsd(), 0, this.cluster.getTotalBandwidth()) && tempVm.getVmOs() != null && Integer.parseInt(bandwidth.getText()) >= 4) {
                this.cluster.getVmArray().add(tempVm);
                this.cluster.allocateClusterResources(tempVm.marshalVm());
                this.updatable();
                this.deletable();
                this.reportable();
                this.enableContinue();
            }
            else {
                System.out.println("A problem was encountered while trying to create a Vm Networked.");
                tempVm.resetCounter();
            }
            if (!this.cluster.getVmArray().isEmpty()) {
                for (Vm v : this.cluster.getVmArray())
                    System.out.println(v);
            }
        }
        else
            System.out.println("Null vm characteristics. No VM was created.");
        this.view.getBotPanel().showCard("cMenu");
        this.view.getTopPanel().showCard("vMenu");
    }

    public void createVmNetworkedGPU(JTextField cores, JTextField ram, JTextField ssd, JTextField os,JTextField bandwidth, JTextField gpu){
        if (!cores.getText().equals("") && !ram.getText().equals("") && !ssd.getText().equals("") && !os.getText().equals("") && !bandwidth.getText().equals("")) {
            VmNetworkedGPU tempVm = new VmNetworkedGPU(Integer.parseInt(cores.getText()), Integer.parseInt(ram.getText()), os.getText(), Integer.parseInt(ssd.getText()),Integer.parseInt(bandwidth.getText()), Integer.parseInt(gpu.getText()));
            if (tempVm.checkAvailableResources(this.cluster.getTotalCores(), this.cluster.getTotalRam(), this.cluster.getTotalSsd(), this.cluster.getTotalGPU(), this.cluster.getTotalBandwidth()) && tempVm.getVmOs() != null && Integer.parseInt(bandwidth.getText()) >= 4) {
                this.cluster.getVmArray().add(tempVm);
                this.cluster.allocateClusterResources(tempVm.marshalVm());
                this.updatable();
                this.deletable();
                this.reportable();
                this.enableContinue();
            }
            else {
                System.out.println("A problem was encountered while trying to create a Vm Networked GPU.");
                tempVm.resetCounter();
            }
            if (!this.cluster.getVmArray().isEmpty()) {
                for (Vm v : this.cluster.getVmArray())
                    System.out.println(v);
            }
        }
        else
            System.out.println("Null vm characteristics. No VM was created.");
        this.view.getBotPanel().showCard("cMenu");
        this.view.getTopPanel().showCard("vMenu");
    }

    public Vector<Vm> getVmArray() {
        return this.cluster.getVmArray();
    }

    public void enableVmUpdateFields(Vm v) {
        if (v.returnType().equals("PlainVm")) {
            this.view.getBotPanel().getUpdate().getCores().setEnabled(true);
            this.view.getBotPanel().getUpdate().getRam().setEnabled(true);
            this.view.getBotPanel().getUpdate().getSsd().setEnabled(true);
            this.view.getBotPanel().getUpdate().getBandwidth().setEnabled(false);
            this.view.getBotPanel().getUpdate().getGpu().setEnabled(false);
        }
        else if (v.returnType().equals("VmGPU")) {
            this.view.getBotPanel().getUpdate().getCores().setEnabled(true);
            this.view.getBotPanel().getUpdate().getRam().setEnabled(true);
            this.view.getBotPanel().getUpdate().getSsd().setEnabled(true);
            this.view.getBotPanel().getUpdate().getGpu().setEnabled(true);
            this.view.getBotPanel().getUpdate().getBandwidth().setEnabled(false);
        }
        else if (v.returnType().equals("VmNetworked")) {
            this.view.getBotPanel().getUpdate().getCores().setEnabled(true);
            this.view.getBotPanel().getUpdate().getRam().setEnabled(true);
            this.view.getBotPanel().getUpdate().getSsd().setEnabled(true);
            this.view.getBotPanel().getUpdate().getBandwidth().setEnabled(true);
            this.view.getBotPanel().getUpdate().getGpu().setEnabled(false);
        }
        else if (v.returnType().equals("VmNetworkedGPU")) {
            this.view.getBotPanel().getUpdate().getCores().setEnabled(true);
            this.view.getBotPanel().getUpdate().getRam().setEnabled(true);
            this.view.getBotPanel().getUpdate().getSsd().setEnabled(true);
            this.view.getBotPanel().getUpdate().getBandwidth().setEnabled(true);
            this.view.getBotPanel().getUpdate().getGpu().setEnabled(true);
        }
    }

    public Vector<String> getOsArray() {
        Globals.OS[] osArray = Globals.OS.values();
        Vector<String> osVector = new Vector<>();

        for (Globals.OS os : osArray) {
            osVector.add(os.toString());
        }

        return osVector;
    }

    public Cluster getCluster() {
        return this.cluster;
    }

    public void applyVmUpdate(Vm v, String os, int cores, int ram, int ssd, int gpu, int bandwidth) {
        if (v != null) {
            if (v.returnType().equals("PlainVm")) {
                PlainVm plainV = (PlainVm) v;
                PlainVm tempVm = new PlainVm();
                if (!os.equals(v.getVmOs()))
                    tempVm.setVmOs(os);       
                else
                    tempVm.setVmOs(v.getVmOs());     
                if (cores != 0) {
                    tempVm.setAvVmCores(cores);
                    if (v.getAvVmCores() > cores)
                        this.cluster.setTotalCores(this.cluster.getTotalCores() + v.getAvVmCores() - cores);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalCores() - (cores - v.getAvVmCores()));
                }
                else
                    tempVm.setAvVmCores(v.getAvVmCores());
                if (ram != 0) {
                    tempVm.setAvVmRam(ram);
                    if (v.getAvVmRam() > ram)
                        this.cluster.setTotalRam(this.cluster.getTotalRam() + v.getAvVmRam() - ram);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalRam() - (ram - v.getAvVmRam()));
                }
                else
                    tempVm.setAvVmRam(v.getAvVmRam());          
                if (ssd != 0) {
                    tempVm.setAvVmSsd(ssd);
                    if (plainV.getAvVmSsd() > ssd)
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() + plainV.getAvVmSsd() - ssd);
                    else
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() - (ssd - plainV.getAvVmSsd()));
                }
                else
                    tempVm.setAvVmSsd(plainV.getAvVmSsd());    
      
                this.cluster.getVmArray().set(this.cluster.getVmArray().indexOf(v), tempVm);
                tempVm.setVmId(v.getVmId());
                tempVm.resetCounter();
                System.out.println(this.cluster.getVmArray());
                System.out.println(this.cluster.getTotalCores());
            }
            if (v.returnType().equals("VmGPU")) {
                VmGPU gpuV = (VmGPU) v;
                VmGPU tempVm = new VmGPU();
                if (!os.equals(v.getVmOs()))
                    tempVm.setVmOs(os);       
                else
                    tempVm.setVmOs(v.getVmOs());     
                if (cores != 0) {
                    tempVm.setAvVmCores(cores);
                    if (v.getAvVmCores() > cores)
                        this.cluster.setTotalCores(this.cluster.getTotalCores() + v.getAvVmCores() - cores);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalCores() - (cores - v.getAvVmCores()));
                }
                else
                    tempVm.setAvVmCores(v.getAvVmCores());
                if (ram != 0) {
                    tempVm.setAvVmRam(ram);
                    if (v.getAvVmRam() > ram)
                        this.cluster.setTotalRam(this.cluster.getTotalRam() + v.getAvVmRam() - ram);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalRam() - (ram - v.getAvVmRam()));
                }
                else
                    tempVm.setAvVmRam(v.getAvVmRam());          
                if (ssd != 0) {
                    tempVm.setAvVmSsd(ssd);
                    if (gpuV.getAvVmSsd() > ssd)
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() + gpuV.getAvVmSsd() - ssd);
                    else
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() - (ssd - gpuV.getAvVmSsd()));
                }
                else
                    tempVm.setAvVmSsd(gpuV.getAvVmSsd());   
                if (gpu != 0) {
                    tempVm.setAvVmGPU(gpu);
                    if (gpuV.getAvVmGPU() > gpu)
                        this.cluster.setTotalGPU(this.cluster.getTotalGPU() + gpuV.getAvVmGPU() - gpu);
                    else
                        this.cluster.setTotalGPU(this.cluster.getTotalGPU() - (gpu - gpuV.getAvVmGPU()));
                }
                else
                    tempVm.setAvVmGPU(gpuV.getAvVmGPU());
                this.cluster.getVmArray().set(this.cluster.getVmArray().indexOf(v), tempVm);
                tempVm.setVmId(v.getVmId());
                tempVm.resetCounter();
                System.out.println(this.cluster.getVmArray());
                System.out.println(this.cluster.getTotalCores());
            }
            if (v.returnType().equals("VmNetworked")) {
                VmNetworked netV = (VmNetworked) v;
                VmNetworked tempVm = new VmNetworked();
                if (!os.equals(v.getVmOs()))
                    tempVm.setVmOs(os);       
                else
                    tempVm.setVmOs(v.getVmOs());     
                if (cores != 0) {
                    tempVm.setAvVmCores(cores);
                    if (v.getAvVmCores() > cores)
                        this.cluster.setTotalCores(this.cluster.getTotalCores() + v.getAvVmCores() - cores);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalCores() - (cores - v.getAvVmCores()));
                }
                else
                    tempVm.setAvVmCores(v.getAvVmCores());
                if (ram != 0) {
                    tempVm.setAvVmRam(ram);
                    if (v.getAvVmRam() > ram)
                        this.cluster.setTotalRam(this.cluster.getTotalRam() + v.getAvVmRam() - ram);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalRam() - (ram - v.getAvVmRam()));
                }
                else
                    tempVm.setAvVmRam(v.getAvVmRam());          
                if (ssd != 0) {
                    tempVm.setAvVmSsd(ssd);
                    if (netV.getAvVmSsd() > ssd)
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() + netV.getAvVmSsd() - ssd);
                    else
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() - (ssd - netV.getAvVmSsd()));
                }
                else
                    tempVm.setAvVmSsd(netV.getAvVmSsd());   
                if (bandwidth != 0) {
                    tempVm.setAvVmBandwidth(bandwidth);
                    if (netV.getAvVmBandwidth() > bandwidth)
                        this.cluster.setTotalBandwidth(this.cluster.getTotalBandwidth() + netV.getAvVmBandwidth() - bandwidth);
                    else
                        this.cluster.setTotalBandwidth(this.cluster.getTotalBandwidth() - (bandwidth - netV.getAvVmBandwidth()));
                }
                else
                    tempVm.setAvVmBandwidth(netV.getAvVmBandwidth());
                this.cluster.getVmArray().set(this.cluster.getVmArray().indexOf(v), tempVm);
                tempVm.setVmId(v.getVmId());
                tempVm.resetCounter();
                System.out.println(this.cluster.getVmArray());
                System.out.println(this.cluster.getTotalCores());
            }
            if (v.returnType().equals("VmNetworkedGPU")) {
                VmNetworkedGPU netGpuV = (VmNetworkedGPU) v;
                VmNetworkedGPU tempVm = new VmNetworkedGPU();
                if (!os.equals(v.getVmOs()))
                    tempVm.setVmOs(os);       
                else
                    tempVm.setVmOs(v.getVmOs());     
                if (cores != 0) {
                    tempVm.setAvVmCores(cores);
                    if (v.getAvVmCores() > cores)
                        this.cluster.setTotalCores(this.cluster.getTotalCores() + v.getAvVmCores() - cores);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalCores() - (cores - v.getAvVmCores()));
                }
                else
                    tempVm.setAvVmCores(v.getAvVmCores());
                if (ram != 0) {
                    tempVm.setAvVmRam(ram);
                    if (v.getAvVmRam() > ram)
                        this.cluster.setTotalRam(this.cluster.getTotalRam() + v.getAvVmRam() - ram);
                    else
                        this.cluster.setTotalCores(this.cluster.getTotalRam() - (ram - v.getAvVmRam()));
                }
                else
                    tempVm.setAvVmRam(v.getAvVmRam());          
                if (ssd != 0) {
                    tempVm.setAvVmSsd(ssd);
                    if (netGpuV.getAvVmSsd() > ssd)
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() + netGpuV.getAvVmSsd() - ssd);
                    else
                        this.cluster.setTotalSsd(this.cluster.getTotalSsd() - (ssd - netGpuV.getAvVmSsd()));
                }
                else
                    tempVm.setAvVmSsd(netGpuV.getAvVmSsd());   
                if (bandwidth != 0) {
                    tempVm.setAvVmBandwidth(bandwidth);
                    if (netGpuV.getAvVmBandwidth() > bandwidth)
                        this.cluster.setTotalBandwidth(this.cluster.getTotalBandwidth() + netGpuV.getAvVmBandwidth() - bandwidth);
                    else
                        this.cluster.setTotalBandwidth(this.cluster.getTotalBandwidth() - (bandwidth - netGpuV.getAvVmBandwidth()));
                }
                else
                    tempVm.setAvVmBandwidth(netGpuV.getAvVmBandwidth());
                if (gpu != 0) {
                    tempVm.setAvVmGPU(bandwidth);
                    if (netGpuV.getAvVmGPU() > bandwidth)
                        this.cluster.setTotalGPU(this.cluster.getTotalGPU() + netGpuV.getAvVmGPU() - gpu);
                    else
                        this.cluster.setTotalGPU(this.cluster.getTotalGPU() - (gpu - netGpuV.getAvVmGPU()));
                }
                else
                    tempVm.setAvVmGPU(netGpuV.getAvVmGPU());
                this.cluster.getVmArray().set(this.cluster.getVmArray().indexOf(v), tempVm);
                tempVm.setVmId(v.getVmId());
                tempVm.resetCounter();
                System.out.println(this.cluster.getVmArray());
                System.out.println(this.cluster.getTotalCores());
            }
        }
        else
            System.out.println("You must choose a vm to update.");
    }

    public void vmDelete(Vm v, JComboBox<Vm> comboBox) {
        if (v != null){
            this.cluster.getVmArray().remove(v);
            comboBox.setSelectedIndex(-1);
        }
        else
            System.out.println("Cannot delete a null vm");

        System.out.println(this.getVmArray());
    }

    public void enableContinue() {
        this.view.getBotPanel().getMenu().getBtnContinue().setEnabled(true);
    }

    public void showProgramMenu() {
        this.view.getBotPanel().showCard("pMenu");
        this.view.getTopPanel().showCard("pMenu");
    }

    public void showCreateProgram() {
        this.view.getBotPanel().showCard("cProgram");
        this.view.getTopPanel().showCard("cpHeader");
    }

    public void createProgram(int cores, int ram, int ssd, int gpu, int bandwidth, int time) {
        Program p = new Program(cores, ram, ssd, gpu, bandwidth, time);
        this.cluster.getProgArray().add(p);
        System.out.println(this.cluster.getProgArray());
    }

    public void runPrograms() {
        this.cluster.addToQueue();
        this.cluster.runPrograms();
        this.quit();
    }
}
