package org.tomasrohr.dbdc.core.bare

import org.tomasrohr.dbdc.core.IRecordChewer
import java.sql.SQLException

class SQLQueryExecutor(val conn: java.sql.Connection, val queryStr: String, val recordChewer: (java.sql.ResultSet) -> Unit ) {

    fun go()  {
        val stmt: java.sql.Statement  = conn.createStatement()
        stmt.setQueryTimeout(30)
        try {
            stmt.executeQuery(queryStr).use { resultSet ->
                while (resultSet.next()) {
                    recordChewer(resultSet)
                }
            }
        } catch (sq: SQLException) {
            throw sq
        } finally {
        }
    }
}