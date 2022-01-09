package org.tomasrohr.dbdc.dbi

import org.tomasrohr.dbdc.core.IRecordChewer
import org.tomasrohr.dbdc.core.bare.SQLQueryExecutor

class DBSession(
        private val conn: java.sql.Connection
) {

    fun query(queryStr: String, recordChewer: (java.sql.ResultSet) -> Unit ): SQLQueryExecutor {
        return SQLQueryExecutor(conn, queryStr, recordChewer)
    }
    
}