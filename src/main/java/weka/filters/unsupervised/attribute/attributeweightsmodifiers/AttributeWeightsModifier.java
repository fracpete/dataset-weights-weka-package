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
 * AttributeWeightsModifier.java
 * Copyright (C) 2015 University of Waikato, Hamilton, New Zealand
 */
package weka.filters.unsupervised.attribute.attributeweightsmodifiers;

import weka.core.Instances;

import java.io.Serializable;

/**
 * Interface for algorithms that modify attribute weights.
 * 
 * @author fracpete (fracpete at waikato dot ac dot nz)
 */
public interface AttributeWeightsModifier
  extends Serializable {

  /**
   * Returns the new output format.
   *
   * @param inputFormat the input format, before applying the weights
   * @return		the output format, after applying the weights
   * @throws Exception	if determination fails
   */
  public Instances determineOutputFormat(Instances inputFormat) throws Exception;

  /**
   * Modifies the attribute weights.
   * 
   * @param data        the data to process
   * @return		the modified data
   */
  public Instances modifyAttributeWeights(Instances data) throws Exception;
}
