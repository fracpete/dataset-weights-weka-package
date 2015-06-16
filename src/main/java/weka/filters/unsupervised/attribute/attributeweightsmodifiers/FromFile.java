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
 * FromFile.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.attribute.attributeweightsmodifiers;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 <!-- globalinfo-start -->
 * Uses the weights stored in a file (one weight per line).
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 * Valid options are: <p>
 * 
 * <pre> -weights &lt;file&gt;
 *  The file with weights (one per line)
 *  (default: .)</pre>
 * 
 * <pre> -do-not-check-capabilities
 *  If set, modifier capabilities are not checked
 *  (use with caution).</pre>
 * 
 <!-- options-end -->
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class FromFile
  extends AbstractAttributeWeightsModifier {

  private static final long serialVersionUID = -5716918435393494286L;

  /** the file to load the instance weights from. */
  protected File m_WeightsFile;

  /**
   * Returns a string describing this scheme.
   *
   * @return a description of the filter suitable for displaying in the
   *         explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Uses the weights stored in a file (one weight per line).";
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
	"\tThe file with weights (one per line)\n"
	  + "\t(default: .)",
	"weights", 1, "-weights <file>"));

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

    tmpStr = Utils.getOption("weights", options);
    if (tmpStr.isEmpty())
      tmpStr = ".";
    setWeightsFile(new File(tmpStr));

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

    result.add("-weights");
    result.add("" + getWeightsFile());

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[result.size()]);
  }

  /**
   * Sets the file with the weights.
   *
   * @param value the file
   */
  public void setWeightsFile(File value) {
    m_WeightsFile = value;
  }

  /**
   * Get the file with the weights.
   *
   * @return the file
   */
  public File getWeightsFile() {
    return m_WeightsFile;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String weightsFileTipText() {
    return "The file with the weights (one per line).";
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

    if (!m_WeightsFile.exists())
      throw new IllegalArgumentException("Weights file does not exist: " + m_WeightsFile);
    if (!m_WeightsFile.isDirectory())
      throw new IllegalArgumentException("Weights file points to a directory: " + m_WeightsFile);
  }

  /**
   * Loads the weights from disk.
   *
   * @return		the weights
   * @throws Exception	if reading of weights fails
   */
  protected List<Double> loadWeights() throws Exception {
    List<Double>	result;
    BufferedReader	reader;
    String		line;

    result = new ArrayList<Double>();
    reader = null;
    try {
      reader = new BufferedReader(new FileReader(m_WeightsFile));
      while ((line = reader.readLine()) != null)
        result.add(Double.parseDouble(line));
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if (reader != null) {
        try {
          reader.close();
        }
        catch (Exception e) {
          // ignored
        }
      }
    }

    return result;
  }

  /**
   * Returns the new output format.
   *
   * @param inputFormat the input format, before applying the weights
   * @return		the output format, after applying the weights
   * @throws Exception	if determination fails
   */
  public Instances determineOutputFormat(Instances inputFormat) throws Exception {
    Instances			result;
    ArrayList<Attribute> 	atts;
    Attribute			oldAtt;
    Attribute			newAtt;
    int				i;
    List<Double>	  weights;

    weights = loadWeights();
    if (weights.size() < inputFormat.numAttributes())
      throw new IllegalStateException("Not enough weights: " + weights.size() + " < " + inputFormat.numAttributes());
    if (weights.size() > inputFormat.numAttributes())
      System.err.println("More weights than rows: " + weights.size() + " > " + inputFormat.numAttributes());

    atts = new ArrayList<Attribute>();
    for (i = 0; i < inputFormat.numAttributes(); i++) {
      oldAtt = inputFormat.attribute(i);
      newAtt = (Attribute) oldAtt.copy();
      newAtt.setWeight(weights.get(i));
      atts.add(newAtt);
    }

    return new Instances(inputFormat.relationName(), atts, 0);
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

    result = new Instances(determineOutputFormat(data), data.numInstances());
    for (Instance inst: data)
      result.add((Instance) inst.copy());

    return result;
  }
}
