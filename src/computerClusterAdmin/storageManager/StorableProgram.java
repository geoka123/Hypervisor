package computerClusterAdmin.storageManager;

public interface StorableProgram {

    void unMarshalProgram(String Data) throws UnMarshalingException;

    String marshalProgram();

}
