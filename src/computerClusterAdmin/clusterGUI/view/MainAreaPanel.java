package computerClusterAdmin.clusterGUI.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;
import computerClusterAdmin.clusterGUI.view.DeleteVmViews.DeleteVm;
import computerClusterAdmin.clusterGUI.view.ProgramMenu.ProgramMenu;
import computerClusterAdmin.clusterGUI.view.TakeReportViews.TakeVmReport;
import computerClusterAdmin.clusterGUI.view.CreateProgramViews.CreateProgram;
import computerClusterAdmin.clusterGUI.view.CreateVmViews.CreateVm;
import computerClusterAdmin.clusterGUI.view.PlainVmViews.PlainVmPanel;
import computerClusterAdmin.clusterGUI.view.UpdateVmViews.UpdateVm;
import computerClusterAdmin.clusterGUI.view.VmGPUViews.VmGPUPanel;
import computerClusterAdmin.clusterGUI.view.VmMenuViews.VmMenu;
import computerClusterAdmin.clusterGUI.view.VmNetworkedGPUViews.VmNetworkedGPUPanel;
import computerClusterAdmin.clusterGUI.view.VmNetworkedViews.VmNetworkedPanel;

public class MainAreaPanel extends ControlledPanel{
    CardLayout cards;
    JPanel cardPanel;
    CreateVm create;
    UpdateVm update;
    DeleteVm delete;
    TakeVmReport takeReport;
    PlainVmPanel pVmPanel;
    VmGPUPanel pGPUVm;
    VmNetworkedPanel netVmPanel;
    VmNetworkedGPUPanel netGPUVmPanel;
    VmMenu menu;
    ProgramMenu pMenu;
    CreateProgram cProgram;

    public MainAreaPanel(Controller gc) {
        super(gc);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(900, 350));

        cards = new CardLayout();

        cardPanel = new JPanel();
        cardPanel.setLayout(cards);

        menu = new VmMenu(this.gc);
        create = new CreateVm(this.gc);
        pVmPanel = new PlainVmPanel(gc);
        pGPUVm = new VmGPUPanel(this.gc);
        netVmPanel = new VmNetworkedPanel(this.gc);
        netGPUVmPanel = new VmNetworkedGPUPanel(this.gc);
        update = new UpdateVm(this.gc);
        delete = new DeleteVm(this.gc);
        takeReport = new TakeVmReport(this.gc);
        pMenu = new ProgramMenu(this.gc);
        cProgram = new CreateProgram(this.gc);
        
        cardPanel.add(menu, "cMenu");
        cardPanel.add(create, "createVm");
        cardPanel.add(pVmPanel, "pVmPanel");
        cardPanel.add(pGPUVm,"pGPUVm");
        cardPanel.add(netVmPanel,"netVmPanel");
        cardPanel.add(netGPUVmPanel,"netGPUVmPanel");
        cardPanel.add(update, "updateVm");
        cardPanel.add(delete, "deleteVm");
        cardPanel.add(takeReport, "takeReport");
        cardPanel.add(pMenu, "pMenu");
        cardPanel.add(cProgram, "cProgram");

        cards.show(cardPanel, "cMenu");

        this.add(cardPanel);
    }

    public void showCard(String s) {
        cards.show(cardPanel, s);
    }

    public VmMenu getMenu() {
        return menu;
    }

    public void setMenu(VmMenu menu) {
        this.menu = menu;
    }

    public UpdateVm getUpdate() {
        return update;
    }

    public void setUpdate(UpdateVm update) {
        this.update = update;
    }

    public TakeVmReport getTakeReport() {
        return takeReport;
    }

    
}
