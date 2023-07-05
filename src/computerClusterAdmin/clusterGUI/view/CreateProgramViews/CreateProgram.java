package computerClusterAdmin.clusterGUI.view.CreateProgramViews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;

public class CreateProgram extends ControlledPanel{
    private JSpinner bandwidth;
    private JSpinner cores;
    private JSpinner ram;
    private JSpinner ssd;
    private JSpinner gpu;
    private JSpinner time;

    public CreateProgram(Controller controller) {
        super(controller);
        
        setLayout(null);
            
        JLabel lblNewLabel = new JLabel("GIVE RESOURCES");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(380, 58, 111, 34);
         add(lblNewLabel);
            
        JLabel lblCpu = new JLabel("CPU");
         lblCpu.setHorizontalAlignment(SwingConstants.CENTER);
        lblCpu.setBounds(145, 106, 111, 34);
        add(lblCpu);
            
        JLabel lblRam = new JLabel("RAM");
        lblRam.setHorizontalAlignment(SwingConstants.CENTER);
        lblRam.setBounds(145, 138, 111, 34);
        add(lblRam);
            
        JLabel lblSsd = new JLabel("SSD");
        lblSsd.setHorizontalAlignment(SwingConstants.CENTER);
        lblSsd.setBounds(145, 170, 111, 34);
        add(lblSsd);
            
        JLabel lblBandwidth = new JLabel("BANDWIDTH");
        lblBandwidth.setHorizontalAlignment(SwingConstants.CENTER);
        lblBandwidth.setBounds(145, 202, 111, 34);
        add(lblBandwidth);
            
        JLabel lblGpu = new JLabel("GPU");
        lblGpu.setHorizontalAlignment(SwingConstants.CENTER);
        lblGpu.setBounds(145, 233, 111, 34);
        add(lblGpu);
            
        cores = new JSpinner(new SpinnerNumberModel(1, 1, this.gc.getCluster().getTotalCores(), 1));
		cores.setBounds(266, 114, 54, 20);
        add(cores);
            
        ram = new JSpinner(new SpinnerNumberModel(1, 1, this.gc.getCluster().getTotalRam(), 1));
		ram.setBounds(266, 146, 54, 20);
        add(ram);
            
        ssd = new JSpinner(new SpinnerNumberModel(1, 1, this.gc.getCluster().getTotalSsd(), 1));
		ssd.setBounds(266, 178, 54, 20);
        add(ssd);
        
        bandwidth = new JSpinner(new SpinnerNumberModel(0, 0, this.gc.getCluster().getTotalBandwidth(), 1));
		bandwidth.setBounds(266, 210, 54, 20);
        add(bandwidth);
            
		gpu = new JSpinner(new SpinnerNumberModel(0, 0, this.gc.getCluster().getTotalGPU(), 1));
		gpu.setBounds(266, 241, 54, 20);
        add(gpu);
        
        JLabel lblTime = new JLabel("TIME");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setBounds(145, 266, 111, 34);
		add(lblTime);
		
		time = new JSpinner(new SpinnerNumberModel(1, 1, this.gc.getCluster().getTotalSsd(), 1));
		time.setBounds(266, 274, 54, 20);
		add(time);

        JButton backBtn = new JButton("BACK");
		backBtn.setForeground(new Color(240, 0, 0));
		backBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		backBtn.setBackground(Color.LIGHT_GRAY);
		backBtn.setBounds(10, 319, 85, 21);
		backBtn.addActionListener((e) -> {
			this.gc.showProgramMenu();
		});
		add(backBtn);
            
		JButton btnOk = new JButton("OK");
		btnOk.setForeground(new Color(240, 0, 0));
		btnOk.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));
		btnOk.setBackground(Color.LIGHT_GRAY);
		btnOk.setBounds(805, 318, 85, 21);
        btnOk.addActionListener((e) -> {
            controller.createProgram((int) cores.getValue(), (int) ram.getValue(), (int) ssd.getValue(), (int) gpu.getValue(), (int) bandwidth.getValue(), (int) time.getValue());
            controller.showProgramMenu();
        });
		add(btnOk);
    }
}
