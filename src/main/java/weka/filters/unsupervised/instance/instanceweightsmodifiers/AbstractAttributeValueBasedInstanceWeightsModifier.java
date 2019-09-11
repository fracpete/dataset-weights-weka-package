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
 * AbstractAttributeValueBasedInstanceWeightsModifier.java
 * Copyright (C) 2019 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.instance.instanceweightsmodifiers;

import weka.core.Instances;
import weka.core.Option;
import weka.core.SingleIndex;
import weka.core.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Ancestor for modifiers that work on a range of rows that get identified
 * by matching a regular expression against an attribute's values.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractAttributeValueBasedInstanceWeightsModifier
  extends AbstractInstanceWeightsModifier {

  /** the index of the attribute which values to match against the regexp. */
  protected SingleIndex m_AttributeIndex = new SingleIndex("first");

  /** the values to match. */
  protected String m_RegExp = ".*";

  /** whether to invert the matching sense. */
  protected boolean m_Invert = false;

  /** the compiled pattern to use. */
  protected transient Pattern m_Pattern;

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration<Option> listOptions() {
    Vector<Option> result = new Vector<Option>();

    result.addElement(new Option(
      "\tSpecify the index of the attribute which values are matched against the regexp.\n"
	+ "\t(default: first)",
      "A", 1, "-A <index>"));

    result.addElement(new Option(
      "\tThe regular expression to match the attribute names against.\n"
	+ "\t(default: .*)", "E",
      1, "-E <regexp>"));

    result.addElement(new Option(
      "\tWhether to invert the matching sense of the regular expression.\n"
	+ "\t(default: not inverted)", "I",
      0, "-I"));

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

    tmpStr = Utils.getOption('A', options);
    if (tmpStr.isEmpty())
      tmpStr = "first";
    setAttributeIndex(tmpStr);

    tmpStr = Utils.getOption("E", options);
    if (tmpStr.length() != 0)
      setRegExp(tmpStr);
    else
      setRegExp(".*");

    setInvert(Utils.getFlag("I", options));

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

    result.add("-A");
    result.add("" + getAttributeIndex());

    result.add("-E");
    result.add(getRegExp());

    if (getInvert())
      result.add("-I");

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[0]);
  }

  /**
   * Sets index of the attribute which values are matched against the regexp.
   *
   * @param value the index of the attribute
   */
  public void setAttributeIndex(String value) {
    m_AttributeIndex.setSingleIndex(value);
  }

  /**
   * Get the index of the attribute which values are matched against the regexp.
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
    return "The position (starting from 1) of the attribute which values are "
      + "matched against the regular expression (first and last are valid indices).";
  }

  /**
   * Sets the regular expression to use for matching the attribute values.
   *
   * @param value a regular expression
   */
  public void setRegExp(String value) {
    m_RegExp  = value;
    m_Pattern = null;
  }

  /**
   * Gets the regular expression used for matching the attribute values.
   *
   * @return a regular expression
   */
  public String getRegExp() {
    return m_RegExp;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String regExpTipText() {
    return "The regular expression to use for matching against the attribute "
      + "values (see https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html).";
  }

  /**
   * Sets whether to invert the matching sense of the regular expression.
   *
   * @param value true if to invert
   */
  public void setInvert(boolean value) {
    m_Invert = value;
  }

  /**
   * Gets whether the matching sense of the regular expression has been inverted.
   *
   * @return true if inverted
   */
  public boolean getInvert() {
    return m_Invert;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String invertTipText() {
    return "If enabled, the matching sense of the regular expression gets inverted.";
  }

  /**
   * Compiles an returns the pattern.
   *
   * @return		the pattern
   * @throws Exception	if pattern compilation fails
   */
  protected Pattern getPattern() throws Exception {
    if (m_Pattern == null)
      m_Pattern = Pattern.compile(m_RegExp);
    return m_Pattern;
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

    getPattern();
    m_AttributeIndex.setUpper(data.numAttributes() - 1);
    if (m_AttributeIndex.getIndex() == -1)
      throw new IllegalArgumentException("Attribute index not valid: " + m_AttributeIndex.getSingleIndex());
    if (!(data.attribute(m_AttributeIndex.getIndex()).isNominal() || data.attribute(m_AttributeIndex.getIndex()).isString()))
      throw new IllegalArgumentException("Attribute is neither nominal nor string: " + m_AttributeIndex.getSingleIndex());
  }

  /**
   * Checks whether the value is a match (takes invert into account).
   *
   * @param value	the value to check
   * @return		true if match
   * @throws Exception	if pattern compilation fails
   */
  protected boolean isMatch(String value) throws Exception {
    boolean	result;

    result = getPattern().matcher(value).matches();
    if (m_Invert)
      result = !result;

    return result;
  }
}
