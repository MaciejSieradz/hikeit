package com.example.hikeit.core.data.xml

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.KonsumerException
import com.gitlab.mvysny.konsumexml.childInt
import com.gitlab.mvysny.konsumexml.konsumeXml
import com.gitlab.mvysny.konsumexml.xmlDouble

data class Point(val latitude: Double, val longitude: Double, val elevation: Int) {
    companion object {
        fun xml(k: Konsumer): Point {
            k.checkCurrent("trkpt")
            val point = Point(
                latitude = k.attributes.getValue("lat").toDouble(),
                longitude = k.attributes.getValue("lon").toDouble(),
                elevation = k.childInt("ele")
            )
            k.childText("time")
            return point
        }
    }
}

data class Trail(
    val points: List<Point>
) {
    companion object {
        fun xml(k: Konsumer): Trail {
            k.checkCurrent("trk")
            k.childText("name")
            k.child("link") {
                skipContents()
            }
            return Trail(k.child("trkseg") { children("trkpt") { Point.xml(this) } })
        }
    }
}

data class Link(val text: String) {
    companion object {
        fun xml(k: Konsumer): Link {
            k.checkCurrent("link")
            return Link(k.childText("text"))
        }
    }
}

data class Bounds(
    val maxLatitude: Double,
    val maxLongitude: Double,
    val minLatitude: Double,
    val minLongitude: Double
) {
    companion object {
        fun xml(k: Konsumer): Bounds {
            k.checkCurrent("bounds")
            return Bounds(
                maxLatitude = k.attributes["maxlat"].xmlDouble(),
                maxLongitude = k.attributes["maxlon"].xmlDouble(),
                minLatitude = k.attributes["minlat"].xmlDouble(),
                minLongitude = k.attributes["minlon"].xmlDouble()
            )
        }
    }
}

data class Metadata(
    val bounds: Bounds
) {
    companion object {
        fun xml(k: Konsumer): Metadata {
            k.checkCurrent("metadata")
            k.child("link") { Link.xml(this) }
            k.childText("time")
            return Metadata(k.child("bounds") { Bounds.xml(this) })
        }
    }
}

data class Route(val metadata: Metadata, val trail: Trail) {

    companion object {
        fun xml(k: Konsumer): Route {
            k.checkCurrent("gpx")
            return Route(
                k.child("metadata") { Metadata.xml(this) },
                k.child("trk") { Trail.xml(this) })
        }
    }
}

fun String.parseGpxFile() : Route {
    try {
        return konsumeXml().child("gpx") { Route.xml(this) }
    } catch (e: KonsumerException) {
        throw GpxParsingException("Error occurred during parsing GPX file!")
    } catch (e: Exception) {
        throw GpxParsingException("GPX file is empty!")
    }
}