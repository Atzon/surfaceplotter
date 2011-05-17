/*
 * Created by JFormDesigner on Tue May 17 09:54:22 CEST 2011
 */

package com.googlecode.surfaceplotter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.googlecode.surfaceplotter.AbstractSurfaceModel.Plotter;
import com.googlecode.surfaceplotter.beans.JGridBagScrollPane;
import com.googlecode.surfaceplotter.surface.JSurface;
import com.googlecode.surfaceplotter.surface.SurfaceModel;
import com.googlecode.surfaceplotter.surface.VerticalConfigurationPanel;
import com.googlecode.surfaceplotter.surface.SurfaceModel.PlotColor;
import com.googlecode.surfaceplotter.surface.SurfaceModel.PlotType;

/** Main panel to display a surface plot.
 * 
 * @author eric
 */
public class JSurfacePanel extends JPanel {
	
	
	public JSurfacePanel() {
		this(createDefaultSurfaceModel());
	}

	
	/**
	 * @return
	 */
	private static SurfaceModel createDefaultSurfaceModel() {
		final AbstractSurfaceModel sm = new AbstractSurfaceModel();

		sm.setPlotFunction2(false);
		
		sm.setCalcDivisions(50);
		sm.setDispDivisions(50);
		sm.setContourLines(10);

		sm.setXMin(-3);
		sm.setXMax(3);
		sm.setYMin(-3);
		sm.setYMax(3);

		sm.setBoxed(false);
		sm.setDisplayXY(false);
		sm.setExpectDelay(false);
		sm.setAutoScaleZ(true);
		sm.setDisplayZ(false);
		sm.setMesh(false);
		sm.setPlotType(PlotType.SURFACE);
		//sm.setPlotType(PlotType.WIREFRAME);
		//sm.setPlotType(PlotType.CONTOUR);
		//sm.setPlotType(PlotType.DENSITY);

		sm.setPlotColor(PlotColor.SPECTRUM);
		//sm.setPlotColor(PlotColor.DUALSHADE);
		//sm.setPlotColor(PlotColor.FOG);
		//sm.setPlotColor(PlotColor.OPAQUE);
		
		new Thread(new Runnable() {
			public  float f1( float x, float y)
			{
				float r = x*x+y*y;
				
				if (r == 0 ) return 1f;
				return (float)( Math.sin(r)/(r));
			}
			
			public  float f2( float x, float y)
			{
				return (float)(Math.sin(x*y));
			}
			public void run()
			{
				Plotter p = sm.newPlotter(sm.getCalcDivisions());
				int im=p.getWidth();
				int jm=p.getHeight();
				for(int i=0;i<im;i++)
					for(int j=0;j<jm;j++)
					{
						float x,y;
						x=p.getX(i);
						y=p.getY(j);
						p.setValue(i,j,f1(x,y),f2(x,y) );
					}
			}
		}).start();
		
		return sm;

	}

	public JSurfacePanel(SurfaceModel model) {
		super(new BorderLayout());
		initComponents();
		
		String name = (String) configurationToggler.getValue(Action.NAME);
		getActionMap().put(name, configurationToggler);
		getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), name);
		
		setModel(model);

	}

	

	public void setModel(SurfaceModel model) {
		if (model instanceof AbstractSurfaceModel)
			configurationPanel.setModel((AbstractSurfaceModel) model);
		else {
			scrollpane.setVisible(false);
			configurationPanel.setModel(null);
		}
		surface.setModel(model);
	}

	/**
	 * @return
	 * @see java.awt.Component#getFont()
	 */
	public Font getTitleFont() {
		return title.getFont();
	}

	/**
	 * @return
	 * @see javax.swing.JLabel#getIcon()
	 */
	public Icon getTitleIcon() {
		return title.getIcon();
	}

	/**
	 * @return
	 * @see javax.swing.JLabel#getText()
	 */
	public String getTitleText() {
		return title.getText();
	}

	/**
	 * @return
	 * @see java.awt.Component#isVisible()
	 */
	public boolean isTitleVisible() {
		return title.isVisible();
	}

	/**
	 * @param font
	 * @see javax.swing.JComponent#setFont(java.awt.Font)
	 */
	public void setTitleFont(Font font) {
		title.setFont(font);
	}

	/**
	 * @param icon
	 * @see javax.swing.JLabel#setIcon(javax.swing.Icon)
	 */
	public void setTitleIcon(Icon icon) {
		title.setIcon(icon);
	}

	/**
	 * @param text
	 * @see javax.swing.JLabel#setText(java.lang.String)
	 */
	public void setTitleText(String text) {
		title.setText(text);
	}

	/**
	 * @param aFlag
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	public void setTitleVisible(boolean aFlag) {
		title.setVisible(aFlag);
	}

	/**
	 * @return
	 * @see java.awt.Component#isVisible()
	 */
	public boolean isConfigurationVisible() {
		return scrollpane.isVisible();
	}

	/**
	 * @param aFlag
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	public void setConfigurationVisible(boolean aFlag) {
		scrollpane.setVisible(aFlag);
		invalidate();
		revalidate();
	}

	private void titleMouseClicked(MouseEvent e) {
		toggleConfiguration();
	}

	private void toggleConfiguration() {
		setConfigurationVisible(!isConfigurationVisible());
	}

	private void surfaceMouseClicked() {
		surface.requestFocusInWindow();
	}

	
	
	
	
	

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		ResourceBundle bundle = ResourceBundle.getBundle("com.googlecode.surfaceplotter.JSurfacePanel");
		title = new JLabel();
		surface = new JSurface();
		scrollpane = new JGridBagScrollPane();
		configurationPanel = new VerticalConfigurationPanel();
		configurationToggler = new AbstractAction(){public void actionPerformed(ActionEvent e){toggleConfiguration();}};

		//======== this ========
		setLayout(new GridBagLayout());
		((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
		((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
		((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

		//---- title ----
		title.setText(bundle.getString("title.text"));
		title.setHorizontalTextPosition(SwingConstants.CENTER);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBackground(Color.white);
		title.setOpaque(true);
		title.setFont(title.getFont().deriveFont(title.getFont().getSize() + 4f));
		add(title, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- surface ----
		surface.setToolTipText(bundle.getString("surface.toolTipText"));
		surface.setInheritsPopupMenu(true);
		surface.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				surfaceMouseClicked();
			}
		});
		add(surface, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//======== scrollpane ========
		{
			scrollpane.setWidthFixed(true);

			//---- configurationPanel ----
			configurationPanel.setNextFocusableComponent(this);
			scrollpane.setViewportView(configurationPanel);
		}
		add(scrollpane, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- configurationToggler ----
		configurationToggler.putValue(Action.NAME, bundle.getString("configurationToggler.Name"));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel title;
	private JSurface surface;
	private JGridBagScrollPane scrollpane;
	private VerticalConfigurationPanel configurationPanel;
	private AbstractAction configurationToggler;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
