# The webresource compactor

This a fixed webresource compactor.

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
      <version>${gong.version}</version>
      <type>jar</type>
    </dependency>
```