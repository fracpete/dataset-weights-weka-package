dataset-weights-weka-package
============================

Weka package with filters that allow modifying attribute/instance weights.

The following filters are available:
* `weka.filters.unsupervised.attribute.ModifyAttributeWeights`
* `weka.filters.unsupervised.instance.ModifyInstanceWeights`

Available schemes for *attribute* weights:
* `PassThrough` - dummy, does nothing
* `FixedValue` - applies the user-specified weight to selected range of attributes  

Available schemes for *instance* weights:
* `PassThrough` - dummy, does nothing
* `FixedValue` - applies the user-specified weight to selected range of rows  
