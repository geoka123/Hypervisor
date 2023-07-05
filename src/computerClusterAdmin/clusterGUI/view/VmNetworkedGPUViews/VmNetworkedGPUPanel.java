package computerClusterAdmin.clusterGUI.view.VmNetworkedGPUViews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;

public class VmNetworkedGPUPanel extends ControlledPanel{
    private JTextField cores;
    private JTextField ram;
    private JTextField ssd;
    private JTextField gpu;
    private JTextField os;
    private JTextField bandwidth;

    public VmNetworkedGPUPanel(Controller gc) {
        super(gc);
        setLayout(null);

		JButton backBtn = new JButton("BACK");

		backBtn.setForeground(new Color(240, 0, 0));

		backBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));

		backBtn.setBackground(Color.LIGHT_GRAY);

		backBtn.setBounds(10, 319, 85, 21);
		backBtn.addActionListener((e) -> {
			this.gc.goBackVm();
		});
		add(backBtn);

		ssd = new JTextField();
		ssd.setBackground(new Color(192, 192, 192));
		ssd.setColumns(10);
		ssd.setBounds(429, 179, 171, 19);
		add(ssd);

		JLabel lblNewLabel_1_3 = new JLabel("SSD:");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		lblNewLabel_1_3.setBounds(287, 168, 132, 35);
		add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_2 = new JLabel("RAM:");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(287, 134, 132, 35);
		add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_1 = new JLabel("CPU:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(287, 100, 132, 35);
		add(lblNewLabel_1_1);

		cores = new JTextField();
		cores.setBackground(new Color(192, 192, 192));
		cores.setColumns(10);
		cores.setBounds(429, 111, 171, 19);
		add(cores);

		ram = new JTextField();
		ram.setBackground(new Color(192, 192, 192));
		ram.setColumns(10);
		ram.setBounds(429, 145, 171, 19);
		add(ram);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener((e) -> {
		});
		btnOk.setForeground(new Color(240, 0, 0));
		btnOk.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		btnOk.setBackground(Color.LIGHT_GRAY);
		btnOk.setBounds(805, 318, 85, 21);
        btnOk.addActionListener((e) -> {
            this.gc.createVmNetworkedGPU(cores, ram, ssd, os, bandwidth, gpu);
            cores.setText("");
            ram.setText("");
            ssd.setText("");
            os.setText("");
            gpu.setText("");
            bandwidth.setText("");
        });
		add(btnOk);

		JLabel lblNewLabel_1_3_1 = new JLabel("GPU:");
		lblNewLabel_1_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		lblNewLabel_1_3_1.setBounds(287, 201, 132, 35);
		add(lblNewLabel_1_3_1);

		gpu = new JTextField();
		gpu.setBackground(new Color(192, 192, 192));
		gpu.setColumns(10);
		gpu.setBounds(429, 212, 171, 19);
		add(gpu);

		JLabel lblNewLabel_1_1_1 = new JLabel("OS:");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		lblNewLabel_1_1_1.setBounds(287, 66, 132, 35);
		add(lblNewLabel_1_1_1);

		os = new JTextField();
		os.setColumns(10);
		os.setBackground(Color.LIGHT_GRAY);
		os.setBounds(429, 77, 171, 19);
		add(os);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("B/W:");
		lblNewLabel_1_3_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3_1_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		lblNewLabel_1_3_1_1.setBounds(287, 238, 132, 35);
		add(lblNewLabel_1_3_1_1);
		
		bandwidth = new JTextField();
		bandwidth.setColumns(10);
		bandwidth.setBackground(Color.LIGHT_GRAY);
		bandwidth.setBounds(429, 249, 171, 19);
		add(bandwidth);
    }
}
