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
 * FromAttribute.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.instance.instanceweightsmodifiers;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.SingleIndex;
import weka.core.Utils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

/**
 <!-- globalinfo-start -->
 * Uses the values from a numeric attribute as instance weights.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 * Valid options are: <p>
 * 
 * <pre> -C &lt;index&gt;
 *  Specify the index of the attribute with the weights. First and last
 *  are valid indexes.(default: last)</pre>
 * 
 * <pre> -do-not-check-capabilities
 *  If set, modifier capabilities are not checked
 *  (use with caution).</pre>
 * 
 <!-- options-end -->
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class FromAttribute
  extends AbstractInstanceWeightsModifier {

  private static final long serialVersionUID = -5716918435393494286L;

  /** the index of the attribute with the instance weights. */
  protected SingleIndex m_AttributeIndex;

  /**
   * Returns a string describing this scheme.
   *
   * @return a description of the filter suitable for displaying in the
   *         explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Uses the values from a numeric attribute as instance weights.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration<Option> listOptions() {
    Vector<Option> 	result;

    result = new Vector<Option>();

    result.addElement(
      new Option(
	"\tSpecify the index of the attribute with the weights. First and last\n"
	  + "\tare valid indexes.(default: last)",
	"C", 1, "-C <index>"));

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

    tmpStr = Utils.getOption('C', options);
    if (tmpStr.isEmpty())
      tmpStr = "last";
    setAttributeIndex(tmpStr);

    super.setOptions(options);
  }

  /**
   * Gets the current settings of the filter.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {
    Vector<String> result;

    result = new Vector<String>();

    result.add("-C");
    result.add("" + getAttributeIndex());

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[result.size()]);
  }

  /**
   * Sets index of the attribute with the weights.
   *
   * @param value the index of the attribute
   */
  public void setAttributeIndex(String value) {
    m_AttributeIndex.setSingleIndex(value);
  }

  /**
   * Get the index of the attribute with the weights.
   *
   * @return the index of the attribute
   */
  public String getAttributeIndex() {
    return m_AttributeIndex.getSingleIndex();
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String attributeIndexTipText() {
    return "The position (starting from 1) of the attribute with the weights "
      + "(first and last are valid indices).";
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

    m_AttributeIndex.setUpper(data.numAttributes() - 1);
    if (m_AttributeIndex.getIndex() == -1)
      throw new IllegalArgumentException("Attribute index not valid: " + m_AttributeIndex.getSingleIndex());
    if (!data.attribute(m_AttributeIndex.getIndex()).isNumeric())
      throw new IllegalArgumentException("Attribute is not numeric: " + m_AttributeIndex.getSingleIndex());
  }

  /**
   * Returns the new output format.
   *
   * @param inputFormat the input format, before applying the weights
   * @return		the output format, after applying the weights
   * @throws Exception	if determination fails
   */
  public Instances determineOutputFormat(Instances inputFormat) throws Exception {
    return new Instances(inputFormat, 0);
  }

  /**
   * Peforms the actual modification of the instance weights.
   *
   * @param data        the data to process
   * @return		the modified data
   * @throws Exception	if modifying fails
   */
  @Override
  protected Instances doModify(Instances data) throws Exception {
    Instances	result;
    Instance	inst;
    int		i;
    int		index;

    result = new Instances(determineOutputFormat(data), data.numInstances());

    index = m_AttributeIndex.getIndex();
    for (i = 0; i < data.numInstances(); i++) {
      inst = (Instance) data.instance(i).copy();
      inst.setWeight(inst.value(index));
      result.add(inst);
    }

    return result;
  }
}
