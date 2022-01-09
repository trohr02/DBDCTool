package org.tomasrohr.dbdc.core.bare

import lombok.Getter
import lombok.Setter
import org.tomasrohr.dbdc.core.DCTable
import org.tomasrohr.dbdc.core.DataCatalog
import org.tomasrohr.dbdc.core.Protocoder
import org.tomasrohr.dbdc.excp.TableNotFoundException

open class BareProtocoder(private val dataCatalog: DataCatalog) : Protocoder {
    private val currentSchema: String? = null
    protected val columnSep = System.lineSeparator() + "  ,"
    protected val tableSep = System.lineSeparator() + "  "

    var maxWidth: Int

    private var indent: String? = null

    private var NL: String? = null

    private var leadingComma: String? = null

    private var trailingComma: String? = null

    private val commaInFront: Boolean

    private val queryEnd: String

    fun getIndent() = indent

    fun setIndent(indent: String) {
        this.indent = indent
        leadingComma = "$indent,"
    }

    fun getNL() = NL

    fun setNL(NL: String) {
        this.NL = " $NL"
        trailingComma = ",$NL"
    }

    fun concatArray(a: Array<String>, sep: String?, lineLength: Int): String {
        val sb = StringBuilder(300)
        var i = 1
        for (col in a) {
            sb.append(col)
            if (i++ < a.size) if (sb.length + a[i].length > lineLength) sb.append(",").append(NL) else sb.append(columnSep)
        }
        return sb.toString()
    }

    @Throws(TableNotFoundException::class)
    override fun columnList(schemaObjectStr: String): String {
        val dcTable = dataCatalog.getTable(schemaObjectStr) ?: throw TableNotFoundException(schemaObjectStr)
        return columnList(dcTable)
    }

    @Throws(TableNotFoundException::class)
    fun columnList(dcTable: DCTable): String {
        val sb = StringBuilder(300)
        var i = 1
        if (commaInFront) for (nm in dcTable.columnNames ) {
            if (i++ > 1) sb.append(leadingComma) else sb.append(indent).append(" ")
            sb.append(nm).append(NL)
        } else {
            val cntColumns: Int = dcTable.columnNames.size
            for (nm in dcTable.columnNames ) {
                sb.append(nm)
                if (i++ < cntColumns) sb.append(trailingComma)
            }
        }
        return sb.toString()
    }

    @Throws(TableNotFoundException::class)
    override fun columnListCompact(schemaObjectStr: String): String {
        return columnList(dataCatalog.getTable(schemaObjectStr))
    }

    fun columnListOneLine(dcTable: DCTable): String {
        val sb = StringBuilder(300)
        val cntColumns: Int = dcTable.columnNames.size
        var i = 1
        for (nm in dcTable.columnNames) {
            sb.append(nm)
            if (i++ < cntColumns) sb.append(", ")
        }
        return sb.toString()
    }

    @Throws(TableNotFoundException::class)
    override fun selectFrom(schemaObjectStr: String): String {
        return selectFrom(dataCatalog.getTable(schemaObjectStr))
    }

    @Throws(TableNotFoundException::class)
    fun selectFrom(dcTable: DCTable): String {
        val sb = StringBuilder(400)
        sb.append(dcTable.ql())
        //        .append(getQueryEnd());
        return sb.toString()
    }

    @Throws(TableNotFoundException::class)
    fun selectColumnsFrom(columns: Array<String>, dcTable: DCTable): String {
        val sb = StringBuilder(400)
        sb.append("SELECT")
                .append(NL)
                .append(concatArray(columns, columnSep, maxWidth))
                .append(NL)
                .append("FROM ")
                .append(dcTable.ql())
        //                .append(getQueryEnd());
        return sb.toString()
    }

    init {
        setIndent("  ")
        setNL(System.lineSeparator())
        commaInFront = true
        maxWidth = 120
        queryEnd = ";" + System.lineSeparator()
    }

}