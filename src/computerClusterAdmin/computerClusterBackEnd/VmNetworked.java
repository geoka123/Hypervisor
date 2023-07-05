package computerClusterAdmin.computerClusterBackEnd;

import computerClusterAdmin.storageManager.UnMarshalingException;

import utils.NotEnoughBandwidthException;

public class VmNetworked extends PlainVm {
    protected int avVmBandwidth;
    protected int alVmBandwidth;

    public VmNetworked(int cores, int ram, String os, int ssd, int bandwidth) {
        super(cores, ram, os, ssd);
        this.avVmBandwidth = bandwidth;
        this.alVmBandwidth = 0;
    }

    public VmNetworked() {
        super();
    }

    public String toString() {
        return (super.toString() + "Vm Bandwidth: " + this.avVmBandwidth + "\t");
    }

    @Override
    public String returnType() {
        return "VmNetworked";
    }

    public boolean checkAvailableResources(int totalCores, int totalRam, int totalSsd, int totalGpu,
            int totalBandwidth) {
        if (!super.checkAvailableResources(totalCores, totalRam, totalSsd, totalGpu, totalBandwidth))
            return false;
        if (totalBandwidth < avVmBandwidth) {
            System.out.println("not enough gpu available");
            return false;
        }
        return true;
    }

    public boolean checkProgramResources(int pCores, int pRam, int pSsd, int pBandwidth, int pGPU) {
        if (!super.checkProgramResources(pCores, pRam, pSsd, pBandwidth, pGPU))
            return false;
        if (pBandwidth > avVmBandwidth) {
            System.out.println("not enough bandwidth available");
            return false;
        }
        return true;
    }

    @Override
    public void calculateVmLoad() {
        this.vmLoad += (((double) this.alVmCores / (this.avVmCores + this.alVmCores))
                + ((double) this.alVmRam / (this.avVmRam + this.alVmRam))
                + ((double) this.alVmSsd / (this.avVmSsd + this.alVmSsd))
                + ((double) this.alVmBandwidth / (this.avVmBandwidth + this.alVmBandwidth))) / 4.0;
        this.vmLoad *= 100;
    }

    @Override
    public void unMarshalVm(String Data) throws UnMarshalingException {
        try {
            String[] vmResources = Data.split(",");
            for (String vmResource : vmResources) {
                String[] keyValue = vmResource.split(":");
                switch (keyValue[0].toLowerCase()) {
                    case "os": {
                        this.setVmOs(keyValue[1].toUpperCase());
                        break;
                    }
                    case "cores": {
                        this.setAvVmCores(Integer.parseInt(keyValue[1]));
                        break;
                    }
                    case "ram": {
                        this.setAvVmRam(Integer.parseInt(keyValue[1]));
                        break;
                    }
                    case "ssd": {
                        this.setAvVmSsd(Integer.parseInt(keyValue[1]));
                        break;
                    }
                    case "bandwidth": {
                        if (Integer.parseInt(keyValue[1])>=4)
                            this.setAvVmBandwidth(Integer.parseInt(keyValue[1]));
                        else {
                            throw new NotEnoughBandwidthException("Bandwidth too low. Must be greater or equal than 4.");
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            throw new UnMarshalingException(e.getMessage());
        }
    }

    @Override
    public String marshalVm() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.marshalVm()).append(",").append("bandwidth:").append(this.avVmBandwidth);
        return sb.toString();
    }

    public void allocateVmResources(String data) {
        try {
            super.allocateVmResources(data);
            String[] vmResources = data.split(",");
            for (String resource : vmResources) {
                String keyVal[] = resource.split(":");
                switch (keyVal[0].toLowerCase()) {
                    case "bandwidth": {
                        this.avVmSsd -= Integer.parseInt(keyVal[1]);
                        this.alVmSsd += Integer.parseInt(keyVal[1]);
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
        super.freeVmResources(data);
        try {
            String[] vmResources = data.split(",");
            for (String resource : vmResources) {
                String keyVal[] = resource.split(":");
                switch (keyVal[0].toLowerCase()) {
                    case "bandwidth": {
                        this.avVmBandwidth += Integer.parseInt(keyVal[1]);
                        this.alVmBandwidth -= Integer.parseInt(keyVal[1]);
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

    public int getAvVmBandwidth() {
        return avVmBandwidth;
    }

    public void setAvVmBandwidth(int avVmBandwidth) {
        this.avVmBandwidth = avVmBandwidth;
    }

    public int getAlVmBandwidth() {
        return alVmBandwidth;
    }

    public void setAlVmBandwidth(int alVmBandwidth) {
        this.alVmBandwidth = alVmBandwidth;
    }

}
