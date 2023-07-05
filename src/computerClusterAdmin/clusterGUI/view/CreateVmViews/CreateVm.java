package computerClusterAdmin.clusterGUI.view.CreateVmViews;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class CreateVm extends ControlledPanel{
    public CreateVm(Controller gc) {
		super(gc);
		setLayout(null);
		
		JButton plainVmBtn = new JButton("PLAIN VM");
		plainVmBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		plainVmBtn.setBounds(100, 10, 153, 33);
		plainVmBtn.addActionListener((e) -> {
			this.gc.showPlainVmCreationForm();
		});
		add(plainVmBtn);
		
		JLabel lblNewLabel_1 = new JLabel("CPU, RAM, SSD");
		lblNewLabel_1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(312, 12, 173, 28);
		add(lblNewLabel_1);
		
		JButton btnVmGpu = new JButton("VM GPU");
		btnVmGpu.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		btnVmGpu.setBounds(100, 86, 153, 33);
		btnVmGpu.addActionListener((e -> {
			this.gc.showVmGPUCreationForm();
		} ));
		add(btnVmGpu);
		
		JLabel lblNewLabel_1_1 = new JLabel("CPU, RAM, SSD, GPU");
		lblNewLabel_1_1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(312, 91, 193, 28);
		add(lblNewLabel_1_1);
		
		JButton btnVmNetworked = new JButton("VM NETWORKED");
		btnVmNetworked.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		btnVmNetworked.setBounds(100, 160, 157, 33);
		btnVmNetworked.addActionListener((e) -> {
			this.gc.showVmNetworkedCreationForm();
		});
		add(btnVmNetworked);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("CPU, RAM, SSD, BANDWIDTH");
		lblNewLabel_1_1_1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(312, 161, 249, 28);
		add(lblNewLabel_1_1_1);
		
		JButton btnVmNetworkedGpu = new JButton("VM NETWORKED GPU");
		btnVmNetworkedGpu.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		btnVmNetworkedGpu.setBounds(100, 235, 193, 33);
		btnVmNetworkedGpu.addActionListener((e) -> {
			this.gc.showVmNetworkedGPUCreationForm();
		});
		add(btnVmNetworkedGpu);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("CPU, RAM, SSD, BANDWIDTH, GPU");
		lblNewLabel_1_1_1_1.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(312, 236, 295, 28);
		add(lblNewLabel_1_1_1_1);

		JButton backBtn = new JButton("BACK");
		backBtn.setForeground(new Color(240, 0, 0));
		backBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		backBtn.setBackground(Color.LIGHT_GRAY);
		backBtn.setBounds(10, 319, 85, 21);
		backBtn.addActionListener((e) -> {
			this.gc.goBackVm();
		});
		add(backBtn);
	}
}
