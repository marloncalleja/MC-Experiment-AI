package marlon.minecraftai.settings;

public @interface ConstrainedBlockFloat {

	float min();

	float max();

	float defaultValue();

}
