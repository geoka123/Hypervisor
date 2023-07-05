package computerClusterAdmin.computerClusterBackEnd;

import computerClusterAdmin.storageManager.UnMarshalingException;

public class VmGPU extends PlainVm {
    private int avVmGPU;
    private int alVmGPU;

    public VmGPU(int cores, int ram, String os, int ssd, int gpu) {
        super(cores, ram, os, ssd);
        this.avVmGPU = gpu;
        this.alVmGPU = 0;
        this.vmLoad = 0;
    }

    public VmGPU() {
        super();
        this.vmLoad = 0;
    }

    @Override
    public String toString() {
        return (super.toString() + "Vm GPU: " + this.avVmGPU + "\t");
    }

    @Override
    public String returnType() {
        return "VmGPU";
    }

    public boolean checkAvailableResources(int totalCores, int totalRam, int totalSsd, int totalGpu,
            int totalBandwidth) {
        if (!super.checkAvailableResources(totalCores, totalRam, totalSsd, totalGpu, totalBandwidth))
            return false;
        if (totalGpu < avVmGPU) {
            System.out.println("not enough gpu available");
            return false;
        }
        return true;
    }

    public boolean checkProgramResources(int pCores, int pRam, int pSsd, int pBandwidth, int pGPU) {
        if (!super.checkProgramResources(pCores, pRam, pSsd, pBandwidth, pGPU))
            return false;
        if (pGPU > avVmGPU) {
            System.out.println("not enough gpu available");
            return false;
        }
        return true;
    }

    @Override
    public void calculateVmLoad() {
        this.vmLoad = (((double) this.alVmCores / (this.avVmCores + this.alVmCores))
                + ((double) this.alVmRam / (this.avVmRam + this.alVmRam))
                + ((double) this.alVmSsd / (this.avVmSsd + this.alVmSsd))
                + ((double) this.alVmGPU / (this.avVmGPU + this.alVmGPU))) / 4.0;
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
                    case "gpu": {
                        this.setAvVmGPU(Integer.parseInt(keyValue[1]));
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
        sb.append(super.marshalVm()).append(",").append("gpu:").append(this.avVmGPU);
        return sb.toString();
    }

    public void allocateVmResources(String data) {
        try {
            super.allocateVmResources(data);
            String[] vmResources = data.split(",");
            for (String resource : vmResources) {
                String keyVal[] = resource.split(":");
                switch (keyVal[0].toLowerCase()) {
                    case "gpu": {
                        this.avVmGPU -= Integer.parseInt(keyVal[1]);
                        this.alVmGPU += Integer.parseInt(keyVal[1]);
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
                    case "gpu": {
                        this.avVmGPU += Integer.parseInt(keyVal[1]);
                        this.alVmGPU -= Integer.parseInt(keyVal[1]);
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

    public int getAvVmGPU() {
        return avVmGPU;
    }

    public void setAvVmGPU(int avVmGPU) {
        this.avVmGPU = avVmGPU;
    }

    public int getAlVmGPU() {
        return alVmGPU;
    }

    public void setAlVmGPU(int alVmGPU) {
        this.alVmGPU = alVmGPU;
    }
}
