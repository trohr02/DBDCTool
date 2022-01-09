package org.tomasrohr.dbdc.core

interface IRecordChewer {
    abstract fun chewRecord(resultSet: java.sql.ResultSet)
}