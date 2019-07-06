# Discord MineSweeper Library

## Import
```xml
<repositories>
    <repository>
    	<id>jitpack.io</id>
        <url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
	    <groupId>com.github.Doc94</groupId>
	    <artifactId>DiscordMinesweeperLib</artifactId>
	    <version>master-SNAPSHOT</version>
	</dependency>
</dependencies>
```

## Use
```java
try {
	int defaultDifficulty = 30;
    int defaultWidht = 10;
    int defaultHeight = 10;
    MinesweeperBoard board = new MinesweeperBoard(defaultDifficulty,defaultWidht,defaultHeight);
    String strGame = board.build(); //The game builded to use in embed description or message of discord.
} catch (MinesweeperBoardException e) {
	//If the values cause any issue with the rules of the Class
}
```