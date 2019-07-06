package me.mrdoc.discord.minesweeper;

import org.jetbrains.annotations.Contract;

/**
 *  @since 1.0
 *  @author NurMarvin
 */
final class Field {
    private final boolean bomb;
    private final int x;
    private final int y;

    /**
     * A field of the {@link MinesweeperBoard}
     * @param bomb Is the field a bomb?
     * @param x The X coordinate of the field
     * @param y The Y coordinate of the field
     */
    Field(boolean bomb, int x, int y) {
        this.bomb = bomb;
        this.x = x;
        this.y = y;
    }

    @Contract(pure = true)
    boolean isBomb() {
        return bomb;
    }

    @Contract(pure = true)
    int getX() {
        return x;
    }

    @Contract(pure = true)
    int getY() {
        return y;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field) {
            Field other = (Field) obj;

            return other.getX() == this.getX() && other.getY() == this.getY() == other.isBomb() == this.isBomb();
        }
        return false;
    }
}
