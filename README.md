dataset-weights-weka-package
============================

**[WEKA](http://www.cs.waikato.ac.nz/ml/weka/) package** offering filters that allow modification of attribute/instance weights.

The following filters are available:
* `weka.filters.unsupervised.attribute.ModifyAttributeWeights`
* `weka.filters.unsupervised.instance.ModifyInstanceWeights`

Available modifiers for *attribute* weights:
* `FixedValue` - applies the user-specified weight to selected range of attributes  
* `FromFile` - uses the weights stored in a file
* `PassThrough` - dummy, does nothing

Available modifiers for *instance* weights:
* `FixedValue` - applies the user-specified weight to selected range of rows  
* `FromAttribute` - uses the values from a numeric attribute as weights
* `FromFile` - uses the weights stored in a file
* `PassThrough` - dummy, does nothing


How to use packages
-------------------

For more information on how to install the package, see:

http://weka.wikispaces.com/How+do+I+use+the+package+manager%3F

