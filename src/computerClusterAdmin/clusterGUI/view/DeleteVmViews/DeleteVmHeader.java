package computerClusterAdmin.clusterGUI.view.DeleteVmViews;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DeleteVmHeader extends JPanel{
    public DeleteVmHeader() {

    }

    @Override
	public void paint(Graphics g) {
		int x = this.getWidth() / 2 - 50;
		int y = this.getHeight() / 2;
        g.setFont(getFont().deriveFont(Font.BOLD));
        Font largeFontSize = g.getFont().deriveFont(20f);
        g.setFont(largeFontSize);
		g.drawString("DELETE VM", x, y);
	}
}
