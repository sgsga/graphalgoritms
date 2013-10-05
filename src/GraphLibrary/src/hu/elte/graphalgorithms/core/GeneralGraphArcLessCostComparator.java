package hu.elte.graphalgorithms.core;
import hu.elte.graphalgorithms.core.GeneralGraphArc;
import java.util.Comparator;

public final class GeneralGraphArcLessCostComparator<K extends GeneralGraphArc> implements Comparator<K>{

        Float epsilon = 0.0f;

        public GeneralGraphArcLessCostComparator() {
        }
        
        
        
        public GeneralGraphArcLessCostComparator(Float epsilon) {
            this.epsilon = epsilon;
        }

        
        @Override
        public int compare(K e, K f) {
            float diff = e.getCost()-f.getCost();
            if (Math.abs(diff) <= epsilon) return 0;
            if (diff < 0) return -1;
            return 1;
        }
        
    }