package computerClusterAdmin.computerClusterBackEnd;

import computerClusterAdmin.storageManager.UnMarshalingException;

public class PlainVm extends Vm {
    public int avVmSsd;
    public int alVmSsd;

    public PlainVm(int cores, int ram, String os, int ssd) {
        super(cores, ram, os);
        this.avVmSsd = ssd;
        this.alVmSsd = 0;
    }

    public PlainVm() {
        super();
    }

    public String toString() {
        return (super.toString() + "Vm SSD: " + this.avVmSsd + "\t");
    }

    @Override
    public String returnType() {
        return "PlainVm";
    }

    public boolean checkAvailableResources(int totalCores, int totalRam, int totalSsd, int totalGpu,
            int totalBandwidth) {
        if (!super.checkAvailableResources(totalCores, totalRam, totalSsd, totalGpu, totalBandwidth))
            return false;
        if (totalSsd < avVmSsd) {
            System.out.println("not enough ssd available");
            return false;
        }
        return true;
    }

    public boolean checkProgramResources(int pCores, int pRam, int pSsd, int pBandwidth, int pGPU) {
        if (!super.checkProgramResources(pCores, pRam, pSsd, pBandwidth, pGPU))
            return false;
        if (pSsd > avVmSsd) {
            System.out.println("not enough ssd available");
            return false;
        }
        return true;
    }

    @Override
    public void calculateVmLoad() {
        this.vmLoad += (((double) this.alVmCores / (this.avVmCores + this.alVmCores))
                + ((double) this.alVmRam / (this.avVmRam + this.alVmRam))
                + ((double) this.alVmSsd / (this.avVmSsd + this.alVmSsd))) / 3.0;
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
        sb.append(super.marshalVm()).append("ssd:").append(this.avVmSsd);
        return sb.toString();
    }

    public void allocateVmResources(String data) {
        try {
            super.allocateVmResources(data);
            String[] vmResources = data.split(",");
            for (String resource : vmResources) {
                String keyVal[] = resource.split(":");
                switch (keyVal[0].toLowerCase()) {
                    case "ssd": {
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
                    case "ssd": {
                        this.avVmSsd += Integer.parseInt(keyVal[1]);
                        this.alVmSsd -= Integer.parseInt(keyVal[1]);
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

    public int getAvVmSsd() {
        return avVmSsd;
    }

    public void setAvVmSsd(int avVmSsd) {
        this.avVmSsd = avVmSsd;
    }

    public int getAlVmSsd() {
        return alVmSsd;
    }

    public void setAlVmSsd(int alVmSsd) {
        this.alVmSsd = alVmSsd;
    }

    
}
