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
 * AbstractRangeBasedInstanceWeightsModifier.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.instance.instanceweightsmodifiers;

import weka.core.Instances;
import weka.core.Option;
import weka.core.Range;
import weka.core.Utils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Ancestor for modifiers that work on a range of rows.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractRangeBasedInstanceWeightsModifier
  extends AbstractInstanceWeightsModifier {

  /** the row range to work on. */
  protected Range m_RowIndices = new Range("first-last");

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration<Option> listOptions() {
    Vector<Option> result = new Vector<Option>();

    result.addElement(new Option("\tThe row range to work on.\n"
      + "This is a comma separated list of row indices, with "
      + "\"first\" and \"last\" valid values.\n"
      + "\tSpecify an inclusive range with \"-\".\n"
      + "\tE.g: \"first-3,5,6-10,last\".\n" + "\t(default: first-last)", "R",
      1, "-R <range>"));

    result.addAll(Collections.list(super.listOptions()));

    return result.elements();
  }

  /**
   * Parses a given list of options.
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  @Override
  public void setOptions(String[] options) throws Exception {
    String tmpStr;
    String className;
    String[] classOptions;

    tmpStr = Utils.getOption("R", options);
    if (tmpStr.length() != 0)
      setRowIndices(tmpStr);
    else
      setRowIndices("first-last");

    super.setOptions(options);
  }

  /**
   * Gets the current settings of the filter.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {
    Vector<String> result = new Vector<String>();

    result.add("-R");
    result.add(getRowIndices());

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[result.size()]);
  }

  /**
   * Sets which rows are to be acted on.
   *
   * @param value a string representing the list of rows. Since the string
   *          will typically come from a user, rows are indexed from1. <br>
   *          eg: first-3,5,6-last
   */
  public void setRowIndices(String value) {
    m_RowIndices.setRanges(value);
  }

  /**
   * Gets the current range selection.
   *
   * @return a string containing a comma separated list of ranges
   */
  public String getRowIndices() {
    return m_RowIndices.getRanges();
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String rowIndicesTipText() {
    return "Specify range of rows to act on; "
      + "this is a comma separated list of row indices, with "
      + "\"first\" and \"last\" valid values; specify an inclusive "
      + "range with \"-\"; eg: \"first-3,5,6-10,last\".";
  }

  /**
   * Hook method for performing checks before modifying the weights.
   *
   * @param data	the data to check
   * @throws Exception	if check fails
   */
  @Override
  protected void check(Instances data) throws Exception {
    super.check(data);

    m_RowIndices.setUpper(data.numInstances() - 1);
  }
}
