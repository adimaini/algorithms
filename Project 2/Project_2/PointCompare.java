import java.util.*;
import java.io.*;

public class PointCompare implements Comparator<Point> {
	public int compare(Point a, Point b) {
		if (a.x < b.x) {
        return 1;
        }
        else if (a.x > b.x) 
        {
            return -1;
        }
        else if (a.x == b.x)
        {
            if (a.y < b.y)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return 0;
        }
	}
}