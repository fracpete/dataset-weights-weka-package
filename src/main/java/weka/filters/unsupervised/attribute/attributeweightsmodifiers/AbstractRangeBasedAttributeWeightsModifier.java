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

/*
 * AbstractRangeBasedAttributeWeightsModifier.java
 * Copyright (C) 2015-2019 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.attribute.attributeweightsmodifiers;

import weka.core.Instances;
import weka.core.Option;
import weka.core.Range;
import weka.core.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Ancestor for modifiers that only work on a subset of attributes.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractRangeBasedAttributeWeightsModifier
  extends AbstractAttributeWeightsModifier {

  private static final long serialVersionUID = -6347414272696310567L;

  /** the attribute range to work on. */
  protected Range m_AttributeIndices = new Range("first-last");

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration<Option> listOptions() {
    Vector<Option> result = new Vector<Option>();

    result.addElement(new Option("\tThe attribute range to work on.\n"
      + "This is a comma separated list of attribute indices, with "
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

    tmpStr = Utils.getOption("R", options);
    if (tmpStr.length() != 0)
      setAttributeIndices(tmpStr);
    else
      setAttributeIndices("first-last");

    super.setOptions(options);
  }

  /**
   * Gets the current settings of the filter.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {
    List<String> result = new ArrayList<String>();

    result.add("-R");
    result.add(getAttributeIndices());

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[0]);
  }

  /**
   * Sets which attributes are to be acted on.
   *
   * @param value a string representing the list of attributes. Since the string
   *          will typically come from a user, attributes are indexed from1. <br>
   *          eg: first-3,5,6-last
   */
  public void setAttributeIndices(String value) {
    m_AttributeIndices.setRanges(value);
  }

  /**
   * Gets the current range selection.
   *
   * @return a string containing a comma separated list of ranges
   */
  public String getAttributeIndices() {
    return m_AttributeIndices.getRanges();
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String attributeIndicesTipText() {
    return "Specify range of attributes to act on; "
      + "this is a comma separated list of attribute indices, with "
      + "\"first\" and \"last\" valid values; specify an inclusive "
      + "range with \"-\"; eg: \"first-3,5,6-10,last\".";
  }

  /**
   * Hook method for performing checks before modifying the weights.
   *
   * @param data	the data to check
   * @throws Exception	if modifying fails
   */
  @Override
  protected void check(Instances data) throws Exception {
    super.check(data);

    m_AttributeIndices.setUpper(data.numAttributes() - 1);
  }
}
