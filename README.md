# JSONQuery
when you want to write queries in JSON Format.

Usage
Create Json query file in query.

```JSON
query.json
{
  "query":["select","student"],
  "query2":["select","manager"],
  "components":{
    "select":["select top 10 * from"],
    "student":["student where name =",":name"],
    "manager":["manager where name =",":name"],
    "account":["account where name =",":name"]
  }
}
```

Then Use JSON query framework in project like,

```java
JSONQuery jsonQuery = new JSONQuery("query","components", "query.json");
jsonQuery.addParam(new Param("Dhananjay", Types.VARCHAR, ":name"));

jsonQuery.addPrefixQueryPart("--can add prefix part to query");

System.out.println(jsonQuery.getFinalSqlString());

jsonQuery = new JSONQuery("query2","components", "query.json");
jsonQuery.addParam(new Param("Dhananjay", Types.VARCHAR, ":name"));


System.out.println(jsonQuery.getFinalSqlString());
```

O/P  Queries

```sql
 select top 10 * from student where name = 'Dhananjay'
 select top 10 * from manager where name = 'Dhananjay'
```



The example used is very simple use of JSONQuery .

This framework is useful when you have lot's queries having duplicate SQL statement, In those cases this duplicate statements can be converted into components and this components can be used to assemble query from it.