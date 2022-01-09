package org.tomasrohr.dbdc.dbi

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object DBConnectionManager {
    private var conn: Connection? = null
    val logger: Logger = LoggerFactory.getLogger(DBConnectionManager::class.java)
    fun listDrivers() {
        val e: Enumeration<*> = DriverManager.getDrivers()
        while (e.hasMoreElements()) {
            println(e.nextElement())
        }
    }

    @Throws(SQLException::class)
    fun getConnection(jdbcUrl: String?, username: String, password: String): Connection? {
        if (conn != null) return conn
        val connProps = Properties()
        connProps["user"] = username
        connProps["password"] = password
        conn = DriverManager.getConnection(jdbcUrl, connProps)
        /* SQLException, SQLTimeoutException*/if (conn == null) {
            // to by se nemelo stat
            assert(conn == null) { "conn should not be null here, exception should have been thrown" }
        } else {
            logger.debug("Connected")
        }
        return conn
    }

    @Throws(SQLException::class)
    fun getConnection(jdbcDriver: java.sql.Driver, jdbcUrl: String?, username: String, password: String): Connection? {
        if (conn != null) return conn
        val connProps = Properties()
        connProps["user"] = username
        connProps["password"] = password
        conn = jdbcDriver.connect(jdbcUrl, connProps)
        /* SQLException, SQLTimeoutException*/if (conn == null) {
            // to by se nemelo stat
            assert(conn == null) { "conn should not be null here, exception should have been thrown" }
        } else {
            logger.debug("Connected")
        }
        return conn
    }


    enum class JdbcUrl(// /DATABASE=dbt_sprint_d,DBS_PORT=1025,USEXVIEWS=ON
            private val url: String) {
        ORACLE("jdbc:oracle:thin:@"),  // localhost:1521:xe";
        TERADATA("jdbc:teradata://");

        fun get(): String {
            return url
        }

        operator fun get(host: String, port: String, serviceName: String): String {
            return when (this) {
                ORACLE -> "$url$host:$port:$serviceName"
                TERADATA -> "$url$host/DBS_PORT=$port,USEXVIEWS=ON"
                else -> throw AssertionError("Unknown operations $this")
            }
        }

        operator fun get(host: String, port: String): String {
            return when (this) {
                ORACLE -> throw IllegalArgumentException("Missing [serviceName] argument for ORACLE")
                TERADATA -> "$url$host/DBS_PORT=$port,USEXVIEWS=ON"
                else -> throw AssertionError("Unknown operations $this")
            }
        }

    } // end JdbcUrl enum
}
