package computerClusterAdmin.clusterGUI.Control;

//---------Imports---------
import javax.swing.JPanel;

// Φτιάχνουμε ένα JPanel το οποίο ενσωματώνει υποχρεωτικά ένα αντικείμενο τύπου Controller μέσα του. 
// Όλα τα υπόλοιπα panel του GUI θα κληρονομούν από αυτή την κλάση.

public class ControlledPanel extends JPanel{
    protected Controller gc;

    public ControlledPanel(Controller gc) {
        this.gc = gc;
    }

    public Controller getGc() {
        return gc;
    }

    public void setGc(Controller gc) {
        this.gc = gc;
    }

}
