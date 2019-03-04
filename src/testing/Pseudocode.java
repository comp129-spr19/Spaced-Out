/*
 * 
 * // RIP 8 HOURS OF DANILO'S LIFE package testing;
 * 
 * 
 * import java.awt.Color; import java.util.ArrayList;
 * 
 * import acm.graphics.GCompound; import acm.graphics.GLabel;
 * 
 * public class Pseudocode extends GCompound { private static final String[]
 * ALGORITHM = { "PurpleRobot collectPurpleRobot(Dimension ", "", ") {", "\n",
 * "    if (BasePurple > CurrentDimensionColor) {", "\n",
 * "        return (collectPurpleRobot( Dimension ", "", ") + ThisRobot);",
 * "\n", "    } else {", "\n", "        return (ThisRobot);", "\n", "    }",
 * "\n", "}", "\n" }; private ArrayList<GCompound> levelCode = new
 * ArrayList<GCompound>(); private int maxLevels; private double startX; private
 * double startY; private Color textColor; private double labelHeight; private
 * String font;
 * 
 * public Pseudocode(int levels, double x, double y, Color c, String f) {
 * this.maxLevels = levels; this.startX = x; this.startY = y; this.textColor =
 * c; this.font = f;
 * 
 * // initialize function call formatting formatCallCompound(levelCode, levels,
 * x, y);
 * 
 * // initialize function return formatting for (int i = 0; i < levels; i++) {
 * levelCode.add(formatReturnCompound(levelCode, i, x, y)); }
 * 
 * }
 * 
 * public GCompound formatCallCompound(ArrayList<GLabel> labelList, int level,
 * int x, int y) { String tempString = ""; int counter = 1; boolean
 * functionCallVisited = false;
 * 
 * for (int i = 0; i < level; i++) { GCompound tempCompound = new GCompound();
 * 
 * for (int j = 0; j < ALGORITHM.length; j++) { if (ALGORITHM[j].equals("\n")) {
 * GLabel tempLabel = new GLabel(tempString, (double) x, (double) y); //
 * Something need to go here to move the labels down
 * tempLabel.setColor(textColor); labelList.add(tempLabel); tempString = "";
 * counter++; } else if (ALGORITHM[j].equals("") && ) { tempString +=
 * Integer.toString(level) } else {
 * 
 * } } }
 * 
 * return; }
 * 
 * public GCompound formatReturnCompound() { return; }
 * 
 * public GCompound getCode(int level, int state) { return levelCode.get(level *
 * state); } }
 */