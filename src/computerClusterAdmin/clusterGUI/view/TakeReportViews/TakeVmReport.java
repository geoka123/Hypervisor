package computerClusterAdmin.clusterGUI.view.TakeReportViews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;
import computerClusterAdmin.computerClusterBackEnd.Vm;

public class TakeVmReport extends ControlledPanel{
    JRadioButton allBtn;
    JRadioButton oneBtn;
    JComboBox<Vm> comboBox;
    JTextArea textArea;

    public TakeVmReport(Controller controller) {
        super(controller);
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

		JLabel lblNewLabel = new JLabel("ALL:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(313, 66, 42, 49);
		add(lblNewLabel);
		
		JLabel lblOne = new JLabel("ONE:");
		lblOne.setHorizontalAlignment(SwingConstants.CENTER);
		lblOne.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOne.setBounds(313, 119, 42, 49);
		add(lblOne);
		
		allBtn = new JRadioButton("");
		allBtn.setBounds(361, 80, 23, 21);
        allBtn.addActionListener((e) -> {
            if (allBtn.isSelected()) {
                oneBtn.setSelected(false);
                controller.takeReportOfAll();
            }   
        });
		add(allBtn);
		
		oneBtn = new JRadioButton("");
		oneBtn.setBounds(361, 135, 23, 21);
        oneBtn.addActionListener((e) -> {
            if (oneBtn.isSelected()) {
                allBtn.setSelected(false);
                controller.enableComboBoxReport();
            }   
        });
		add(oneBtn);
		
		comboBox = new JComboBox<>(controller.getVmArray());
        comboBox.setEnabled(false);
		comboBox.setBounds(401, 135, 218, 21);
        comboBox.addActionListener((e) -> {
			if (e.getSource() == comboBox){
				controller.takeReportOfOne((Vm) comboBox.getSelectedItem());
			}
		});
		add(comboBox);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 192, 880, 106);
        textArea.setEditable(false);
		add(textArea);
    }

    public JComboBox<Vm> getComboBox() {
        return comboBox;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    
}
