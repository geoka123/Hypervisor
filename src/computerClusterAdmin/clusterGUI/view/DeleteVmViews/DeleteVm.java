package computerClusterAdmin.clusterGUI.view.DeleteVmViews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;
import computerClusterAdmin.computerClusterBackEnd.Vm;

public class DeleteVm extends ControlledPanel{

    JComboBox<Vm> comboBox;
    public DeleteVm(Controller controller) {
        super(controller);
        setLayout(null);
		
		comboBox = new JComboBox<>(controller.getVmArray());
		comboBox.setBounds(256, 29, 367, 27);
		add(comboBox);
		
		JButton backBtn = new JButton("BACK");
        backBtn.setForeground(new Color(240, 0, 0));
		backBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		backBtn.setBackground(Color.LIGHT_GRAY);
		backBtn.setBounds(10, 319, 85, 21);
        backBtn.addActionListener((e) -> {
            controller.goBackVm();
        });
		add(backBtn);
		
		JButton okBtn = new JButton("OK");
        okBtn.setForeground(new Color(240, 0, 0));
		okBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		okBtn.setBackground(Color.LIGHT_GRAY);
		okBtn.setBounds(805, 319, 85, 21);
        okBtn.addActionListener((e) -> {
            this.gc.vmDelete((Vm) comboBox.getSelectedItem(),comboBox);
            this.gc.goBackVm();
        });
		add(okBtn);

    }
    public JComboBox<Vm> getComboBox() {
        return comboBox;
    }
    public void setComboBox(JComboBox<Vm> comboBox) {
        this.comboBox = comboBox;
    }

    

    
}
