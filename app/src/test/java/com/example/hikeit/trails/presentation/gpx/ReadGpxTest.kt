package com.example.hikeit.trails.presentation.gpx

import com.example.hikeit.core.data.xml.Bounds
import com.example.hikeit.core.data.xml.GpxParsingException
import com.example.hikeit.core.data.xml.Metadata
import com.example.hikeit.core.data.xml.Point
import com.example.hikeit.core.data.xml.Route
import com.example.hikeit.core.data.xml.Trail
import com.example.hikeit.core.data.xml.parseGpxFile
import com.gitlab.mvysny.konsumexml.konsumeXml
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ReadGpxTest {

    @Test
    fun `when parsing correct gpx expect Route`() {
        val validGpx = """
    <?xml version="1.0" encoding="UTF-8"?><gpx creator="" xmlns="http://www.topografix.com/GPX/1/1" version="1.1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd">
        <metadata>
            <link href="">
              <text></text>
            </link>
            <time>2024-10-31T08:55:03Z</time>
            <bounds maxlat="49.3" maxlon="20.1" minlat="49.3" minlon="20"/>
        </metadata>

        <trk>
          <name>Schronisko PTTK przy Morskim Oku – Palenica Białczańska</name>
          <link href="">
            <text>Schronisko PTTK przy Morskim Oku – Palenica Białczańska</text>
          </link>
          <trkseg>
            <trkpt lat="49" lon="20">
              <ele>1406</ele>
              <time>2024-10-31T08:55:03Z</time>
            </trkpt>
            <trkpt lat="49.2" lon="21">
              <ele>1403</ele>
              <time>2024-10-31T08:55:27Z</time>
            </trkpt>
            <trkpt lat="49.3" lon="22">
              <ele>1401</ele>
              <time>2024-10-31T08:55:42Z</time>
            </trkpt>
            <trkpt lat="49.4" lon="23">
              <ele>1400</ele>
              <time>2024-10-31T08:55:50Z</time>
            </trkpt>
          </trkseg>
        </trk>
    </gpx>
        """.trimIndent()

        val expectedRoute = Route(
            metadata = Metadata(
                bounds = Bounds(49.3, 20.1, 49.3, 20.0)
            ),
            trail = Trail(
                points = listOf(
                    Point(49.0, 20.0, 1406),
                    Point(49.2, 21.0, 1403),
                    Point(49.3, 22.0, 1401),
                    Point(49.4, 23.0, 1400)
                )
            )
        )

        val route = validGpx.konsumeXml().child("gpx") { Route.xml(this) }

        assertEquals(expectedRoute, route)
    }

    @Test
    fun `when incorrect gpx throw GpxParsingException`() {
        val invalidGpx = "<gpx></gpx>"

        val exception = assertThrows(
            GpxParsingException::class.java,
        ) {
            invalidGpx.parseGpxFile()
        }

        assertEquals("Error occurred during parsing GPX file!", exception.message)
    }

    @Test
    fun `when empty gpx throw GpxParsingException`() {
        val emptyGpx = ""

        val exception = assertThrows(
            GpxParsingException::class.java,
        ) {
            emptyGpx.parseGpxFile()
        }

        assertEquals("GPX file is empty!", exception.message)
    }
}