package connectfour.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Juuso
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    
    private int maxSize;

    public LRUCache(int maxSize) {
        super(maxSize * 4 / 3, 0.75f, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
