dataset-weights-weka-package
============================

Weka package with filters that allow modifying attribute/instance weights.

The following filters are available:
* `weka.filters.unsupervised.attribute.ModifyAttributeWeights`
* `weka.filters.unsupervised.instance.ModifyInstanceWeights`

Available modifiers for *attribute* weights:
* `PassThrough` - dummy, does nothing
* `FixedValue` - applies the user-specified weight to selected range of attributes  

Available modifiers for *instance* weights:
* `PassThrough` - dummy, does nothing
* `FixedValue` - applies the user-specified weight to selected range of rows  
* `FromAttribute` - uses the values from a numeric attribute as weights

