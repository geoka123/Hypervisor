package computerClusterAdmin.clusterGUI.view.UpdateVmViews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;
import computerClusterAdmin.computerClusterBackEnd.Vm;

public class UpdateVm extends ControlledPanel{
    private JComboBox<String> os;
    private JSpinner cores;
    private JSpinner ram;
    private JSpinner ssd;
    private JSpinner gpu;
    private JSpinner bandwidth;

    public UpdateVm(Controller gc) {
        super(gc);
        setLayout(null);
		
		JComboBox<Vm> comboBox = new JComboBox<>(this.gc.getVmArray());
		comboBox.addActionListener((e) -> {
			if (e.getSource() == comboBox){
				this.gc.enableVmUpdateFields((Vm) comboBox.getSelectedItem());
			}
		});
		comboBox.setBounds(256, 29, 367, 27);
		add(comboBox);
		
		JLabel lblNewLabel = new JLabel("OS");
		lblNewLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(293, 96, 89, 42);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CPU");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		lblNewLabel_1.setBounds(293, 133, 89, 42);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("RAM");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(293, 169, 89, 42);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("SSD");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(293, 206, 89, 42);
		add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("GPU");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		lblNewLabel_1_3.setBounds(293, 242, 89, 42);
		add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("B/W");
		lblNewLabel_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_4.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		lblNewLabel_1_4.setBounds(293, 276, 89, 42);
		add(lblNewLabel_1_4);
		
		os = new JComboBox<>(this.gc.getOsArray());
		os.setBounds(407, 108, 112, 21);
		add(os);
		
		cores = new JSpinner(new SpinnerNumberModel(0, 0, this.gc.getCluster().getTotalCores(), 1));
		cores.setEnabled(false);
		cores.setBounds(407, 146, 112, 20);
		add(cores);
		
		ram = new JSpinner(new SpinnerNumberModel(0, 0, this.gc.getCluster().getTotalRam(), 1));
		ram.setEnabled(false);
		ram.setBounds(407, 182, 112, 20);
		add(ram);
		
		ssd = new JSpinner(new SpinnerNumberModel(0, 0, this.gc.getCluster().getTotalSsd(), 1));
		ssd.setEnabled(false);
		ssd.setBounds(407, 219, 112, 20);
		add(ssd);
		
		gpu = new JSpinner(new SpinnerNumberModel(0, 0, this.gc.getCluster().getTotalGPU(), 1));
		gpu.setEnabled(false);
		gpu.setBounds(407, 255, 112, 20);
		add(gpu);
		
		bandwidth = new JSpinner(new SpinnerNumberModel(4, 4, this.gc.getCluster().getTotalBandwidth(), 1));
		bandwidth.setEnabled(false);
		bandwidth.setBounds(407, 289, 112, 20);
		add(bandwidth);

		JButton backBtn = new JButton("BACK");
		backBtn.setForeground(new Color(240, 0, 0));
		backBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		backBtn.setBackground(Color.LIGHT_GRAY);
		backBtn.setBounds(10, 319, 85, 21);
		backBtn.addActionListener((e) -> {
			this.gc.goBackVm();
		});
		add(backBtn);
		
		JButton okBtn = new JButton("OK");
		okBtn.setForeground(new Color(240, 0, 0));
		okBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		okBtn.setBackground(Color.LIGHT_GRAY);
		okBtn.setBounds(805, 319, 85, 21);
		okBtn.addActionListener((e) -> {
			this.gc.applyVmUpdate((Vm) comboBox.getSelectedItem(), os.getSelectedItem().toString(), (int) cores.getValue(), (int) ram.getValue(), (int) ssd.getValue(), (int) gpu.getValue(), (int) bandwidth.getValue());
		});
		add(okBtn);
    }

	public JSpinner getCores() {
		return cores;
	}

	public void setCores(JSpinner cores) {
		this.cores = cores;
	}

	public JSpinner getRam() {
		return ram;
	}

	public void setRam(JSpinner ram) {
		this.ram = ram;
	}

	public JSpinner getSsd() {
		return ssd;
	}

	public void setSsd(JSpinner ssd) {
		this.ssd = ssd;
	}

	public JSpinner getGpu() {
		return gpu;
	}

	public void setGpu(JSpinner gpu) {
		this.gpu = gpu;
	}

	public JSpinner getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(JSpinner bandwidth) {
		this.bandwidth = bandwidth;
	}
}
