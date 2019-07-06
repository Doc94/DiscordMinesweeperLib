package me.mrdoc.discord.minesweeper;

import me.mrdoc.discord.minesweeper.exception.MinesweeperBoardException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @since 1.0
 * @author NurMarvin, Doc
 * @apiNote This work only in discord, the
 */
public final class MinesweeperBoard {
    private final ArrayList<Field> fields;
    private final int width;
    private final int height;
    private final int difficulty;
    private Field revealedField;

    /**
     * Creates a new board of Minesweeper
     * <br>
     * The
     * @param difficulty Difficulty in percent for bombs (1-100)
     * @param width width of Board
     * @param height height of Board
     * @throws MinesweeperBoardException if the parameters are not valid with the rules of the Board
     */
    public MinesweeperBoard(int difficulty, int width, int height) throws MinesweeperBoardException {
        if(difficulty <= 0 || difficulty > 100) {
            throw new MinesweeperBoardException("Invalid difficulty value (1-100).");
        }
        if(width <= 0) {
            throw new MinesweeperBoardException("Invalid width value (cannot be less than or equal to 0).");
        } else if(height <= 0) {
            throw new MinesweeperBoardException("Invalid height value (cannot be less than or equal to 0).");
        }

        if(width * height > 198) {
            throw new MinesweeperBoardException("Your grid size is too big (width * height are over 198).");
        } else if(width * height <= 4) {
            throw new MinesweeperBoardException("Your grid size is too small (width * height are less than or equal to 4).");
        }

        this.difficulty = difficulty;
        this.height = height;
        this.width = width;
        this.fields = new ArrayList<>();
        this.populate();
    }

    /**
     * Populates the mine field
     */
    private void populate() {
        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                if (ThreadLocalRandom.current().nextInt(100) <= this.difficulty) {
                    this.addField(new Field(true, x, y));
                } else this.addField(new Field(false, x, y));
    }

    /**
     * Adds a {@link Field} to the mine field and can happen to become the initially revealed field
     * @param field The field to add
     */
    private void addField(Field field) {
        if (this.revealedField == null && ThreadLocalRandom.current().nextBoolean() && !field.isBomb())
            this.revealedField = field;
        this.fields.add(field);
    }

    /**
     * Build the board into a message with a spoiler tag for each field
     * @return The board as a discord message
     */
    @NotNull
    public final String build() {
        boolean revealedFieldSet = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : this.fields) {
            if (field.getX() == 0 && field.getY() != 0) stringBuilder.append("\n");

            if (field.isBomb()) {
                stringBuilder.append("||\uD83D\uDCA3||");
                continue;
            }

            if ((!revealedFieldSet && this.revealedField == null) || revealedField.equals(field)) {
                stringBuilder.append(this.getBombsNear(field)).append("⃣");
                revealedFieldSet = true;
            } else stringBuilder.append("||").append(this.getBombsNear(field)).append("⃣").append("||");
        }
        return stringBuilder.toString();
    }

    /**
     * Gets the bombs the near a given {@link Field}. Can be 8 at max.
     * @param field The {@link Field} which should have the amount of bombs around it checked
     * @return The amount of bombs around the {@link Field}
     */

    private int getBombsNear(@NotNull Field field) {
        return this.getBombsNear(field.getX(), field.getY());
    }

    /**
     * Gets the bombs the near the given coordinates. Can be 8 at max.
     * @param x The X coordinate that should be checked
     * @param y The Y coordinate that should be checked
     * @return The amount of bombs around the given coordinates
     */

    private int getBombsNear(int x, int y) {
        int bombs = 0;
        for (int xOffset = -1; xOffset < 2; xOffset++)
            for (int yOffset = -1; yOffset < 2; yOffset++) {
                Field field = this.getFieldAt(x + xOffset, y + yOffset);
                if (field == null) continue;

                if (field.isBomb()) bombs++;
            }
        return bombs;
    }

    /**
     * Gets the {@link Field} at the given coordinates.
     * @param x The X coordinate of the {@link Field}
     * @param y The Y coordinate of the {@link Field}
     * @return The {@link Field} at the given coordinates. May be null.
     */

    @Nullable
    private Field getFieldAt(int x, int y) {
        for (Field field : this.fields) {
            if (field.getX() == x && field.getY() == y) return field;
        }
        return null;
    }
}
