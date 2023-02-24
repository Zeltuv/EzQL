# 💥 EzQL <sup>Now, it's ez</sup>

Do you struggle with all those long and annoying MySQL command lines ? Use EzQL ! You will be provided an easy and light API (Made with HikariCP) to use MySQL !

> **Note: This library needs HikariCP and the JDBC MySQL Connector and only works for Java 17.**

# 🪨 Artifacts

*Maven:*
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.Zeltuv</groupId>
    <artifactId>EzQL</artifactId>
    <version>1.0.0</version>
   </dependency>
</dependencies>
```
*Gradle:*
```gradle
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.Zeltuv:EzQL:1.0.0'
}
```

Or you can [download it as a JAR](https://github.com/Zeltuv/EzQL/releases/tag/1.0.0).

# ☕ Java Doc

We also propose a java doc to make the lib simpler to use ! [Here is the link](https://zeltuv.github.io/ezql/).

# ⚙️ Basic usage examples

Here is a sample code that exposes most of the features of the lib.

```java
public static void main(String[] args) {
        EzqlCredentials ezqlCredentials = new EzqlCredentials(
                "host",
                "database",
                "username",
                "password",
                20, // Max pools
                3306, // Port
                true // SSL
        );

        EzqlDatabase ezqlDatabase = new EzqlDatabase(ezqlCredentials);
        
        ezqlDatabase = new EzqlDatabase(ezqlCredentials, ezqlCredentials1 -> {
            HikariConfig hikariConfig = ...
            
            ...
            
            return hikariConfig;
        });

        ezqlDatabase.connect();

        EzqlTable ezqlTable = ezqlDatabase.addTable("myTable",
                EzqlColumn.get(DataType.VARCHAR,"id",36,true),
                EzqlColumn.get(DataType.VARCHAR,"firstname",16),
                EzqlColumn.get(DataType.INT,"age")
        );

        ezqlTable.pushRow(
                "MyID",
                "FirstName",
                69
        );

        EzqlRow row = ezqlTable.getSingleRow("id","MyID");
        row = ezqlTable.getSingleRow("id","MyID", /*needed columns*/ Set.of( "firstname","age"));

        List<EzqlRow> rows = ezqlTable.getAllRows();
        rows = ezqlTable.getAllRows(/*needed columns*/ Set.of( "firstname","age"));

        rows = ezqlTable.getRows("id","MyID");
        rows = ezqlTable.getRows("id","MyID", /*needed columns*/ Set.of( "firstname","age"));

        ezqlTable.removeRows("id","MyID");

        ezqlTable.updateRow("id","MyID",new EzqlRow("firstname", "Bartu","age",48));

        ezqlDatabase.disconnect();
    }
```

# ✒️ Credits

This lib has been fully made by me **Zeltuv**.
