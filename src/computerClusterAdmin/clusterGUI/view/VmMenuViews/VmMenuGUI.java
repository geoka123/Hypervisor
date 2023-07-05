package computerClusterAdmin.clusterGUI.view.VmMenuViews;

import java.awt.*;

import javax.swing.*;

public class VmMenuGUI extends JPanel{
    public VmMenuGUI() {

    }

    @Override
	public void paint(Graphics g) {
		int x = this.getWidth() / 2 - 50;
		int y = this.getHeight() / 2;
        g.setFont(getFont().deriveFont(Font.BOLD));
        Font largeFontSize = g.getFont().deriveFont(20f);
        g.setFont(largeFontSize);
		g.drawString("VM MENU", x, y);
	}

}
