package de.digitalemil.eaglerefimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import javax.swing.*;

public class EagleFrame extends JFrame {

	UIFrame ui;
	Properties properties;

	public void init(String args[]) {
		properties = new Properties();
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].charAt(0) == '-' && args[i + 1].charAt(0) != '-') {
				properties.put(args[i], args[i + 1]);
			}
		}

		int width, height;
		try {
			width = Integer.parseInt((String) properties.get("-width"));
		} catch (Exception e) {
			width = 480;
		}
		try {
			height = Integer.parseInt((String) properties.get("-height"));
		} catch (Exception e) {
			height = 320;
		}
		String level;
		level = (String) properties.get("-level");
		if (level == null)
			level = "iicaptain";
		System.getProperties().put("level", level);

		String uiFrame;
		uiFrame = (String) properties.get("-frame");
		if (uiFrame == null)
			uiFrame = "de.digitalemil.eaglerefimpl.UIFrame";

		Class uiClass = null;
		try {
			uiClass = Class.forName(uiFrame);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		Class[] params = new Class[] { uiFrame.getClass(),
				properties.getClass() };
		try {
			Constructor con = uiClass.getConstructor(params);
			Object p[] = new Object[] { uiFrame, properties };
			try {
				ui = (UIFrame) con.newInstance(p);
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			}
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (SecurityException ex) {
			ex.printStackTrace();
		}

		// ;"iiCaptain", properties);
		/*
		 * setSize(width+8, height+30); ui.setSize(width, height);
		 * setLocation((Toolkit.getDefaultToolkit().getScreenSize().width -
		 * width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height -
		 * height) / 2); ui.setVisible(true);
		 */
		// ((Frame)getParent()).setVisible(false);
		add(ui);
		pack();
		setVisible(true);
		setSize(width, height + 20);
		ui.setSize(width, height);
		setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2);
		ui.setVisible(true);
	}

	public void paint(Graphics g) {
		ui.paint(g);
	}

	public static void main(String[] args) {
		try {
			EagleFrame applet = new EagleFrame();
			applet.init(args);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
