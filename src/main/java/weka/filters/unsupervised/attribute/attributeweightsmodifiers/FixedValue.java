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
 * FixedValue.java
 * Copyright (C) 2015-2019 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.attribute.attributeweightsmodifiers;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 <!-- globalinfo-start -->
 * Uses a user-supplied attribute weight on all the attributes in the defined range.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 * Valid options are: <p>
 * 
 * <pre> -weight &lt;number&gt;
 *  The attribute weight to use.
 *  (default: 1.0)</pre>
 * 
 * <pre> -R &lt;range&gt;
 *  The attribute range to work on.
 * This is a comma separated list of attribute indices, with "first" and "last" valid values.
 *  Specify an inclusive range with "-".
 *  E.g: "first-3,5,6-10,last".
 *  (default: first-last)</pre>
 * 
 * <pre> -do-not-check-capabilities
 *  If set, modifier capabilities are not checked
 *  (use with caution).</pre>
 * 
 <!-- options-end -->
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class FixedValue
  extends AbstractRangeBasedAttributeWeightsModifier {

  private static final long serialVersionUID = 4432458832078542477L;

  /** the fixed weight to use. */
  protected double m_Weight = 1.0;

  /**
   * Returns a string describing this scheme.
   *
   * @return a description of the filter suitable for displaying in the
   *         explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Uses a user-supplied attribute weight on all the attributes in the defined range.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration<Option> listOptions() {
    Vector<Option> result = new Vector<Option>();

    result.addElement(
      new Option(
	"\tThe attribute weight to use.\n"
	  + "\t(default: 1.0)",
	"weight", 1, "-weight <number>"));

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
    String 	tmpStr;

    tmpStr = Utils.getOption("weight", options);
    if (tmpStr.length() != 0) {
      setWeight(Double.parseDouble(tmpStr));
    }
    else {
      setWeight(1.0);
    }

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

    result.add("-weight");
    result.add("" + getWeight());

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[0]);
  }

  /**
   * Sets the weight to use.
   *
   * @param value the weight
   */
  public void setWeight(double value) {
    m_Weight = value;
  }

  /**
   * Gets the current weight.
   *
   * @return the weight
   */
  public double getWeight() {
    return m_Weight;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String weightTipText() {
    return "The weight to use.";
  }

  /**
   * Returns the new output format.
   *
   * @param inputFormat the input format, before applying the weights
   * @return		the output format, after applying the weights
   * @throws Exception	if determination fails
   */
  public Instances determineOutputFormat(Instances inputFormat) throws Exception {
    ArrayList<Attribute> 	atts;
    Attribute			oldAtt;
    Attribute			newAtt;
    int				i;

    m_AttributeIndices.setUpper(inputFormat.numAttributes() - 1);
    atts = new ArrayList<Attribute>();
    for (i = 0; i < inputFormat.numAttributes(); i++) {
      oldAtt = inputFormat.attribute(i);
      newAtt = (Attribute) oldAtt.copy();
      if (m_AttributeIndices.isInRange(i))
	newAtt.setWeight(m_Weight);
      atts.add(newAtt);
    }

    return new Instances(inputFormat.relationName(), atts, 0);
  }

  /**
   * Performs the actual modification of the attribute weights.
   *
   * @param data        the data to process
   * @return		the modified data
   * @throws Exception	if modifying fails
   */
  @Override
  protected Instances doModify(Instances data) throws Exception {
    Instances	result;

    result = new Instances(determineOutputFormat(data), data.numInstances());
    for (Instance inst: data)
      result.add((Instance) inst.copy());

    return result;
  }
}
