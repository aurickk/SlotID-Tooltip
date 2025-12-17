package clickslot.id.tooltip;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlotIDTooltipClient implements ClientModInitializer {
	public static final String MOD_ID = "slotid-tooltip";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("SlotID Tooltip mod loaded - hover over inventory slots to see their ID!");
	}
}
