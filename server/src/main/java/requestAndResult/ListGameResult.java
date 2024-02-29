package requestAndResult;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public record ListGameResult(Collection<GameData> games, String message) {
}
