package de.digitalemil.eaglerefimpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UIFrame extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    protected Properties properties = new Properties();
    private JMenu jmenuFile,  jmenuHelp,  jmenuDebug;
    private JMenuItem jmiExit,  jmiConsole,  jmiMyLispHelp,  jmiWatchValues;
    protected java.awt.Image img;
    protected long now;
    protected boolean setup= false;
    protected UIComponent ui;
    protected boolean intouch= false;
	JApplet japplet;
	private boolean dragged;

    public UIFrame(String title, Properties p) {
	//	super(null);
//        super(title);
	super();
	//japplet= ja;

		
        this.properties = p;

       // createMenuBar();
       // getContentPane().
		setLayout(new BorderLayout());
       // getContentPane().
		setFocusable(true);
        //getContentPane()
		addKeyListener(this);
        //getContentPane().
		addMouseListener(this);
        //getContentPane()
		addMouseMotionListener(this);
    }


    /*public void repaint() {
        super.repaint();
    }*/

    public void keyPressed(KeyEvent e) {
	ui.key(e.getKeyCode());
}
/*
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_RIGHT:
break;
    case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_DOWN:
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_F1:
            case KeyEvent.VK_1:
            case KeyEvent.VK_7:
                break;
            case KeyEvent.VK_F2:
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
            case KeyEvent.VK_9:
                break;
            default:
                int key= e.getKeyChar();
        }
    }
*/
    public void myrepaint() { 
//System.out.println("repaint");
    	paintImmediately(0, 0, getWidth(), getHeight());
//	repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

   public void actionPerformed(ActionEvent e) {
	myrepaint();
} 
    public void setup() {
        try {
    System.out.println("setup UIFrame!: "+getWidth()+" "+getHeight());
            setVisible(true);
            
        	ui= new UIComponent(getWidth(), getHeight());
            //getRootPane().getContentPane().add(ui, BorderLayout.CENTER);
add(ui, BorderLayout.SOUTH);
            //getRootPane().getContentPane().doLayout(); 
           // getRootPane().pack();
       //     javax.swing.Timer timer = new javax.swing.Timer(25, this);
//timer.setInitialDelay(0);
//timer.start(); 
  java.util.Timer timer = new java.util.Timer();
            TimerTask task = new TimerTask() {

                public void run() {
                    myrepaint();
                }
            };
            timer.scheduleAtFixedRate(task, 100, 25);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
		setup = true;
    }

    public void paint(Graphics g) {
     	if (!setup) {
            setup();
        }
        //super.paint(g);
	ui.paint(g);
    }

    protected void createMenuBar() {
       /* JMenuBar mb = new JMenuBar();
        mb.setFont(new java.awt.Font("Dialog", 0, 12));
        jmenuFile = new JMenu("File");
        jmenuFile.setFont(new java.awt.Font("Dialog", 0, 12));
        jmenuFile.setMnemonic(java.awt.event.KeyEvent.VK_F);

        JMenuItem jmi;

        // File->Exit
        jmi = new JMenuItem("Exit");
        jmi.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileExit(evt);
            }
        });
        jmi.setFont(new java.awt.Font("Dialog", 0, 12));
        jmi.setMnemonic(java.awt.event.KeyEvent.VK_X);
        //        jmi.setIcon(new ImageIcon(getClass().getResource("/exit.png")));
        jmenuFile.add(jmi);

        jmenuDebug = new JMenu("Debug");
        jmenuDebug.setFont(new java.awt.Font("Dialog", 0, 12));
        jmenuDebug.setMnemonic(java.awt.event.KeyEvent.VK_D);

        // Debug->Console
        jmiConsole = new JMenuItem("Console");
        jmiConsole.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDebugConsole(evt);
            }
        });
        jmiConsole.setFont(new java.awt.Font("Dialog", 0, 12));
        jmiConsole.setMnemonic(java.awt.event.KeyEvent.VK_C);
        jmenuDebug.add(jmiConsole);

        // Debug->WatchValues
        jmiWatchValues = new JMenuItem("Watch Values");
        jmiWatchValues.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDebugWatchValues(evt);
            }
        });
        jmiWatchValues.setFont(new java.awt.Font("Dialog", 0, 12));
        jmiWatchValues.setMnemonic(java.awt.event.KeyEvent.VK_W);
        jmenuDebug.add(jmiWatchValues);

        // Debug->MyLisp Help
        jmiMyLispHelp = new JMenuItem("MyLisp Help");
        jmiMyLispHelp.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDebugMyLispHelp(evt);
            }
        });
        jmiMyLispHelp.setFont(new java.awt.Font("Dialog", 0, 12));
        jmiMyLispHelp.setMnemonic(java.awt.event.KeyEvent.VK_M);
        jmenuDebug.add(jmiMyLispHelp);

        // Help
        jmenuHelp = new JMenu("Help");
        jmenuHelp.setFont(new java.awt.Font("Dialog", 0, 12));
        jmenuHelp.setMnemonic(java.awt.event.KeyEvent.VK_H);

        // Help-Contents
        jmi = new JMenuItem("Coming Soon");
        jmi.setMnemonic(java.awt.event.KeyEvent.VK_C);
        jmi.setFont(new java.awt.Font("Dialog", 0, 12));

        jmi.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpContents(evt);
            }
        });
        jmenuHelp.add(jmi);

        setJMenuBar(mb);
        mb.add(jmenuFile);
        mb.add(jmenuDebug);
        mb.add(jmenuHelp);
*/
		}

    private void menuDebugConsole(java.awt.event.ActionEvent evt) {
    }

    private void menuDebugWatchValues(java.awt.event.ActionEvent evt) {
    }

    private void menuDebugMyLispHelp(java.awt.event.ActionEvent evt) {
    }

    private void menuFileExit(java.awt.event.ActionEvent evt) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void menuHelpContents(java.awt.event.ActionEvent evt) {
        System.out.println("Help is not yet available.");
    }

    public static String getHelpText() {
        return ("No help available!");
    }

    public void changed(String string, Object object, Object object0) {
        System.out.println(string + ": " + object0.toString());
    }

    public Object getValue(String key) {
        return null;
    }

    public void putValue(String key, Object value) {
    }


	
	public void mouseDragged(MouseEvent arg0) {
		dragged = true;
	//	System.out.println("Mouse Dragged: "+arg0.getX()+" "+arg0.getY());
		ui.touch(arg0.getX(), arg0.getY());

		// TODO Auto-generated method stub
		
	}


	
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void mouseClicked(MouseEvent arg0) {
		
	}


	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void mousePressed(MouseEvent arg0) {
		dragged= false;
//		System.out.println("Mouse Pressed: "+arg0.getX()+" "+arg0.getY());
		ui.touchStart(arg0.getX(), arg0.getY());
		// TODO Auto-generated method stub
		
	}


	
	public void mouseReleased(MouseEvent arg0) {
		if(dragged) {
			dragged= false;
		}
		ui.touchStop(arg0.getX(), arg0.getY());		
				
//		System.out.println("Mouse Released: "+arg0.getX()+" "+arg0.getY());
		
		// TODO Auto-generated method stub
		
	}
}
