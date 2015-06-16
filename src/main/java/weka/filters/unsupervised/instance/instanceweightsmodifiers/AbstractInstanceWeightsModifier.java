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
 * AbstractInstanceWeightsModifier.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.instance.instanceweightsmodifiers;

import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.CapabilitiesHandler;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * Ancestor for instance weights modifiers.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractInstanceWeightsModifier
  implements Serializable, OptionHandler, InstanceWeightsModifier, CapabilitiesHandler {

  private static final long serialVersionUID = -7927301115335139138L;

  /** Whether capabilities should not be checked before classifier is built. */
  protected boolean m_DoNotCheckCapabilities = false;

  /**
   * Returns a string describing this scheme.
   *
   * @return a description of the filter suitable for displaying in the
   *         explorer/experimenter gui
   */
  public abstract String globalInfo();

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
        "\tIf set, modifier capabilities are not checked\n"
          + "\t(use with caution).",
        "do-not-check-capabilities", 0, "-do-not-check-capabilities"));

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
    setDoNotCheckCapabilities(Utils.getFlag("do-not-check-capabilities", options));
    Utils.checkForRemainingOptions(options);
  }

  /**
   * Gets the current settings of the filter.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {
    Vector<String> options = new Vector<String>();

    if (getDoNotCheckCapabilities())
      options.add("-do-not-check-capabilities");

    return options.toArray(new String[options.size()]);
  }

  /**
   * Set whether not to check capabilities.
   *
   * @param value true if capabilities are not to be checked.
   */
  public void setDoNotCheckCapabilities(boolean value) {
    m_DoNotCheckCapabilities = value;
  }

  /**
   * Get whether capabilities checking is turned off.
   *
   * @return true if capabilities checking is turned off.
   */
  public boolean getDoNotCheckCapabilities() {
    return m_DoNotCheckCapabilities;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String doNotCheckCapabilitiesTipText() {
    return "If set, modifier capabilities are not checked (use with caution to reduce runtime).";
  }

  /**
   * Returns the Capabilities of this filter. Derived filters have to override
   * this method to enable capabilities.
   *
   * @return the capabilities of this object
   * @see Capabilities
   */
  @Override
  public Capabilities getCapabilities() {
    Capabilities result;

    result = new Capabilities(this);
    result.enableAll();

    result.setMinimumNumberInstances(0);

    return result;
  }

  /**
   * Returns the Capabilities of this filter, customized based on the data.
   * I.e., if removes all class capabilities, in case there's not class
   * attribute present or removes the NO_CLASS capability, in case that there's
   * a class present.
   *
   * @param data the data to use for customization
   * @return the capabilities of this object, based on the data
   * @see #getCapabilities()
   */
  public Capabilities getCapabilities(Instances data) {
    Capabilities result;
    Capabilities classes;
    Iterator<Capability> iter;
    Capability cap;

    result = getCapabilities();

    // no class? -> remove all class capabilites apart from NO_CLASS
    if (data.classIndex() == -1) {
      classes = result.getClassCapabilities();
      iter = classes.capabilities();
      while (iter.hasNext()) {
        cap = iter.next();
        if (cap != Capability.NO_CLASS) {
          result.disable(cap);
          result.disableDependency(cap);
        }
      }
    }
    // class? -> remove NO_CLASS
    else {
      result.disable(Capability.NO_CLASS);
      result.disableDependency(Capability.NO_CLASS);
    }

    return result;
  }

  /**
   * Hook method for performing checks before modifying the weights.
   * <br><br>
   * Default implementation only ensures that data is present.
   *
   * @param data	the data to check
   * @throws Exception	if check fails
   */
  protected void check(Instances data) throws Exception {
    if (data == null)
      throw new IllegalStateException("No data provided!");

    if (!m_DoNotCheckCapabilities) {
      try {
	getCapabilities(data).testWithFail(data);
      }
      catch (Exception e) {
	throw new IllegalArgumentException(e);
      }
    }
  }

  /**
   * Peforms the actual modification of the instance weights.
   *
   * @param data        the data to process
   * @return		the modified data
   * @throws Exception	if modifying fails
   */
  protected abstract Instances doModify(Instances data) throws Exception;

  /**
   * Modifies the instance weights.
   *
   * @param data        the data to process
   * @return		the modified data
   * @throws Exception	if modifying fails
   */
  public Instances modifyInstanceWeights(Instances data) throws Exception {
    check(data);
    return doModify(data);
  }
}
