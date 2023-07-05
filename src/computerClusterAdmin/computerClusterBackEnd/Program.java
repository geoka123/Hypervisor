package computerClusterAdmin.computerClusterBackEnd;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import computerClusterAdmin.storageManager.StorableProgram;
import computerClusterAdmin.storageManager.UnMarshalingException;

import utils.DuplicateEntryException;
import utils.Globals;

public class Program implements StorableProgram, Comparable<Program>, Serializable {
    private int programId;
    private int programDemandedCores;
    private int programDemandedRAM;
    private int programDemandedSSD;
    private int programDemandedGPU;
    private int programDemandedBandwidth;
    private String programOS;
    private int programExpectedTime;
    private int programExecutionTime;
    private int executionTime;
    private Date initTime;
    private double programPriority;
    private static HashSet<Integer> pIdSet = new HashSet<>();
    private int rejected;

    // ------------------------------Constructors--------------------------------
    public Program(int cores, int ram, int ssd, int gpu, int bandwidth, int expectedTime) {
        this.programDemandedCores = cores;
        this.programDemandedRAM = ram;
        this.programDemandedSSD = ssd;
        if (gpu != 0)
            this.programDemandedGPU = gpu;
        if (bandwidth != 0)
            this.programDemandedBandwidth = bandwidth;
        this.programExpectedTime = expectedTime;
        this.programId = generateProgramId();
        this.rejected = 0;
    }

    public Program() {
        this.programId = generateProgramId();
        this.rejected = 0;
    }

    // Marshaling a Program object (converting to String)
    @Override
    public String marshalProgram() {
        StringBuffer sb = new StringBuffer("cores:");
        sb.append(this.programDemandedCores).append(",").append("ram:").append(this.programDemandedRAM).append(",")
                .append("ssd:").append(this.programDemandedSSD).append(",");
        if (this.programDemandedGPU != 0)
            sb.append("gpu:").append(this.programDemandedGPU).append(",");
        if (this.programDemandedBandwidth != 0)
            sb.append("bandwidth:").append(this.programDemandedBandwidth).append(",");
        sb.append("time:").append(this.programExpectedTime);
        return sb.toString();
    }

    // Overriding the toString method so that it prints out the attributes of a
    // Program object in a more elegant way
    @Override
    public String toString() {
        String s1 = "ProgramId: " + this.programId + "\t" + "Program Cores: " + this.programDemandedCores + "\t"
                + "Program RAM: " + this.programDemandedRAM + "\t"
                + "Program SSD: " + this.programDemandedSSD + "\t"
                + "Program Expected Time: " + this.programExpectedTime + "\t" + "Program OS: " + this.programOS + "\t"
                + "Program Priority: " + this.programPriority + "\t";

        if (this.programDemandedGPU != 0)
            s1 += "Program GPU: " + this.programDemandedGPU + "\t";
        if (this.programDemandedBandwidth != 0)
            s1 += "Program Bandwidth: " + this.programDemandedBandwidth + "\t";

        return s1;
    }

    @Override
    public void unMarshalProgram(String Data) throws UnMarshalingException {
        String[] progResources = Data.split(",");
        for (String progResource : progResources) {
            String[] keyValue = progResource.split(":");
            switch (keyValue[0].toLowerCase()) {
                case "cores": {
                    this.setProgramDemandedCores(Integer.parseInt(keyValue[1]));
                    break;
                }
                case "ram": {
                    this.setProgramDemandedRAM(Integer.parseInt(keyValue[1]));
                    break;
                }
                case "ssd": {
                    this.setProgramDemandedSSD(Integer.parseInt(keyValue[1]));
                    break;
                }
                case "time": {
                    this.setProgramExpectedTime(Integer.parseInt(keyValue[1]));
                    break;
                }
                case "gpu": {
                    this.setProgramDemandedGPU(Integer.parseInt(keyValue[1]));
                    break;
                }
                case "bandwidth": {
                    this.setProgramDemandedBandwidth(Integer.parseInt(keyValue[1]));
                    break;
                }
                default:
                    break;
            }
        }
    }

    @Override
    public int compareTo(Program o) {
        if (this.programPriority < o.programPriority)
            return -1;
        else if (this.programPriority > o.programPriority)
            return 1;
        else
            return 0;
    }

