package org.tomasrohr.dbdc.core.teradata

import com.sun.java.swing.plaf.motif.MotifRadioButtonMenuItemUI
import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.tomasrohr.dbdc.Main
import org.tomasrohr.dbdc.core.*
import org.tomasrohr.dbdc.core.bare.BareNamedObject
import org.tomasrohr.dbdc.core.bare.DCO_EMPTY
import org.tomasrohr.dbdc.core.bare.SQLQueryExecutor
import org.tomasrohr.dbdc.dbi.DBSession
import org.tomasrohr.dbdc.excp.DBDCException
import java.io.FileInputStream
import java.util.*

const val KEY_LOAD_TABLES_QUERY: String = "LoadTablesQuery" ;
const val PROPERTY_FILE_NAME: String = "dc-teradata.properties";

class TdDataCatalog (
        private val dbSession: DBSession
) : DataCatalog {
    private val log: Logger = LoggerFactory.getLogger(Main::class.java)

    val TABLES_RESULTSET_COLUMNS: List<String> = listOf(
            "db_name", "table_name", "gt_flag",
            "column_name", "data_type", "char_length", "data_precision", "data_scale", "character_set",
            "nullable", "default_value", "internal_data_type", "upi_column_num", "usi_column_num")
    val TABLES_RESULTSET_COLUMN_CLASSES: List<String> = listOf(
            "java.lang.String", "java.lang.String", "int",
            "java.lang.String", "java.lang.String", "int", "int", "int", "java.lang.String",
            "int", "java.lang.String", "java.lang.String", "int", "int")

    private val props: Properties = Properties()


    init {
        props.load(FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "dc-teradata.properties"))
    }

    private var myAllDbs: MutableList<String> = mutableListOf()
    private val myAllTables: MutableMap<SchemaObjectName, DCTable> = mutableMapOf()


    override val allSchemas: List<String> = myAllDbs
    override val allDbs: List<String> = myAllDbs
    override val allTables: Map<SchemaObjectName, DCTable> = myAllTables


    override fun getTable(schema: String, name: String): DCTable {
        TODO("Not yet implemented")
    }

    override fun getTable(schemaObjectName: SchemaObjectName): DCTable {
        TODO("Not yet implemented")
    }

    override fun getTable(schemaObjectString: String): DCTable {
        TODO("Not yet implemented")
    }

    /**
     * Check that retrieved DC metadata data set about tables is as expected
     * @throws DBDCException
     */
    private fun verifyResultSetSchema(metadata: java.sql.ResultSetMetaData, columnNames: List<String>, columnClasses: List<String>) {
        for (i in 1..metadata.getColumnCount() ) {
            if (metadata.getColumnName(i) != columnNames[i])
                throw DBDCException("Retrieved DC metadata data set about tables does not have same columns as expected." +
                        " Expected '${metadata.getColumnName(i)}', got '${columnNames[i]}'" +
                        " Please, fix $TABLES_RESULTSET_COLUMNS in property file")
            if ( metadata.getColumnClassName(i) != columnClasses[i] )
                throw DBDCException("Retrieved DC metadata data set about tables does not have same columns datatypes as expected." +
                        " Expected '${metadata.getColumnClassName(i)}', got '${columnClasses[i]}'" +
                        " Please, fix $TABLES_RESULTSET_COLUMN_CLASSES in property file")
        }
    }

    /**
     *  Run DB query to retrieve all tables and columns and load them into internal data catalog
     *
     *  @throws SQLQueryExecutor
     */
    override fun loadTables() {
        if ( ! props.containsKey(KEY_LOAD_TABLES_QUERY) )
            throw DBDCException("Key $KEY_LOAD_TABLES_QUERY not found in property file $PROPERTY_FILE_NAME")
        val queryStr = props.getProperty(KEY_LOAD_TABLES_QUERY)

        var lastDbName: String = ""
        var lastTableName: String = ""
        var tableColumns: MutableMap<String, DCColumn> = mutableMapOf();

        // this will not throw SQLException
        val query: SQLQueryExecutor = dbSession.query(queryStr) { resultSet ->
            run {
                // compare resultSet schema
                //verifyResultSetSchema(resultSet.metaData, TABLES_RESULTSET_COLUMNS, TABLES_RESULTSET_COLUMN_CLASSES)
                val dbName = resultSet.getString(1)
                val tableName = resultSet.getString(2)
                var table: DCTable
                var db: DCNamedObject = DCO_EMPTY;

                val flagGT = resultSet.getInt(3)
                val isGT = flagGT != null && flagGT == 1
                val columnName = resultSet.getString(4)
                val dataType = resultSet.getString(5)
                val length = resultSet.getInt(6)
                val precision = resultSet.getInt(7)
                val scale = resultSet.getInt(8)
                val characterSet = resultSet.getString(9)
                val isUnicode = characterSet != null && "UNICODE".equals(characterSet)
                val flagNullable = resultSet.getInt(10)
                val isNotNull = flagNullable != null && flagNullable == 0
                    val defaultValue = resultSet.getString(11)
                val internalDataType = resultSet.getString(12)
                val upiColumnNum = resultSet.getInt(13)
                val usiColumnNum = resultSet.getInt(14)

                log.info("{}  {}", tableName, columnName)

                val column = TdColumn(columnName, dataType, length, precision, scale, isNotNull, defaultValue, internalDataType, isUnicode);
                tableColumns[columnName] = column

                if (dbName != lastDbName || tableName != lastTableName) {
                    if (dbName != lastDbName) {
                        myAllDbs.add(dbName)
                        db = BareNamedObject(dbName, null)
                        lastDbName = dbName
                        lastTableName = tableName
                    }
                    if (tableName != lastTableName ) {
                        lastTableName = tableName
                        table = TdTable(tableName, db, db, tableColumns, isGT)
                        tableColumns = mutableMapOf();
                        log.info("-------- {} {} --------",dbName, tableName)
                        myAllTables.put(SchemaObjectName.of(dbName, tableName), table)
                    }


                }
            }
        }
        // this may throw
        query.go();
        log.info("Number of tables loaded myAllTables: {}", myAllTables.keys.size)
    }

}