package com.altinity.clickhouse.sink.connector.db;

import com.altinity.clickhouse.sink.connector.model.KafkaMetaData;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.kafka.connect.data.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class with all functions related
 * to creating Raw queries for Clickhouse JDBC library
 */
public class QueryFormatter {

    private static final Logger log = LoggerFactory.getLogger(QueryFormatter.class);

    /**
     * There could be a possibility that the column count will not match
     * between Source and Clickhouse.
     * - We will drop records if the columns are not present in clickhouse.
     * @param tableName
     * @param fields
     * @return
     */
    public MutablePair<String, Map<String, Integer>> getInsertQueryUsingInputFunction(String tableName, List<Field> fields,
                                                                                      Map<String, String> columnNameToDataTypeMap,
                                                                                      boolean includeKafkaMetaData,
                                                                                      boolean includeRawData,
                                                                                      String rawDataColumn,
                                                                                      String signColumn,
                                                                                      String versionColumn,
                                                                                      String replacingMergeTreeDeleteColumn,
                                                                                      DBMetadata.TABLE_ENGINE tableEngine) {


        Map<String, Integer> colNameToIndexMap = new HashMap<String, Integer>();
        int index = 1;

        StringBuilder colNamesDelimited = new StringBuilder();
        StringBuilder colNamesToDataTypes = new StringBuilder();

        if(fields == null) {
            log.error("getInsertQueryUsingInputFunction, fields empty");
            return null;
        }

        for (Map.Entry<String, String> entry : columnNameToDataTypeMap.entrySet()) {

            //for(Field f: fields) {
            String sourceColumnName = entry.getKey();
            //String sourceColumnName = f.name();
            // Get Field Name and lookup in the Clickhouse column to datatype map.
            String dataType = columnNameToDataTypeMap.get(sourceColumnName);

            if(dataType != null) {
                colNamesDelimited.append(sourceColumnName).append(",");
                colNamesToDataTypes.append(sourceColumnName).append(" ").append(dataType).append(",");
                colNameToIndexMap.put(sourceColumnName, index++);
            } else {
                log.error(String.format("Table Name: %s, Column(%s) ignored", tableName, sourceColumnName));
            }
        }
        if(includeKafkaMetaData) {
            for(KafkaMetaData metaDataColumn: KafkaMetaData.values()) {
                String metaDataColName = metaDataColumn.getColumn();
                if(columnNameToDataTypeMap.containsKey(metaDataColName)) {
                    String dataType = columnNameToDataTypeMap.get(metaDataColName);

                    colNamesDelimited.append(metaDataColName).append(",");
                    colNamesToDataTypes.append(metaDataColName).append(" ").append(dataType).append(",");
                    colNameToIndexMap.put(metaDataColName, index++);
                } else {
                    //log.error("Kafka metadata enabled but column not added to clickhouse: "  + rawDataColumn );
                }
            }
        }
        if(includeRawData) {
            if(columnNameToDataTypeMap.containsKey(rawDataColumn)) {
                // Also check if the data type is String.
                String dataType = columnNameToDataTypeMap.get(rawDataColumn);
                if(dataType.contains("String")) {
                    colNamesDelimited.append(rawDataColumn).append(",");

                    colNamesToDataTypes.append(rawDataColumn).append(" ").append("String").append(",");
                    colNameToIndexMap.put(rawDataColumn, index++);
                }
//                else {
//                    log.error("RAW DATA column is not of String datatype: "  + rawDataColumn );
//
//                }
            }
            else {
                log.error("RAW DATA enabled but column not added to clickhouse: "  + rawDataColumn );
            }
        }

        // Add sign column(-1 if its delete, 1 for update)
        if(tableEngine != null && tableEngine.getEngine().equalsIgnoreCase(DBMetadata.TABLE_ENGINE.COLLAPSING_MERGE_TREE.getEngine())) {
            if (signColumn != null && columnNameToDataTypeMap.containsKey(signColumn)) {
                colNamesDelimited.append(signColumn).append(",");
                colNamesToDataTypes.append(signColumn).append(" ").append(columnNameToDataTypeMap.get(signColumn)).append(",");
                colNameToIndexMap.put(signColumn, index++);

            }
        }

        // Add version column(Set timestamp))
//        if(tableEngine != null && tableEngine.getEngine().equalsIgnoreCase(DBMetadata.TABLE_ENGINE.REPLACING_MERGE_TREE.getEngine())) {
//            if (versionColumn != null && columnNameToDataTypeMap.containsKey(versionColumn)) {
//                colNamesDelimited.append(versionColumn).append(",");
//                colNamesToDataTypes.append(versionColumn).append(" ").append(columnNameToDataTypeMap.get(versionColumn)).append(",");
//                colNameToIndexMap.put(versionColumn, index++);
//
//            }
//
//            // Add replacingmergetree sign delete column.
//            if(replacingMergeTreeDeleteColumn != null && columnNameToDataTypeMap.containsKey(replacingMergeTreeDeleteColumn)) {
//                colNamesDelimited.append(replacingMergeTreeDeleteColumn).append(",");
//                colNamesToDataTypes.append(replacingMergeTreeDeleteColumn).append(" ").append(columnNameToDataTypeMap.get(replacingMergeTreeDeleteColumn)).append(",");
//                colNameToIndexMap.put(replacingMergeTreeDeleteColumn, index++);
//            }
//        }

        //Remove terminating comma
        int colNamesIndex = colNamesDelimited.lastIndexOf(",");
        if(colNamesIndex != -1 )
            colNamesDelimited.deleteCharAt(colNamesIndex);

        int colNamesToDataTypesIndex = colNamesToDataTypes.lastIndexOf(",");
        if(colNamesToDataTypesIndex != -1)
            colNamesToDataTypes.deleteCharAt(colNamesToDataTypesIndex);

        String insertQuery = String.format("insert into %s(%s) select %s from input('%s')", tableName, colNamesDelimited, colNamesDelimited, colNamesToDataTypes);
        MutablePair<String, Map<String, Integer>> response = new MutablePair<String, Map<String, Integer>>();

        response.left = insertQuery;
        response.right = colNameToIndexMap;

        return response;
    }
    /**
     * Function to construct an INSERT query using input functions.
     *
     * @param tableName Table Name
     * @return Insert query using Input function.
     */
    public String getInsertQueryUsingInputFunction(String tableName,  Map<String, String> columnNameToDataTypeMap) {
        // "insert into mytable select col1, col2 from input('col1 String, col2 DateTime64(3), col3 Int32')"))

        StringBuilder colNamesDelimited = new StringBuilder();
        StringBuilder colNamesToDataTypes = new StringBuilder();

        for (Map.Entry<String, String> entry : columnNameToDataTypeMap.entrySet()) {
            colNamesDelimited.append(entry.getKey()).append(",");
            colNamesToDataTypes.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
        }

        //Remove terminating comma
        colNamesDelimited.deleteCharAt(colNamesDelimited.lastIndexOf(","));
        colNamesToDataTypes.deleteCharAt(colNamesToDataTypes.lastIndexOf(","));

        return String.format("insert into %s select %s from input('%s')", tableName, colNamesDelimited, colNamesToDataTypes);
    }
}
