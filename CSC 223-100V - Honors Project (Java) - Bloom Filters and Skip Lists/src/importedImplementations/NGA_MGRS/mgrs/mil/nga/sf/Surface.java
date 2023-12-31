package importedImplementations.NGA_MGRS.mgrs.mil.nga.sf;

/**
 * The base type for all 2-dimensional geometry types. A 2-dimensional geometry
 * is a geometry that has an area.
 * 
 * @author osbornb
 */
public abstract class Surface extends Geometry {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            geometry type
	 * @param hasZ
	 *            has z
	 * @param hasM
	 *            has m
	 */
	protected Surface(GeometryType type, boolean hasZ, boolean hasM) {
		super(type, hasZ, hasM);
	}

}
