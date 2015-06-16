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

import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Utils;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Ancestor for instance weights modifiers.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractInstanceWeightsModifier
  implements Serializable, OptionHandler, InstanceWeightsModifier {

  private static final long serialVersionUID = -7927301115335139138L;

  /**
   * Returns a string describing this filter.
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
    return new Vector<Option>().elements();
  }

  /**
   * Parses a given list of options.
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  @Override
  public void setOptions(String[] options) throws Exception {
    Utils.checkForRemainingOptions(options);
  }

  /**
   * Gets the current settings of the filter.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {
    return new String[0];
  }

  /**
   * Hook method for performing checks before modifying the weights.
   * <br><br>
   * Default implementation only ensures that data is present.
   *
   * @param data	the data to check
   */
  protected void check(Instances data) {
    if (data == null)
      throw new IllegalStateException("No data provided!");
  }

  /**
   * Peforms the actual modification of the instance weights.
   *
   * @param data        the data to process
   * @return		the modified data
   */
  protected abstract Instances doModify(Instances data);

  /**
   * Modifies the instance weights.
   *
   * @param data        the data to process
   * @return		the modified data
   */
  public Instances modifyInstanceWeights(Instances data) {
    check(data);
    return doModify(data);
  }
}
