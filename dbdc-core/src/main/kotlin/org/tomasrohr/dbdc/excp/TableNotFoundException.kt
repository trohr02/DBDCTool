package org.tomasrohr.dbdc.excp

import java.lang.Exception

open class TableNotFoundException(message: String): Exception(message);
