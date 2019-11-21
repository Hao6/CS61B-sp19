import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    List<Bear> bears;
    List<Bed> beds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        this.bears = bears;
        this.beds = beds;
    }

    private static Bear getRandomBear(List<Bear> b) {
        return b.get(StdRandom.uniform(0, b.size()));
    }
    private static Bed getRandomBed(List<Bed> b) {
        return b.get(StdRandom.uniform(0, b.size()));
    }

    private static List<Bear> connect(List<Bear> b1, List<Bear> b2) {
        List<Bear> resList = new ArrayList<>(b1.size() + b2.size());
        for (int i = 0; i < b1.size(); i += 1) {
            resList.add(b1.get(i));
        }
        for (int i = 0; i < b2.size(); i += 1) {
            resList.add(b2.get(i));
        }
        b1 = null;
        b2 = null;
        return resList;
    }

    private static List<Bed> connect1(List<Bed> b1, List<Bed> b2) {
        List<Bed> resList = new ArrayList<>(b1.size() + b2.size());
        for (int i = 0; i < b1.size(); i += 1) {
            resList.add(b1.get(i));
        }
        for (int i = 0; i < b2.size(); i += 1) {
            resList.add(b2.get(i));
        }
        b1 = null;
        b2 = null;
        return resList;
    }

    private static List<Bear> mergeSolved(List<Bear> bears, List<Bed> beds) {
        if (bears.size() <= 1) {
            return bears;
        }
        List<Bear> lessBears = new ArrayList<>();
        List<Bear> greaterBears = new ArrayList<>();
        List<Bear> equalBears = new ArrayList<>();
        Bed pivotBed = getRandomBed(beds);
        for (Bear bear : bears) {
            if (pivotBed.compareTo(bear) > 0) {
                lessBears.add(bear);
            } else if (pivotBed.compareTo(bear) < 0) {
                greaterBears.add(bear);
            } else {
                equalBears.add(bear);
            }
        }
        Bear pivotBear = equalBears.get(0);
        List<Bed> lessBeds = new ArrayList<>();
        List<Bed> greaterBeds = new ArrayList<>();
        List<Bed> equalBeds = new ArrayList<>();
        for (Bed bed : beds) {
            if (pivotBear.compareTo(bed) > 0) {
                lessBeds.add(bed);
            } else if (pivotBear.compareTo(bed) < 0) {
                greaterBeds.add(bed);
            } else {
                equalBeds.add(bed);
            }
        }
        return connect(connect(mergeSolved(lessBears, lessBeds), equalBears),
                mergeSolved(greaterBears, greaterBeds));
    }

    private static List<Bed> mergeSolved1(List<Bed> beds, List<Bear> bears) {
        if (beds.size() <= 1) {
            return beds;
        }
        Bear pivotBear = getRandomBear(bears);
        List<Bed> lessBeds = new ArrayList<>();
        List<Bed> greaterBeds = new ArrayList<>();
        List<Bed> equalBeds = new ArrayList<>();
        for (Bed bed : beds) {
            if (pivotBear.compareTo(bed) > 0) {
                lessBeds.add(bed);
            } else if (pivotBear.compareTo(bed) < 0) {
                greaterBeds.add(bed);
            } else {
                equalBeds.add(bed);
            }
        }


        List<Bear> lessBears = new ArrayList<>();
        List<Bear> greaterBears = new ArrayList<>();
        List<Bear> equalBears = new ArrayList<>();
        Bed pivotBed = equalBeds.get(0);
        for (Bear bear : bears) {
            if (pivotBed.compareTo(bear) > 0) {
                lessBears.add(bear);
            } else if (pivotBed.compareTo(bear) < 0) {
                greaterBears.add(bear);
            } else {
                equalBears.add(bear);
            }
        }

        return connect1(connect1(mergeSolved1(lessBeds, lessBears), equalBeds),
                mergeSolved1(greaterBeds, greaterBears));
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return mergeSolved(bears, beds);
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return mergeSolved1(beds, bears);
    }
}
