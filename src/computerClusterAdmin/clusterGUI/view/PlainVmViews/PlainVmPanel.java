package computerClusterAdmin.clusterGUI.view.PlainVmViews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;

public class PlainVmPanel extends ControlledPanel{
    private JTextField cores;
	private JTextField ram;
	private JTextField ssd;
	private JTextField os;

	public PlainVmPanel(Controller gc) {
        super(gc);
        setLayout(null);

		

		JLabel lblNewLabel_1_1 = new JLabel("CPU:");

		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_1_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));

		lblNewLabel_1_1.setBounds(286, 107, 132, 35);

		add(lblNewLabel_1_1);

		

		JLabel lblNewLabel_1_2 = new JLabel("RAM:");

		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_1_2.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));

		lblNewLabel_1_2.setBounds(286, 141, 132, 35);

		add(lblNewLabel_1_2);

		

		JLabel lblNewLabel_1_3 = new JLabel("SSD:");

		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_1_3.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));

		lblNewLabel_1_3.setBounds(286, 175, 132, 35);

		add(lblNewLabel_1_3);

		

		JButton backBtn = new JButton("BACK");

		backBtn.setForeground(new Color(240, 0, 0));

		backBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));

		backBtn.setBackground(Color.LIGHT_GRAY);

		backBtn.setBounds(10, 319, 85, 21);
		backBtn.addActionListener((e) -> {
			this.gc.goBackVm();
		});
		add(backBtn);

		

		JButton btnOk = new JButton("OK");

		btnOk.setForeground(new Color(240, 0, 0));

		btnOk.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 10));

		btnOk.setBackground(Color.LIGHT_GRAY);
		btnOk.setBounds(805, 318, 85, 21);
        btnOk.addActionListener((e) -> {
            this.gc.createPlainVm(cores, ram, ssd, os);
            cores.setText("");
            ram.setText("");
            ssd.setText("");
            os.setText("");
        });
		add(btnOk);

		

		cores = new JTextField();

		cores.setBackground(new Color(192, 192, 192));

		cores.setBounds(428, 118, 171, 19);

		add(cores);

		cores.setColumns(10);

		

		ram = new JTextField();

		ram.setBackground(new Color(192, 192, 192));

		ram.setColumns(10);

		ram.setBounds(428, 152, 171, 19);

		add(ram);

		

		ssd = new JTextField();

		ssd.setBackground(new Color(192, 192, 192));

		ssd.setColumns(10);

		ssd.setBounds(428, 186, 171, 19);

		add(ssd);

		

		JLabel lblNewLabel_1_1_1 = new JLabel("OS:");

		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);

		lblNewLabel_1_1_1.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));

		lblNewLabel_1_1_1.setBounds(286, 73, 132, 35);

		add(lblNewLabel_1_1_1);

		

		os = new JTextField();

		os.setColumns(10);

		os.setBackground(Color.LIGHT_GRAY);

		os.setBounds(428, 84, 171, 19);

		add(os);



	}

}
