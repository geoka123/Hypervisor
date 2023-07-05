package computerClusterAdmin.clusterGUI.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import computerClusterAdmin.clusterGUI.Control.Controller;

import java.awt.Container;
import java.awt.Dimension;

public class MainWindow extends JFrame{
    static public final int WIDTH = 900;
	static public final int HEIGHT = 450;
    static public final int TOP_HEIGHT = 100;

    private TopPanel topPanel;
    private MainAreaPanel botPanel;
    Controller gc;

    public MainWindow(Controller gc) {
        this.gc = gc;
        Container c = this.getContentPane();

        c.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        topPanel = new TopPanel(this.gc);
        this.add(topPanel, BorderLayout.PAGE_START);

        botPanel = new MainAreaPanel(this.gc);
        this.add(botPanel, BorderLayout.PAGE_END);

        this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public TopPanel getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(TopPanel topPanel) {
        this.topPanel = topPanel;
    }

    public MainAreaPanel getBotPanel() {
        return botPanel;
    }

    public void setBotPanel(MainAreaPanel botPanel) {
        this.botPanel = botPanel;
    }
}
