package com.ssafy.enjoytrip.infrastructure.overpass.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.ssafy.enjoytrip.infrastructure.overpass.util.OverpassUtil.toCoordinateArray;

public class OverpassGeometryHelper {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static Geometry createPolygonFromRelationMembers(ArrayNode members) {
        List<Geometry> segments = new ArrayList<>();

        for (JsonNode member : members) {
            if (!"outer".equals(member.path("role").asText())) continue;

            ArrayNode geometry = (ArrayNode) member.get("geometry");
            if (geometry == null || geometry.size() < 2) continue;

            segments.add(geometryFactory.createLineString(toCoordinateArray(geometry)));
        }

        org.locationtech.jts.operation.polygonize.Polygonizer polygonizer = new org.locationtech.jts.operation.polygonize.Polygonizer();
        polygonizer.add(segments);

        Collection<Polygon> polygons = polygonizer.getPolygons();
        if (polygons.isEmpty()) return null;

        return polygons.size() == 1
                ? polygons.iterator().next()
                : geometryFactory.createMultiPolygon(polygons.toArray(new Polygon[0]));
    }

    public static Geometry createPolygonFromWayGeometry(ArrayNode geometry) {
        if (geometry.size() < 4) return null;

        Coordinate[] coords = Arrays.copyOf(toCoordinateArray(geometry), geometry.size() + 1);
        coords[geometry.size()] = coords[0];

        LinearRing shell = geometryFactory.createLinearRing(coords);
        return geometryFactory.createPolygon(shell);
    }
}
