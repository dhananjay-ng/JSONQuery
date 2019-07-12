
import com.google.gson.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author dhannajayN
 */
public class JSONQuery {
    private HashMap<String, Param> paramMap;
    private StringBuilder sqlBuilder;
    private Reader fileReader;
    private JsonObject queryContents;
    private String driverNodeName;
    private String componentNodeName;
    private List<String> prefixQueryPart;

    public JSONQuery(String driverNodeName, String componentNodeName, HashMap<String, Param> paramMap, String queryFilePath) {
        this.driverNodeName = driverNodeName;
        this.componentNodeName = componentNodeName;
        this.paramMap = paramMap;
        fileReader = new InputStreamReader(JSONQuery.class.getClassLoader().getResourceAsStream(queryFilePath));
        sqlBuilder = new StringBuilder();
        queryContents = new JsonParser().parse(fileReader).getAsJsonObject();
        prefixQueryPart = new ArrayList<>();

    }

    public JSONQuery(String driverNodeName, String componentNodeName, String queryFilePath) {
        this.driverNodeName = driverNodeName;
        this.componentNodeName = componentNodeName;
        this.paramMap = new HashMap<>();
        fileReader = new InputStreamReader(JSONQuery.class.getClassLoader().getResourceAsStream(queryFilePath));
        sqlBuilder = new StringBuilder();
        queryContents = new JsonParser().parse(fileReader).getAsJsonObject();
        prefixQueryPart = new ArrayList<>();
    }

    public void addParam(Param param) {
        paramMap.put(param.getName(), param);
    }

    public void addPrefixQueryPart(String sqlPart) {
        prefixQueryPart.add(sqlPart);
    }

    public String getFinalSqlString() {
        if (this.sqlBuilder != null && this.sqlBuilder.length() > 0) {
            return   this.sqlBuilder.toString();
        }
        if (prefixQueryPart.size() > 0){
            prefixQueryPart.forEach(s -> {
                sqlBuilder.append(s).append("\n");
            });
        }
        JsonObject queryComponents = queryContents.get(this.componentNodeName).getAsJsonObject();
        for (JsonElement componentName : queryContents.get(this.driverNodeName).getAsJsonArray()) {
            for (JsonElement sqlComponent : queryComponents.get(componentName.getAsString()).getAsJsonArray()) {
                processSqlStatementComponent(sqlComponent);
            }
        }
        return this.sqlBuilder.toString();
    }

    private void processSqlStatementComponent(JsonElement sqlComponent) {
        if (sqlComponent.getAsString().startsWith(":")) {
            //param
            processSqlParam(sqlComponent);

        } else {
            //statememnt
            this.sqlBuilder.append(sqlComponent.getAsString()).append("\n");
        }
    }

    private void processSqlParam(JsonElement sqlComponent) {
        Param param = this.paramMap.get(sqlComponent.getAsString());

        if (Types.OTHER == param.getType() || Types.INTEGER == param.getType()) {
            this.sqlBuilder.append(" ").append(param.getValue()).append(" ");

        } else if (Types.VARCHAR == param.getType() || Types.NVARCHAR == param.getType()) {
            this.sqlBuilder.append(" ").append("'")
                    .append(param.getValue()).append("'").append(" ");

        }
    }

}
