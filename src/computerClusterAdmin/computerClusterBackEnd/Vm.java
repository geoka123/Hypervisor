package computerClusterAdmin.computerClusterBackEnd;

import java.util.Vector;

import computerClusterAdmin.storageManager.StorableVm;

import utils.Globals;

public abstract class Vm implements StorableVm, Comparable<Vm> {
    protected int vmId;
    protected int avVmCores;
    protected int avVmRam;
    protected String vmOs;
    protected double vmLoad;

    protected static int counter = 0;

    protected int alVmCores;
    protected int alVmRam;
    private Vector<Program> runningPrograms;

    public Vm(int cores, int ram, String os) {
        this.vmId = ++counter;
        this.avVmCores = cores;
        this.avVmRam = ram;
        if (Globals.checkOs(os))
            this.vmOs = os.toUpperCase();
        this.alVmCores = 0;
        this.alVmRam = 0;
        runningPrograms = new Vector<Program>();

    }

    public Vm() {
        this.vmId = ++counter;
        runningPrograms = new Vector<Program>();
    }

    @Override
    public String toString() {
        return ("VmId: " + this.vmId + "\t\t" + "Vm Cores: " + this.avVmCores + "\t" + "Vm RAM: " + this.avVmRam + "\t"
                + "Vm OS: " + this.vmOs + "\t" + "Vm Load: " + (int) this.vmLoad + "%" + "\t\t");
    }

    @Override
    public int compareTo(Vm o) {
        return (int) Math.floor(this.vmLoad - o.getVmLoad());
    }

    public boolean checkAvailableResources(int totalCores, int totalRam, int totalSsd, int totalGpu,
            int totalBandwidth) {
        if (totalCores < this.avVmCores) {
            System.out.println("not enough cores available");
            return false;
        }
        if (totalRam < this.avVmRam) {
            System.out.println("not enough ram available");
            return false;
        }
        return true;
    }

    public boolean checkProgramResources(int pCores, int pRam, int pSsd, int pBandwidth, int pGPU) {
        if (pCores > avVmCores) {
            System.out.println("not enough cores available");
            return false;
        }
        if (pRam > avVmRam) {
            System.out.println("not enough ram available");
            return false;
        }
        return true;
    }

    public void addToRunningPrograms(Program p) {
        if (this.runningPrograms.add(p))
            System.out.println("Program with id:" + p.getProgramId() + " succesfully added to VM with id: " + this.vmId + ".");
    }

    public abstract void calculateVmLoad();

    public String marshalVm() {
        StringBuffer sb = new StringBuffer("os:");
        sb.append(this.vmOs).append(",").append("cores:").append(this.avVmCores).append(",").append("ram:")
                .append(this.avVmRam).append(",");
        return sb.toString();
    }

    public void allocateVmResources(String data) {
        try {
            String[] vmResources = data.split(",");
            for (String resource : vmResources) {
                String keyVal[] = resource.split(":");
                switch (keyVal[0].toLowerCase()) {
                    case "cores": {
                        this.avVmCores -= Integer.parseInt(keyVal[1]);
                        this.alVmCores += Integer.parseInt(keyVal[1]);
                        break;
                    }
                    case "ram": {
                        this.avVmRam -= Integer.parseInt(keyVal[1]);
                        this.alVmRam += Integer.parseInt(keyVal[1]);
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while allocating resources of Vm with id: " + this.vmId);
        }
    }

    public void freeVmResources(String data) {
        try {
            String[] vmResources = data.split(",");
            for (String resource : vmResources) {
                String keyVal[] = resource.split(":");
                switch (keyVal[0].toLowerCase()) {
                    case "cores": {
                        this.avVmCores += Integer.parseInt(keyVal[1]);
                        this.alVmCores -= Integer.parseInt(keyVal[1]);
                        break;
                    }
                    case "ram": {
                        this.avVmRam += Integer.parseInt(keyVal[1]);
                        this.alVmRam -= Integer.parseInt(keyVal[1]);
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while freeing resources of Vm with id: " + this.vmId);
        }
    }

    public abstract String returnType();

    public int getVmId() {
        return vmId;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public int getAvVmCores() {
        return avVmCores;
    }

    public void setAvVmCores(int avVmCores) {
        this.avVmCores = avVmCores;
    }

    public int getAvVmRam() {
        return avVmRam;
    }

    public void setAvVmRam(int avVmRam) {
        this.avVmRam = avVmRam;
    }

    public String getVmOs() {
        return vmOs;
    }

    public void setVmOs(String vmOs) {
        if (Globals.checkOs(vmOs))
            this.vmOs = vmOs;
        else
            System.out.println("invalid os");
    }

    public void resetCounter() {
        counter--;
    }

    public double getVmLoad() {
        return vmLoad;
    }

    public void setVmLoad(double vmLoad) {
        this.vmLoad = vmLoad;
    }

    public int getAlVmCores() {
        return alVmCores;
    }

    public void setAlVmCores(int alVmCores) {
        this.alVmCores = alVmCores;
    }

    public int getAlVmRam() {
        return alVmRam;
    }

    public void setAlVmRam(int alVmRam) {
        this.alVmRam = alVmRam;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Vm.counter = counter;
    }

    public Vector<Program> getRunningPrograms() {
        return runningPrograms;
    }

    public void setRunningPrograms(Vector<Program> runningPrograms) {
        this.runningPrograms = runningPrograms;
    }
    
}
