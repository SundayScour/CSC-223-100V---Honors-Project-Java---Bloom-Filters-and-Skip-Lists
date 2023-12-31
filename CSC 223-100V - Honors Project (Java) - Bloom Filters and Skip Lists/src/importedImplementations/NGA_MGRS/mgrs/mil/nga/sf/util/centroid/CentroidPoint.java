package importedImplementations.NGA_MGRS.mgrs.mil.nga.sf.util.centroid;

import java.util.List;

import importedImplementations.NGA_MGRS.mgrs.mil.nga.sf.Geometry;
import importedImplementations.NGA_MGRS.mgrs.mil.nga.sf.GeometryCollection;
import importedImplementations.NGA_MGRS.mgrs.mil.nga.sf.GeometryType;
import importedImplementations.NGA_MGRS.mgrs.mil.nga.sf.MultiPoint;
import importedImplementations.NGA_MGRS.mgrs.mil.nga.sf.Point;
import importedImplementations.NGA_MGRS.mgrs.mil.nga.sf.util.SFException;

/**
 * Calculate the centroid from point based geometries. Implementation based on
 * the JTS (Java Topology Suite) CentroidPoint.
 * 
 * @author osbornb
 * @since 1.0.3
 */
public class CentroidPoint {

	/**
	 * Point count
	 */
	private int count = 0;

	/**
	 * Sum of point locations
	 */
	private Point sum = new Point();

	/**
	 * Constructor
	 */
	public CentroidPoint() {

	}

	/**
	 * Constructor
	 * 
	 * @param geometry
	 *            geometry to add
	 */
	public CentroidPoint(Geometry geometry) {
		add(geometry);
	}

	/**
	 * Add a point based dimension 0 geometry to the centroid total
	 * 
	 * @param geometry
	 *            geometry
	 */
	public void add(Geometry geometry) {
		GeometryType geometryType = geometry.getGeometryType();
		switch (geometryType) {
		case POINT:
			add((Point) geometry);
			break;
		case MULTIPOINT:
			MultiPoint multiPoint = (MultiPoint) geometry;
			for (Point point : multiPoint.getPoints()) {
				add(point);
			}
			break;
		case GEOMETRYCOLLECTION:
		case MULTICURVE:
		case MULTISURFACE:
			@SuppressWarnings("unchecked")
			GeometryCollection<Geometry> geomCollection = (GeometryCollection<Geometry>) geometry;
			List<Geometry> geometries = geomCollection.getGeometries();
			for (Geometry subGeometry : geometries) {
				add(subGeometry);
			}
			break;
		default:
			throw new SFException("Unsupported "
					+ this.getClass().getSimpleName() + " Geometry Type: "
					+ geometryType);
		}
	}

	/**
	 * Add a point to the centroid total
	 * 
	 * @param point
	 *            point
	 */
	private void add(Point point) {
		count++;
		sum.setX(sum.getX() + point.getX());
		sum.setY(sum.getY() + point.getY());
	}

	/**
	 * Get the centroid point
	 * 
	 * @return centroid point
	 */
	public Point getCentroid() {
		Point centroid = new Point(sum.getX() / count, sum.getY() / count);
		return centroid;
	}

}
