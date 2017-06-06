package marlon.minecraftai.ai.tools.rate;

import marlon.minecraftai.ai.ItemFilter;
import marlon.minecraftai.ai.path.world.BlockFloatMap;
import net.minecraft.item.ItemStack;

public class FilterRater extends Rater {
	protected final ItemFilter filter;

	public FilterRater(ItemFilter filter, String name, BlockFloatMap values) {
		super(name, values);
		this.filter = filter;
	}

	@Override
	protected boolean isAppleciable(ItemStack item, int forBlockAndMeta) {
		return filter.matches(item);
	}
}