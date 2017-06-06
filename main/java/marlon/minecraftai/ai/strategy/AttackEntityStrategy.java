package marlon.minecraftai.ai.strategy;

import java.util.HashSet;

import com.google.common.base.Predicate;

import marlon.minecraftai.ai.AIHelper;
import marlon.minecraftai.ai.ItemFilter;
import marlon.minecraftai.ai.animals.AnimalyType;
import marlon.minecraftai.ai.selectors.AndSelector;
import marlon.minecraftai.ai.selectors.ColorSelector;
import marlon.minecraftai.ai.selectors.ItemSelector;
import marlon.minecraftai.ai.selectors.NotSelector;
import marlon.minecraftai.ai.selectors.OrSelector;
import marlon.minecraftai.ai.selectors.OwnTameableSelector;
import marlon.minecraftai.ai.selectors.XPOrbSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AttackEntityStrategy extends FaceInteractStrategy{ //Added Marlon.C
	
	private final class KillableSelector implements Predicate<Entity> {
		@Override
		public boolean apply(Entity e) {
			if (!type.hasAnimalClass(e)) {
				return false;
			}

			return ((EntityAnimal) e).getGrowingAge() >= 0
					&& ((EntityAnimal) e).getHealth() > 0;
		}
	}

	private final int maxKills;
	private final AnimalyType type;
	private final HashSet<Entity> hitEntities = new HashSet<Entity>();
	private final EnumDyeColor color;
	private int cooldown;
	private int lastKills;

	public AttackEntityStrategy() {
		this(-1, AnimalyType.ANY, null);
	}

	public AttackEntityStrategy(int maxKills, AnimalyType type, EnumDyeColor color) {
		this.maxKills = maxKills;
		this.type = type;
		this.color = color;
	}

	@Override
	protected Predicate<Entity> entitiesToInteract(AIHelper helper) {
		if (maxKillsReached()) {
			return new Predicate<Entity>() {
				@Override
				public boolean apply(Entity var1) {
					return false;
				}
			};
		} else {
			Predicate<Entity> s = new KillableSelector();
			if (color != null) {
				s = new AndSelector(new ColorSelector(color), s);
			}
			return new AndSelector(s, new NotSelector(new OwnTameableSelector(
					helper.getMinecraft().thePlayer)));
		}
	}

	@Override
	protected Predicate<Entity> entitiesToFace(AIHelper helper) {
		return new OrSelector(super.entitiesToFace(helper), new ItemSelector(),
				new XPOrbSelector());
	}

	@Override
	protected boolean doInteractWithCurrent(Entity entityHit, AIHelper helper) {
		if (cooldown > 0) {
			cooldown--;
			return false;
		} else {
			helper.selectCurrentItem(new ItemFilter() {
				@Override
				public boolean matches(ItemStack itemStack) {
					return itemStack != null && itemStack.getItem() instanceof ItemSword;
				}
			});
			boolean interacted = super.doInteractWithCurrent(entityHit, helper);
			if (interacted) {
				cooldown = 5;
			}
			return interacted;
		}
	}

	@Override
	protected void doInteract(Entity entityHit, AIHelper helper) {
		hitEntities.add(entityHit);
		helper.overrideAttack();
	}

	public boolean maxKillsReached() {
		if (maxKills >= 0) {
			lastKills = countKills();
			if (lastKills >= maxKills) {
				return true;
			}
		}
		return false;
	}

	private int countKills() {
		int kills = 0;
		for (Entity e : hitEntities) {
			if (e instanceof EntityAnimal
					&& ((EntityAnimal) e).getHealth() <= 0) {
				kills++;
			}
		}
		return kills;
	}

	@Override
	public String getDescription(AIHelper helper) {
		if (maxKills < 0) {
			return "Gather meat";
		} else {
			return "Gather meat " + lastKills + "/" + maxKills;
		}
	}

	@Override
	public boolean hasFailed() {
		return maxKills >= 0 && !maxKillsReached();
	}

}
