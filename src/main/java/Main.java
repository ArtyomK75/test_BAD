import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        String fromFileName;
        try {
            fromFileName = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FounderMaxValue founderMaxValue = new FounderMaxValue();
        FounderMinValue founderMinValue = new FounderMinValue();
        FounderMedianAndMiddleValue founderMedianAndMiddleValue = new FounderMedianAndMiddleValue();
        FounderMaxSequenceAscValues founderMaxSequenceAscValuesValue = new FounderMaxSequenceAscValues();
        FounderMinSequenceDescValues founderMinSequenceDescValuesValue = new FounderMinSequenceDescValues();


        try (Stream<String> stream = Files.lines(Paths.get(fromFileName))) {
            stream.forEach(s -> {int value = Integer.parseInt(s);
                founderMaxValue.handle(value);
                founderMinValue.handle(value);
                founderMedianAndMiddleValue.handle(value);
                founderMaxSequenceAscValuesValue.handle(value);
                founderMinSequenceDescValuesValue.handle(value);});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("Max value is %d%n", founderMaxValue.getValue());
        System.out.printf("Min value is %d%n", founderMinValue.getValue());
        System.out.printf("Median value is %d%n", founderMedianAndMiddleValue.getMedianValue());
        System.out.printf("Middle value is %d%n", founderMedianAndMiddleValue.getMiddleValue());
        System.out.println("Max ascending sequence: " + founderMaxSequenceAscValuesValue.getValues());
        System.out.println("Max descending sequence: " + founderMinSequenceDescValuesValue.getValues());
    }

    private static class FounderMaxValue {
        private int value = Integer.MIN_VALUE;

        public void handle(int nextValue) {
            value = Math.max(value, nextValue);
        }

        public int getValue() {
            return value;
        }
    }

    private static class FounderMinValue {
        private int value = Integer.MAX_VALUE;

        public void handle(int nextValue) {
            value = Math.min(value, nextValue);
        }

        public int getValue() {
            return value;
        }
    }

    private static class FounderMedianAndMiddleValue {
        private final List<Integer> values = new ArrayList<>();
        private boolean isSorted;

        public void handle(int nextValue) {
            values.add(nextValue);
        }

        public int getMedianValue() {
            sortList();
            int midIndex = values.size() / 2 - 1;
            if (values.size() % 2 == 0) {
                return (values.get(midIndex) + values.get(midIndex + 1)) / 2;
            }
            return values.get(midIndex + 1);
        }

        public long getMiddleValue() {
            sortList();
            int index = values.size();
            if (index == 0) {
                return 0;
            }
            return values.stream()
                    .mapToLong(Long::valueOf)
                    .sum() / index;
        }

        private void sortList() {
            if(!isSorted) {
                Collections.sort(values);
                isSorted = true;
            }
        }
    }

    private static class FounderMaxSequenceAscValues {
        boolean isSequenceFinished;
        int currentValue = Integer.MIN_VALUE;
        List<Integer> mainList = new ArrayList<>();
        List<Integer> currentList = new ArrayList<>();

        public void handle(int nextValue) {
            isSequenceFinished = nextValue < currentValue;
            if (isSequenceFinished) {
                if (mainList.size() < currentList.size()) {
                    mainList.clear();
                    mainList.addAll(currentList);
                }
                currentList.clear();
            }
            currentList.add(nextValue);
            currentValue = nextValue;
        }

        public List<Integer> getValues() {
            if (mainList.size() < currentList.size()) {
                return currentList;
            }
            return  mainList;
        }
    }

    private static class FounderMinSequenceDescValues {
        boolean isSequenceFinished;
        int currentValue = Integer.MAX_VALUE;
        List<Integer> mainList = new ArrayList<>();
        List<Integer> currentList = new ArrayList<>();

        public void handle(int nextValue) {
            isSequenceFinished = nextValue > currentValue;
            if (isSequenceFinished) {
                if (mainList.size() < currentList.size()) {
                    mainList.clear();
                    mainList.addAll(currentList);
                }
                currentList.clear();
            }
            currentList.add(nextValue);
            currentValue = nextValue;
        }

        public List<Integer> getValues() {
            if (mainList.size() < currentList.size()) {
                return currentList;
            }
            return  mainList;
        }
    }

}
