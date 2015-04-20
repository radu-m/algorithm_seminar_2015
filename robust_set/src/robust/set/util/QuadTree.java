package robust.set.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A quadtree is contains either an ArrayList of data or four other underlying
 * quadtrees.
 * @author stin7054
 */

public class QuadTree  {

    private final int level;
    private final List<Double> position;
    private final double length;
    private List<Point<Double>> Points;
    private HashMap<List<Boolean>,QuadTree> leafQuadTrees;


    public QuadTree(int level , List<Double> position, double length) 
    {
        this.level = level;
        this.position = position;
        this.length = length;
        Points = new ArrayList();

    }
    
    public Boolean insert(Point<Double> p)
    {
        // Ignore objects that do not belong in this quad tree
        //if (!boundary.containsPoint(p)) // is not implemented yet
        //    return false; // object cannot be added

        Points.add(p);
        
        // If this is the last level
        if (level == 0)
          return true;
        
        // Find a identity key for a leafQuadTree
        List<Boolean> key = findKey(p);
        
        // Put p in a leafQuadTree
        if (leafQuadTrees.containsKey(key))
        {
            return leafQuadTrees.get(key).insert(p);
        }
        else
        {
            List<Double> TMPposition = position;
            for (int i = 0; i < p.position.size(); i++)
                if (key.get(i))
                    TMPposition.set(i, TMPposition.get(i) + (Double)(length / 2.0));
            
            leafQuadTrees.put(key, new QuadTree(level - 1,TMPposition,length / 2.0));
            return leafQuadTrees.get(key).insert(p);
        }

        // Otherwise, the point cannot be inserted for some unknown reason (this should never happen)
        //return false;
    }
    
    private List<Boolean> findKey(Point<Double> p)
    {
        List<Boolean> key = new ArrayList();
        for (int i = 0; i < p.position.size(); i++)
            key.add(i, Double.compare(p.position.get(i), position.get(i) + length / (Double) 2.0) != 0);
        return key;
    }
    
    public List<QuadTree> getNotesOnLevel(int level)
    {
        if (this.level == level)
        {
            List<QuadTree> list = new ArrayList();
            list.add(this);
            return list;
        }
        else
        {
            List<QuadTree> list = new ArrayList();
            for (QuadTree qt : leafQuadTrees.values())
                list.addAll(qt.getNotesOnLevel(level));
            return list;
        }
    }
    
}
