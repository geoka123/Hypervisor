package computerClusterAdmin.clusterGUI.view;

import java.awt.Font;

import javax.swing.JButton;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;

public class Menu extends ControlledPanel{

    public Menu(Controller gc) {
		super(gc);
		setLayout(null);
		
		JButton createBtn = new JButton("CREATE VM");
		createBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		createBtn.setBounds(100, 10, 153, 33);
		createBtn.addActionListener((e) -> {
			this.gc.vmCreation();
		});
		add(createBtn);
		
		JButton btnVmGpu = new JButton("UPDATE VM");
		btnVmGpu.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		btnVmGpu.setBounds(100, 86, 153, 33);
		add(btnVmGpu);
		
		JButton btnVmNetworked = new JButton("DELETE VM");
		btnVmNetworked.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		btnVmNetworked.setBounds(100, 160, 157, 33);
		add(btnVmNetworked);
		
		JButton btnVmNetworkedGpu = new JButton("TAKE REPORT");
		btnVmNetworkedGpu.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		btnVmNetworkedGpu.setBounds(100, 235, 157, 33);
		add(btnVmNetworkedGpu);
    }
}

