package computerClusterAdmin.clusterGUI.view.TakeReportViews;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TakeVmReportHeader extends JPanel{
    public TakeVmReportHeader() {

    }

    @Override
	public void paint(Graphics g) {
		int x = this.getWidth() / 2 - 50;
		int y = this.getHeight() / 2;
        g.setFont(getFont().deriveFont(Font.BOLD));
        Font largeFontSize = g.getFont().deriveFont(20f);
        g.setFont(largeFontSize);
		g.drawString("TAKE REPORT", x, y);
	}
}
