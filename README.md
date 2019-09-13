# dataset-weights-weka-package

[Weka](http://www.cs.waikato.ac.nz/ml/weka/) package offering filters that allow modification of attribute/instance weights.

The following filters are available:
* `weka.filters.unsupervised.attribute.ModifyAttributeWeights`
* `weka.filters.unsupervised.instance.ModifyInstanceWeights`

Available modifiers for *attribute* weights:
* `FixedValue` - applies the user-specified weight to selected range of attributes  
* `FixedValueByName` - applies the user-specified weight to attributes that match 
  the specified regular expression (matching can be inverted)  
* `FromFile` - uses the weights stored in a file
* `PassThrough` - dummy, does nothing

Available modifiers for *instance* weights:
* `FixedValue` - applies the user-specified weight to selected range of rows  
* `FixedValueByRegExp` - applies the user-specified weight to the rows which
  values of a specified attribute match the regular expression (matching can 
  be inverted)  
* `FromAttribute` - uses the values from a numeric attribute as weights
* `FromFile` - uses the weights stored in a file
* `PassThrough` - dummy, does nothing


## Releases

* [2019.9.13](https://github.com/fracpete/dataset-weights-weka-package/releases/download/v2019.9.13/dataset-weights-2019.9.13.zip)
* [2019.9.11](https://github.com/fracpete/dataset-weights-weka-package/releases/download/v2019.9.11/dataset-weights-2019.9.11.zip)
* [2015.6.16](https://github.com/fracpete/dataset-weights-weka-package/releases/download/v2015.6.16/dataset-weights-2015.6.16.zip)


## How to use packages

For more information on how to install the package, see:

https://waikato.github.io/weka-wiki/packages/manager/

## Maven

Use the following dependency in your `pom.xml`:

```xml
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>dataset-weights-weka-package</artifactId>
      <version>2019.9.13</version>
      <type>jar</type>
      <exclusions>
        <exclusion>
          <groupId>nz.ac.waikato.cms.weka</groupId>
          <artifactId>weka-dev</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
```
