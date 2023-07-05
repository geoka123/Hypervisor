package computerClusterAdmin.clusterGUI.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;
import computerClusterAdmin.clusterGUI.view.DeleteVmViews.DeleteVmHeader;
import computerClusterAdmin.clusterGUI.view.TakeReportViews.TakeVmReportHeader;
import computerClusterAdmin.clusterGUI.view.CreateProgramViews.CreateProgramHeader;
import computerClusterAdmin.clusterGUI.view.CreateVmViews.CreateVmHeader;
import computerClusterAdmin.clusterGUI.view.ProgramMenuViews.ProgramMenuGUI;
import computerClusterAdmin.clusterGUI.view.UpdateVmViews.UpdateVmHeader;
import computerClusterAdmin.clusterGUI.view.VmMenuViews.VmMenuGUI;

public class TopPanel extends ControlledPanel{
    CardLayout cards;
    VmMenuGUI vmMenu;
    ProgramMenuGUI progMenu;
    JPanel cardPanel;
    CreateVmHeader cVmHeader;
    UpdateVmHeader uVmHeader;
    DeleteVmHeader dVmHeader;
    TakeVmReportHeader trVmHeader;
    CreateProgramHeader cpHeader;

    public TopPanel(Controller gc) {
        super(gc);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(900, 100));

        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        vmMenu = new VmMenuGUI();
        progMenu = new ProgramMenuGUI();
        cVmHeader = new CreateVmHeader();
        uVmHeader = new UpdateVmHeader();
        dVmHeader = new DeleteVmHeader();
        trVmHeader = new TakeVmReportHeader();
        cpHeader = new CreateProgramHeader();

        cardPanel.add(vmMenu, "vMenu");
        cardPanel.add(progMenu, "pMenu");
        cardPanel.add(cVmHeader, "cVmHeader");
        cardPanel.add(uVmHeader, "uVmHeader");
        cardPanel.add(dVmHeader, "dVmHeader");
        cardPanel.add(trVmHeader, "trVmHeader");
        cardPanel.add(cpHeader, "cpHeader");
        this.showCard("vMenu");

        this.add(cardPanel, BorderLayout.CENTER);
    }

    public void showCard(String s) {
        cards.show(cardPanel, s);
    }
}
