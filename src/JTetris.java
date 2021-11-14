import java.awt.Color;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Game.Game;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class JTetris extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JTetris frame = new JTetris();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage() + "\n frame err");
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JTetris() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "\nsetLookAndFeel Err");
		}

		setTitle("Tetris V1.5 - by Agzam4");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JTetris.class.getResource("/ICO/ICO.png")));
		
		setResizable(false);
//		setIconImage(Toolkit.getDefaultToolkit().getImage(JTetris.class.getResource("/Blocks/Agzam4_Pixel.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 525);
//		setMaximumSize(new Dimension(506,555));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);//new BorderLayout(0, 0));
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		
		JLabel screen = new JLabel();
		contentPane.add(screen);
		screen.setBackground(Color.BLACK);
		screen.setOpaque(true);
		screen.setBounds(0, 0, 500, 500);
		screen.setFocusable(true);
		
		Game game = new Game();
		game.Go(screen/* , this */);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(screen, popupMenu);
		
		JCheckBoxMenuItem aot = new JCheckBoxMenuItem("Always on Top");
		popupMenu.add(aot);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(25, 5, 25, 1));
		spinner.setToolTipText("Quality");
		popupMenu.add(spinner);
		
		aot.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Aot(aot.isSelected());
			}
		});
		
		spinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				game.quality = (int) spinner.getValue();
			}
		});
		
	}

	private void Aot(boolean aot) {
		setAlwaysOnTop(aot);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
