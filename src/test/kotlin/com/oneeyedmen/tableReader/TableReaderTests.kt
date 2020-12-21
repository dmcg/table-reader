package com.oneeyedmen.tableReader

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TableReaderTests {

    @Test
    fun `readTable no header`() {
        assertEquals(
            emptyList(),
            readTable(emptyList())
        )
        assertEquals(
            listOf(mapOf("0" to "field0", "1" to "field1")),
            readTable(listOf("field0,field1"))
        )
        assertEquals(
            listOf(
                mapOf("0" to "row0-field0", "1" to "row0-field1"),
                mapOf("0" to "row1-field0", "1" to "row1-field1")
            ),
            readTable(
                listOf(
                    "row0-field0,row0-field1",
                    "row1-field0,row1-field1"
                )
            )
        )

        assertEquals(
            listOf(mapOf()),
            readTable(listOf(""))
        )
    }

    @Test
    fun `readTable with header`() {
        assertEquals(
            emptyList(),
            readTable(emptyList())
        )
        assertEquals(
            listOf(mapOf("h0" to "field0", "h1" to "field1")),
            readTable(
                "h0,h1",
                listOf("field0,field1")
            )
        )
        assertEquals(
            listOf(
                mapOf("h0" to "row0-field0", "h1" to "row0-field1"),
                mapOf("h0" to "row1-field0", "h1" to "row1-field1", "2" to "row1-field2"),
            ),
            readTable(
                "h0,h1",
                listOf(
                    "row0-field0,row0-field1",
                    "row1-field0,row1-field1,row1-field2"
                )
            )
        )
        assertEquals(
            listOf(mapOf("h0" to "field0", "1" to "field1")),
            readTable(
                "h0",
                listOf("field0,field1")
            )
        )

        assertEquals(
            listOf(mapOf("0" to "field0", "1" to "field1")),
            readTable(
                "",
                listOf("field0,field1")
            )
        )
    }

    @Test
    fun `readTableWithHeader`() {
        assertEquals(
            emptyList(),
            readTableWithHeader(emptyList())
        )
        assertEquals(
            listOf(mapOf("h0" to "field0", "h1" to "field1")),
            readTableWithHeader(
                listOf(
                    "h0,h1",
                    "field0,field1"
                )
            )
        )
        assertEquals(
            listOf(
                mapOf("h0" to "row0-field0", "h1" to "row0-field1"),
                mapOf("h0" to "row1-field0", "h1" to "row1-field1", "2" to "row1-field2"),
            ),
            readTableWithHeader(
                listOf(
                    "h0,h1",
                    "row0-field0,row0-field1",
                    "row1-field0,row1-field1,row1-field2"
                )
            )
        )
    }

    @Test
    fun `sequence readTableWithHeader`() {
        assertEquals(
            emptyList(),
            readTableWithHeader(emptySequence()).toList()
        )
        assertEquals(
            listOf(mapOf("h0" to "field0", "h1" to "field1")),
            readTableWithHeader(
                sequenceOf(
                    "h0,h1",
                    "field0,field1"
                )
            ).toList()
        )
    }
}

