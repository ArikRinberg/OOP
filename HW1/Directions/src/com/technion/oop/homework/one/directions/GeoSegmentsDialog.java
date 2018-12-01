package com.technion.oop.homework.one.directions;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;
	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with the an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);
		
		this.parent = pnlParent;
		
		lstSegments = new JList<>(new DefaultListModel<GeoSegment>());
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		populateListSegments();
		
		JScrollPane scrlSegments = new JScrollPane(lstSegments);
		scrlSegments.setPreferredSize(new Dimension(450, 150));
		
		JLabel lblSegments = new JLabel("GeoSegments:");
		lblSegments.setLabelFor(lstSegments);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeoSegment chosenSegment = lstSegments.getSelectedValue();
				System.out.println(chosenSegment);
				if (checkLegalSegment(chosenSegment))
				{
					hideDiag();
					parent.addSegment(chosenSegment);
				}
				else
				{
					GeoPoint routeEnd = parent.getRouteEnd();
			        JOptionPane.showMessageDialog(
			        		null, 
			        		"Illegal segment choice.\n Please choose a segment that"
			        		+ " starts at " + routeEnd, 
			        		"Illegal Segment Choice",
			        		JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
				
		JButton btnCancel= new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideDiag();
			}
		});
			
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(20,20,0,0);
		gridbag.setConstraints(lblSegments, c);
		this.add(lblSegments);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,20,20,20);
		gridbag.setConstraints(scrlSegments, c);
		this.add(scrlSegments);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,20,20,0);
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(btnAdd, c);
		this.add(btnAdd);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,20,20);
		c.anchor = GridBagConstraints.SOUTHEAST;
		gridbag.setConstraints(btnCancel, c);
		this.add(btnCancel);
		
		// TODO Write the body of this method
	}
	
	private void hideDiag()
	{
		this.setVisible(false);
	}
	
	private boolean checkLegalSegment(GeoSegment segment)
	{
		GeoPoint routeEnd = parent.getRouteEnd();
		return routeEnd == null || segment.getP1().equals(routeEnd);
	}
	
	private void populateListSegments()
	{
		DefaultListModel<GeoSegment> model =
				(DefaultListModel<GeoSegment>)(this.lstSegments.getModel());
		for (GeoSegment segment : ExampleGeoSegments.segments)
		{
			model.addElement(segment);
		}
	}
}
