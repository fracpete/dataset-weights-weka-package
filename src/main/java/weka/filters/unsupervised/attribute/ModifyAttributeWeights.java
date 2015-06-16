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
 * ModifyAttributeWeights.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.attribute;

import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;
import weka.filters.SimpleBatchFilter;
import weka.filters.unsupervised.attribute.attributeweightsmodifiers.AttributeWeightsModifier;
import weka.filters.unsupervised.attribute.attributeweightsmodifiers.PassThrough;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

/**
 <!-- globalinfo-start -->
 * Applies a scheme for modifying the weights of the attributes.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 * Valid options are: <p>
 * 
 * <pre> -modifier &lt;classname + options&gt;
 *  The attribute weights modifier to use.
 *  (default: weka.filters.unsupervised.attribute.attributeweightsmodifiers.PassThrough)</pre>
 * 
 * <pre> -output-debug-info
 *  If set, filter is run in debug mode and
 *  may output additional info to the console</pre>
 * 
 * <pre> -do-not-check-capabilities
 *  If set, filter capabilities are not checked before filter is built
 *  (use with caution).</pre>
 * 
 <!-- options-end -->
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class ModifyAttributeWeights
  extends SimpleBatchFilter {

  private static final long serialVersionUID = 9140334137670320734L;

  /** the scheme to use for modifying the weights. */
  protected AttributeWeightsModifier m_Modifier = new PassThrough();

  /**
   * Returns a string describing this filter.
   *
   * @return a description of the filter suitable for displaying in the
   *         explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Applies a scheme for modifying the weights of the attributes.";
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
	"\tThe attribute weights modifier to use.\n"
	  + "\t(default: " + PassThrough.class.getName() + ")",
	"modifier", 1, "-modifier <classname + options>"));

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
    String 	className;
    String[] 	classOptions;

    tmpStr = Utils.getOption("modifier", options);
    if (tmpStr.length() != 0) {
      classOptions    = Utils.splitOptions(tmpStr);
      className       = classOptions[0];
      classOptions[0] = "";
      setModifier((AttributeWeightsModifier) Utils.forName(AttributeWeightsModifier.class, className, classOptions));
    }
    else {
      setModifier(new PassThrough());
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
    Vector<String> result = new Vector<String>();

    result.add("-modifier");
    result.add(Utils.toCommandLine(getModifier()));

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[result.size()]);
  }

  /**
   * Sets the weights modifier to use.
   *
   * @param value the modifier to use
   */
  public void setModifier(AttributeWeightsModifier value) {
    m_Modifier = value;
  }

  /**
   * Gets the current weights modifier.
   *
   * @return the modifier
   */
  public AttributeWeightsModifier getModifier() {
    return m_Modifier;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String modifierTipText() {
    return "The weights modifier to apply to the data.";
  }

  /**
   * Determines the output format based on the input format and returns this. In
   * case the output format cannot be returned immediately, i.e.,
   * immediateOutputFormat() returns false, then this method will be called from
   * batchFinished().
   *
   * @param inputFormat the input format to base the output format on
   * @return the output format
   * @throws Exception in case the determination goes wrong
   */
  @Override
  protected Instances determineOutputFormat(Instances inputFormat) throws Exception {
    return m_Modifier.determineOutputFormat(inputFormat);
  }

  /**
   * Processes the given data (may change the provided dataset) and returns the
   * modified version. This method is called in batchFinished().
   *
   * @param instances the data to process
   * @return the modified data
   * @throws Exception in case the processing goes wrong
   */
  @Override
  protected Instances process(Instances instances) throws Exception {
    return m_Modifier.modifyAttributeWeights(instances);
  }
}
