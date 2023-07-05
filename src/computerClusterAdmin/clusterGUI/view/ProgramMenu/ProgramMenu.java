package computerClusterAdmin.clusterGUI.view.ProgramMenu;

import javax.swing.JButton;

import computerClusterAdmin.clusterGUI.Control.ControlledPanel;
import computerClusterAdmin.clusterGUI.Control.Controller;

public class ProgramMenu extends ControlledPanel{

    public ProgramMenu(Controller controller) {
        super(controller);
		setLayout(null);
		
		JButton pCreateBtn = new JButton("CREATE PROGRAM");
		pCreateBtn.setBounds(100, 115, 164, 32);
		pCreateBtn.addActionListener((e) -> {
			this.gc.showCreateProgram();
		});
		add(pCreateBtn);
		
		JButton runProgramBtn = new JButton("RUN PROGRAM");
		runProgramBtn.setBounds(100, 180, 164, 32);
		runProgramBtn.addActionListener((e) -> {
			controller.runPrograms();
		});
		add(runProgramBtn);
    }
}
