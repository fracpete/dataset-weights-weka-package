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
 * PassThrough.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.filters.unsupervised.attribute.attributeweightsmodifiers;

import weka.core.Instances;

/**
 <!-- globalinfo-start -->
 * Dummy modifier, doesn't change the weights.
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 <!-- options-end -->
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class PassThrough
  extends AbstractAttributeWeightsModifier {

  private static final long serialVersionUID = 4432458832078542477L;

  /**
   * Returns a string describing this filter.
   *
   * @return a description of the filter suitable for displaying in the
   *         explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Dummy modifier, doesn't change the weights.";
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
   * Peforms the actual modification of the attribute weights.
   *
   * @param data        the data to process
   * @return		the modified data
   */
  @Override
  protected Instances doModify(Instances data) {
    return data;
  }
}
