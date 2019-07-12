import java.sql.Types;

public class JsonQueryTest {
    public static void main(String[] args) {
        JSONQuery jsonQuery = new JSONQuery("query","components", "query.json");
        jsonQuery.addParam(new Param("Dhananjay", Types.VARCHAR, ":name"));

        jsonQuery.addPrefixQueryPart("--can add prefix part to query");

        System.out.println(jsonQuery.getFinalSqlString());

        jsonQuery = new JSONQuery("query2","components", "query.json");
        jsonQuery.addParam(new Param("Dhananjay", Types.VARCHAR, ":name"));


        System.out.println(jsonQuery.getFinalSqlString());
        /** o/p
         * --can add prefix part to query
         * select top 10 * from student where name = 'Dhananjay'
         * select top 10 * from manager where name = 'Dhananjay'
         */

    }
}
