# The webresource compactor

This a better webresource compactor.

From webapp-dispatcher you should remove

```$xml
    <dependency>
      <groupId>com.polopoly</groupId>
      <artifactId>webresource-compactor</artifactId>
      <version>${polopoly.version}</version>
      <type>jar</type>
    </dependency>
```

and replace it with:

```$xml
    <dependency>
      <groupId>com.atex.gong</groupId>
      <artifactId>webresource-compactor</artifactId>
      <version>${webresource.version}</version>
      <type>jar</type>
    </dependency>
```

Where `${webresource.version}` can be:

| Version | Gong Version |
|---------|--------------|
| 1.0     | 2.3          |
| 1.1     | 2.5          |



## Polopoly Version
10.16.5

## Code Status
The code in this repository is provided with the following status: **PROJECT**

Under the open source initiative, Atex provides source code for plugin with different levels of support. There are three different levels of support used. These are:

- EXAMPLE  
The code is provided as an illustration of a pattern or blueprint for how to use a specific feature. Code provided as is.

- PROJECT  
The code has been identified in an implementation project to be generic enough to be useful also in other projects. This means that it has actually been used in production somewhere, but it comes "as is", with no support attached. The idea is to promote code reuse and to provide a convenient starting point for customization if needed.

- PRODUCT  
The code is provided with full product support, just as the core Polopoly product itself.
If you modify the code (outside of configuraton files), the support is voided.


## License
Atex Polopoly Source Code License
Version 1.0 February 2012

See file **LICENSE** for details
