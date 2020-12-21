package com.oneeyedmen.tableReader

fun readTableWithHeader(lines: List<String>): List<Map<String, String>> =
    readTableWithHeader(lines.asSequence()).toList()

fun readTableWithHeader(lines: Sequence<String>): Sequence<Map<String, String>> {
    val (head, tail) = lines.destruct() ?: return emptySequence()
    return readTable(head, tail)
}

fun readTable(header: String, lines: List<String>): List<Map<String, String>> =
    readTable(lines, header.toHeaderProvider())

fun readTable(header: String, lines: Sequence<String>): Sequence<Map<String, String>> =
    readTable(lines, header.toHeaderProvider())

fun readTable(
    lines: List<String>,
    headerProvider: (Int) -> String = Integer::toString
): List<Map<String, String>> =
    readTable(lines.asSequence(), headerProvider).toList()

fun readTable(
    lines: Sequence<String>,
    headerProvider: (Int) -> String = Integer::toString
): Sequence<Map<String, String>> =
    lines.map { readLine(it, headerProvider) }

private fun String.toHeaderProvider(): (Int) -> String =
    splitFields(",").let { fields ->
        { index ->
            if (index > fields.size - 1)
                index.toString()
            else
                fields[index]
        }
    }

private fun readLine(content: String, headerProvider: (Int) -> String): Map<String, String> {
    val fields: List<String> = content.splitFields(",")
    val headers = (0..fields.size).map(headerProvider)
    return headers.zip(fields).toMap()
}

private fun String.splitFields(delimiters: String): List<String> =
    split(delimiters).let { parts ->
        if (parts.size == 1 && parts.first().isEmpty())
            emptyList()
        else parts
    }

private fun <T> Sequence<T>.destruct(): Pair<T, Sequence<T>>? {
    val iterator = this.iterator()
    return if (!iterator.hasNext())
        null
    else
        iterator.next() to Sequence { iterator }
}