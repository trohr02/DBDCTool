package org.tomasrohr.dbdc

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomasrohr.dbdc.core.SchemaObjectName
import org.tomasrohr.dbdc.core.teradata.TdDataCatalog
import org.tomasrohr.dbdc.excp.TableNotFoundException
import org.tomasrohr.dbdc.dbi.DBConnectionManager
import org.tomasrohr.dbdc.dbi.DBSession
import java.io.File
import java.io.IOException
import java.net.URLClassLoader
import java.sql.Connection
import java.sql.Driver
import java.sql.SQLException


object Main {
    private val log: Logger = LoggerFactory.getLogger(Main::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        log.info("Hello!")
        log.info("CWD is \"{}\"", System.getProperty("user.dir"))
        DBConnectionManager.listDrivers()

/*
        try(Connection c = ConnectionManager.getConnection(
                ConnectionManager.JdbcUrl.ORACLE.get("localhost","1521", "XE"),
                "system",
                "oracle" )) {
*/
        val url: String = DBConnectionManager.JdbcUrl.TERADATA.get("localhost", "7025")
        log.info("JDBC URL: {}", url)
        try {
/*            val c: Connection? = DBConnectionManager.getConnection(
                    url,
                    "dbc",
                    "dbc")

 */
            val driverPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "lib" + System.getProperty("file.separator") + "terajdbc4.jar"
            log.info("Driver file: {}",File(driverPath))
            val urls = arrayOf(File(driverPath).toURI().toURL());

            val classLoader = URLClassLoader(urls)
            val drvClass = classLoader.loadClass("com.teradata.jdbc.TeraDriver")
            val jdbcDriver: java.sql.Driver = drvClass.newInstance() as Driver
            if ( jdbcDriver == null )
                    log.info("jdbc driver  is null")
            val c: Connection? = DBConnectionManager.getConnection(
                    jdbcDriver,
                    url,
                    "dbc",
                    "dbc")

            if ( c != null ) {
                log.info("Connected")
                val dc = TdDataCatalog(DBSession(c) )
                dc.loadTables()
                log.info("loadTables() ran")
                log.info("Number of tables loaded: {}", dc.allTables.keys.size)

                for( o: SchemaObjectName in dc.allTables.keys ) {
                    println(o.toString());
                }
                //println(dc.getTable(SchemaObjectName.of("DBT_SPRINT_D.T_SALES_FUNCTION_MASTER")).toString())
            }
        } catch (e: SQLException) {

            log.error("Connection failed")
           e.printStackTrace()
        } catch (ioe: IOException) {
            log.error("IOException: ", ioe)
        } catch (tnf: TableNotFoundException) {
            log.error("Table not found", tnf)
        }
    }
}