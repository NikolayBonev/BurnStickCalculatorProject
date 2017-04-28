package readers;
import java.util.List;

import domain.*;

/*
 * The interface has to be implemented from all the parameter readers for compatible.
 */

public interface ParametersReader {
	public abstract boolean read(String url, List<Point> points, List<Stick> sticks);
}
