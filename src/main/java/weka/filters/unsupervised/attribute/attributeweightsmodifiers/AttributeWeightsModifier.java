/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * AttributeWeightsModifier.java
 * Copyright (C) 2015 University of Waikato, Hamilton, New Zealand
 */
package weka.filters.unsupervised.attribute.attributeweightsmodifiers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import weka.classifiers.evaluation.Prediction;
import weka.core.Attribute;
import weka.gui.ComponentHelper;

/**
 * Interface for algorithms that modify attribute weights.
 * 
 * @author fracpete (fracpete at waikato dot ac dot nz)
 */
public interface AttributeWeightsModifier
  extends Serializable {

  /**
   * Modifies the attribute weights.
   * 
   * @param data        the data to process
   * @return		the modified data
   */
  public Instances modifyAttributeWeights(Instances data);
}
