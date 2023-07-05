package computerClusterAdmin.clusterGUI.view.VmMenuViews;

import java.awt.Font;

import javax.swing.JButton;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;

public class VmMenu extends ControlledPanel{
	private JButton createBtn;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton takeReportBtn;
	private JButton btnContinue;


    public VmMenu(Controller gc) {
		super(gc);
		setLayout(null);
		
		createBtn = new JButton("CREATE VM");
		createBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		createBtn.setBounds(100, 10, 153, 33);
		createBtn.addActionListener((e) -> {
			this.gc.vmCreation();
		});
		add(createBtn);
		
		updateBtn = new JButton("UPDATE VM");
		updateBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 15));
		updateBtn.setBounds(100, 86, 153, 33);
		if (this.gc.getVmArray().isEmpty())
			updateBtn.setEnabled(false);
		updateBtn.addActionListener((e) -> {
			this.gc.vmUpdate();
		});
		add(updateBtn);
		
		deleteBtn = new JButton("DELETE VM");
		deleteBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		deleteBtn.setBounds(100, 160, 157, 33);
		if (this.gc.getVmArray().isEmpty())
			deleteBtn.setEnabled(false);
		deleteBtn.addActionListener((e) -> {
			this.gc.vmDeletion();
		});
		add(deleteBtn);
		
		takeReportBtn = new JButton("TAKE REPORT");
		takeReportBtn.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		takeReportBtn.setBounds(100, 235, 157, 33);
		if (this.gc.getVmArray().isEmpty())
			takeReportBtn.setEnabled(false);
		takeReportBtn.addActionListener((e) -> {
			this.gc.vmReport();
		});
		add(takeReportBtn);

		btnContinue = new JButton("CONTINUE");
		btnContinue.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		if (this.gc.getVmArray().isEmpty())
			btnContinue.setEnabled(false);
		btnContinue.setBounds(100, 293, 157, 33);
		btnContinue.addActionListener((e) -> {
			this.gc.showProgramMenu();
		});
		add(btnContinue);
    }

	public JButton getUpdateBtn() {
		return updateBtn;
	}

	public void setUpdateBtn(JButton updateBtn) {
		this.updateBtn = updateBtn;
	}

	public JButton getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(JButton deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public JButton getTakeReportBtn() {
		return takeReportBtn;
	}

	public void setTakeReportBtn(JButton takeReportBtn) {
		this.takeReportBtn = takeReportBtn;
	}

	public JButton getBtnContinue() {
		return btnContinue;
	}
}