    // Calculate the program's priority using the known formula
    public void calculatePriority(int totalCores, int totalRAM, int totalSSD, int totalGPU, int totalBandwidth) {
        this.programPriority = (double) this.programDemandedCores / totalCores
                + (double) this.programDemandedRAM / totalRAM
                + (double) this.programDemandedSSD / totalSSD;

        if (this.programDemandedGPU != 0 && totalGPU != 0)
            this.programPriority += (double) this.programDemandedGPU / totalGPU;
        if (this.programDemandedBandwidth != 0 && totalBandwidth != 0)
            this.programPriority += (double) this.programDemandedBandwidth / totalBandwidth;
    }

    // Calculate executionTime using two known time stamps
    public void calculateExecutionTime(Date currentExecTime, Date startExecTime) {
        this.executionTime = Globals.computeDuration(startExecTime, currentExecTime);
    }

    // Calculate programExecutionTime based on the time the program was assigned to
    // a VM and the current time
    protected void calculateProgramExecutionTime(Date initTime) {
        Date currentTime = new Date();
        this.programExecutionTime = Globals.computeDuration(initTime, currentTime);
    }

    // Generate a random and unique integer for the program ID
    private int generateProgramId() {
        Random r = new Random();
        int id = r.nextInt(Integer.MAX_VALUE); // Generate a positive Integer

        try {
            boolean added = pIdSet.add(id);
            if (!added)
                throw new DuplicateEntryException("Program id cannot be duplicate...");
            else
                return id;
        } catch (DuplicateEntryException e) {
            System.out.println("Program id cannot be duplicate...");
            return -1;
        }
    }

    // ------------------------------Getters--------------------------------
    protected int getProgramId() {
        return programId;
    }

    protected int getProgramDemandedCores() {
        return programDemandedCores;
    }

    protected int getProgramDemandedRAM() {
        return programDemandedRAM;
    }

    protected int getProgramDemandedSSD() {
        return programDemandedSSD;
    }

    protected int getProgramDemandedGPU() {
        return programDemandedGPU;
    }

    protected int getProgramDemandedBandwidth() {
        return programDemandedBandwidth;
    }

    protected String getProgramOS() {
        return programOS;
    }

    protected int getProgramExpectedTime() {
        return programExpectedTime;
    }

    protected int getProgramExecutionTime() {
        return programExecutionTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public double getProgramPriority() {
        return programPriority;
    }

    public HashSet<Integer> getpIdSet() {
        return pIdSet;
    }

    // ------------------------------Setters--------------------------------
    protected void setProgramId(int programId) {
        this.programId = programId;
    }

    private void setProgramDemandedCores(int programDemandedCores) {
        this.programDemandedCores = programDemandedCores;
    }

    private void setProgramDemandedRAM(int programDemandedRAM) {
        this.programDemandedRAM = programDemandedRAM;
    }

    private void setProgramDemandedSSD(int programDemandedSSD) {
        this.programDemandedSSD = programDemandedSSD;
    }

    private void setProgramDemandedGPU(int programDemandedGPU) {
        this.programDemandedGPU = programDemandedGPU;
    }

    private void setProgramDemandedBandwidth(int programDemandedBandwidth) {
        this.programDemandedBandwidth = programDemandedBandwidth;
    }

    protected void setProgramOS(String programOS) {
        if (Globals.checkOs(programOS))
            this.programOS = programOS;
    }

    private void setProgramExpectedTime(int programExpectedTime) {
        this.programExpectedTime = programExpectedTime;
    }

    protected void setProgramExecutionTime(int programExecutionTime) {
        this.programExecutionTime = programExecutionTime;
    }

    protected void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    protected void setProgramPriority(double programPriority) {
        this.programPriority = programPriority;
    }

    protected static void setpIdSet(HashSet<Integer> pSet) {
        pIdSet = pSet;
    }

    protected int getRejected() {
        return rejected;
    }

    protected void setRejected(int rejected) {
        this.rejected = rejected;
    }

    protected Date getInitTime() {
        return initTime;
    }

    protected void setInitTime(Date initTime) {
        this.initTime = initTime;
    }
}
