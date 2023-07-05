package computerClusterAdmin.clusterGUI.view.ProgramMenuViews;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ProgramMenuGUI extends JPanel{
    public ProgramMenuGUI() {

    }

    @Override
	public void paint(Graphics g) {
		int x = this.getWidth() / 2 - 80;
		int y = this.getHeight() / 2;
        g.setFont(getFont().deriveFont(Font.BOLD));
        Font largeFontSize = g.getFont().deriveFont(20f);
        g.setFont(largeFontSize);
		g.drawString("PROGRAM MENU", x, y);
	}
}
