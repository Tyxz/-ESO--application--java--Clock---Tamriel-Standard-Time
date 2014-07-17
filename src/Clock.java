import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JToggleButton;
import javax.swing.JSeparator;

import data.LoreDate;
import data.Settings;
import data.Time;
import lang.English;
import lang.German;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;

/**
 * 
 * @author Tyx | Arne Rantzen
 * @version 0.4.0
 * 
 */
public class Clock extends Thread {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Clock window = new Clock();
					window.main.setVisible(true);
					window.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JWindow main;
	private Time t;
	private List<Image> icons = new ArrayList<>();
	private Settings settings;
	private JLabel lblClock, lblDate, lblTime = new JLabel();
	private JPanel centerPanel;
	private JSlider sldrTime;
	private JTextField txtfldDate, txtfldClock;

	private boolean showClock = true, showDate = true, showSettings = false;
	private JScrollPane sidePane;
	private JPanel localPanel;

	/**
	 * Create the application.
	 */
	public Clock() {
		settings = loadSettings();
		t = new Time(settings);

		initialize();
	}

	private void createSidePanel() {
		sidePane = new JScrollPane();
		JPanel sidePanel = new JPanel();
		sidePanel.setMinimumSize(new Dimension(1200, 0));
		GridBagLayout gbl_sidePanel = new GridBagLayout();
		gbl_sidePanel.columnWidths = new int[] { 222, 0 };
		gbl_sidePanel.rowHeights = new int[] { 17, 17, 17, 17, 17, 17, 17, 17,
				17, 17, 17, 17, 0 };
		gbl_sidePanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_sidePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		sidePanel.setLayout(gbl_sidePanel);

		JToggleButton tglbtnDate = new JToggleButton("Date");
		tglbtnDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showDate = !showDate;
				if (showDate) {
					centerPanel.add(lblDate);
				} else {
					centerPanel.remove(lblDate);
				}
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		});
		GridBagConstraints gbc_tglbtnDate = new GridBagConstraints();
		gbc_tglbtnDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnDate.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnDate.gridx = 0;
		gbc_tglbtnDate.gridy = 0;
		sidePanel.add(tglbtnDate, gbc_tglbtnDate);
		tglbtnDate.setSelected(showDate);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings = new Settings();
				txtfldClock.setText(settings.getClockFormat());
				txtfldDate.setText(settings.getDateFormat());
			}
		});

		JLabel lblDate_1 = new JLabel("Desc");
		lblDate_1.setText( "<html><body>"
				+ "<p><strong>Year:</strong> _y = 582 _yy = 2E 582</p>"
				+ "<p><strong>Month:</strong> _M = 4 _MM = 04 _MMM = First Seed</p>"
				+ "<p><strong>Day:</strong> _d = 4 _dd = 04 _ddd = Fredas</p>"
				+ "</body></html>");
		GridBagConstraints gbc_lblDate_1 = new GridBagConstraints();
		gbc_lblDate_1.fill = GridBagConstraints.BOTH;
		gbc_lblDate_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblDate_1.gridx = 0;
		gbc_lblDate_1.gridy = 1;
		sidePanel.add(lblDate_1, gbc_lblDate_1);

		txtfldDate = new JTextField();
		txtfldDate.setText(settings.getDateFormat());
		txtfldDate.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				settings.setDateFormat(txtfldDate.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				settings.setDateFormat(txtfldDate.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				settings.setDateFormat(txtfldDate.getText());
			}
		});
		GridBagConstraints gbc_txtfldDate = new GridBagConstraints();
		gbc_txtfldDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtfldDate.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldDate.gridx = 0;
		gbc_txtfldDate.gridy = 2;
		sidePanel.add(txtfldDate, gbc_txtfldDate);

		JToggleButton tglbtnClock = new JToggleButton("Clock");
		tglbtnClock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showClock = !showClock;
				if (showClock) {
					centerPanel.add(lblClock);
				} else {
					centerPanel.remove(lblClock);
				}
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		});
		GridBagConstraints gbc_tglbtnClock = new GridBagConstraints();
		gbc_tglbtnClock.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnClock.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnClock.gridx = 0;
		gbc_tglbtnClock.gridy = 3;
		sidePanel.add(tglbtnClock, gbc_tglbtnClock);
		tglbtnClock.setSelected(showClock);

		JLabel lblClock_1 = new JLabel("Desc");
		lblClock_1.setText("<html><body>" + "<p><strong>Hour:</strong> _h = 9 _hh = 09</p>"
				+ "<p><strong>Minute:</strong> _m = 9 _mm = 09</p>"
				+ "<p><strong>Second:</strong> _s = 9 _ss = 09</p>"
				+ "</body></html>");
		GridBagConstraints gbc_lblClock_1 = new GridBagConstraints();
		gbc_lblClock_1.fill = GridBagConstraints.BOTH;
		gbc_lblClock_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblClock_1.gridx = 0;
		gbc_lblClock_1.gridy = 4;
		sidePanel.add(lblClock_1, gbc_lblClock_1);

		txtfldClock = new JTextField();
		txtfldClock.setText(settings.getClockFormat());
		txtfldClock.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				settings.setClockFormat(txtfldClock.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				settings.setClockFormat(txtfldClock.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				settings.setClockFormat(txtfldClock.getText());
			}
		});
		GridBagConstraints gbc_txtfldClock = new GridBagConstraints();
		gbc_txtfldClock.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtfldClock.insets = new Insets(0, 0, 5, 0);
		gbc_txtfldClock.gridx = 0;
		gbc_txtfldClock.gridy = 5;
		sidePanel.add(txtfldClock, gbc_txtfldClock);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 6;
		sidePanel.add(separator, gbc_separator);

		JLabel lblDesc = new JLabel("Desc");
		lblDesc.setText("Daytime in seconds: ");
		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.fill = GridBagConstraints.BOTH;
		gbc_lblDesc.insets = new Insets(0, 0, 5, 0);
		gbc_lblDesc.gridx = 0;
		gbc_lblDesc.gridy = 7;
		sidePanel.add(lblDesc, gbc_lblDesc);

		sldrTime = new JSlider(20900, 21000);
		sldrTime.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				settings.setDayLength(sldrTime.getValue());
				lblTime.setText("Daylength: " + sldrTime.getValue() + "s");
			}
		});
		sldrTime.setMinorTickSpacing(5);
		sldrTime.setMajorTickSpacing(25);
		sldrTime.setPaintLabels(true);
		sldrTime.setPaintTicks(true);
		sldrTime.setValue((int) settings.getDayLength());
		GridBagConstraints gbc_sldrTime = new GridBagConstraints();
		gbc_sldrTime.fill = GridBagConstraints.BOTH;
		gbc_sldrTime.insets = new Insets(0, 0, 5, 0);
		gbc_sldrTime.gridx = 0;
		gbc_sldrTime.gridy = 8;
		sidePanel.add(sldrTime, gbc_sldrTime);

		lblTime = new JLabel();
		lblTime.setText("Daylength: " + settings.getDayLength() + "s");
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.fill = GridBagConstraints.BOTH;
		gbc_lblTime.insets = new Insets(0, 0, 5, 0);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 9;
		sidePanel.add(lblTime, gbc_lblTime);

		JButton btnNoon = new JButton("Noon");
		btnNoon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNoon.setToolTipText("Use this Button at Noon");
		GridBagConstraints gbc_btnNoon = new GridBagConstraints();
		gbc_btnNoon.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNoon.insets = new Insets(0, 0, 5, 0);
		gbc_btnNoon.gridx = 0;
		gbc_btnNoon.gridy = 10;
		sidePanel.add(btnNoon, gbc_btnNoon);
		btnNoon.setVisible(false); // 0.3.1
		btnReset.setToolTipText("Will replace all Settings with the default values!");
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReset.gridx = 0;
		gbc_btnReset.gridy = 11;
		sidePanel.add(btnReset, gbc_btnReset);
		sidePane.setViewportView(sidePanel);
		main.getContentPane().add(sidePane, BorderLayout.EAST);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		main = new JWindow();
		main.setSize(600, 200);
		loadIcons();
		main.setIconImage(icons.get(0));
		main.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				settings.saveSettings(settings);
				System.exit(0);
			}
		});
		main.setLayout(new BorderLayout(0, 0));

		localPanel = new JPanel();
		main.add(localPanel, BorderLayout.NORTH);
		localPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JRadioButton rdbtnEnglish = new JRadioButton("English");
		rdbtnEnglish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setLocal(new English());
			}
		});
		if (settings.getLocal().getLanguage().equals("en")) {
			rdbtnEnglish.setSelected(true);
		}
		localPanel.add(rdbtnEnglish);

		JRadioButton rdbtnDeutsch = new JRadioButton("Deutsch");
		rdbtnDeutsch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setLocal(new German());
			}
		});
		if (settings.getLocal().getLanguage().equals("de")) {
			rdbtnDeutsch.setSelected(true);
		}
		localPanel.add(rdbtnDeutsch);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEnglish);
		group.add(rdbtnDeutsch);

		JToggleButton tglbtnSettings = new JToggleButton("Settings");
		tglbtnSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSettings = !showSettings;
				if (showSettings) {
					createSidePanel();
					main.add(sidePane, BorderLayout.EAST);
					main.setSize((int) (main.getWidth() * (1.5)), (int) (main.getHeight() * (2.4)));
				} else {
					main.remove(sidePane);
					main.setSize((int) (main.getWidth() / (1.5)), (int) (main.getHeight() / (2.4)));
				}
				main.revalidate();
				main.repaint();
			}
		});
		tglbtnSettings.setSelected(showSettings);
		localPanel.add(tglbtnSettings);
		
		centerPanel = new JPanel();
		main.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(0, 1, 0, 0));

		lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Nyala", Font.BOLD, 30));
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(lblDate);

		lblClock = new JLabel("Clock");
		lblClock.setFont(new Font("Nyala", Font.BOLD, 30));
		lblClock.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(lblClock);

		JButton btnSync = new JButton("Sync");
		btnSync.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSync.setToolTipText("Use this Button to sync your changes.");
		main.add(btnSync, BorderLayout.SOUTH);
		btnSync.setVisible(false); // V0.1

		JLabel about = new JLabel();
		String abt = "Author: Tyx - Arne Rantzen | (c) 2014 All rights reserverd | Version "
				+ Settings.getVersion();
		about.setText(abt);
		main.add(about, BorderLayout.SOUTH);
		
		main.pack();
		main.setVisible(true);
	}

	/**
	 * Load saved settings or give back defaults
	 * 
	 * @return settings
	 */
	private Settings loadSettings() {
		Settings settings = null;
		File file = new File(".settings");
		boolean failed = false;
		InputStream fis = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				ObjectInputStream o = new ObjectInputStream(fis);
				settings = (Settings) o.readObject();
				o.close();
			} catch (Exception e) {
				System.err.println(e);
				failed = true;
			} finally {
				try {
					fis.close();
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		} else {
			failed = true;
		}

		if (failed) {
			return new Settings();
		}
		return settings;
	}
	
	/**
	 * Load the list of images for the icon into the icons list
	 */
	private void loadIcons() {
		for (int i = 16; i < 512; i *= 2) {
			Image img = new ImageIcon("/img/clock_" + i + ".ico").getImage();
			icons.add(img);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(1000);
				LoreDate date = t.createDateAndTime();
				String text = "";
				if (showDate) {
					text = date.printDate(settings);
				}
				lblDate.setText(text);

				if (showClock) {
					text = date.printClock(settings);
				}
				lblClock.setText(text);
				main.pack();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
