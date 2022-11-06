package view;

import dungeon.ItemType;

import java.util.Map;

/**
 * This class exposes the list of all the files that need to be
 *    used by the view of the application.
 */
class FileConstants {
  static final String IMAGE_PATH = "/img";
  static final String OTYUGH_IMG_PATH = "otyugh.png";
  static final String PIT_IMG_PATH = "pit.png";
  static final String THIEF_IMG_PATH = "thief.png";
  static final String PLAYER_IMG_PATH = "player.png";
  static final String BLANK_IMG_PATH = "blank.png";
  static final String BLACK_ARROW = "arrow-black.png";

  static final Map<ItemType, String> ITEM_TO_FILE_ICON_MAPPING = Map.ofEntries(
          Map.entry(ItemType.RUBY, "ruby.png"),
          Map.entry(ItemType.DIAMOND, "diamond.png"),
          Map.entry(ItemType.ARROW, "arrow-white.png"),
          Map.entry(ItemType.SAPPHIRE, "emerald.png")
  );

  static final Map<String, String> STENCH_FILE_MAPPING = Map.ofEntries(
          Map.entry("light", "stench01.png"),
          Map.entry("strong", "stench02.png")
  );

  static final Map<String, String> CAVE_TO_ICON_MAPPING = Map.ofEntries(
          Map.entry("nsew", "color-cells/NSEW.png"),
          Map.entry("nse", "color-cells/NSE.png"),
          Map.entry("nsw", "color-cells/NSW.png"),
          Map.entry("new", "color-cells/NEW.png"),
          Map.entry("sew", "color-cells/SEW.png"),
          Map.entry("n", "color-cells/N.png"),
          Map.entry("s", "color-cells/S.png"),
          Map.entry("e", "color-cells/E.png"),
          Map.entry("w", "color-cells/W.png")
  );

  static final Map<String, String> TUNNEL_TO_ICON_MAPPING = Map.ofEntries(
          Map.entry("ew", "color-cells/EW.png"),
          Map.entry("ns", "color-cells/NS.png"),
          Map.entry("sw", "color-cells/SW.png"),
          Map.entry("nw", "color-cells/NW.png"),
          Map.entry("ne", "color-cells/NE.png"),
          Map.entry("se", "color-cells/SE.png")
  );
}
