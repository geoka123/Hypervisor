package computerClusterAdmin.storageManager;

public interface StorableVm {

    void unMarshalVm(String Data) throws UnMarshalingException;

    String marshalVm();

}
