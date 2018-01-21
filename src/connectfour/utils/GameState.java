package connectfour.utils;

public class GameState {

    long state;

    public GameState(long state) {
        this.state = state;
    }

    public GameState() {
        this(0L);
    }

    public GameState(GameState state) {
        this(state.getState());
    }

    public long getState() {
        return state;
    }

    public void setColor(int column, int row, boolean color) {
        validateColumn(column);
        validateRow(row);

        long newValue = color ? 1 : 0;
        long positionMask = getPositionMask(column, row);
        changeState(newValue, positionMask);
    }

    public void setColumnHeight(int column, int height) {
        validateColumn(column);
        validateHeight(height);

        long mask = getHeightMask(column);
        changeState(height, mask);
    }

    public boolean getColor(int column, int row) {
        validateColumn(column);
        validateRow(row);

        long mask = getPositionMask(column, row);
        return getStateValueWithMask(mask) > 0;
    }

    public int getHeightOfColumn(int column) {
        validateColumn(column);

        long mask = getHeightMask(column);
        return (int) getStateValueWithMask(mask);
    }

    public void dropColor(boolean color, int column) {
        int columnHeight = getHeightOfColumn(column);

        if (columnHeight > 5) {
            throw new IllegalStateException("Tried to drop color to filled column");
        }

        setColor(column, columnHeight, color);
        setColumnHeight(column, columnHeight + 1);
    }

    private void changeState(long value, long mask) {
        long newBits = fitValueToMask(value, mask);
        state = state & (~mask);
        state = state | newBits;
    }

    private long fitValueToMask(long value, long mask) {
        int maskPosition = positionOfFirstOne(mask);

        long rightShiftedMask = mask >> maskPosition;
        long CroppedValue = rightShiftedMask & value;
        long leftShiftedCroppedValue = CroppedValue << maskPosition;

        return leftShiftedCroppedValue;
    }

    private int positionOfFirstOne(long mask) {
        for (int i = 0; i < 64; i++) {
            if ((mask & (1L << i)) > 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("Mask had no ones.");
    }

    private long getPositionMask(int column, int row) {
        int position = getPosition(column, row);
        long mask = 1L << position;
        return mask;
    }

    private int getPosition(int column, int row) {
        return column * 6 + row;
    }

    private long getHeightMask(int column) {
        long mask = 7;
        return mask << (42 + column * 3);
    }

    private long getStateValueWithMask(long mask) {
        int maskPosition = positionOfFirstOne(mask);
        return (state & mask) >> maskPosition;
    }

    private void validateColumn(int column) {
        if (column < 0 || column >= 7) {
            throw new IllegalArgumentException("Tried to input column number " + column + ", but only columns between 0 and 6 are allowed.");
        }
    }

    private void validateRow(int row) {
        if (row < 0 || row >= 6) {
            throw new IllegalArgumentException("Tried to input row number " + row + ", but only rows between 0 and 5 are allowed.");
        }
    }

    private void validateHeight(int height) {
        if (height < 0 || height >= 7) {
            throw new IllegalArgumentException("Tried to input height number " + height + ", but only heights between 0 and 6 are allowed.");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (this.state ^ (this.state >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameState other = (GameState) obj;
        if (this.state != other.state) {
            return false;
        }
        return true;
    }

}
